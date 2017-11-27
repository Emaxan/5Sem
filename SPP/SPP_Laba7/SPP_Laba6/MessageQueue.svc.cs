using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Configuration;
using System.Data.SQLite;
using System.Linq;
using System.ServiceModel;

namespace SPP_Laba6
{
    [ServiceBehavior(InstanceContextMode = InstanceContextMode.Single)]
    public class MessageQueue : IMessageQueue, IDisposable
    {
        private const string QueueNames = "Queue_";
        private const string WorkerNames = "Worker_";
        private readonly object _locker = new object();
        private readonly List<Queue> _queues = new List<Queue>();
        private readonly List<Worker> _workers = new List<Worker>();
        private readonly SQLiteConnection _conn;
        private bool _disposed;

        private void SetQueues(NameValueCollection keys)
        {
            var i = 1;
            while(true)
            {
                if(!keys.AllKeys.Contains(QueueNames + i))
                {
                    break;
                }

                var priority = int.Parse(keys[QueueNames + i]);
                var queue = new Queue(priority, QueueNames + i);
                _queues.Add(queue);
                i++;
            }
        }

        private void SetWorkers(NameValueCollection keys)
        {
            var i = 1;
            while(true)
            {
                if(!keys.AllKeys.Contains(WorkerNames + i))
                {
                    break;
                }

                var values = keys[WorkerNames + i].Split(',');
                var queues = values.Select(value => _queues.Find(q => q.Priority == int.Parse(value))).
                    Where(val => val != null).
                    ToList();
                var worker = new Worker(queues, _locker);
                worker.Start();
                _workers.Add(worker);
                i++;
            }
        }

        public MessageQueue()
        {
            if(_disposed)
            {
                throw new ObjectDisposedException(GetType().Name);
            }

            var keys = ConfigurationManager.AppSettings;

            SetQueues(keys);
            SetWorkers(keys);

            _conn = new SQLiteConnection(ConfigurationManager.ConnectionStrings["SQLite"].ConnectionString);
            _conn.Open();
            var cmd = _conn.CreateCommand();
            cmd.CommandText = @"CREATE TABLE [MessageQueue]" +
                              "([id] integer PRIMARY KEY AUTOINCREMENT NOT NULL," +
                              "[message] char(5000) NOT NULL," +
                              "[parameters] char(5000) NOT NULL," +
                              "[priority] integer NOT NULL);";
            //cmd.CommandText = @"DROP TABLE [MessageQueue]";
            try
            {
                cmd.ExecuteNonQuery();
            }
            catch(SQLiteException ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        public void AddMassage(int priority, string message, string parameters)
        {
            if(_disposed)
            {
                throw new ObjectDisposedException(GetType().Name);
            }

            //lock(_locker)
            //{
            //    var msg = new Message(message, parameters);
            //    _queue.Enqueue(msg);
            //    Console.WriteLine($"Message added.\n{msg}\n\n");
            //}
        }

        public bool RemoveMessage(int priority)
        {
            if(_disposed)
            {
                throw new ObjectDisposedException(GetType().Name);
            }

            //lock(_locker)
            //{
            //    var msg = _queue.Dequeue();
            //    Console.WriteLine($"Message removed.\n{msg}\n\n");
            //    return true;
            //}
            return false;
        }

        public void Dump()
        {
            if(_disposed)
            {
                throw new ObjectDisposedException(GetType().Name);
            }

            lock(_locker)
            {
                var list = new List<IBaseJob>();
                var trans = _conn.BeginTransaction();
                foreach(var queue in _queues)
                {
                    while(queue.Peek() != null)
                    {
                        try
                        {
                            dynamic message = queue.Dequeue();

                            list.Add(message);
                            var cmd = _conn.CreateCommand();
                            cmd.Transaction = trans;
                            cmd.CommandText = "INSERT INTO MessageQueue(message, parameters, priority) " +
                                              "VALUES (@message, @parameters, @priority);";
                            cmd.Parameters.AddWithValue("@message", message.Msg);
                            cmd.Parameters.AddWithValue("@parameters", message.Parameters);
                            cmd.Parameters.AddWithValue("@priority", message.Priority);
                            cmd.ExecuteNonQuery();
                        }
                        catch(SQLiteException ex)
                        {
                            foreach(var message in list)
                            {
                                queue.Enqueue(message);
                            }

                            trans.Rollback();
                            Console.WriteLine(ex.Message);
                            return;
                        }
                    }
                }

                Console.WriteLine($"{list.Count} messages dumped.\n\n");
                list.Clear();
                trans.Commit();
            }
        }

        public void Restore()
        {
            if(_disposed)
            {
                throw new ObjectDisposedException(GetType().Name);
            }

            lock(_locker)
            {
                var list = new List<Message>();
                var trans = _conn.BeginTransaction();
                try
                {
                    var cmd = _conn.CreateCommand();
                    cmd.Transaction = trans;
                    cmd.CommandText = "SELECT * FROM MessageQueue";
                    var rdr = cmd.ExecuteReader();

                    while(rdr.Read())
                    {
                        var message = new Message(rdr.GetString(1), rdr.GetString(2), rdr.GetInt32(3));
                        list.Add(message);
                    }

                    rdr.Close();
                    cmd.CommandText = "DELETE FROM MessageQueue";
                    cmd.ExecuteNonQuery();
                }
                catch(SQLiteException ex)
                {
                    trans.Rollback();
                    Console.WriteLine(ex.Message);
                    return;
                }
                catch(Exception ex)
                {
                    trans.Rollback();
                    Console.WriteLine(ex.Message);
                    return;
                }

                list.Reverse();
                foreach(var message in list)
                {
                    var queue = _queues.Find(q => q.Priority == message.Priority);
                    if(queue == null)
                    {
                        continue;
                    }

                    queue.Enqueue(message);
                    Console.WriteLine($"Message restored.\n{message}\n\n");
                }

                list.Clear();
                trans.Commit();
            }
        }

        private void ReleaseUnmanagedResources()
        {
            foreach(var worker in _workers)
            {
                worker.Stop();
            }
        }

        private void Dispose(bool disposing)
        {
            _disposed = true;
            ReleaseUnmanagedResources();
            if(!disposing)
            {
                return;
            }

            lock(_locker)
            {
                _conn?.Dispose();
            }
        }

        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }

        ~MessageQueue()
        {
            Dispose(false);
        }
    }
}
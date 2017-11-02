using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data.SQLite;
using System.ServiceModel;

namespace SPP_Laba6
{
    [ServiceBehavior(InstanceContextMode = InstanceContextMode.Single)]
    public class MessageQueue : IMessageQueue, IDisposable
    {
        private readonly object _locker = new object();
        private readonly Queue _queue;
        private readonly Worker _worker;
        private readonly SQLiteConnection _conn;
        private bool _disposed;

        public MessageQueue()
        {
            if(_disposed)
            {
                throw new ObjectDisposedException(GetType().Name);
            }

            _queue = new Queue();
            _worker = new Worker(_queue, _locker);
            _worker.Start();

            _conn = new SQLiteConnection(ConfigurationManager.ConnectionStrings["SQLite"].ConnectionString);
            _conn.Open();
            var cmd = _conn.CreateCommand();
            cmd.CommandText = @"CREATE TABLE [MessageQueue]" +
                              "([id] integer PRIMARY KEY AUTOINCREMENT NOT NULL," +
                              "[message] char(5000) NOT NULL," +
                              "[parameters] char(5000) NOT NULL);";
            try
            {
                cmd.ExecuteNonQuery();
            }
            catch(SQLiteException ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        public void AddMassage(string message, string parameters)
        {
            if(_disposed)
            {
                throw new ObjectDisposedException(GetType().Name);
            }

            lock(_locker)
            {
                var msg = new Message(message, parameters);
                _queue.Enqueue(msg);
                Console.WriteLine($"Message added.\n{msg}");
            }
        }

        public bool RemoveMessage()
        {
            if(_disposed)
            {
                throw new ObjectDisposedException(GetType().Name);
            }

            lock(_locker)
            {
                var msg = _queue.Dequeue();
                Console.WriteLine($"Message removed.\n{msg}");
                return true;
            }
        }

        public void Dump()
        {
            if(_disposed)
            {
                throw new ObjectDisposedException(GetType().Name);
            }

            lock(_locker)
            {
                var list = new List<Message>();
                var trans = _conn.BeginTransaction();
                while(_queue.Peek() != null)
                {
                    try
                    {
                        var m = _queue.Dequeue();
                        list.Add(m);
                        var cmd = _conn.CreateCommand();
                        cmd.Transaction = trans;
                        cmd.CommandText = "INSERT INTO MessageQueue(message, parameters) " +
                                          "VALUES (@message, @parameters);";
                        cmd.Parameters.AddWithValue("@message", m.Msg);
                        cmd.Parameters.AddWithValue("@parameters", m.Parameters);
                        cmd.ExecuteNonQuery();
                    }
                    catch(SQLiteException ex)
                    {
                        foreach(var message in list)
                        {
                            _queue.Enqueue(message);
                        }

                        trans.Rollback();
                        Console.WriteLine(ex.Message);
                        return;
                    }
                }
                Console.WriteLine($"{list.Count} messages dumped.");
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
                        var message = new Message(rdr.GetString(1), rdr.GetString(2));
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

                foreach(var message in list)
                {
                    _queue.Enqueue(message);
                    Console.WriteLine($"Message restored.\n{message}");
                }
                list.Clear();
                trans.Commit();
            }
        }

        private void ReleaseUnmanagedResources()
        {
            _worker.Stop();
        }

        private void Dispose(bool disposing)
        {
            _disposed = true;
            ReleaseUnmanagedResources();
            if(disposing)
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
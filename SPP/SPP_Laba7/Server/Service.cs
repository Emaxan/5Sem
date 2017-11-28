using System;
using System.ServiceModel;
using Service_Contract;
using System.Collections.Generic;
using System.Threading;
using System.IO;
using System.Data.SQLite;
using System.Data.Common;
using System.Configuration;
using System.Linq;
using StackExchange.Redis;

namespace Server
{
    [ServiceBehavior(InstanceContextMode = InstanceContextMode.Single)]
    public class Service : IContract
    {       
        private const string DbName = "QueryDump.db";
        private const string DbTableName = "dump";
        private const string DbFieldName = "obj";
        private const string DbFieldQname = "qname";
        private const string QStartNames = "Query";
        private const string WStartNames = "Worker";

        private readonly object _loker = new object();
        
        private List<string> Query { get; set; }
        private string FileForDump { get; set; }
        private List<Query> Querys { get; set; }
        private List<Worker> Workers { get; set; }
        private ConnectionMultiplexer Redis { get; set; }

        public Service()
        {
            Query = new List<string>();
            InitConnectToDB();

            Querys = new List<Query>();
            Workers = new List<Worker>();

            Redis = ConnectionMultiplexer.Connect("localhost");

            string[] keys = ConfigurationManager.AppSettings.AllKeys;

            SetQuerys(keys);
            SetWorkers(keys); 

            var time = Convert.ToInt32(ConfigurationManager.AppSettings["Dump"]);

            new Timer(DumpQuery, null, time, time);
        }

        void SetWorkerQuerys(string qNames, Worker newWorker)
        {
            foreach(var query in Querys)
            {
                if (qNames.IndexOf(query.RedisKey) != -1)
                {
                    newWorker.Queryes.Add(query);
                }
            }
        }

        void SetWorkers(string[] keys)
        {
            foreach(var key in keys)
            {
                if(key.IndexOf(WStartNames) == -1)
                {
                    continue;
                }

                var qNames = ConfigurationManager.AppSettings[key];
                var newWorker = new Worker(Redis, this._loker)
                                   {
                                       Name = key
                                   };
                SetWorkerQuerys(qNames, newWorker);
                Workers.Add(newWorker);
            }
        }

        void SetQuerys(string[] keys)
        {
            foreach(var key in keys)
            {
                if(key.IndexOf(QStartNames) == -1)
                {
                    continue;
                }

                var priority = Convert.ToInt32(ConfigurationManager.AppSettings[key]);
                var newQuery = new Query(key, priority);
                Querys.Add(newQuery);
            }
        }

        private void InitConnectToDB()
        {
            lock (_loker)
            {
                FileForDump = GetDumpFile();
                if (FileForDump != null)
                {
                   ConnectToDB();
                }
            }
          
        }

        private void ConnectToDB()
        {
            using (var connection = new SQLiteConnection($"Data Source={FileForDump}"))
            {
                using (var command = new SQLiteCommand($"SELECT name FROM sqlite_master WHERE type='table' AND name='{DbTableName}'", connection))
                {
                    command.Connection.Open();
                    var reader = command.ExecuteReader();

                    if (!reader.HasRows)
                    {
                        CreateDumpTable(connection);
                        Console.WriteLine("Dump table created");
                    }
                    else
                    {
                        Console.WriteLine("Table already exist");
                    }
                    reader.Close();
                }
            }
            Console.WriteLine("Connected to DB");
        }

        private void CreateDumpTable(SQLiteConnection connection)
        {
            var command =
                new
                    SQLiteCommand($"CREATE TABLE `{DbTableName}` (id INTEGER PRIMARY KEY, {DbFieldName} TEXT, {DbFieldQname} TEXT)")
                    {
                        Connection = connection
                    };
            command.ExecuteNonQuery();
            Console.WriteLine("Table in DB created successfully");
        }

        private string GetDumpFile()
        {
            if (!File.Exists(DbName))
            {
                var dbName = CreateDBFile(DbName);
                Console.WriteLine("DB created");
                return dbName;
            }
            else
            {
                return DbName;
            }
        }

        private string CreateDBFile(string name)
        {
            SQLiteConnection.CreateFile(name);
            if (File.Exists(name))
            {
                Console.WriteLine("DB for dump created successfully");
                return name;
            }

            Console.WriteLine("Error in creating DB for dump");
            return null;
        }

        private bool CheckQuerys(string qName)
        {
            return Querys.Any(q => q.RedisKey == qName);
        }

        private string GetQName(string obj)
        {
            return (from t in Querys where obj.IndexOf(t.RedisKey) != -1 select t.RedisKey).FirstOrDefault();
        }

        private string GetQMessage(string Obj)
        {
            return (from query in Querys where Obj.IndexOf(query.RedisKey) != -1 select Obj.Replace(query.RedisKey, "")).FirstOrDefault();
        }

        public void AddMessage(string obj)
        {
            lock(_loker)
            {
                var qName = GetQName(obj);
                var qMessage = GetQMessage(obj);
                if (qName == null) return;
                if (qMessage == null) return;
                if(!CheckQuerys(qName))
                {
                    return;
                }

                var db = Redis.GetDatabase();
                db.ListRightPush(qName, qMessage);
                Console.WriteLine("Receive:" + obj);
            }
        }

        public bool RemoveMessage(string obj)
        {
            lock (_loker)
            {
                var qName = GetQName(obj);
                var qMessage = GetQMessage(obj);
                if (qName == null) return false;
                if (qMessage == null) return false;
                var db = Redis.GetDatabase();
                var ret = db.ListRemove(qName, qMessage);
                if (ret == 0) return false; 
                Console.WriteLine("Removed: " + obj);
                return true;
            }
        }

        public void ClearDumps()
        {
            using (var connection = new SQLiteConnection($"Data Source={FileForDump}"))
            {
                using (var command = new SQLiteCommand($"DELETE FROM {DbTableName} WHERE id>0", connection))
                {
                    command.Connection.Open();
                    command.ExecuteNonQuery();
                }
            }
        }

        public void Dump()
        {
            using (var connection = new SQLiteConnection($"Data Source={FileForDump}"))
            {
                using (var command = new SQLiteCommand(connection))
                {
                    command.Connection.Open();
                    var db = Redis.GetDatabase();
                    var K = 0;
                    foreach(var query in Querys)
                    {
                        string Message = null;
                        while ((Message = db.ListGetByIndex(query.RedisKey, K)) != null)
                        {
                            command.CommandText = $"INSERT INTO {DbTableName} ({DbFieldName},{DbFieldQname}) VALUES ('{Message}','{query.RedisKey}')";
                            command.ExecuteNonQuery();
                            K++;
                        }
                        
                    }
                }
                
            }
        }

        public void DumpQuery(object obj)
        {
            lock (_loker)
            {
                ClearDumps();
                Dump();
                Console.WriteLine("Dumped");
            }

        }

        private void RestoreMessages(SQLiteDataReader reader)
        {
            var db = Redis.GetDatabase();
            foreach (DbDataRecord record in reader)
            {
                var qMessage = record[DbFieldName].ToString();
                var qKey = record[DbFieldQname].ToString();
                RedisValue[] strs = db.ListRange(qKey);

                var isExists = false;

                foreach(string str in strs)
                {
                    if (qMessage == str) isExists = true;
                }

                if (!isExists)
                {
                    db.ListRightPush(qKey, qMessage);
                }
            }
            
        }

        public void Restore()
        {
            using (var connection = new SQLiteConnection($"Data Source={FileForDump}"))
            {
                using (var command = new SQLiteCommand($"SELECT {DbFieldName},{DbFieldQname} FROM {DbTableName} ", connection))
                {
                    command.Connection.Open();
                    var reader = command.ExecuteReader();
                    if (reader.HasRows)
                        RestoreMessages(reader);
                    reader.Close();
                }
            }
        }

        public void RestoreQuery()
        {
            lock (_loker)
            {
                Restore();
                Console.WriteLine("Restored");
            }
        }
    }
}

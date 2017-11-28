using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.IO;
using System.Runtime.Serialization.Json;
using Service_Contract;
using System.Reflection;
using StackExchange.Redis;


namespace Server
{
    class Worker
    {
        public string Name { get; set; }
        public readonly object Loker;
        public List<Query> Queryes { get; }
        public ConnectionMultiplexer Redis { get; set; }

        public Worker(ConnectionMultiplexer redis, object loker)
        {
            Queryes = new List<Query>();
            Redis = redis;
            Loker = loker;
            new Timer(DoWork, null, 0, 5000);
        }

        private Type GetType(string name)
        {
            var assemblyes = Directory.EnumerateFiles(AppDomain.CurrentDomain.BaseDirectory, "*.*", SearchOption.AllDirectories)
                .Where(s => s.EndsWith(".dll") || s.EndsWith(".exe"));
            return assemblyes.Select(Assembly.LoadFile).Select(asm => asm.GetType(name)).FirstOrDefault(type => type != null);
        }

        private object DeserializeObj(string jsonStr, Type type)
        {
            var jsonFormatter = new DataContractJsonSerializer(type);
            object obj;
            using (var stream = new MemoryStream(Encoding.Default.GetBytes(jsonStr)))
            {
                obj = jsonFormatter.ReadObject(stream);
            }

            return obj;
        }

        private void PerformWork(string messageStr, int priority)
        {
            try
            {
                var message = (QMessage)DeserializeObj(messageStr, typeof(QMessage));
                var type = GetType(message.ClassName);
                if(type == null)
                {
                    return;
                }

                var desObj = DeserializeObj(message.Obj, type);
                if(!(desObj is IBaseJob))
                {
                    return;
                }

                Console.WriteLine($"Worker {Name} perform work with priority {priority}:");
                var job = desObj as IBaseJob;
                job.Perform();
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        private void DoWork(object obj)
        {
            lock (Loker)
            {
                var db = Redis.GetDatabase();
                foreach(var query in Queryes)
                {
                    for (var j = 0; j<query.Priority;j++)
                    {
                        string message = db.ListLeftPop(query.RedisKey);
                        if (message != null) PerformWork(message, query.Priority);
                    }
                }
            }
        }
    }
}

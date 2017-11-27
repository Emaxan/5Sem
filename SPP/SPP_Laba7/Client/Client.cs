using System;
using System.Text;
using System.Threading;
using Client.MessageQueue;
using Newtonsoft.Json;

namespace Client
{
    public static class Client
    {
        public static void Main(string[] args)
        {
            IMessageQueue client = new MessageQueueClient();

            for(var i = 0; i < 5; i++)
            {
                var s1 = RandomString(5);
                Thread.Sleep(1);
                var s2 = RandomString(5);
                Thread.Sleep(1);
                var s3 = RandomString(5);
                Thread.Sleep(1);
                var s4 = RandomString(5);
                var arr = new object[]
                          {
                              s1,
                              s2,
                              s3,
                              s4
                          };
                client.AddMassage(RandomString(10), JsonConvert.SerializeObject(arr));
                Thread.Sleep(1000);
            }

            client.RemoveMessage();
            while(true)
            {
                var c = Console.ReadKey();
                switch(c.Key)
                {
                    case ConsoleKey.D1:
                        client.Dump();
                        break;
                    case ConsoleKey.D2:
                        client.Restore();
                        break;
                    case ConsoleKey.D0:
                        return;
                }
            }
        }

        private static string RandomString(int size)
        {
            var random = new Random((int)DateTime.Now.Ticks + new Random((int)DateTime.Now.Ticks).Next());
            var builder = new StringBuilder();
            for(var i = 0; i < size; i++)
            {
                var ch = Convert.ToChar(Convert.ToInt32(Math.Floor(26 * random.NextDouble() + 65)));
                builder.Append(ch);
            }

            return builder.ToString();
        }
    }
}
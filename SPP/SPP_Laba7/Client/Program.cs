using System;
using System.ServiceModel;
using Service_Contract;
using System.Text;
using System.IO;
using System.Runtime.Serialization.Json;
using System.Collections.Generic;

namespace Client
{
    class Program
    {
       
        static void Main(string[] args)
        {
            try
            {
                Console.Title = "CLIENT";
                var address = new Uri("http://localhost:64133/IContract");
                var binding = new BasicHttpBinding();

                var endpoint = new EndpointAddress(address);

                ChannelFactory<IContract> factory = new ChannelFactory<IContract>(binding, endpoint);

                var channel = factory.CreateChannel();

                QClass[] arr = new QClass[10];
                List<string> strs = new List<string>();
                var rnd = new Random();

                for (var i = 0; i < arr.Length; i++)
                {
                    arr[i] = new QClass(i, RandomString(3), RandomString(3));
                    var serializedObj = SerializeObj(arr[i], arr[i].GetType());
                    var msg = new QMessage
                    {
                        Obj = serializedObj
                    };
                    var t = arr[i].GetType();
                    msg.ClassName = t.Namespace + "." + t.Name;
                    strs.Add("Query_" + Convert.ToString(rnd.Next(3) + 1) + SerializeObj(msg, msg.GetType()));
                    channel.AddMessage(strs[i]);
                }
                channel.RemoveMessage(strs[9]);

                channel.RestoreQuery();



            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            finally
            {
                Console.ReadKey();
            }
                    
        }

        private static string RandomString(int size)
        {
            var random = new Random((int)DateTime.Now.Ticks);
            var builder = new StringBuilder();
            for (var i = 0; i < size; i++)
            {
                var ch = Convert.ToChar(Convert.ToInt32(Math.Floor(26 * random.NextDouble() + 65)));
                builder.Append(ch);
            }

            return builder.ToString();
        }

        private static string SerializeObj(object obj, Type type)
        {
            using (var stream = new MemoryStream())
            {
                var jsonFormatter = new DataContractJsonSerializer(type);
                jsonFormatter.WriteObject(stream, obj);
                byte[] bytes = stream.ToArray();
                var str = Encoding.Default.GetString(bytes);
                return str;
            }
        }
    }
}

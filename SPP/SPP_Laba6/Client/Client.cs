using System;
using Client.MessageQueue;
using Newtonsoft.Json;

namespace Client
{
    public static class Client
    {
        public static void Main(string[] args)
        {
            IMessageQueue client = new MessageQueueClient();
            object[] arr =
            {
                "string",
                1,
                1.5,
                true
            };
            var id = client.AddMassage("It's my first message", JsonConvert.SerializeObject(arr));
            client.RemoveMessage(id);
            Console.ReadKey();
        }
    }
}
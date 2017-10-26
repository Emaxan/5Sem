using System;

namespace SPP_Laba6
{
    public class MessageQueue : IMessageQueue
    {
        private readonly Worker _worker = new Worker();
        
        public Guid AddMassage(string message, string parameters)
        {
            var g = Guid.NewGuid();
            Console.WriteLine("Message added. " + message + " " + parameters + " " + g);
            return g;
        }

        public bool RemoveMessage(Guid guid)
        {
            Console.WriteLine("Message removed. " + guid);
            return true;
        }
    }
}
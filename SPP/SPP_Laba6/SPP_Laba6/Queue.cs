using System.Collections.Generic;

namespace SPP_Laba6
{
    public class Queue
    {
        private readonly Queue<Message> _queue = new Queue<Message>();

        public void Add(Message message)
        {
            _queue.Enqueue(message);
        }
    }
}
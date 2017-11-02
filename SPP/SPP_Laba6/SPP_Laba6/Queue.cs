using System.Collections.Generic;

namespace SPP_Laba6
{
    public class Queue
    {
        private readonly Queue<Message> _queue = new Queue<Message>();

        public void Enqueue(Message message)
        {
            _queue.Enqueue(message);
        }

        public Message Dequeue()
        {
            return _queue.Dequeue();
        }

        public Message Peek()
        {
            return _queue.Count == 0 ? null : _queue.Peek();
        }
    }
}
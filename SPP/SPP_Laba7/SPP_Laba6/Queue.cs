using System.Collections.Generic;

namespace SPP_Laba6
{
    public class Queue
    {
        private readonly Queue<IBaseJob> _queue = new Queue<IBaseJob>();
        public int Priority { get; }
        public string Name { get; }

        public Queue(int priority, string name)
        {
            Priority = priority;
            Name = name;
        }

        public void Enqueue(IBaseJob message)
        {
            _queue.Enqueue(message);
        }

        public IBaseJob Dequeue()
        {
            return _queue.Dequeue();
        }

        public IBaseJob Peek()
        {
            return _queue.Count == 0 ? null : _queue.Peek();
        }
    }
}
using System;
using System.Timers;
using Timer = System.Timers.Timer;

namespace SPP_Laba6
{
    public class Worker
    {
        private readonly object _locker;
        private readonly Timer _timer = new Timer();

        private Queue Queue { get; }

        public Worker(Queue queue, Object locker)
        {
            _locker = locker;
            Queue = queue;
        }

        public void Start()
        {
            _timer.Elapsed += OnTimedEvent;
            _timer.Interval = int.Parse(System.Configuration.ConfigurationManager.AppSettings["WorkerTime"]);
            _timer.Enabled = true;
            _timer.Start();
        }

        private void OnTimedEvent(object sender, ElapsedEventArgs e)
        {
            lock(_locker)
            {
                if(Queue.Peek() != null)
                {
                    var m = Queue.Dequeue();
                    Console.WriteLine(Message.Perform(m));
                }
                else
                {
                    Console.WriteLine("Do nothing.\n\n");
                }
            }
        }

        public void Stop()
        {
            _timer.Enabled = false;
        }
    }
}
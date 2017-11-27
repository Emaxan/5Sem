using System;
using System.Collections.Generic;
using System.Timers;
using Timer = System.Timers.Timer;

namespace SPP_Laba6
{
    public class Worker
    {
        private readonly object _locker;
        private readonly Timer _timer = new Timer();

        private List<Queue> Queues { get; }

        public Worker(List<Queue> queues, object locker)
        {
            _locker = locker;
            Queues = queues;
        }

        public void Start()
        {
            _timer.Elapsed += OnTimedEvent;
            _timer.Interval = int.Parse(System.Configuration.ConfigurationManager.AppSettings["WorkerTime"]);
            _timer.Enabled = true;
            _timer.Start();
        }

        private void OnTimedEvent(object sender, ElapsedEventArgs e) // TODO
        {
            lock(_locker)
            {
                if(Queues[0].Peek() != null)
                {
                    var m = Queues[0].Dequeue();
                    Console.WriteLine(m.Perform());
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
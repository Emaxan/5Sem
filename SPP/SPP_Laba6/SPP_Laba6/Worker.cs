using System;
using System.Timers;
using Timer = System.Timers.Timer;

namespace SPP_Laba6
{
    public class Worker
    {
        private readonly Timer _timer = new Timer();

        public void Start()
        {
            _timer.Elapsed += OnTimedEvent;
            _timer.Interval = 5000;
            _timer.Enabled = true;
            _timer.Start();
        }

        private void OnTimedEvent(object sender, ElapsedEventArgs e)
        {
            Console.WriteLine("Timer event.");
        }

        public void Stop()
        {
            _timer.Enabled = false;
        }
    }
}
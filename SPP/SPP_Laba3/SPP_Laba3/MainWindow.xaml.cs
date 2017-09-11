using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Threading;
using System.Windows;
using SPP_CustomThreadPool;

namespace SPP_Laba3
{
	public partial class MainWindow
	{
		private readonly List<ClientHandle> _handles = new List<ClientHandle>();
		private readonly ConcurrentAccumulator _accumulator;
        private readonly CustomThreadPool _pool;
	    private const int MaxThreadCount = 1000;
	    private const int MinThreadCount = 20;

        public MainWindow()
		{
			InitializeComponent();
            _pool = CustomThreadPool.GetInstance(MinThreadCount, MaxThreadCount);
			_accumulator = new ConcurrentAccumulator(1000, 6000, OnFlush);
			_accumulator.OnObjectCountChange += UpdateObjectCount;
			LThreads.Content = $"Thread count: {_handles.Count}";
		}
		

		private void OnFlush(List<object> items)
		{
			try
			{
				Dispatcher.Invoke(
					() =>
					{
						LbLog.Items.Add("Flushing buffer");
					});
			}
			catch(OperationCanceledException)
			{
			}
		}

		private void UpdateObjectCount(object sender, int e)
		{
			Dispatcher.Invoke(
				() =>
				{
					LObjects.Content = $"Objects in buffer: {e}";
				});
		}

		private bool TaskFunction()
		{
			while(true)
			{
				_accumulator.Add(new object());
				Thread.Sleep(100);
			}
		}

		private void BAdd_Click(object sender, RoutedEventArgs e)
		{
		    if(_handles.Count == MaxThreadCount)
		    {
		        return;
		    }

			var added = false;
			var thread = _pool.QueueUserTask(TaskFunction);

			try
			{
				_handles.Add(thread);
				added = true;
			}
			catch(Exception exception)
			{
				LbLog.Items.Add(exception.Message);
				_handles.Remove(thread);
			}

			if(added)
			{
				LThreads.Content = $"Thread count: {_handles.Count}";
			}
		}

		private void BRemove_Click(object sender, RoutedEventArgs e)
		{
			if(_handles.Count == 0)
			{
				return;
			}

			var thread = _handles.First();
            CustomThreadPool.CancelUserTask(thread);
			_handles.Remove(thread);
			LThreads.Content = $"Thread count: {_handles.Count}";
		}

		private void OnWindowClosing(object sender, CancelEventArgs e)
		{
			foreach(var t in _handles)
			{
				CustomThreadPool.CancelUserTask(t);
			}
            _pool.Dispose();
			_accumulator.Dispose();
		}
	}
}

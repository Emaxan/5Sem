using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using System.Windows;

namespace SPP_Laba3
{
	public partial class MainWindow
	{
		private readonly List<Thread> _threads = new List<Thread>();
		private readonly Dictionary<Thread, bool> _threadOff = new Dictionary<Thread, bool>();
		private readonly ConcurrentAccumulator _accumulator;


		public MainWindow()
		{
			InitializeComponent();
			_accumulator = new ConcurrentAccumulator(1000, 6000, OnFlush);
			_accumulator.OnObjectCountChange += UpdateObjectCount;
			LThreads.Content = $"Thread count: {_threads.Count}";
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
			catch(OperationCanceledException ex)
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

		private void TaskFunction(object arg)
		{
			while(true)
			{
				if(_threadOff[(Thread)arg])
				{
					_accumulator.Add(new object());
				}
				else
				{
					break;
				}
			}
		}

		private void BAdd_Click(object sender, RoutedEventArgs e)
		{
			var added = false;
			var thread = new Thread(TaskFunction);

			try
			{
				_threads.Add(thread);
				_threadOff.Add(thread, true);
				thread.Start(thread);
				added = true;
			}
			catch(Exception exception)
			{
				LbLog.Items.Add(exception.Message);
				_threads.Remove(thread);
				_threadOff.Remove(thread);
			}

			if(added)
			{
				LThreads.Content = $"Thread count: {_threads.Count}";
			}
		}

		private void BRemove_Click(object sender, RoutedEventArgs e)
		{
			if(_threads.Count == 0)
			{
				return;
			}

			var thread = _threads.First();
			_threadOff[thread] = false;
			_threads.Remove(thread);
			LThreads.Content = $"Thread count: {_threads.Count}";
			//thread.Join();
		}

		private void OnWindowClosing(object sender, CancelEventArgs e)
		{
			foreach(var t in _threads)
			{
				t.Abort();
			}
			_accumulator.Dispose();
		}
	}
}

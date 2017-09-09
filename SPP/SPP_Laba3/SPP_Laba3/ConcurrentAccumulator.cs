using System;
using System.Collections.Generic;
using System.Threading;

namespace SPP_Laba3
{
	public class ConcurrentAccumulator : IDisposable
	{
		private readonly List<object> _buffer = new List<object>();
		private readonly int _maxObjectsCount;
		private readonly int _timeSpan;
		private readonly FlushCallback _onFlush;
		private bool _timerOn;
		
		public event EventHandler<int> OnObjectCountChange;
		public delegate void FlushCallback(List<object> items);


		public ConcurrentAccumulator(int maxObjectsCount, int timeSpan, FlushCallback callback)
		{
			_maxObjectsCount = maxObjectsCount;
			_timeSpan = timeSpan;
			var timer = new Thread(TimerWork);
			_onFlush = callback;
			_timerOn = true;
			timer.Start();
		}


		private void TimerWork(object state)
		{
			while(_timerOn)
			{
				lock(_buffer)
				{
					_onFlush(_buffer);
					_buffer.Clear();
				}
				OnObjectCountChange?.Invoke(this, _buffer.Count);
				Thread.Sleep(_timeSpan);
			}
		}

		public void Add(object item)
		{
			lock(_buffer)
			{
				_buffer.Add(item);
				if(_buffer.Count == _maxObjectsCount)
				{
					_onFlush(_buffer);
					_buffer.Clear();
				}
			}
			OnObjectCountChange?.Invoke(this, _buffer.Count);
			Thread.Sleep(100);
		}

		#region IDisposable pattern

		private void Dispose(bool disposing)
		{
			if (disposing)
			{
				_timerOn = false;
			}
		}

		public void Dispose()
		{
			Dispose(true);
			GC.SuppressFinalize(this);
		}

		~ConcurrentAccumulator()
		{
			Dispose(false);
		}

		#endregion
	}
}

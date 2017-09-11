using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;

namespace SPP_Laba3
{
	public class ConcurrentAccumulator : IDisposable
	{
		private readonly List<object> _buffer = new List<object>();
		private readonly int _maxObjectsCount;
		private readonly int _timeSpan;
		private readonly FlushCallback _onFlush;
		private bool _timerOn;
	    private bool _disposed = false;
		
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
			while(_timerOn && !_disposed)
			{
				Thread.Sleep(_timeSpan);
				List<object> buf;
				lock(_buffer)
				{
					buf = _buffer;
					Task.Run(() => _onFlush(buf));
					_buffer.Clear();
				}
			    if(!_disposed)
			    {
			        OnObjectCountChange?.Invoke(this, _buffer.Count);
			    }
			}
		}

		public void Add(object item)
		{
			List<object> buf;
			lock(_buffer)
			{
				_buffer.Add(item);
				buf = _buffer;
				if(_buffer.Count == _maxObjectsCount)
				{
					Task.Run(() => _onFlush(buf));
					_buffer.Clear();
				}
			}
			OnObjectCountChange?.Invoke(this, _buffer.Count);
		}

		#region IDisposable pattern

		private void Dispose(bool disposing)
		{
		    if(!disposing)
		    {
		        return;
		    }

		    _disposed = true;
		    _timerOn = false;
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

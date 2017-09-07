using System;
using System.Threading;

namespace SPP_CustomThreadPool
{
	internal class TaskItem // running items in the pool - TaskHandle gets a thread to execute it 
	{
		public Thread Handler;
		public DateTime StartTime = DateTime.MaxValue;
		public TaskHandle TaskHandle;
		public TaskState TaskState = TaskState.NotStarted;
	}
}

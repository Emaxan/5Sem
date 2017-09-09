using System;
using System.Threading;

namespace SPP_CustomThreadPool
{
	internal class TaskItem
	{
		public Thread Handler;
		public DateTime StartTime = DateTime.MaxValue;
		public TaskHandle TaskHandle;
		public TaskState TaskState = TaskState.NotStarted;
	}
}

using System;

namespace SPP_CustomThreadPool
{
	internal class TaskHandle
	{
		public Action<TaskStatus> Callback;
		public UserTask Task;
		public ClientHandle Token;
	}
}

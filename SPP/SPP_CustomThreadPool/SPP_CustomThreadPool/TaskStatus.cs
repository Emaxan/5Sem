using System;

namespace SPP_CustomThreadPool
{
	public class TaskStatus
	{
		public int Pid;
		public Exception InnerException;
		public bool Success = true;
	}
}

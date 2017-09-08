using System;

namespace SPP_CustomThreadPool
{
	public class TaskStatus
	{
	    public int Number;
	    public Guid Id;
		public Exception InnerException;
		public bool Success = true;
	}
}

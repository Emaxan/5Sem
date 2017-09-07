using System;

namespace SPP_CustomThreadPool
{
	internal class TaskHandle // Item in waiting queue
	{
		public Action<TaskStatus> Callback; // optional - in case user want's a notification of completion
		public UserTask Task; // the item to be queued - supplied by the caller
		public ClientHandle Token; // generate this everytime an usertask is queued and return to the caller as a reference. 
	}
}

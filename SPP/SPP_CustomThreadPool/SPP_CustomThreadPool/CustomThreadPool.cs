using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;

namespace SPP_CustomThreadPool
{
	public delegate void UserTask();

	public class CustomThreadPool
	{
		private List<TaskItem> _pool;
		private Queue<TaskHandle> _readyQueue;
		private Thread _taskScheduler;

		private void InitializeThreadPool()
		{
			_readyQueue = new Queue<TaskHandle>();
			_pool = new List<TaskItem>();

			InitPoolWithMinCapacity(); // initialize Pool with Minimum capacity - that much thread must be kept ready

			var lastCleanup = DateTime.Now; // monitor this time for next cleaning activity

			_taskScheduler = new Thread(
									() =>
									{
										do
										{
											lock(_syncLock) // obtaining lock for ReadyQueue
											{
												while(_readyQueue.Count > 0 && _readyQueue.Peek().Task == null)
													_readyQueue.Dequeue(); // remove canceled item/s - canceled item will have it's task set to null

												var itemCount = _readyQueue.Count;
												for(var i = 0; i < itemCount; i++)
												{
													var readyItem = _readyQueue.Peek(); // the Top item of queue
													var added = false;
													lock(_criticalLock) // lock for the Pool
													{
														foreach(var ti in _pool) // while reading the pool another thread should not add/remove to that pool
															lock(ti) // locking item
															{
																if(ti.TaskState != TaskState.Completed)
																	continue; // if in the Pool task state is completed then a different task can be handed over to that thread
																ti.TaskHandle = readyItem;
																ti.TaskState = TaskState.NotStarted;
																added = true;
																_readyQueue.Dequeue();
																break;
															}

														if(!added && _pool.Count < Max)
														{ // if all threads in pool are busy and the count is still less than the Max
															// limit set then create a new thread and add that to pool
															var ti = new TaskItem
																	{
																		TaskState = TaskState.NotStarted,
																		TaskHandle = readyItem
																	};
															// add a new TaskItem in the pool
															AddTaskToPool(ti);
															added = true;
															_readyQueue.Dequeue();
														}
													}
													if(!added)
														break; // It's already crowded so try after sometime
												}
											}
											if(DateTime.Now - lastCleanup > TimeSpan.FromMilliseconds(CleanupInterval))
												// It's long time - so try to cleanup Pool once.
											{
												CleanupPool();
												lastCleanup = DateTime.Now;
											}
											else
											{
												Thread.Yield(); // either of these two can work - the combination is also fine for our demo. 
												Thread.Sleep(SchedulingInterval); // Don't run madly in a loop - wait for sometime for things to change.
												// the wait should be minimal - close to zero
											}
										}
										while(true);
									})
								{
									Priority = ThreadPriority.AboveNormal
								};
			_taskScheduler.Start();
		}

		private void InitPoolWithMinCapacity()
		{
			for(var i = 0; i <= Min; i++)
			{
				var ti = new TaskItem
						{
							TaskState = TaskState.NotStarted,
							TaskHandle = new TaskHandle
										{
											Task = () =>
													{
													},
											Callback = taskStatus =>
														{
														},
											Token = new ClientHandle
													{
														Id = Guid.NewGuid()
													}
										}
						};
				AddTaskToPool(ti);
			}
		}

		private void AddTaskToPool(TaskItem taskItem)
		{
			taskItem.Handler = new Thread(
				() =>
				{
					do
					{
						var enter = false;
						lock(taskItem)
							// the taskState of taskItem is exposed to scheduler
							// thread also so access that always with this lock
						{ // Only two thread can contend for this [cancel and executing
							// thread as taskItem itself is mapped to a dedicated thread]
							// if aborted then allow it to exit the loop so that it can complete and free-up thread resource.
							// this state means it has been removed from Pool already.
							if(taskItem.TaskState == TaskState.Aborted)
								break;

							if(taskItem.TaskState == TaskState.NotStarted)
							{
								taskItem.TaskState = TaskState.Processing;
								taskItem.StartTime = DateTime.Now;
								enter = true;
							}
						}
						if(enter)
						{
							var taskStatus = new TaskStatus();
							try
							{
								taskItem.TaskHandle.Task.Invoke(); // execute the UserTask
								taskStatus.Success = true;
							}
							catch(Exception ex)
							{
								taskStatus.Success = false;
								taskStatus.InnerException = ex;
							}
							lock(taskItem) // Only two thread can contend for this [cancel and executing
								// thread as taskItem itself is mapped to a dedicated thread]
							{
								if(taskItem.TaskHandle.Callback != null && taskItem.TaskState != TaskState.Aborted)
									try
									{
										taskItem.TaskState = TaskState.Completed;
										taskItem.StartTime = DateTime.MaxValue;

										taskItem.TaskHandle.Callback(taskStatus); // notify callback with task-status
									}
									catch
									{
										// suppress exception
									}
							}
						}
						// give other thread a chance to execute as it's current execution completed already
						Thread.Yield();
						Thread.Sleep(MinWait); //TODO: need to see if Sleep is required here
					}
					while(true); // it's a continuous loop until task gets abort request
				});
			taskItem.Handler.Start();
			lock(_criticalLock) // always use this lock for Pool
			{
				_pool.Add(taskItem);
			}
		}

		private void CleanupPool()
		{
			List<TaskItem> filteredTask;
			lock(_criticalLock) // acquiring lock for Pool
			{
				filteredTask = _pool.Where(
										ti => ti.TaskHandle.Token.IsSimpleTask && DateTime.Now - ti.StartTime > TimeSpan.FromMilliseconds(MaxWait))
									.ToList();
			}
			foreach(var taskItem in filteredTask)
				CancelUserTask(taskItem.TaskHandle.Token);
			lock(_criticalLock)
			{
				filteredTask = _pool.Where(ti => ti.TaskState == TaskState.Aborted).ToList();
				foreach(var taskItem in filteredTask) // clean all aborted thread
				{
					try
					{
						taskItem.Handler.Abort(); // does not work
						taskItem.Handler.Priority = ThreadPriority.Lowest;
						taskItem.Handler.IsBackground = true;
					}
					catch
					{
						// ignored
					}
					_pool.Remove(taskItem);
				}
				var total = _pool.Count;
				if(total >= Min) // clean waiting threads over minimum limit
				{
					filteredTask = _pool.Where(ti => ti.TaskState == TaskState.Completed).ToList();
					foreach(var taskItem in filteredTask)
					{
						taskItem.Handler.Priority = ThreadPriority.AboveNormal;
						taskItem.TaskState = TaskState.Aborted;
						_pool.Remove(taskItem);
						total--;
						if(total == Min)
							break;
					}
				}
				while(_pool.Count < Min)
				{
					var ti = new TaskItem
							{
								TaskState = TaskState.NotStarted,
								TaskHandle = new TaskHandle
											{
												Task = () =>
														{
														},
												Token = new ClientHandle
														{
															Id = Guid.NewGuid()
														}
											}
							};
					ti.TaskHandle.Callback = taskStatus =>
											{
											};
					AddTaskToPool(ti);
				}
			}
		}

		#region public interface

		// Locks
		private readonly object _syncLock = new object();

		private readonly object _criticalLock = new object();

		public ClientHandle QueueUserTask(UserTask task, Action<TaskStatus> callback)
		{
			var th = new TaskHandle
					{
						Task = task,
						Token = new ClientHandle
								{
									Id = Guid.NewGuid()
								},
						Callback = callback
					};
			lock(_syncLock) // main-lock - will be used for accessing ReadyQueue always
			{
				_readyQueue.Enqueue(th);
			}
			return th.Token;
		}

		public static void CancelUserTask(ClientHandle clientToken)
		{
			lock(GetInstance._syncLock)
			{
				var thandle = GetInstance._readyQueue.FirstOrDefault(th => th.Token.Id == clientToken.Id);
				if(thandle != null) // in case task is still in queue only
				{
					thandle.Task = null;
					thandle.Callback = null;
					thandle.Token = null;
				}
				else // in case thread is running the task - try aborting the thread to cancel the operation (rude behavior)
				{
					var itemCount = GetInstance._readyQueue.Count;
					TaskItem taskItem;
					lock(GetInstance._criticalLock)
					{
						taskItem = GetInstance._pool.FirstOrDefault(task => task.TaskHandle.Token.Id == clientToken.Id);
					}
					if(taskItem == null)
						return;
					lock(taskItem) // only item need the locking
					{
						if(taskItem.TaskState != TaskState.Completed)
							// double check - in case by the time this lock obtained callback already happened
						{
							taskItem.TaskState = TaskState.Aborted;
							taskItem.TaskHandle.Callback = null; // stop callback
						}
						if(taskItem.TaskState != TaskState.Aborted)
							return;
						try
						{
							taskItem.Handler.Abort(); // **** it does not work ****
							taskItem.Handler.Priority = ThreadPriority.BelowNormal;
							taskItem.Handler.IsBackground = true;
						}
						catch
						{
							// ignored
						}
					}
				}
			}
		}

		#endregion

		#region configurable items

		private const int Max = 8; // maximum no of threads in pool
		private const int Min = 3; // minimum no of threads in pool
		private const int MinWait = 10; // milliseconds
		private const int MaxWait = 15000; // milliseconds - threshold for simple task
		private const int CleanupInterval = 60000; // millisecond - to free waiting threads in pool
		private const int SchedulingInterval = 10; // millisecond - look for task in queue in loop

		#endregion

		#region singleton instance of threadpool

		private CustomThreadPool()
		{
			InitializeThreadPool();
		}

		public static CustomThreadPool GetInstance { get; } = new CustomThreadPool();

		#endregion
	}
}

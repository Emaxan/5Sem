using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using JetBrains.Annotations;

namespace SPP_CustomThreadPool
{
	public delegate bool UserTask();

	[UsedImplicitly]
	public class CustomThreadPool : IDisposable
	{
		private List<TaskItem> _pool;
		private Queue<TaskHandle> _readyQueue;
		private Thread _taskScheduler;

	    public static bool Suspend = false;

		private void InitializeThreadPool()
		{
			_readyQueue = new Queue<TaskHandle>();
			_pool = new List<TaskItem>();

			InitPoolWithMinCapacity();

			var lastCleanup = DateTime.Now;

			_taskScheduler = new Thread(
									() =>
									{
										do
										{
										    while(Suspend)
										    {
										    }
											lock(_syncLock)
											{
												while(_readyQueue.Count > 0 && _readyQueue.Peek().Task == null)
												{
													_readyQueue.Dequeue();
												}

												var itemCount = _readyQueue.Count;
												for(var i = 0; i < itemCount; i++)
												{
													var readyItem = _readyQueue.Peek();
													var added = false;
													lock(_criticalLock)
													{
														foreach(var ti in _pool)
														{
															lock(ti)
															{
																if(ti.TaskState != TaskState.Completed)
																{
																	continue;
																}
																ti.TaskHandle = readyItem;
																ti.TaskState = TaskState.NotStarted;
															    ti.TaskHandle.Token.State = ti.TaskState;
																added = true;
																_readyQueue.Dequeue();
																break;
															}
														}

														if(!added && _pool.Count < _max)
														{
															var ti = new TaskItem
																	{
																		TaskState = TaskState.NotStarted,
																		TaskHandle = readyItem
																	};
														    ti.TaskHandle.Token.State = ti.TaskState;
                                                            AddTaskToPool(ti);
															added = true;
															_readyQueue.Dequeue();
														}
													}
													if(!added)
													{
														break;
													}
												}
											}
											if(DateTime.Now - lastCleanup > TimeSpan.FromMilliseconds(CleanupInterval))
											{
												CleanupPool();
												lastCleanup = DateTime.Now;
											}
											else
											{
												Thread.Yield();
												Thread.Sleep(SchedulingInterval);
											}
										}
										while(true);
										// ReSharper disable once FunctionNeverReturns
									})
								{
									Priority = ThreadPriority.AboveNormal
								};
			_taskScheduler.Start();
		}

		private void InitPoolWithMinCapacity()
		{
			for(var i = 0; i < _min; i++)
			{
				AddEmptyTaskToPool();
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
						{
							if(taskItem.TaskState == TaskState.Aborted)
							{
								break;
							}

							if(taskItem.TaskState == TaskState.NotStarted)
							{
								taskItem.TaskState = TaskState.Processing;
							    taskItem.TaskHandle.Token.State = taskItem.TaskState;
                                taskItem.StartTime = DateTime.Now;
								enter = true;
							}
						}
						if(enter)
						{
							var taskStatus = new TaskStatus
											{
												Pid = Thread.CurrentThread.ManagedThreadId
											};
							try
							{
								taskItem.TaskHandle.Task.Invoke();
								taskStatus.Success = true;
							}
							catch(Exception ex)
							{
								taskStatus.Success = false;
								taskStatus.InnerException = ex;
							}
							lock(taskItem)
							{
								if(taskItem.TaskHandle.Callback != null && taskItem.TaskState != TaskState.Aborted)
								{
									try
									{
										taskItem.TaskState = TaskState.Completed;
									    taskItem.TaskHandle.Token.State = taskItem.TaskState;
                                        taskItem.StartTime = DateTime.MaxValue;

										taskItem.TaskHandle.Callback(taskStatus);
									}
									catch
									{
										// suppress exception
									}
								}
							}
						}
						Thread.Yield();
					}
					while(true);
				});
			lock(_criticalLock)
			{
				_pool.Add(taskItem);
			}
			taskItem.Handler.Start();
		}

		private void CleanupPool()
		{
			List<TaskItem> filteredTask;
			lock(_criticalLock)
			{
				filteredTask = _pool.Where(
										ti => ti.TaskHandle.Token.IsSimpleTask && DateTime.Now - ti.StartTime > TimeSpan.FromMilliseconds(MaxWait))
									.ToList();
			}
			foreach(var taskItem in filteredTask)
			{
				CancelUserTask(taskItem.TaskHandle.Token);
			}
			lock(_criticalLock)
			{
				filteredTask = _pool.Where(ti => ti.TaskState == TaskState.Aborted).ToList();
				foreach(var taskItem in filteredTask)
				{
					try
					{
						taskItem.Handler.Abort();
					}
					catch(ThreadAbortException)
					{
						// ignored
					}
					_pool.Remove(taskItem);
				}
				var total = _pool.Count;
				if(total >= _min)
				{
					filteredTask = _pool.Where(ti => ti.TaskState == TaskState.Completed).ToList();
					foreach(var taskItem in filteredTask)
					{
						taskItem.Handler.Priority = ThreadPriority.AboveNormal;
						taskItem.TaskState = TaskState.Aborted;
					    taskItem.TaskHandle.Token.State = taskItem.TaskState;
                        _pool.Remove(taskItem);
						total--;
						if(total == _min)
							break;
					}
				}
				while(_pool.Count < _min)
				{
					AddEmptyTaskToPool();
				}
			}
		}

		private void AddEmptyTaskToPool()
		{
			var ti = new TaskItem
					{
						TaskState = TaskState.NotStarted,
						TaskHandle = new TaskHandle
									{
										Task = () => true,
										Token = new ClientHandle
												{
													Id = Guid.NewGuid()
												}
									}
					};
		    ti.TaskHandle.Token.State = ti.TaskState;
            ti.TaskHandle.Callback = taskStatus =>
									{
									};
			AddTaskToPool(ti);
		}

		#region public interface

		/// <summary>
		///     Lock for ReadyQueue
		/// </summary>
		private readonly object _syncLock = new object();

		/// <summary>
		///     Lock for Pool
		/// </summary>
		private readonly object _criticalLock = new object();

		[UsedImplicitly]
		public ClientHandle QueueUserTask(UserTask task, Action<TaskStatus> callback = null)
		{
			var th = new TaskHandle
					{
						Task = task,
						Token = new ClientHandle
								{
									Id = Guid.NewGuid(),
                                    State = TaskState.NotStarted
								},
						Callback = callback
					};
			lock(_syncLock)
			{
				_readyQueue.Enqueue(th);
			}
			return th.Token;
		}

		// ReSharper disable once MemberCanBePrivate.Global
		public static void CancelUserTask(ClientHandle clientToken)
		{
			lock(GetInstance()._syncLock)
			{
				var thandle = GetInstance()._readyQueue.FirstOrDefault(th => th.Token?.Id == clientToken.Id);
				if(thandle != null)
				{
					thandle.Task = null;
					thandle.Callback = null;
					thandle.Token = null;
				}
				else
				{
					TaskItem taskItem;
					lock(GetInstance()._criticalLock)
					{
						taskItem = GetInstance()._pool.FirstOrDefault(task => task.TaskHandle.Token.Id == clientToken.Id);
					}
					if(taskItem == null)
					{
						return;
					}
					lock(taskItem)
					{
						if(taskItem.TaskState != TaskState.Completed)
						{
							taskItem.TaskState = TaskState.Aborted;
						    taskItem.TaskHandle.Token.State = taskItem.TaskState;
                            taskItem.TaskHandle.Callback = null;
						}
						if(taskItem.TaskState != TaskState.Aborted)
						{
							return;
						}
						try
						{
							taskItem.Handler.Abort();
						}
						catch(ThreadAbortException)
						{
							// ignored
						}
					}
				}
			}
		}

		#endregion

		#region configurable items

		private readonly int _max; // maximum no of threads in pool
		private readonly int _min; // minimum no of threads in pool
		private const int MaxWait = 15000; // milliseconds - threshold for simple task
		private const int CleanupInterval = 60000; // millisecond - to free waiting threads in pool
		private const int SchedulingInterval = 10; // millisecond - look for task in queue in loop

		#endregion

		#region singleton instance of thread pool

		private CustomThreadPool(int min, int max)
		{
			_min = min;
			_max = max;
			InitializeThreadPool();
		}

		private static CustomThreadPool _instance;
		private static readonly object Locker = new object();

		// ReSharper disable once MemberCanBePrivate.Global
		public static CustomThreadPool GetInstance(int min = 50, int max = 50)
		{
			// ReSharper disable once InvertIf
			if(_instance == null)
			{
				lock(Locker)
				{
					if(_instance == null)
					{
						_instance = new CustomThreadPool(min, max);
					}
				}
			}
			return _instance;
		}

		#endregion

		#region IDisposable pattern

		private void ReleaseUnmanagedResources()
		{
			_taskScheduler.Abort();
			lock(_criticalLock)
			{
				foreach(var item in _pool)
				{
					item.TaskState = TaskState.Aborted;
				    item.TaskHandle.Token.State = item.TaskState;
                    item.Handler.Abort();
				}
				_pool.Clear();
			}
		}

		public void Dispose()
		{
			ReleaseUnmanagedResources();
			GC.SuppressFinalize(this);
		}

		~CustomThreadPool()
		{
			ReleaseUnmanagedResources();
		}

		#endregion
	}
}

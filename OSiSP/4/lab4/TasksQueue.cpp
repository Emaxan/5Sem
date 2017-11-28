#include "stdafx.h"
#include "TasksQueue.h"


void TasksQueue::addTask(Task * task)
{
	EnterCriticalSection(&csWorkWithQueue);
	tasks->push(task);
	LeaveCriticalSection(&csWorkWithQueue);
}

Task* TasksQueue::getTask()
{
	Task *result = NULL;

	EnterCriticalSection(&csWorkWithQueue);
	if (!tasks->empty()) {
		result = tasks->front();
		tasks->pop();
	}
	LeaveCriticalSection(&csWorkWithQueue);
	return result;
}

TasksQueue::TasksQueue()
{
	tasks = new std::queue<Task *>();
	InitializeCriticalSectionAndSpinCount(&csWorkWithQueue, 4000);
}


TasksQueue::~TasksQueue()
{
	while (!tasks->empty()) {
		delete tasks->front();
		tasks->pop();
	}
	delete tasks;
}

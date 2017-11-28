#pragma once

#include "stdafx.h"
#include <queue>
#include "Task.h"

class TasksQueue
{
private:
	std::queue<Task *> *tasks;
	CRITICAL_SECTION csWorkWithQueue;
public:
	void addTask(Task *task);
	Task* getTask();
	TasksQueue();
	virtual ~TasksQueue();
};


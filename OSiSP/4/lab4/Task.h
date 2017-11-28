#pragma once

typedef void TaskCallback(void *param);

class Task
{
private:
	void *param;
	TaskCallback *action;
public:
	void execute() volatile;
	Task(void *param, TaskCallback *action);
	virtual ~Task();
};


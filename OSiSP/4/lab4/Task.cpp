#include "stdafx.h"
#include "Task.h"


void Task::execute() volatile
{
	if (action != NULL) {
		action(param);
	}
}

Task::Task(void *param, TaskCallback *action)
{
	this->param = param;
	this->action = action;
}


Task::~Task()
{
}

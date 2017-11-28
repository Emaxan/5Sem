
#include "stdafx.h"
#include "TasksQueue.h"
#include "Task.h"
#include "ServiceProcedures.h"

#define INPUT_FILE "text.txt"
#define OUTPUT_FILE "result.txt"

CRITICAL_SECTION csQueueHandler;
CONDITION_VARIABLE cvQueueHandler;
volatile PWORKINGTHREADPARAM workingThreadParam;

DWORD WINAPI queueHandlerThreadAction(void *lpParameter);
DWORD WINAPI workingThreadAction(void *lpParameter);

bool startThreads(TasksQueue *tasksQueue, HANDLE *threads)
{
	InitializeCriticalSectionAndSpinCount(&csQueueHandler, 4000);
	InitializeConditionVariable(&cvQueueHandler);
	for (int i = 0; i < SORTING_THREADS_AMOUNT; i++) {
		InitializeCriticalSectionAndSpinCount(&(workingThreadParam[i].cs), 4000);
		InitializeConditionVariable(&(workingThreadParam[i].cv));
		workingThreadParam[i].task = NULL;
	}
	threads[0] = CreateThread(NULL, 0, queueHandlerThreadAction, tasksQueue, 0, NULL);
	if (threads[0] == NULL) {
		return false;
	}
	for (int i = 1; i <= SORTING_THREADS_AMOUNT; i++) {
		threads[i] = CreateThread(NULL, 0, workingThreadAction, &(workingThreadParam[i - 1]), 0, NULL);
	}
	return true;
}

void createTasks(std::vector<char *> **partsOfFile, TasksQueue *tasksQueue)
{
	Task *currentTask;
	for (int i = 0; i < SORTING_THREADS_AMOUNT; i++) {
		currentTask = new Task(partsOfFile[i], taskAction);
		tasksQueue->addTask(currentTask);
	}
#ifdef DEBUG
	printf("Main thread wake tasks queue handler thread\n");
#endif
	EnterCriticalSection(&csQueueHandler);
	WakeConditionVariable(&cvQueueHandler);
	LeaveCriticalSection(&csQueueHandler);
}

DWORD WINAPI queueHandlerThreadAction(void *lpParameter)
{
	TasksQueue *tasksQueue = static_cast<TasksQueue *>(lpParameter);
	Task *currentTask;
	int i = 0;
#ifdef DEBUG
	printf("Tasks queue handler thread started\n");
#endif
	EnterCriticalSection(&csQueueHandler);
	while (i < SORTING_THREADS_AMOUNT) {
		currentTask = tasksQueue->getTask();
		if (currentTask == NULL) {
#ifdef DEBUG
			printf("Tasks queue handler thread sleep on CV\n");
#endif
			SleepConditionVariableCS(&cvQueueHandler, &csQueueHandler, INFINITE);
#ifdef DEBUG
			printf("Tasks queue handler thread wake on CV\n");
#endif
		}
		else {
			EnterCriticalSection(&(workingThreadParam[i].cs));
			workingThreadParam[i].task = currentTask;
			WakeConditionVariable(&(workingThreadParam[i].cv));
			LeaveCriticalSection(&(workingThreadParam[i].cs));
#ifdef DEBUG
			printf("Tasks queue handler thread gave task %d\n", i);
#endif
			i++;
		}
	}
	LeaveCriticalSection(&csQueueHandler);
#ifdef DEBUG
	printf("Tasks queue handler thread finished\n");
#endif
	ExitThread(0);
	return 0;
}

DWORD WINAPI workingThreadAction(void *lpParameter)
{
	PWORKINGTHREADPARAM param = static_cast<PWORKINGTHREADPARAM>(lpParameter);
#ifdef DEBUG
	DWORD pid = GetCurrentThreadId();
	printf("Sorting thread %ul started\n", pid);
#endif
	EnterCriticalSection(&(param->cs));
	while (param->task == NULL) {
#ifdef DEBUG
		printf("Sorting thread %ul sleep on CV\n", pid);
#endif
		SleepConditionVariableCS(&(param->cv), &(param->cs), INFINITE);
#ifdef DEBUG
		printf("Sorting thread %ul wake on CV\n", pid);
#endif
	}
#ifdef DEBUG
	printf("Sorting thread %ul started task\n", pid);
#endif
	param->task->execute();
	delete param->task;
	LeaveCriticalSection(&(param->cs));
#ifdef DEBUG
	printf("Sorting thread %ul finished task\n", pid);
#endif
	return 0;
}

int main()
{
	HANDLE *threads = new HANDLE[SORTING_THREADS_AMOUNT + 1];
	std::vector<char *> **partsOfFile = new std::vector<char *>*[SORTING_THREADS_AMOUNT];
	std::vector<char *> *strings = new std::vector<char *>();
	TasksQueue tasksQueue;
	workingThreadParam = new WORKINGTHREADPARAM[SORTING_THREADS_AMOUNT];

	try {
		loadStrings(strings, INPUT_FILE);
	}
	catch (std::exception *ex) {
		printf("%s\n", ex->what());
		delete ex;
		return 0;
	}
	
	if (!startThreads(&tasksQueue, threads)) {
		printf("Unable to start thread!\n");
		releaseResources(strings, partsOfFile, threads, workingThreadParam);
		return 0;
	}
	
	divideStringsArray(strings, partsOfFile);
	createTasks(partsOfFile, &tasksQueue);

#ifdef DEBUG
	printf("Main thread waiting for other threads...\n");
#endif
	WaitForMultipleObjects(SORTING_THREADS_AMOUNT + 1, threads, true, INFINITE);
#ifdef DEBUG
	printf("Main thread stopped waiting\n\n");
#endif

	for (int i = 0; i <= SORTING_THREADS_AMOUNT; i++) {
		CloseHandle(threads[i]);
	}

	joinResults(strings, partsOfFile);
	try {
		writeStringsToFile(strings, OUTPUT_FILE);
	}
	catch (std::exception *ex) {
		printf("%s\n", ex->what());
		delete ex;
		return 0;
	}

	releaseResources(strings, partsOfFile, threads, workingThreadParam);

	printf("Done!\n");
	getchar();
	return 0;
}


#pragma once

#include "Task.h"

#define STRING_MAX_LENGTH 255
#define SORTING_THREADS_AMOUNT 4
#define DEBUG

typedef struct _WORKINGTHREADPARAM {
	CRITICAL_SECTION cs;
	CONDITION_VARIABLE cv;
	volatile Task *task;
} WORKINGTHREADPARAM, *PWORKINGTHREADPARAM;

void taskAction(void *param);
void loadStrings(std::vector<char *> *strings, char *filename);
void writeStringsToFile(std::vector<char *> *strings, char *filename);
void divideStringsArray(std::vector<char *> *strings, std::vector<char *> **parts);
void joinResults(std::vector<char *> *strings, std::vector<char *> **parts);
void releaseResources(std::vector<char *> *strings, std::vector<char *> **partsOfFile, 
	HANDLE *threads, PWORKINGTHREADPARAM threadsParams);

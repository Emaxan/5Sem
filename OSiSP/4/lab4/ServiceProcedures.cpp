#include "stdafx.h"
#include "ServiceProcedures.h"
#include <algorithm>

bool str1LessThanStr2(char *str1, char *str2)
{
	return strcmp(str1, str2) < 0;
}

void taskAction(void *param)
{
	std::vector<char *> *stringsForSort = static_cast<std::vector<char *> *>(param);
	std::sort(stringsForSort->begin(), stringsForSort->end(), str1LessThanStr2);
}

void loadStrings(std::vector<char *> *strings, char *filename)
{
	FILE *f;
	if (fopen_s(&f, filename, "rt") != 0) {
		throw new std::exception("Unable to open file!");
	}

	char *currentString = new char[STRING_MAX_LENGTH];
	int currentStringLength;
	while (fgets(currentString, STRING_MAX_LENGTH, f) != NULL) {
		currentStringLength = strlen(currentString);
		if ((currentStringLength > 0) && (currentString[currentStringLength - 1] == '\n')) {
			currentString[currentStringLength - 1] = '\0';
		}
		strings->push_back(currentString);
		currentString = new char[STRING_MAX_LENGTH];
	}

	delete currentString;
	fclose(f);
}

void writeStringsToFile(std::vector<char *> *strings, char *filename)
{
	FILE *f;
	if (fopen_s(&f, filename, "wt+") != 0) {
		throw new std::exception("Unable to open file!");
	}
	for (int i = 0; i < strings->size(); i++) {
		fprintf_s(f, "%s\n", (*strings)[i]);
	}
	fclose(f);
}

void divideStringsArray(std::vector<char *> *strings, std::vector<char *> **parts)
{
	int stringsPerThreadAmount = strings->size() / SORTING_THREADS_AMOUNT;
	int extraStringsAmount = strings->size() % SORTING_THREADS_AMOUNT;
	int firstStringInd;
	int lastStringInd;

	for (int i = 0; i < SORTING_THREADS_AMOUNT; i++) {
		parts[i] = new std::vector<char *>();
		firstStringInd = i * stringsPerThreadAmount;
		lastStringInd = (i == (SORTING_THREADS_AMOUNT - 1))
			? (i + 1) * stringsPerThreadAmount + extraStringsAmount
			: (i + 1) * stringsPerThreadAmount;
		for (int j = firstStringInd; j < lastStringInd; j++) {
			parts[i]->push_back((*strings)[j]);
		}
	}
}

void joinResults(std::vector<char *> *strings, std::vector<char *> **parts)
{
	char *minString;
	int minStringPartInd, currentPart;
	int size = 0;
	int *partInd = new int[SORTING_THREADS_AMOUNT];
	for (int i = 0; i < SORTING_THREADS_AMOUNT; i++) {
		size += parts[i]->size();
		partInd[i] = 0;
	}
	strings->clear();

	for (int i = 0; i < size; i++) {
		minString = NULL;
		currentPart = 0;
		while ((currentPart < SORTING_THREADS_AMOUNT) && (NULL == minString)) {
			if (partInd[currentPart] < parts[currentPart]->size()) {
				minString = (*parts[currentPart])[partInd[currentPart]];
				minStringPartInd = currentPart;
			}
			currentPart++;
		}
		while (currentPart < SORTING_THREADS_AMOUNT) {
			if (partInd[currentPart] < parts[currentPart]->size()) {
				if (str1LessThanStr2((*parts[currentPart])[partInd[currentPart]], minString)) {
					minString = (*parts[currentPart])[partInd[currentPart]];
					minStringPartInd = currentPart;
				}
			}
			currentPart++;
		}
		strings->push_back(minString);
		partInd[minStringPartInd]++;
	}

	delete partInd;
}

void releaseResources(std::vector<char *> *strings, std::vector<char *> **partsOfFile, 
		HANDLE *threads, PWORKINGTHREADPARAM threadsParams)
{
	for (int i = 0; i < SORTING_THREADS_AMOUNT; i++) {
		delete partsOfFile[i];
	}
	for (int i = 0; i < strings->size(); i++) {
		delete (*strings)[i];
	}
	delete partsOfFile;
	delete threads;
	delete strings;
	delete threadsParams;
}

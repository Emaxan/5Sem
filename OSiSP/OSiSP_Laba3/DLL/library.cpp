#include "library.h"

extern "C" __declspec(dllexport) bool replaceMemory(ReplaceArgs *args) {
	wchar_t *oldValue = args->oldValue;
	wchar_t *newValue = args->newValue;

	if (wcslen(oldValue) == 0 || wcslen(newValue) > wcslen(oldValue)) {
		return false;
	}

	int oldValueLength = wcslen(oldValue);
	int newValueLength = wcslen(newValue);

	HANDLE process = GetCurrentProcess();

	SYSTEM_INFO systemInfo;
	GetSystemInfo(&systemInfo);

	for (char *start = (char*)systemInfo.lpMinimumApplicationAddress; start < systemInfo.lpMaximumApplicationAddress;) {
		MEMORY_BASIC_INFORMATION memoryInfo;
		VirtualQueryEx(process, start, &memoryInfo, sizeof(memoryInfo));

		if ((memoryInfo.State & MEM_COMMIT) && (memoryInfo.Protect & PAGE_READWRITE)) {
			int remoteAddress = (int) memoryInfo.BaseAddress;
			char *buffer = new char[memoryInfo.RegionSize];
			SIZE_T bytesRead;
			ReadProcessMemory(process, start, buffer, memoryInfo.RegionSize, &bytesRead);

			for (char *bufferStart = buffer, *bufferEnd = buffer + bytesRead - oldValueLength; bufferStart < bufferEnd;) {
				if (memcmp(bufferStart, oldValue, oldValueLength + 1) == 0 && remoteAddress != (int) args->oldValue) {
					int offset = bufferStart - buffer;
					WriteProcessMemory(
						process,
						(char *) memoryInfo.BaseAddress + offset,
						newValue,
						newValueLength * 2,
						NULL
					);
					bufferStart += wcslen(oldValue);
					remoteAddress += wcslen(oldValue);
				} else {
					bufferStart++;
					remoteAddress++;
				}
			}

			delete buffer;
		}
		start += memoryInfo.RegionSize;
	}

	return true;
}

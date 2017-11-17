#include "stdafx.h"

#include <windows.h>
#include <iostream>
#include <string>

#define DLL_PATH "C:\\Users\\Asus-pc\\Desktop\\L3\\Debug\\DLL.dll"

struct ReplaceArgs
{
	wchar_t oldValue[MAX_PATH];
	wchar_t newValue[MAX_PATH];
};

typedef bool(__cdecl *ReplaceMemory)(ReplaceArgs *);

int main(int argc, char *argv[]) {
	if (argc != 4) {
		std::cerr << "Usage: " << argv[0] << " <pid> <old_string> <new_string>" << std::endl;

		return 1;
	}

	int pid = atoi(argv[1]);

	int oldValueLength = strlen(argv[2]) + 1;
	int newValueLength = strlen(argv[3]) + 1;

	size_t resultx;
	std::wstring wc1(oldValueLength + 1, L'#');
	mbstowcs_s(&resultx, &wc1[0], oldValueLength + 1, argv[2], oldValueLength + 1);
	const wchar_t *oldValue = wc1.data();

	std::wstring wc2(newValueLength + 1, L'#');
	mbstowcs_s(&resultx, &wc2[0], newValueLength + 1, argv[3], newValueLength + 1);
	const wchar_t *newValue = wc2.data();

	BOOL result = false;

	/* Get remote process */
	HANDLE process = OpenProcess(
		PROCESS_CREATE_THREAD |
		PROCESS_QUERY_INFORMATION |
		PROCESS_VM_READ |
		PROCESS_VM_WRITE |
		PROCESS_VM_OPERATION,
		FALSE,
		pid
	);

	if (process == NULL) {
		std::cerr << "Unable to open process" << std::endl;

		return 1;
	}

	ReplaceArgs args;

	wcscpy_s(args.oldValue, oldValue);
	wcscpy_s(args.newValue, newValue);

	/* Save args in the remote process */
	LPVOID argsMemory = VirtualAllocEx(
		process,
		NULL,
		sizeof(args),
		MEM_RESERVE | MEM_COMMIT,
		PAGE_READWRITE
	);
	result = WriteProcessMemory(
		process,
		argsMemory,
		&args,
		sizeof(args),
		NULL
	);

	if (!result) {
		std::cerr << "Unable to write arguments" << std::endl;

		return 1;
	}

	/* Save dll path in the remote process */
	LPVOID dllPathMemory = VirtualAllocEx(
		process,
		NULL,
		strlen(DLL_PATH),
		MEM_RESERVE | MEM_COMMIT,
		PAGE_READWRITE
	);
	result = WriteProcessMemory(
		process,
		dllPathMemory,
		DLL_PATH,
		strlen(DLL_PATH),
		NULL
	);

	if (!result) {
		std::cerr << "Unable to write dll path" << std::endl;

		return 1;
	}

	/* Get LoadLibraryA address */
	HMODULE kernel = GetModuleHandle(L"kernel32.dll");
	LPVOID loadLibraryAddress = GetProcAddress(kernel, "LoadLibraryA");

	/* Load library in the remote process */
	HANDLE remoteThread = CreateRemoteThread(
		process,
		NULL,
		NULL,
		(LPTHREAD_START_ROUTINE) loadLibraryAddress,
		dllPathMemory,
		NULL,
		NULL
	);
	WaitForSingleObject(remoteThread, INFINITE);

	/* Get library address in the remote process */
	DWORD library;
	GetExitCodeThread(remoteThread, &library);

	CloseHandle(remoteThread);

	/* Get procedure offset in the local process */
	HMODULE localLibrary = LoadLibraryA(DLL_PATH);

	int procOffset = (int) GetProcAddress(localLibrary, "replaceMemory");
	int offset = procOffset - (int) localLibrary;

	FreeLibrary(localLibrary);

	/* Call procedure in the remote process */
	HANDLE thread = CreateRemoteThread(
		process,
		NULL,
		NULL,
		(LPTHREAD_START_ROUTINE) ((int) library + offset),
		(LPVOID) argsMemory,
		NULL,
		NULL
	);

	WaitForSingleObject(thread, INFINITE);

	CloseHandle(thread);

	CloseHandle(process);

	return 0;
}
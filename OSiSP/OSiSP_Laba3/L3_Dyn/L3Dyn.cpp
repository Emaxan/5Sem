#include "stdafx.h"

#include <windows.h>
#include <iostream>

struct ReplaceArgs
{
	wchar_t oldValue[MAX_PATH];
	wchar_t newValue[MAX_PATH];
};

typedef bool(* ReplaceString)(ReplaceArgs *);

int main()
{
	HMODULE library = LoadLibrary(L"../Debug/DLL.dll");
	ReplaceString replace = (ReplaceString) GetProcAddress(library, "replaceMemory");

	ReplaceArgs args;

	wcscpy_s(args.oldValue, L"STRING1");
	wcscpy_s(args.newValue, L"STRING2");

	wchar_t oldString[] = L"STRING1";

	wprintf(oldString);
	wprintf(L"\n");

	replace(&args);

	wprintf(oldString);

	std::cin.get();

    return 0;
}


#include "stdafx.h"
#include <string.h>
#include <wchar.h>
#include <iostream>
#include "../DLL/library.h"

int main() {
	wchar_t oldString[] = L"STRING1";

	ReplaceArgs args;

	wcscpy_s(args.oldValue, L"STRING1");
	wcscpy_s(args.newValue, L"STRING2");

	wprintf(oldString);
	wprintf(L"\n");

	replaceMemory(&args);

	wprintf(oldString);
	std::cin.get();
	
	return 0;
}

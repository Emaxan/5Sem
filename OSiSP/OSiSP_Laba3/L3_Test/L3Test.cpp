#include "stdafx.h"

#include <iostream>
#include <windows.h>

int main()
{
	std::cout << "PID: " << GetCurrentProcessId() << std::endl;

	wchar_t oldString[] = L"View";

	wprintf(oldString);
	wprintf(L"\n");

	std::cin.get();

	wprintf(oldString);

	std::cin.get();

    return 0;
}


#pragma once

#include <windows.h>

struct ReplaceArgs
{
	wchar_t oldValue[MAX_PATH];
	wchar_t newValue[MAX_PATH];
};

extern "C" __declspec(dllexport) bool replaceMemory(ReplaceArgs *args);

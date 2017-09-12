#include "stdafx.h"
#include "OSiSP_Laba1.h"
#include <winuser.h>
#include <winuser.h>

#define MAX_LOADSTRING 100

HINSTANCE hInst;
WCHAR szTitle[MAX_LOADSTRING];
WCHAR szWindowClass[MAX_LOADSTRING];

ATOM MyRegisterClass(HINSTANCE hInstance);
BOOL InitInstance(HINSTANCE, int);
LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK About(HWND, UINT, WPARAM, LPARAM);

int APIENTRY wWinMain(_In_ HINSTANCE hInstance,
							_In_opt_ HINSTANCE hPrevInstance,
							_In_ LPWSTR lpCmdLine,
							_In_ int nCmdShow)
{
	nCmdShow = 3;

	LoadStringW(hInstance, IDS_APP_TITLE, szTitle, MAX_LOADSTRING);
	LoadStringW(hInstance, IDC_OSISP_LABA1, szWindowClass, MAX_LOADSTRING);
	MyRegisterClass(hInstance);

	if (!InitInstance(hInstance, nCmdShow))
	{
		return FALSE;
	}

	HACCEL hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_OSISP_LABA1));

	MSG msg;

	while (GetMessage(&msg, nullptr, 0, 0))
	{
		if (!TranslateAccelerator(msg.hwnd, hAccelTable, &msg))
		{
			TranslateMessage(&msg);
			DispatchMessage(&msg);
		}
	}

	return static_cast<int>(msg.wParam);
}

ATOM MyRegisterClass(HINSTANCE hInstance)
{
	WNDCLASSEXW wcex;

	wcex.cbSize = sizeof(WNDCLASSEX);

	wcex.style = CS_HREDRAW | CS_VREDRAW | CS_NOCLOSE;
	wcex.lpfnWndProc = WndProc;
	wcex.cbClsExtra = 0;
	wcex.cbWndExtra = 0;
	wcex.hInstance = hInstance;
	wcex.hIcon = LoadIcon(hInstance, MAKEINTRESOURCE(IDI_OSISP_LABA1));
	wcex.hCursor = LoadCursor(nullptr, IDC_CROSS);
	wcex.hbrBackground = reinterpret_cast<HBRUSH>(COLOR_WINDOW + 1);
	wcex.lpszMenuName = MAKEINTRESOURCEW(IDC_OSISP_LABA1);
	wcex.lpszClassName = szWindowClass;
	wcex.hIconSm = LoadIcon(wcex.hInstance, MAKEINTRESOURCE(IDI_SMALL));

	return RegisterClassExW(&wcex);
}

#pragma region Drawing

enum Ways
{
	XY = 0,
	X = 1,
	Y = 2
};

int ellipseCenterX = 100;
int ellipseCenterY = 50;
int ellipseRadiusX = 100;
int ellipseRadiusY = 50;
int step = 5;
RECT rcClient, rcWind;

BOOL DrawWindow(HDC hdc)
{
	HBITMAP BmpBrush = LoadBitmap(hInst, MAKEINTRESOURCE(IDB_BITMAP1));
	HBRUSH brPattern = CreatePatternBrush(BmpBrush);
	SelectObject(hdc, brPattern);
	SelectObject(hdc, GetStockObject(DC_PEN));
	SetDCBrushColor(hdc, RGB(255, 0, 0));
	SetDCPenColor(hdc, RGB(255, 0, 0));

	int top = ellipseCenterY - ellipseRadiusY;
	int left = ellipseCenterX - ellipseRadiusX;
	int right = ellipseCenterX + ellipseRadiusX;
	int bottom = ellipseCenterY + ellipseRadiusY;
	Ellipse(hdc, left, top, right, bottom);

	return TRUE;
}

void ValidateCoords(int ways = 0)
{
	if(ways & Y || ways == XY)
	{
		while (ellipseCenterY - ellipseRadiusY < 0)
		{
			ellipseCenterY += 5 * step;
		}
		while (ellipseCenterY + ellipseRadiusY > rcClient.bottom)
		{
			ellipseCenterY -= 5 * step;
		}
	}
	
	if(ways & X || ways == XY)
	{
		while (ellipseCenterX + ellipseRadiusX > rcClient.right)
		{
			ellipseCenterX -= 5 * step;
		}
		while (ellipseCenterX - ellipseRadiusX < 0)
		{
			ellipseCenterX += 5 * step;
		}
	}	
}

#pragma endregion

BOOL InitInstance(HINSTANCE hInstance, int nCmdShow)
{
	hInst = hInstance;

	HWND hWnd = CreateWindowW(szWindowClass, szTitle, WS_POPUP,
		CW_USEDEFAULT, 0, CW_USEDEFAULT, 0, nullptr, nullptr, hInstance, nullptr);

	if (!hWnd)
	{
		return FALSE;
	}

	ShowWindow(hWnd, nCmdShow);
	UpdateWindow(hWnd);

	GetClientRect(hWnd, &rcClient);
	GetWindowRect(hWnd, &rcWind);

	return TRUE;
}

LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
	switch (message)
	{
	case WM_COMMAND:
		{
			int wmId = LOWORD(wParam);
			switch (wmId)
			{
			case IDM_ABOUT:
				DialogBox(hInst, MAKEINTRESOURCE(IDD_ABOUTBOX), hWnd, About);
				break;
			case IDM_EXIT:
				DestroyWindow(hWnd);
				break;
			default:
				return DefWindowProc(hWnd, message, wParam, lParam);
			}
		}
		break;
	case WM_KEYDOWN:
		{
			switch (wParam)
			{
			case VK_UP:
				ellipseCenterY -= step;
				ValidateCoords(Y);
				break;
			case VK_DOWN:
				ellipseCenterY += step;
				ValidateCoords(Y);
				break;
			case VK_RIGHT:
				ellipseCenterX += step;
				ValidateCoords(X);
				break;
			case VK_LEFT:
				ellipseCenterX -= step;
				ValidateCoords(X);
				break;
			default:
				return DefWindowProc(hWnd, message, wParam, lParam);
			}
			InvalidateRect(hWnd, nullptr, TRUE);
		}
		break;
	case WM_MOUSEMOVE:
		ellipseCenterY = GET_Y_LPARAM(lParam);
		ellipseCenterX = GET_X_LPARAM(lParam);
		ValidateCoords();
		InvalidateRect(hWnd, nullptr, TRUE);
		break;
	case WM_MOUSEWHEEL:
		if (GET_KEYSTATE_WPARAM(wParam) == MK_SHIFT)
		{
			short dy = GET_WHEEL_DELTA_WPARAM(wParam);
			ellipseCenterY += dy < 0 ? -5*step : 5*step;
		}
		else
		{
			short dx = GET_WHEEL_DELTA_WPARAM(wParam);
			ellipseCenterX += dx < 0 ? -5*step : 5*step;
		}
		InvalidateRect(hWnd, nullptr, TRUE);
		break;
	case WM_PAINT:
		{
			PAINTSTRUCT ps;
			HDC hdc = BeginPaint(hWnd, &ps);
			DrawWindow(hdc);
			EndPaint(hWnd, &ps);
		}
		break;
	case WM_DESTROY:
		{
			PostQuitMessage(0); 
		}
		break;
	default:
		return DefWindowProc(hWnd, message, wParam, lParam);
	}
	return 0;
}

INT_PTR CALLBACK About(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
	int wmId = LOWORD(wParam);
	switch (message)
	{
	case WM_INITDIALOG:
		return static_cast<INT_PTR>(TRUE);

	case WM_COMMAND:
		if (wmId == IDOK || wmId == IDCANCEL)
		{
			EndDialog(hDlg, LOWORD(wParam));
			return static_cast<INT_PTR>(TRUE);
		}
		break;
	}
	return static_cast<INT_PTR>(FALSE);
}

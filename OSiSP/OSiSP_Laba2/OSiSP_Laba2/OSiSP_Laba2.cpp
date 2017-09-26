#include "stdafx.h"
#include "OSiSP_Laba2.h"
#include <winuser.h>

#define MAX_LOADSTRING 100

HINSTANCE hInst;
WCHAR szTitle[MAX_LOADSTRING];
WCHAR szWindowClass[MAX_LOADSTRING];

ATOM                MyRegisterClass(HINSTANCE hInstance);
BOOL                InitInstance(HINSTANCE, int);
LRESULT CALLBACK    WndProc(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK    About(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK    ChangeGridSize(HWND, UINT, WPARAM, LPARAM);

int APIENTRY wWinMain(_In_ HINSTANCE hInstance,
                     _In_opt_ HINSTANCE hPrevInstance,
                     _In_ LPWSTR    lpCmdLine,
                     _In_ int       nCmdShow)
{
    LoadStringW(hInstance, IDS_APP_TITLE, szTitle, MAX_LOADSTRING);
    LoadStringW(hInstance, IDC_OSISP_LABA2, szWindowClass, MAX_LOADSTRING);
    MyRegisterClass(hInstance);

    if (!InitInstance (hInstance, nCmdShow))
    {
        return FALSE;
    }

    HACCEL hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_OSISP_LABA2));

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

    wcex.style          = CS_HREDRAW | CS_VREDRAW;
    wcex.lpfnWndProc    = WndProc;
    wcex.cbClsExtra     = 0;
    wcex.cbWndExtra     = 0;
    wcex.hInstance      = hInstance;
    wcex.hIcon          = LoadIcon(hInstance, MAKEINTRESOURCE(IDI_OSISP_LABA2));
    wcex.hCursor        = LoadCursor(nullptr, IDC_ARROW);
    wcex.hbrBackground  = reinterpret_cast<HBRUSH>(COLOR_WINDOW + 1);
    wcex.lpszMenuName   = MAKEINTRESOURCEW(IDC_OSISP_LABA2);
    wcex.lpszClassName  = szWindowClass;
    wcex.hIconSm        = LoadIcon(wcex.hInstance, MAKEINTRESOURCE(IDI_SMALL));

    return RegisterClassExW(&wcex);
}

BOOL InitInstance(HINSTANCE hInstance, int nCmdShow)
{
   hInst = hInstance;

   HWND hWnd = CreateWindowW(szWindowClass, szTitle, WS_SIZEBOX | WS_SYSMENU,
      CW_USEDEFAULT, 0, CW_USEDEFAULT, 0, nullptr, nullptr, hInstance, nullptr);

   if (!hWnd)
   {
      return FALSE;
   }

   ShowWindow(hWnd, nCmdShow);
   UpdateWindow(hWnd);

   return TRUE;
}

#pragma region Drawing

int rowsCount = 0;
int columnsCount = 0;
RECT rcClient;

BOOL DrawLine(HDC hdc, int x0, int y0, int x, int y)
{
	MoveToEx(hdc, x0, y0, NULL);
	return LineTo(hdc, x, y);
}

void DrawWindow(HWND hWnd, HDC hdc)
{
	if (rowsCount == 0 || columnsCount == 0) 
	{
		return;
	}
	SelectObject(hdc, GetStockObject(DC_PEN));
	SetDCPenColor(hdc, RGB(0, 0, 0));
	GetClientRect(hWnd, &rcClient);
	int height = rcClient.bottom;
	int width = rcClient.right;

	if (height < 10 * rowsCount) 
	{
		return;
	}

	int rowsHeight = height / rowsCount;
	int columnsWidth = width / columnsCount;
	
	for (int i = 0; i <= rowsCount; i++) {
		DrawLine(hdc, 0, i*rowsHeight, columnsCount*columnsWidth, i*rowsHeight);
	}
	for (int i = 0; i <= columnsCount; i++) {
		DrawLine(hdc, i*columnsWidth, 0, i*columnsWidth, rowsCount*rowsHeight);
	}

}

#pragma endregion 

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
			case IDM_CHANGEGRIDSIZE:
				DialogBox(hInst, MAKEINTRESOURCE(IDD_CHANGE_GRID_SIZE), hWnd, ChangeGridSize);
				break;
            default:
                return DefWindowProc(hWnd, message, wParam, lParam);
            }
        }
        break;
    case WM_PAINT:
        {
			InvalidateRect(hWnd, nullptr, TRUE);
            PAINTSTRUCT ps;
            HDC hdc = BeginPaint(hWnd, &ps);
			DrawWindow(hWnd, hdc);
            EndPaint(hWnd, &ps);
        }
        break;
    case WM_DESTROY:
        PostQuitMessage(0);
        break;
    default:
        return DefWindowProc(hWnd, message, wParam, lParam);
    }
    return 0;
}

INT_PTR CALLBACK ChangeGridSize(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
	switch (message)
	{
	case WM_INITDIALOG:
		return static_cast<INT_PTR>(TRUE);

	case WM_COMMAND:
		switch (LOWORD(wParam))
		{
		case IDOK:
		{
			TCHAR rows[3];
			TCHAR cols[3];

			GetDlgItemText(hDlg, IDC_ROWS, rows, sizeof rows);
			GetDlgItemText(hDlg, IDC_COLS, cols, sizeof cols);
			
			rowsCount = _ttoi(rows);
			columnsCount = _ttoi(cols); // TODO Validate numbers <= 10
			HWND hWnd = GetParent(hDlg);
			SendMessage(hWnd, WM_PAINT, 0, 0);
		}
		case IDCANCEL:
			EndDialog(hDlg, LOWORD(wParam));
			return static_cast<INT_PTR>(TRUE);

		default: 
			return static_cast<INT_PTR>(FALSE);
		}

	default: 
		return static_cast<INT_PTR>(FALSE);
	}
}

INT_PTR CALLBACK About(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
    switch (message)
    {
    case WM_INITDIALOG:
        return static_cast<INT_PTR>(TRUE);

    case WM_COMMAND:
		switch (LOWORD(wParam))
		{
		case IDOK:
		case IDCANCEL:
			EndDialog(hDlg, LOWORD(wParam));
			return static_cast<INT_PTR>(TRUE);

		default:
			return static_cast<INT_PTR>(FALSE);
		}

	default: 
    	return static_cast<INT_PTR>(FALSE);
    }
}

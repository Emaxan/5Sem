#include "stdafx.h"
#include "OSiSP_Laba2.h"
#include <winuser.h>


#define MAX_LOADSTRING 100
#define MAX_STRING_LENGTH 1024
#define STRING_COUNT 5
#define MAX_NUMBER 10

HINSTANCE hInst;
WCHAR szTitle[MAX_LOADSTRING];
WCHAR szWindowClass[MAX_LOADSTRING];
BOOL changeSize;
int rands[10][10];
TCHAR *szContent[STRING_COUNT] = {
	L"Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.",
	L"Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo.",
	L"Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus.",
	L"Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus.",
	L"Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc."
};

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

   HWND hWnd = CreateWindowW(szWindowClass, szTitle, WS_SIZEBOX | WS_SYSMENU | WS_MAXIMIZEBOX | WS_MINIMIZEBOX,
      CW_USEDEFAULT, 0, CW_USEDEFAULT, 0, nullptr, nullptr, hInstance, nullptr);

   if (!hWnd)
   {
      return FALSE;
   }

   ShowWindow(hWnd, nCmdShow);
   UpdateWindow(hWnd);
   DialogBox(hInst, MAKEINTRESOURCE(IDD_CHANGE_GRID_SIZE), hWnd, ChangeGridSize);
   return TRUE;
}

#pragma region Drawing

int rowsCount = 0;
int columnsCount = 0;
RECT rcClient;

BOOL DrawLine(HDC hdc, int x0, int y0, int x, int y)
{
	MoveToEx(hdc, x0, y0, nullptr);
	return LineTo(hdc, x, y);
}

int DrawRow(HDC hdc, int columnsWidth, int columnsCount, int yStart, int rowNumber)
{
	auto rect = new RECT();
	rect->bottom = 0;
	rect->left = 0;
	rect->top = yStart;
	rect->right = columnsWidth;
	int height = 0;
	for (int i = 0; i < columnsCount; i++) 
	{
		if (changeSize)
		{
			rands[rowNumber][i] = rand() % STRING_COUNT;
		}
		int h = DrawText(hdc, szContent[rands[rowNumber][i]], _tcslen(szContent[rands[rowNumber][i]]), rect, DT_CALCRECT | DT_WORDBREAK);
		if (height < h)
		{
			height = h;
		}
	}
	
	rect->bottom = height + yStart;

	for (int i = 0; i < columnsCount; i++) 
	{
		rect->left = i*columnsWidth;
		rect->right = (i+1)*columnsWidth;
		DrawText(hdc, szContent[rands[rowNumber][i]], _tcslen(szContent[rands[rowNumber][i]]), rect, DT_WORDBREAK);
	}

	return height;
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
	
	int width = rcClient.right;
	int rowsHeight = 0;
	int columnsWidth = width / columnsCount;
	
	DrawLine(hdc, 0, 0, columnsCount*columnsWidth, 0);
	for (int i = 0; i < rowsCount; i++) 
	{		
		rowsHeight += DrawRow(hdc, columnsWidth, columnsCount, 1+rowsHeight, i)+1;
		DrawLine(hdc, 0, rowsHeight, columnsCount*columnsWidth, rowsHeight);
	}

	for (auto i = 0; i <= columnsCount; i++) {
		DrawLine(hdc, i*columnsWidth, 0, i*columnsWidth, rowsHeight);
	}
	changeSize = FALSE;
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
			SetDlgItemText(hDlg, IDC_ERROR_MESSAGE, L"");
			TCHAR rows[3];
			TCHAR cols[3];

			GetDlgItemText(hDlg, IDC_ROWS, rows, sizeof rows);
			GetDlgItemText(hDlg, IDC_COLS, cols, sizeof cols);
			
			rowsCount = _ttoi(rows);
			columnsCount = _ttoi(cols);
			if(rowsCount == 0 || columnsCount == 0 || rowsCount > MAX_NUMBER || columnsCount > MAX_NUMBER)
			{
				SetDlgItemText(hDlg, IDC_ERROR_MESSAGE, L"Wrong numbers.\nPlease enter 1<=x<=10.");
				return static_cast<INT_PTR>(FALSE);
			}
			HWND hWnd = GetParent(hDlg);
			changeSize = TRUE;
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

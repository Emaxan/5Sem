using System;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Media;
using Microsoft.Win32;
using SPP_CustomThreadPool;

namespace SPP_Laba5
{
	public partial class MainWindow
	{
		private Brush _color;
	    private readonly CustomThreadPool _pool;

		public MainWindow()
		{
			InitializeComponent();
            _pool = CustomThreadPool.GetInstance(0, 1000);
		}


		private void CopyClick(object sender, MouseButtonEventArgs e)
		{
			throw new NotImplementedException();
		}

		private void StopClick(object sender, MouseButtonEventArgs e)
		{
			throw new NotImplementedException();
		}

		private void Border_MouseEnter(object sender, MouseEventArgs e)
		{
			var border = sender as Border;
			if(border == null)
			{
				return;
			}

			_color = border.BorderBrush;
			border.BorderBrush = new SolidColorBrush(Colors.Black);
		}

		private void Border_MouseLeave(object sender, MouseEventArgs e)
		{
			var border = sender as Border;
			if (border == null)
			{
				return;
			}

			border.BorderBrush = _color;
		}

		private void OpenSource_Click(object sender, RoutedEventArgs e)
		{
			var ofd = new OpenFileDialog
					{
						CheckFileExists = true,
						CheckPathExists = true,
						Multiselect = false
					};
			if(ofd.ShowDialog() != true)
			{
				return;
			}

		    LSource.Content = ofd.FileNames[0];
		}

		private void OpenDestination_Click(object sender, RoutedEventArgs e)
		{
			var ofd = new OpenFileDialog
					{
						Multiselect = false,
					    CheckFileExists = false,
					    CheckPathExists = false
            };
			if (ofd.ShowDialog() != true)
			{
				return;
			}

		    LDestination.Content = ofd.FileNames[0];
		}

	    private void IudThreadCount_OnKeyDown(object sender, KeyEventArgs e)
	    {
	        e.Handled = true;
	    }
	}
}

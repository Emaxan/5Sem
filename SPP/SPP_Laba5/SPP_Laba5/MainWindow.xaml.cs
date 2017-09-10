using System;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Media;
using Microsoft.Win32;

namespace SPP_Laba5
{
	/// <summary>
	/// Interaction logic for MainWindow.xaml
	/// </summary>
	public partial class MainWindow : Window
	{
		private Brush _color;


		public MainWindow()
		{
			InitializeComponent();
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
						Multiselect = false,
					};
			if(ofd.ShowDialog() != true)
			{
				return;
			}
		}

		private void OpenDestination_Click(object sender, RoutedEventArgs e)
		{
			var ofd = new OpenFileDialog
					{
						Multiselect = false,
					};
			if (ofd.ShowDialog() != true)
			{
				return;
			}
		}
	}
}

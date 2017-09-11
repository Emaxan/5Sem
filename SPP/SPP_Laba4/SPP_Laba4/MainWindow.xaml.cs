using System;
using System.Collections.Generic;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using SPP_Laba4.ArrayHelperExtension;

namespace SPP_Laba4
{
	public partial class MainWindow
	{
		private List<int> _array;


		public MainWindow()
		{
			InitializeComponent();
		}


		private void TbArrayLength_OnKeyDown(object sender, KeyEventArgs e)
		{
			switch (e.Key)
			{
				case Key.End:
				case Key.Home:
				case Key.Left:
				case Key.Right:
				case Key.Delete:
				case Key.D0:
				case Key.D1:
				case Key.D2:
				case Key.D3:
				case Key.D4:
				case Key.D5:
				case Key.D6:
				case Key.D7:
				case Key.D8:
				case Key.D9:
				case Key.NumPad0:
				case Key.NumPad1:
				case Key.NumPad2:
				case Key.NumPad3:
				case Key.NumPad4:
				case Key.NumPad5:
				case Key.NumPad6:
				case Key.NumPad7:
				case Key.NumPad8:
				case Key.NumPad9:
					break;
				default:
					e.Handled = true;
					break;
			}
		}

		private void BGenerate_OnClick(object sender, RoutedEventArgs e)
		{
			if(!int.TryParse(TbArrayLength.Text, out int length))
			{
				MessageBox.Show("Array length not a number", "Error");
				return;
			}

			SpArray.Children.Clear();
			var rand = new Random(DateTime.UtcNow.Millisecond);
			_array = new List<int>();
			for(var i = 0; i < length; i++)
			{
				var number = (byte)(rand.Next(10)*10);
				_array.Add(number);
				var label = new Label
							{
								Content = number
							};
				SpArray.Children.Add(label);
			}
		}

		private void BGetSum_Click(object sender, RoutedEventArgs e)
		{
			if(_array == null)
			{
				return;
			}

			try
			{
                var helper = new ArrayHelper.ArrayHelper();
			    helper.Sum(_array);
			}
			catch (OverflowException ex)
			{
				MessageBox.Show(ex.Message, "Error");
			}
		}
	}
}

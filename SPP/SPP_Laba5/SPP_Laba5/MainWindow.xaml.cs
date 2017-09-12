using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Media;
using Microsoft.Win32;
using SPP_CustomThreadPool;
using System.IO;

namespace SPP_Laba5
{
	public partial class MainWindow
	{
		private Brush _color;
	    private CustomThreadPool _pool;
	    private int _complete;

	    private const int Step = 1024*1024;


        public MainWindow()
		{
			InitializeComponent();
		}


		private void CopyClick(object sender, MouseButtonEventArgs e)
		{
		    if(_pool == null)
		    {
		        MessageBox.Show("You should enter and save value of thread count first.");
                return;
		    }

            if (LSource.Content == null)
            {
                MessageBox.Show("You should choose source file first.");
                return;
		    }

		    if(LDestination.Content == null)
		    {
		        MessageBox.Show("You should choose destination file first.");
                return;
		    }

		    _complete = 0;
            var source = LSource.Content;
		    var destination = LDestination.Content;
		    var sourceFile = new FileInfo(source.ToString());
            var destFile = new FileInfo(destination.ToString());
		    if(destFile.Exists)
		    {
		        destFile.Delete();
		    }
            var file = destFile.Create();
            file.Dispose();
            PbProgress.Maximum = sourceFile.Length;
		    var i = 0L;
		    while(i < sourceFile.Length)
		    {
		        var i1 = i;
		        _pool.QueueUserTask(() =>
		                            {
		                                lock(destFile)
		                                {
		                                    using(var outStream =
		                                        new FileStream(destFile.FullName, FileMode.Open, FileAccess.Write))
		                                    {
		                                        using(var inStream = sourceFile.OpenRead())
		                                        {
		                                            var start = i1;
		                                            inStream.Seek(start, SeekOrigin.Begin);
		                                            outStream.Seek(start, SeekOrigin.Begin);
		                                            var lenght = start + Step <= sourceFile.Length
		                                                             ? Step
		                                                             : sourceFile.Length - start;
		                                            for(var j = 0; j < lenght; j++)
		                                            {
		                                                var buf = (byte)inStream.ReadByte();
		                                                outStream.WriteByte(buf);
                                                        
		                                            }
		                                        }
		                                    }
		                                }

		                                return true;
		                            },
		                            ts =>
		                            {
		                                if(ts.Success)
		                                {
		                                    lock(this)
		                                    {
		                                        _complete += Step;
		                                        Dispatcher.Invoke(() =>
		                                                          {
		                                                              PbProgress.Value = _complete;
		                                                          });
		                                    }
		                                }
		                                else
		                                {
		                                    MessageBox.Show(ts.InnerException.Message);
		                                }
		                            });
		        i += Step;
		    }
		}

	    private void StopClick(object sender, MouseButtonEventArgs e)
		{

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

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            _pool?.Dispose();
        }

	    private void SaveThreadsCount_Click(object sender, RoutedEventArgs e)
	    {
	        if(!IudThreadCount.Value.HasValue)
	        {
	            return;
	        }

	        BSave.IsEnabled = false;
	        IudThreadCount.IsReadOnly = true;
	        _pool = CustomThreadPool.GetInstance(IudThreadCount.Value.Value, IudThreadCount.Value.Value);
        }
	}
}

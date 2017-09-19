using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Security.Cryptography;
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
		private const int Step = 1024*1024;
		private readonly List<ClientHandle> _currentTask = new List<ClientHandle>();
		private Brush _color;
		private int _complete;
		private FileInfo _destFile;
		private CustomThreadPool _pool;

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

			if(LSource.Content == null)
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
			_destFile = new FileInfo(destination.ToString());
			if(_destFile.Exists)
			{
				_destFile.Delete();
			}
			var file = _destFile.Create();
			file.Dispose();
			PbProgress.Maximum = sourceFile.Length;
			var i = 0L;
			while(i < sourceFile.Length)
			{
				var i1 = i;

				_currentTask.Add(_pool.QueueUserTask(() => Task(sourceFile, _destFile, i1), Callback));
				i += Step;
			}
		}

		private void Callback(TaskStatus ts)
		{
			if(ts.Success)
			{
				lock(this)
				{
					_complete += Step;
					Dispatcher.Invoke(
						() =>
						{
							PbProgress.Value = _complete;
						});
				}
			}
			else
			{
				MessageBox.Show(ts.InnerException.Message);
			}
		}

		private bool Task(FileInfo sourceFile, FileInfo destFile, long startPos)
		{
			lock(destFile)
			{
				using(var outStream = destFile.OpenWrite())
				{
					using(var inStream = sourceFile.OpenRead())
					{
						var start = startPos;
						inStream.Seek(start, SeekOrigin.Begin);
						outStream.Seek(start, SeekOrigin.Begin);
						var lenght = start + Step <= sourceFile.Length ? Step : sourceFile.Length - start;
						for(var j = 0; j < lenght; j++)
						{
							var buf = (byte)inStream.ReadByte();
							outStream.WriteByte(buf);
						}
					}
				}
			}

			return true;
		}

		private void StopClick(object sender, MouseButtonEventArgs e)
		{
			CustomThreadPool.Suspend = true;
			for(var i = _complete/Step + 1; i < _currentTask.Count; i++)
			{
				if(_currentTask[i].State != TaskState.NotStarted)
				{
					continue;
				}
				CustomThreadPool.CancelUserTask(_currentTask[i]);
			}
			CustomThreadPool.Suspend = false;
			var file = new FileInfo(LDestination.Content.ToString());
			lock(_destFile)
			{
				file.Delete();
			}
			PbProgress.Value = PbProgress.Maximum;
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
			if(border == null)
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
			if(ofd.ShowDialog() != true)
			{
				return;
			}

			LDestination.Content = ofd.FileNames[0];
		}

		private void IudThreadCount_OnKeyDown(object sender, KeyEventArgs e)
		{
			e.Handled = true;
		}

		private void Window_Closing(object sender, CancelEventArgs e)
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

		private void BCheck_OnClick(object sender, RoutedEventArgs e)
		{
			using(var md5 = MD5.Create())
			{
				using(var source = new FileInfo(LSource.Content.ToString()).OpenRead())
				{
					using(var dest = new FileInfo(LDestination.Content.ToString()).OpenRead())
					{
						var result = true;
						var sourceHash = md5.ComputeHash(source);
						var destinationHash = md5.ComputeHash(dest);
						for (var i = 0; i < sourceHash.Length; i++)
						{
							if (sourceHash[i] != destinationHash[i])
							{
								result = false;
							}
						}
						MessageBox.Show(result ? "True" : "False");
					}
				}
			}
				
		}
	}
}

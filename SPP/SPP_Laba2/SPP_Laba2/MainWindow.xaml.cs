using System;
using System.IO;
using System.Linq;
using System.Security.AccessControl;
using System.Security.Principal;
using System.Windows;
using System.Windows.Controls;
using SPP_CustomThreadPool;

namespace SPP_Laba2
{
	public partial class MainWindow
	{
		public CustomThreadPool Pool;
		private string _sourcePath;
		private string _destinationPath;

		public MainWindow()
		{
			InitializeComponent();
			foreach(var drive in DriveInfo.GetDrives().Where(d => d.IsReady))
			{
				var source = new TreeViewItem
							{
								Header = drive.Name,
								Tag = drive.RootDirectory.FullName
							};
				source.Expanded += Item_Expanded;
				source.Items.Add("*");

				var destination = new TreeViewItem
							{
								Header = drive.Name,
								Tag = drive.RootDirectory.FullName
							};
				destination.Expanded += Item_Expanded;
				destination.Items.Add("*");

				TwSource.Items.Add(source);
				TwDestination.Items.Add(destination);
			}

			//Pool = CustomThreadPool.GetInstance;
			//var task = Pool.QueueUserTask(
			//	() =>
			//	{
			//		// do smth
			//	},
			//	ts =>
			//	{
			//		// callback
			//	});
		}

		private void Item_Expanded(object sender, RoutedEventArgs e)
		{
			var root = sender as TreeViewItem;
			if(!(root?.Items[0] is string))
			{
				return;
			}
			root.Items.Clear();
			var dir = new DirectoryInfo((string)root.Tag);
			foreach(var directory in dir.GetDirectories().Where(d => !d.Attributes.HasFlag(FileAttributes.Hidden)))
			{
				var item = new TreeViewItem
							{
								Header = directory.Name,
								Tag = directory.FullName
							};
				item.Items.Add("*");
				item.Expanded += Item_Expanded;
				root.Items.Add(item);
			}

		}

        private void OnSourceChanged(object sender, RoutedPropertyChangedEventArgs<object> e)
        {
	        _sourcePath = (string)((TreeViewItem)e.NewValue).Tag;
        }

		private void OnDestinationChanged(object sender, RoutedPropertyChangedEventArgs<object> e)
		{
			_destinationPath = (string)((TreeViewItem)e.NewValue).Tag;
		}

		private void OnCopyClick(object sender, RoutedEventArgs e)
		{
			if(_destinationPath==null || !new DirectoryInfo(_destinationPath).Exists)
			{
				MessageBox.Show(this, "You should select existing destination folder first!", "Error!");
				return;
			}
			if(_sourcePath == null || !new DirectoryInfo(_sourcePath).Exists)
			{
				MessageBox.Show(this, "You should select existing source folder first!", "Error!");
				return;
			}

			CopyFolder(_sourcePath, _destinationPath);
		}

		private void CopyFolder(string sourcePath, string destinationPath)
		{
			var dir = new DirectoryInfo(sourcePath);
			foreach(var directory in dir.GetDirectories())
			{
				var newDir = new DirectoryInfo(Path.Combine(destinationPath, directory.Name));
				LbLog.Items.Add("Create " + newDir.FullName);
				newDir.Create();
				CopyFolder(directory.FullName, newDir.FullName);
			}
			foreach (var file in dir.GetFiles())
			{
				try
				{
					var destination = Path.Combine(destinationPath, file.Name);
					LbLog.Items.Add(file.FullName + " -> " + destination);
					file.CopyTo(destination);
				}
				catch(Exception ex)
				{

					LbLog.Items.Add(ex.Message);
				}
			}
		}
	}
}

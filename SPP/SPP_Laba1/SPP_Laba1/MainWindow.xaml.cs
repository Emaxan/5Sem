using System;
using System.ComponentModel;
using System.Linq;
using System.Reflection;
using System.Windows;
using System.Windows.Controls;
using Microsoft.Win32;

namespace SPP_Laba1
{
    public partial class MainWindow
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void LoadAssembly_Click(object sender, RoutedEventArgs e)
        {
            var ofd = new OpenFileDialog
                      {
                          Title = "Choose assembly",
                          CheckFileExists = true,
                          CheckPathExists = true,
                          Filter = "Dynamic library files (*.dll)|*.dll|Executable files (*.exe)|*.exe",
                          Multiselect = true
            };

            if(ofd.ShowDialog(this) != true)
            {
                return;
            }
            
            foreach (var fileName in ofd.FileNames)
            {
                TreeView.Items.Add(LoadAssembly(fileName));
            }

            TreeView.Items.SortDescriptions.Clear();
            TreeView.Items.SortDescriptions.Add(new SortDescription("Header", ListSortDirection.Ascending));
        }

        private TreeViewItem LoadAssembly(string fileName)
        {
            var assembly = Assembly.LoadFrom(fileName);
            var item = new TreeViewItem
                       {
                           Header = assembly.FullName
                       };
            foreach (var type in assembly.ExportedTypes)
            {
                item.Items.Add(LoadType(type));
            }

            item.Items.SortDescriptions.Clear();
            item.Items.SortDescriptions.Add(new SortDescription("Header", ListSortDirection.Ascending));

            return item;
        }

        private TreeViewItem LoadType(Type type)
        {
            var item = new TreeViewItem
                       {
                           Header = type.FullName
                       };

            foreach (var member in type.GetMembers())
            {
                item.Items.Add(LoadMember(member));
            }

            item.Items.SortDescriptions.Clear();
            item.Items.SortDescriptions.Add(new SortDescription("Header", ListSortDirection.Ascending));

            return item;
        }

        private TreeViewItem LoadMember(MemberInfo member)
        {
            const int nameLength = 60;
            var item = new TreeViewItem();
            var header = member.Name;
            header = header.PadRight(nameLength);
            header += "MemberType : " + Enum.GetName(typeof(MemberTypes), member.MemberType);
            header = header.PadRight(nameLength + 40);
            switch (member.MemberType)
            {
                case MemberTypes.Event:
                case MemberTypes.Field:
                case MemberTypes.TypeInfo:
                case MemberTypes.Custom:
                case MemberTypes.NestedType:
                case MemberTypes.All:
                    break;
                case MemberTypes.Constructor:
                    header += ((ConstructorInfo)member).DeclaringType?.Name;
                    break;
                case MemberTypes.Method:
                    var method = (MethodInfo)member;
                    header += method.ReturnType.Name;
                    header = header.PadRight(nameLength + 100);
                    if(method.ReturnType.IsGenericType)
                    {
                        header += method.ReturnType.GetGenericArguments().
                            Aggregate("Generic parameters : ", (cur, type) => cur + type.Name + ",");
                        header = header.Remove(header.Length - 1);
                    }
                    
                    break;
                case MemberTypes.Property:
                    header += ((PropertyInfo)member).PropertyType.Name;
                    break;
                default:
                    throw new ArgumentOutOfRangeException();
            }

            header = header.PadRight(nameLength + 180);
            item.Header = header;

            item.Items.SortDescriptions.Clear();
            item.Items.SortDescriptions.Add(new SortDescription("Header", ListSortDirection.Ascending));

            return item;
        }

        private string getTypeName(Type type)
        {
            var name = "";
            name += type.Name;
            if(!type.IsGenericType)
            {
                return name;
            }

            name += type.GetGenericArguments().
                Aggregate("Generic parameters : ", (cur, t) => cur + t.Name + ",");
            name = name.Remove(name.Length - 1);
            return name;
        }
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;

namespace Laba4_2
{
    public partial class MainWindow
    {
        private Point _curPoint = new Point(1, 1, 1, 1, 1), _delta = new Point(0, 0, 0, 0, 0);
        private List<Row> _list;

        private void Button_Click(object sender, System.Windows.RoutedEventArgs e)
        {

            _curPoint = new Point(1, 1, 1, 1, 1);
            _k = 0;

            _list = new List<Row>
                    {
                        new Row(_curPoint, _k, _eps)
                    };

            FirstStep();
            do
            {
                NextStep(false);
                _k++;
                if (CheckExit())
                    break;
                _curPoint += _delta;
                _list.Add(new Row(_curPoint, _k, _eps));
                if (_k > 1000)
                    break;
            }
            while (true);

            grid.ItemsSource = _list;

            do
            {
                _curPoint = new Point(1, 1, 1, 1, 1);
                _k = 0;

                _list = new List<Row>
                        {
                            new Row(_curPoint, _k, _eps)
                        };

                FirstStep();
                do
                {
                    NextStep(true);
                    _k++;
                    if(CheckExit())
                        break;
                    _curPoint += _delta;
                    _list.Add(new Row(_curPoint, _k, _eps));
                    if(_k > 1000)
                        break;
                }
                while(true);
            }
            while(_list.Last().f > 3500);

            grid1.ItemsSource = _list;
        }

        private double _eps = 0.001, _k;

        public static double Func(Point p)
        {
            return 12000/p.X1 + 20*p.X1 
                    + 30000/p.X2 + 3*p.X2 
                    + 44800/p.X3 + 7*p.X3 
                    + 9000/p.X4 + 3*p.X4 
                    + 360/p.X5 + 8*p.X5;
        }

        private bool CheckConstraints(Point p)
        {
            var g1 = p.X1 >= 1;
            var g2 = p.X2 >= 1;
            var g3 = p.X3 >= 1;
            var g4 = p.X4 >= 1;
            var g5 = p.X5 >= 1;
            var g6 = 4*p.X1 + 3 * p.X2 + 5 * p.X3 + 40 * p.X4 + 20 * p.X5 <= 1500;

            return g1 && g2 && g3 && g4 && g5 && g6;
        }

        private bool CheckExit()
        {
            var c1 = Math.Abs(Func(_curPoint) - Func(_curPoint + _delta)) < _eps;
            var c2 = _curPoint.Dist(_curPoint + _delta) < _eps;

            return c1 && c2;
        }

        public MainWindow()
        {
            InitializeComponent();
        }

        private void NextStep(bool constraints)
        {
            _delta.Clear();
            int sign;

            StepByX5();
            //if (_delta.X5 < 0)
            //    sign = 1;
            //else
            //    sign = -1;
            //while (!CheckConstraints(_curPoint + _delta))
            //{
            //    _delta.X5 += sign * 0.01;
            //    if (_delta.X5 < 1)
            //        break;
            //}

            StepByX1();
            //if (_delta.X1 < 0)
            //    sign = 1;
            //else
            //    sign = -1;
            //while (!CheckConstraints(_curPoint + _delta))
            //{
            //    _delta.X1 += sign * 0.01;
            //    if(_delta.X1 < 1)
            //        break;
            //}

            StepByX2();
            //if (_delta.X2 < 0)
            //    sign = 1;
            //else
            //    sign = -1;
            //while (!CheckConstraints(_curPoint + _delta))
            //{
            //    _delta.X2 += sign * 0.01;
            //    if (_delta.X2 < 1)
            //        break;
            //}

            StepByX4();
            //if (_delta.X4 < 0)
            //    sign = 1;
            //else
            //    sign = -1;
            //while (!CheckConstraints(_curPoint + _delta))
            //{
            //    _delta.X4 += sign * 0.01;
            //    if (_delta.X4 < 1)
            //        break;
            //}

            StepByX3();
            //if (_delta.X3 < 0)
            //    sign = 1;
            //else
            //    sign = -1;
            //while (!CheckConstraints(_curPoint + _delta))
            //{
            //    _delta.X3 += sign * 0.01;
            //    if (_delta.X3 < 1)
            //        break;
            //}

            if(!constraints) return;
            if (!CheckConstraints(_curPoint + _delta))
            {
                ReturnPoint(_delta);
            }

        }

        private void ReturnPoint(Point p)
        {
            var r = new Random(DateTime.Now.Millisecond);
            while (!CheckConstraints(_curPoint + p))
            {
                int sign;
                switch (r.Next(0, 5))
                {
                    case 0:
                        if (p.X1 <= 0)
                            sign = 1;
                        else
                            sign = -1;
                        p.X1 += sign;// * 0.1;
                        break;
                    case 1:
                        if (p.X2 <= 0)
                            sign = 1;
                        else
                            sign = -1;
                        p.X2 += sign;// * 0.1;
                        break;
                    case 2:
                        if (p.X3 <= 0)
                            sign = 1;
                        else
                            sign = -1;
                        p.X3 += sign;// * 0.1;
                        break;
                    case 3:
                        if (p.X4 <= 0)
                            sign = 1;
                        else
                            sign = -1;
                        p.X4 += sign;// * 0.1;
                        break;
                    case 4:
                        if (p.X5 <= 0)
                            sign = 1;
                        else
                            sign = -1;
                        p.X5 += sign;// * 0.1;
                        break;
                }
            }
            //List<SortObject> list = new List<SortObject>
            //                        {
            //                            new SortObject
            //                            {
            //                                Cost = (4/p.X1),
            //                                Count = p.X1,
            //                                Size = 4,
            //                            },
            //                            new SortObject
            //                            {
            //                                Cost = (3/p.X2),
            //                                Count = p.X2,
            //                                Size = 3,
            //                            },
            //                            new SortObject
            //                            {
            //                                Cost = (5/p.X3),
            //                                Count = p.X3,
            //                                Size = 5,
            //                            },
            //                            new SortObject
            //                            {
            //                                Cost = (40/p.X4),
            //                                Count = p.X4,
            //                                Size = 40,
            //                            },
            //                            new SortObject
            //                            {
            //                                Cost = (20/p.X5),
            //                                Count = p.X5,
            //                                Size = 20,
            //                            }
            //                        };
            //list.Sort(Comparison);
            //while(!CheckConstraints(_curPoint + Point.StaticFromList(list)))
            //{
            //    int sign;
            //    if(list[0].Count <= 0)
            //        sign = 1;
            //    else
            //        sign = -1;
            //    if (list[0].Cost < 0.001)
            //        break;
            //    list[0].Count += sign*0.1;
            //    list[0].Cost = list[0].Size/list[0].Cost;
            //    list.Sort(Comparison);
            //}
            //p.FromList(list);
        }

        private int Comparison(SortObject o1, SortObject o2)
        {
            if (o1.Cost > o2.Cost)
                return 1;

            if (o1.Cost < o2.Cost)
                return -1;

            return 0;
        }

        private void FirstStep()
        {
            _delta.Clear();
            StepByX1();
            _curPoint += _delta;
        }

        private void StepByX1()
        {
            var h = Math.Sqrt(600) - _curPoint.X1;
            _delta += new Point(h, 0, 0, 0, 0);
        }

        private void StepByX2()
        {
            var h = 100 - _curPoint.X2;
            _delta += new Point(0, h, 0, 0, 0);
        }
        
        private void StepByX3()
        {
            var h = 80 - _curPoint.X3;
            _delta += new Point(0, 0, h, 0, 0);
        }

        private void StepByX4()
        {
            var h = Math.Sqrt(3000) - _curPoint.X4;
            _delta += new Point(0, 0, 0, h, 0);
        }
        
        private void StepByX5()
        {
            var h = Math.Sqrt(40) - _curPoint.X5;
            _delta += new Point(0, 0, 0, 0, h);
        }
    }

    public class Row
    {
        public Row(Point p, double k, double eps)
        {
            this.k = k;
            this.eps = eps;
            this.p = p;
            this.x1 = p.X1;
            this.x2 = p.X2;
            this.x3 = p.X3;
            this.x4 = p.X4;
            this.x5 = p.X5;
            this.size = 4*p.X1 + 3*p.X2 + 5*p.X3 + 40*p.X4 + 20*p.X5;
            this.f = MainWindow.Func(p);
        }

        public double f { get; }
        public double x1 { get; }
        public double x2 { get; }
        public double x3 { get; }
        public double x4 { get; }
        public double x5 { get; }
        public Point p { get; }
        public double k { get; }
        public double eps { get; }
        public double size { get; }
    }

    public class Point
    {
        public Point(double x1, double x2, double x3, double x4, double x5)
        {
            X1 = x1;
            X2 = x2;
            X3 = x3;
            X4 = x4;
            X5 = x5;
        }

        public double X1 { get; set; }
        public double X2 { get; set; }
        public double X3 { get; set; }
        public double X4 { get; set; }
        public double X5 { get; set; }

        public static Point operator+ (Point p1, Point p2)
        {
            return new Point(p1.X1 + p2.X1, p1.X2 + p2.X2, p1.X3 + p2.X3, p1.X4 + p2.X4, p1.X5 + p2.X5);
        }

        public double Dist(Point p)
        {
            return Math.Sqrt(
                (p.X1 - X1)*(p.X1 - X1)
                + (p.X2 - X2)*(p.X2 - X2)
                + (p.X3 - X3)*(p.X3 - X3)
                + (p.X4 - X4)*(p.X4 - X4)
                + (p.X5 - X5)*(p.X5 - X5));
        }

        public void Clear()
        {
            X1 = 0;
            X2 = 0;
            X3 = 0;
            X4 = 0;
            X5 = 0;
        }

        public override string ToString()
        {
            return $"({X1}, {X2}, {X3}, {X4}, {X5})";
        }

        public static Point StaticFromList(List<SortObject> list)
        {
            var x1 = list.First(so => so.Size == 4).Count;
            var x2 = list.First(so => so.Size == 3).Count;
            var x3 = list.First(so => so.Size == 5).Count;
            var x4 = list.First(so => so.Size == 40).Count;
            var x5 = list.First(so => so.Size == 20).Count;

            return new Point(x1,x2,x3,x4,x5);
        }

        public void FromList(List<SortObject> list)
        {
            X1 = list.First(so => so.Size == 4).Count;
            X2 = list.First(so => so.Size == 3).Count;
            X3 = list.First(so => so.Size == 5).Count;
            X4 = list.First(so => so.Size == 40).Count;
            X5 = list.First(so => so.Size == 20).Count;
        }
    }

    public class SortObject
    {
        public double Cost { get; set; }
        public double Count { get; set; }
        public int Size { get; set; }

        public override string ToString()
        {
            return $"{Cost}, {Count}, {Size}";
        }
    }
}

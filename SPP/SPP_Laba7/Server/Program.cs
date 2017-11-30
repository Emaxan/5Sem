using System;
using System.ServiceModel;

namespace Server
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {
                Console.Title = "SERVER";

                ServiceHost host = new ServiceHost(typeof(Service));

                host.Open();

                Console.WriteLine("App ready to start");
                Console.ReadKey();
                host.Close();
            }
            catch(Exception ex)
            {
                Console.WriteLine(ex.Message);
                Console.ReadLine();
            }

        }
    }
}

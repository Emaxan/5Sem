﻿using System;
using System.ServiceModel;
using System.ServiceModel.Description;
using SPP_Laba6;

namespace ServiceHost
{
    public static class Program
    {
        public static void Main(string[] args)
        {
            var httpBaseAddress = new Uri("http://localhost:13666/MessageService");

            using(var messageQueueHost = new System.ServiceModel.ServiceHost(typeof(MessageQueue), httpBaseAddress))
            {
                messageQueueHost.AddServiceEndpoint(typeof(IMessageQueue), new WSHttpBinding(), "");

                messageQueueHost.Opened += MessageQueueHost_Opened;

                var serviceBehavior = new ServiceMetadataBehavior
                                      {
                                          HttpGetEnabled = true
                                      };
                messageQueueHost.Description.Behaviors.Add(serviceBehavior);

                messageQueueHost.Open();
                Console.WriteLine($"Service is live now at: {httpBaseAddress}");
                Console.ReadKey();
                messageQueueHost.Close();
            }
        }

        private static void MessageQueueHost_Opened(object sender, EventArgs e)
        {
            Worker worker = new Worker();
            worker.Start();
        }
    }
}
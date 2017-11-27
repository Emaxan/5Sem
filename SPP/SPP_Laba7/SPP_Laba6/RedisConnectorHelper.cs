using System;
using StackExchange.Redis;

namespace SPP_Laba6
{
    public class RedisConnectorHelper
    {
        private static readonly Lazy<ConnectionMultiplexer> LazyConnection;

        public static ConnectionMultiplexer Connection
        {
            get { return LazyConnection.Value; }
        }

        static RedisConnectorHelper()
        {
            LazyConnection = new Lazy<ConnectionMultiplexer>(() => ConnectionMultiplexer.Connect("localhost"));
        }
    }
}
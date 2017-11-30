namespace Server
{
    class Query
    {
        public string RedisKey { get; }
        public int Priority { get; }

        public Query(string redisKey, int priority)
        {
            RedisKey = redisKey;
            Priority = priority;
        }

    }
}

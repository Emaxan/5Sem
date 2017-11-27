using System;

namespace SPP_Laba6
{
    public class Message : IBaseJob
    {
        public string Msg { get; }

        public string Parameters { get; }

        public int Priority { get; }


        public Message(string message, string parameters, int priority)
        {
            Msg = message;
            Parameters = parameters;
            Priority = priority;
            Type = GetType();
        }

        public Type Type { get; }

        public string Perform()
        {
            return $"Message performed.\n{this}";
        }

        public override string ToString()
        {
            return $"Message: {Msg}\nParameters: {Parameters}\n\n";
        }
    }
}
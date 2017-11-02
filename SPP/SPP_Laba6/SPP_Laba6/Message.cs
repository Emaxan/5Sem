using System;

namespace SPP_Laba6
{
    public class Message
    {
        public string Msg { get; }

        public string Parameters { get; }


        public Message(string message, string parameters)
        {
            Msg = message;
            Parameters = parameters;
        }

        public static string Perform(Message message)
        {
            return $"Message performed.\n{message}";
        }

        public override string ToString()
        {
            return $"Message: {Msg}\nParameters: {Parameters}\n\n";
        }
    }
}
using System;

namespace SPP_Laba6
{
    public class Message
    {
        private string _message;
        private string _parameters;

        public Message(string message, string parameters)
        {
            _message = message;
            _parameters = parameters;
        }

        public static void Perform(Message message)
        {
            throw new NotImplementedException(nameof(Perform));
        }
    }
}
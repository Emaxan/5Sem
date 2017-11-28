using System;
using System.Runtime.Serialization;

namespace Service_Contract
{
    [DataContract]
    public class QClass : IBaseJob 
    {
        [DataMember]
        public int ObjId { get; set; }
        [DataMember]
        public string Message { get; set; }
        [DataMember]
        public string Params { get; set; }

        public QClass(int objId, string message, string param)
        {
            ObjId = objId;
            Message = message;
            Params = param;
        }

        public void Perform()
        {
            Console.WriteLine($"Object with ID: {ObjId} with message: {Message} and parameters: {Params} removed.");
        }
    }
}

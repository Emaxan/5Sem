using System.Runtime.Serialization;

namespace Service_Contract
{
    [DataContract]
    public class QMessage
    {
        [DataMember]
        public string Obj { get; set; }
        [DataMember]
        public string ClassName { get; set; }
    }
}

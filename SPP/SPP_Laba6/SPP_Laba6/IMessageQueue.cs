using System;
using System.ServiceModel;

namespace SPP_Laba6
{
	[ServiceContract]
	public interface IMessageQueue
	{
        [OperationContract]
	    void AddMassage(string message, string parameters);

        [OperationContract]
	    bool RemoveMessage();

	    [OperationContract]
	    void Dump();

	    [OperationContract]
	    void Restore();

    }


    //// Use a data contract as illustrated in the sample below to add composite types to service operations.
	//[DataContract]
	//public class CompositeType
	//{
	//    [DataMember]
	//	public bool BoolValue { get; set; } = true;

	//    [DataMember]
	//	public string StringValue { get; set; } = "Hello ";
	//}
}

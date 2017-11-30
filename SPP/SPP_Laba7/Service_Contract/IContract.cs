using System.ServiceModel;

namespace Service_Contract
{
    [ServiceContract]
    public interface IContract
    {
        [OperationContract]
        void AddMessage(string obj);
        [OperationContract]
        bool RemoveMessage(string obj);
        [OperationContract]
        void DumpQuery(object obj);
        [OperationContract]
        void RestoreQuery();
    }
}

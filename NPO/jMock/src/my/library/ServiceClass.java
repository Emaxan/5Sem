package my.library;

public interface ServiceClass {
    void setDataAccess(DataAccess dataAccess);

    String getEmail(int id) throws ServiceException;
}

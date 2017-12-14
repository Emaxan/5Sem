package my.library;

public class ServiceClassImpl implements ServiceClass {

    private DataAccess dataAccess;

    @Override
    public void setDataAccess(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public String getEmail(int id) throws ServiceException {
        String email = dataAccess.getEmail(id);
        if(email == null){
            throw new ServiceException("Wrong Id");
        }

        return email;
    }

}

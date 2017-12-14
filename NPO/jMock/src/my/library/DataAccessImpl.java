package my.library;

public class DataAccessImpl implements DataAccess {

    @Override
    public String getEmail(int id) {
        if(id == 5){
            return "myCoolEmail";
        }

        return null;
    }
}

package by.bsuir.MY;

import by.bsuir.MY.dal.dbcontext.DBContext;
import by.bsuir.MY.dal.dbcontext.FilesDBContext;
import by.bsuir.MY.domain.File;
import by.bsuir.MY.domain.exception.WrongDataException;
import by.bsuir.MY.view.App;

import java.io.Console;
import java.io.IOException;

/**
 * .
 */
public final class Main {

    /**
     * .
     */
    private Main() {
    }

    /**
     * .
     *
     * @param args .
     */
    public static void main(final String[] args){
        String dbPath = System.getProperty("user.dir") + "\\database\\";
        DBContext db = null;
        try {
            db = new FilesDBContext(dbPath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        App app = new App(db);
        app.start();
    }
}

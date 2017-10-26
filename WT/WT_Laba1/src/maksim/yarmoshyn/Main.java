////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn;

import maksim.yarmoshyn.dal.dbcontext.DBContext;
import maksim.yarmoshyn.dal.dbcontext.FilesDBContext;
import maksim.yarmoshyn.pl.App;

import java.io.IOException;

/**
 * Main class of the program. Create App object and start executing.
 */
public final class Main {

    /**
     * Empty private constructor.
     */
    private Main() {
    }

    /**
     * Start application.
     *
     * @param args Arguments of cli.
     */
    public static void main(final String[] args) {
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

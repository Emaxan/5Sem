////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.pl.command;

import maksim.yarmoshyn.pl.App;
import maksim.yarmoshyn.pl.state.ShowBookState;

/**
 * Command for showing all books in database.
 */
public class ShowBooksCommand extends BaseCommand implements Command {

    /**
     * Instance of Application.
     */
    private App app;
    /**
     * Command to print page.
     */
    private Command print;

    /**
     * Create new instance of {@link ShowBooksCommand}.
     *
     * @param application Instance of application.
     */
    public ShowBooksCommand(final App application) {
        super("Show books", application.getCtx());
        app = application;
        print = new CurrentPageCommand(application);
    }

    /**
     * Show all books in database.
     */
    @Override
    public void execute() {
        int number = 1;
        ((ShowBookState) app.getShowBookState()).setCurrentPage(number);
        print.execute();
    }
}

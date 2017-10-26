package maksim.yarmoshyn.pl.command;

import maksim.yarmoshyn.pl.App;
import maksim.yarmoshyn.pl.state.ShowBookState;


/**
 * Command to change current page to previous.
 */
public class PrevPageCommand extends BaseCommand implements Command {
    /**
     * Instance of Application.
     */
    private App app;
    /**
     * Command to print page.
     */
    private Command print;

    /**
     * Initialise message, that describe command.
     *
     * @param application Instance of application.
     */
    public PrevPageCommand(final App application) {
        super("Previous page", application.getCtx());
        app = application;
        print = new CurrentPageCommand(application);
    }

    /**
     * Execute the command.
     */
    @Override
    public void execute() {
        int number =
                ((ShowBookState)
                        app.getShowBookState()).getCurrentPage() - 1;
        if (number < 1) {
            number++;
        }
        ((ShowBookState) app.getShowBookState()).setCurrentPage(number);
        print.execute();
    }
}

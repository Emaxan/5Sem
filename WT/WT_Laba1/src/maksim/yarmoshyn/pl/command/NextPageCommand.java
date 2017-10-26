package maksim.yarmoshyn.pl.command;

import maksim.yarmoshyn.bll.service.BookService;
import maksim.yarmoshyn.bll.service.ServiceProvider;
import maksim.yarmoshyn.pl.App;
import maksim.yarmoshyn.pl.state.ShowBookState;

/**
 * Command to Change current page to next.
 */
public class NextPageCommand extends BaseCommand implements Command {
    /**
     * Book service.
     */
    private final BookService bookService;
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
    public NextPageCommand(final App application) {
        super("Next page", application.getCtx());
        app = application;
        print = new CurrentPageCommand(application);

        ServiceProvider sp = new ServiceProvider(getCtx());
        this.bookService = sp.getBookService();
    }

    /**
     * Execute the command.
     */
    @Override
    public void execute() {
        int number =
                ((ShowBookState)
                        app.getShowBookState()).getCurrentPage() + 1;

        int pageCount = bookService.getPageCount();

        if (number > pageCount) {
            number--;
        }
        ((ShowBookState) app.getShowBookState()).setCurrentPage(number);
        print.execute();
    }
}

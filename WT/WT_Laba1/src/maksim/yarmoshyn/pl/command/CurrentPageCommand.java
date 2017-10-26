package maksim.yarmoshyn.pl.command;

import maksim.yarmoshyn.bll.dto.BookDto;
import maksim.yarmoshyn.bll.service.BookService;
import maksim.yarmoshyn.bll.service.ServiceProvider;
import maksim.yarmoshyn.pl.App;
import maksim.yarmoshyn.pl.state.ShowBookState;

import java.util.List;

/**
 * Command to print current page.
 */
public class CurrentPageCommand extends BaseCommand implements Command {
    /**
     * Book service.
     */
    private final BookService bookService;
    /**
     * Instance of Application.
     */
    private App app;

    /**
     * Initialise message, that describe command.
     *
     * @param application Instance of application.
     */
    public CurrentPageCommand(final App application) {
        super("Print current page", application.getCtx());
        app = application;

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
                        app.getShowBookState()).getCurrentPage();
        List<BookDto> books = bookService.getPage(number - 1);
        number = (number - 1) * bookService.getPageSize() + 1;
        for (BookDto book : books) {
            System.out.println(
                    number++ + ") " + book.getAuthor()
                            + " \"" + book.getName() + "\"");
        }
        System.out.println();
        app.setCurrentState(app.getShowBookState());
    }
}

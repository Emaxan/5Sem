package maksim.yarmoshyn.pl.command;

import maksim.yarmoshyn.bll.dto.BookDto;
import maksim.yarmoshyn.bll.service.BookService;
import maksim.yarmoshyn.bll.service.ServiceProvider;
import maksim.yarmoshyn.pl.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Command to add book.
 */
public class AddBookCommand extends BaseCommand implements Command {
    /**
     * Instance of Application.
     */
    private App app;
    /**
     * Book service.
     */
    private BookService bookService;

    /**
     * Initialise message, that describe command.
     *
     * @param application Instance of application.
     */
    public AddBookCommand(final App application) {
        super("Add book", application.getCtx());
        app = application;

        ServiceProvider sp = new ServiceProvider(getCtx());
        bookService = sp.getBookService();
    }

    /**
     * Execute the command.
     */
    @Override
    public void execute() {
        BufferedReader rd =
                new BufferedReader(new InputStreamReader(System.in));
        BookDto b = new BookDto();
        String line;
        try {
            System.out.print("Enter author of the book: ");
            line = rd.readLine();
            b.setAuthor(line);

            System.out.print("Enter name of the book: ");
            line = rd.readLine();
            b.setName(line);

            System.out.print("Enter year of the book: ");
            line = rd.readLine();
            int s;
            try {
                s = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                return;
            }
            b.setYear(s);

        } catch (IOException e) {
            System.out.println("Input failed. " + e.getMessage());
            return;
        }
        bookService.addBook(b);
        System.out.println();
        System.out.println("Successfully added");
    }
}

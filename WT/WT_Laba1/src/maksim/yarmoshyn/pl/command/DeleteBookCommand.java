package maksim.yarmoshyn.pl.command;

import maksim.yarmoshyn.bll.service.BookService;
import maksim.yarmoshyn.bll.service.ServiceProvider;
import maksim.yarmoshyn.pl.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Command to delete book.
 */
public class DeleteBookCommand extends BaseCommand implements Command {
    /**
     * Book service.
     */
    private BookService bookService;

    /**
     * Initialise message, that describe command.
     *
     * @param application Instance of application.
     */
    public DeleteBookCommand(final App application) {
        super("Delete book", application.getCtx());

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
        try {
            String line;
            System.out.print("Enter number of the book: ");
            line = rd.readLine();
            int s;
            try {
                s = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                return;
            }
            bookService.deleteBook(s - 1);
        } catch (IOException e) {
            System.out.println("Input failed. " + e.getMessage());
        }

        System.out.println();
        System.out.println("Successfully deleted.");
    }
}

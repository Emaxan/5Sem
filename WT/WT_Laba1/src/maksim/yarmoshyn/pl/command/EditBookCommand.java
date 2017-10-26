package maksim.yarmoshyn.pl.command;

import maksim.yarmoshyn.bll.dto.BookDto;
import maksim.yarmoshyn.bll.service.BookService;
import maksim.yarmoshyn.bll.service.ServiceProvider;
import maksim.yarmoshyn.pl.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Command to edit book.
 */
public class EditBookCommand extends BaseCommand implements Command {
    /**
     * Book service.
     */
    private BookService bookService;

    /**
     * Initialise message, that describe command.
     *
     * @param application Instance of application.
     */
    public EditBookCommand(final App application) {
        super("Edit book", application.getCtx());

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
            BookDto b;
            String line;

            System.out.print("Enter number of the book: ");
            line = rd.readLine();
            int s;
            try {
                s = Integer.parseInt(line) - 1;
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                return;
            }

            b = bookService.getAll().get(s);

            System.out.println();
            System.out.println("Previous author of the book: " + b.getAuthor());
            System.out.print("Enter new author of the book: ");
            line = rd.readLine();
            b.setAuthor(line);

            System.out.println();
            System.out.println("Previous name of the book: " + b.getName());
            System.out.print("Enter new name of the book: ");
            line = rd.readLine();
            b.setName(line);

            System.out.println();
            System.out.println("Previous year of the book: " + b.getYear());
            System.out.print("Enter new year of the book: ");
            line = rd.readLine();
            int year;
            try {
                year = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                return;
            }
            b.setYear(year);
            bookService.editBook(s, b);
        } catch (IOException e) {
            System.out.println("Input failed. " + e.getMessage());
        }

        System.out.println();
        System.out.println("Successfully changed.");
    }
}

////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.pl.command;

import maksim.yarmoshyn.bll.dto.BookDto;
import maksim.yarmoshyn.bll.service.BookService;
import maksim.yarmoshyn.bll.service.ServiceProvider;
import maksim.yarmoshyn.pl.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Command for searching book in database.
 */
public class SearchBookCommand extends BaseCommand implements Command {

    /**
     * Book service.
     */
    private BookService bookService;

    /**
     * Create new instance of {@link SearchBookCommand}.
     *
     * @param application Instance of application.
     */
    public SearchBookCommand(final App application) {
        super("Search book", application.getCtx());

        ServiceProvider sp = new ServiceProvider(getCtx());
        bookService = sp.getBookService();
    }

    /**
     * Search book in database.
     */
    @Override
    public void execute() {
        System.out.print("Enter string to find: ");
        BufferedReader rd =
                new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            line = rd.readLine().toLowerCase();
        } catch (IOException e) {
            System.out.print("Input failed. " + e.getMessage());
            return;
        }

        List<BookDto> b = bookService.search(
                book ->
                        book.getName().toLowerCase().contains(line)
                     || book.getAuthor().toLowerCase().contains(line));
        int number = 1;
        for (BookDto book : b) {
            System.out.println(
                    number++ + ") " + book.getAuthor()
                            + " \"" + book.getName() + "\"");
        }
        System.out.println();
    }
}

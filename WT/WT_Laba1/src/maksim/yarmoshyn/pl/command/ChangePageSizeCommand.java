package maksim.yarmoshyn.pl.command;

import maksim.yarmoshyn.bll.service.BookService;
import maksim.yarmoshyn.bll.service.ServiceProvider;
import maksim.yarmoshyn.pl.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Command to change page size.
 */
public class ChangePageSizeCommand extends BaseCommand implements Command {
    /**
     * Book service.
     */
    private final BookService bookService;

    /**
     * Initialise message, that describe command.
     *
     * @param application Instance of application.
     */
    public ChangePageSizeCommand(final App application) {
        super("Set page size", application.getCtx());

        ServiceProvider sp = new ServiceProvider(getCtx());
        this.bookService = sp.getBookService();
    }

    /**
     * Execute the command.
     */
    @Override
    public void execute() {
        System.out.print("Enter new page size: ");
        BufferedReader rd =
                new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            line = rd.readLine();
        } catch (IOException e) {
            System.out.print("Input failed. " + e.getMessage());
            return;
        }
        int s;
        try {
            s = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Not a number");
            return;
        }
        System.out.println();
        if (s > 20 || s < 5) {
            System.out.println(
                    "Failed. Max page size = 20 and Min page size = 5"
            );
            System.out.println();
            return;
        }
        System.out.println("Successfully changed");
        bookService.setPageSize(s);
    }
}

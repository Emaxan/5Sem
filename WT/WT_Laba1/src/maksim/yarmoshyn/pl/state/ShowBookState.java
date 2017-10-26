////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.pl.state;

import maksim.yarmoshyn.pl.App;
import maksim.yarmoshyn.pl.command.BackToMenuCommand;
import maksim.yarmoshyn.pl.command.ChangePageSizeCommand;
import maksim.yarmoshyn.pl.command.CurrentPageCommand;
import maksim.yarmoshyn.pl.command.ExitCommand;
import maksim.yarmoshyn.pl.command.NextPageCommand;
import maksim.yarmoshyn.pl.command.PrevPageCommand;

/**
 * State for chowing books.
 */
public class ShowBookState extends BaseState implements State {

    /**
     * Current page number.
     */
    private int currentPage;

    /**
     * Create new instance of state for showing books.
     *
     * @param application Instance of application.
     */
    public ShowBookState(final App application) {
        super(application);

        getCommands().add(new ExitCommand(getApp()));
        getCommands().add(new PrevPageCommand(getApp()));
        getCommands().add(new CurrentPageCommand(getApp()));
        getCommands().add(new NextPageCommand(getApp()));
        getCommands().add(new ChangePageSizeCommand(getApp()));
        getCommands().add(new BackToMenuCommand(getApp()));
    }

    /**
     * Get current page number.
     *
     * @return Current page number.
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Set current page number.
     *
     * @param page Page number to make it current.
     */
    public void setCurrentPage(final int page) {
        this.currentPage = page;
    }
}

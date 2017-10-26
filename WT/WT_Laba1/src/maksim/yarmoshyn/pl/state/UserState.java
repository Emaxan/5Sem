////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.pl.state;

import maksim.yarmoshyn.pl.App;
import maksim.yarmoshyn.pl.command.ExitCommand;
import maksim.yarmoshyn.pl.command.SearchBookCommand;
import maksim.yarmoshyn.pl.command.ShowBooksCommand;
import maksim.yarmoshyn.pl.command.SignOutCommand;

/**
 * State for user actions.
 */
public class UserState extends BaseState implements State {

    /**
     * Create new instance of state for users.
     *
     * @param application Instance of application.
     */
    public UserState(final App application) {
        super(application);

        getCommands().add(new ExitCommand(getApp()));
        getCommands().add(new ShowBooksCommand(getApp()));
        getCommands().add(new SearchBookCommand(getApp()));
        getCommands().add(new SignOutCommand(getApp()));
    }
}

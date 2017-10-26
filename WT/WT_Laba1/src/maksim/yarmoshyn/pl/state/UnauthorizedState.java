////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.pl.state;

import maksim.yarmoshyn.pl.App;
import maksim.yarmoshyn.pl.command.ExitCommand;
import maksim.yarmoshyn.pl.command.SignInCommand;
import maksim.yarmoshyn.pl.command.SignUpCommand;

/**
 * State for unauthorized user actions.
 */
public class UnauthorizedState extends BaseState implements State {

    /**
     * Create new instance of state for unauthorized users.
     *
     * @param application Instance of application.
     */
    public UnauthorizedState(final App application) {
        super(application);

        getCommands().add(new ExitCommand(getApp()));
        getCommands().add(new SignInCommand(getApp()));
        getCommands().add(new SignUpCommand(getApp()));
    }
}

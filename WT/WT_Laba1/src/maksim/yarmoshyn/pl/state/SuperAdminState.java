////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.pl.state;

import maksim.yarmoshyn.pl.App;
import maksim.yarmoshyn.pl.command.AddBookCommand;
import maksim.yarmoshyn.pl.command.ChangeUserRoleCommand;
import maksim.yarmoshyn.pl.command.DeleteBookCommand;
import maksim.yarmoshyn.pl.command.EditBookCommand;
import maksim.yarmoshyn.pl.command.ExitCommand;
import maksim.yarmoshyn.pl.command.SearchBookCommand;
import maksim.yarmoshyn.pl.command.ShowBooksCommand;
import maksim.yarmoshyn.pl.command.SignOutCommand;

/**
 * State for superAdmin actions.
 */
public class SuperAdminState extends BaseState implements State {

    /**
     * Create new instance of state for super admin.
     *
     * @param application Instance of application.
     */
    public SuperAdminState(final App application) {
        super(application);

        getCommands().add(new ExitCommand(getApp()));
        getCommands().add(new ShowBooksCommand(getApp()));
        getCommands().add(new SearchBookCommand(getApp()));
        getCommands().add(new AddBookCommand(getApp()));
        getCommands().add(new DeleteBookCommand(getApp()));
        getCommands().add(new EditBookCommand(getApp()));
        getCommands().add(new ChangeUserRoleCommand(getApp()));
        getCommands().add(new SignOutCommand(getApp()));
    }
}

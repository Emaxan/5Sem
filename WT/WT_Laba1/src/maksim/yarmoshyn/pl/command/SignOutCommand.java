////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.pl.command;

import maksim.yarmoshyn.pl.App;

/**
 * Command for signing out.
 */
public class SignOutCommand extends BaseCommand implements Command {

    /**
     * Instance of application.
     */
    private final App app;

    /**
     * Create new instance of {@link SignOutCommand}.
     *
     * @param application Instance of application
     */
    public SignOutCommand(final App application) {
        super("Sign out", application.getCtx());
        this.app = application;
    }

    /**
     * Sign out from system.
     */
    @Override
    public void execute() {
        app.setUser(null);
        app.returnToMenu();
    }
}

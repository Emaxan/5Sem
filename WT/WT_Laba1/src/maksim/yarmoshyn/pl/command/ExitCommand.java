////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.pl.command;

import maksim.yarmoshyn.pl.App;

/**
 * Command that close application.
 */
public class ExitCommand extends BaseCommand implements Command {

    /**
     * Instance of application.
     */
    private final App app;

    /**
     * Create new instance of {@link ExitCommand}.
     *
     * @param application Instance of application
     */
    public ExitCommand(final App application) {
        super("Exit", application.getCtx());
        this.app = application;
    }

    /**
     * Stop the application.
     */
    @Override
    public void execute() {
        app.setIsWork(false);
    }
}

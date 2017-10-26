package maksim.yarmoshyn.pl.command;

import maksim.yarmoshyn.pl.App;

/**
 * Command to back to menu.
 */
public class BackToMenuCommand extends BaseCommand implements Command {
    /**
     * Instance of Application.
     */
    private App app;

    /**
     * Initialise message, that describe command.
     *
     * @param application Instance of application.
     */
    public BackToMenuCommand(final App application) {
        super("Back to menu", application.getCtx());
        app = application;
    }


    /**
     * Execute the command.
     */
    @Override
    public void execute() {
        app.returnToMenu();
    }
}

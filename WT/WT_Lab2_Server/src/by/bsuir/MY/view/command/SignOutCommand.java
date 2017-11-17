package by.bsuir.MY.view.command;

import by.bsuir.MY.domain.ServiceResponse;
import by.bsuir.MY.view.App;

/**
 * .
 */
public class SignOutCommand extends BaseCommand implements Command {

    /**
     * .
     */
    private App app;

    /**
     * Initialise message, that describe command.
     *
     * @param application .
     */
    public SignOutCommand(final App application) {
        super("Sign out", application.getCtx());
        app = application;
    }

    /**
     * Execute the command.
     *
     * @param request .
     */
    @Override
    public ServiceResponse execute(final String request) {
        app.setUser(null);
        return ServiceResponse.createSuccessful();
    }
}

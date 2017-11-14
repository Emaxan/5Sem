package by.bsuir.MY.view.command;

import by.bsuir.MY.domain.ServiceResponse;
import by.bsuir.MY.view.App;

/**
 * TODO.
 */
public class EditFile extends BaseCommand implements Command {
    /**
     * Initialise message, that describe command.
     *
     * @param application TODO.
     */
    public EditFile(final App application) {
        super("Edit file", application.getCtx());
    }

    /**
     * Execute the command.
     *
     * @param request TODO.
     */
    @Override
    public ServiceResponse execute(final String request) {
        return null; //TODO
    }

    /**
     * Get message, that describe command.
     *
     * @return Message, that describe command.
     */
    @Override
    public String getMessage() {
        return null;
    }
}

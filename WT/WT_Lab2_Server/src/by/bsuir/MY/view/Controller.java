package by.bsuir.MY.view;

import by.bsuir.MY.domain.Interf.ServiceResponseCode;
import by.bsuir.MY.domain.ServiceResponse;
import by.bsuir.MY.view.command.Command;

/**
 * TODO.
 */
public class Controller {
    /**
     * TODO.
     */
    private final CommandProvider provider;

    /**
     * TODO.
     *
     * @param application TODO.
     */
    public Controller(final App application) {
        provider = new CommandProvider(application);
    }

    /**
     * TODO.
     *
     * @param request TODO.
     * @throws ArgumentException TODO.
     * @return TODO.
     */
    public ServiceResponse doAction(final String request) {

        String commandName;

        commandName = request.split("|")[0];

        Command command = provider.getCommand(commandName);

        if (command == null) {
           return ServiceResponse.createUnsuccessful(ServiceResponseCode.BadRequest);
        }

        String params = request.substring(request.indexOf("|"));

        return command.execute(params);
    }
}

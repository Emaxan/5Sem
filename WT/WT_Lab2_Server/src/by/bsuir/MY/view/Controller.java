package by.bsuir.MY.view;

import by.bsuir.MY.domain.Interf.ServiceResponseCode;
import by.bsuir.MY.domain.ServiceResponse;
import by.bsuir.MY.domain.exception.ArgumentException;
import by.bsuir.MY.view.command.Command;

/**
 * TODO.
 */
public class Controller {
    /**
     * TODO.
     */
    private final CommandProvider provider = new CommandProvider();

    /**
     * TODO.
     *
     * @param request TODO.
     * @throws ArgumentException TODO.
     * @return TODO.
     */
    public ServiceResponse doAction(final String request) throws ArgumentException {

        String commandName;

        commandName = request.split("|")[0];

        Command command = provider.getCommand(commandName);

        if (command == null) {
           return ServiceResponse.createUnsuccessful(ServiceResponseCode.BadRequest);
        }

        String response = command.execute(request);

        return ServiceResponse.createSuccessful(response);
    }
}

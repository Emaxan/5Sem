package by.bsuir.MY.view;

import by.bsuir.MY.domain.Interf.ServiceResponseCode;
import by.bsuir.MY.domain.ServiceResponse;
import by.bsuir.MY.view.command.Command;

import java.util.regex.Pattern;

/**
 * .
 */
public class Controller {
    /**
     * .
     */
    private final CommandProvider provider;

    /**
     * .
     *
     * @param application .
     */
    public Controller(final App application) {
        provider = new CommandProvider(application);
    }

    /**
     * .
     *
     * @param request .
     * @return .
     */
    public ServiceResponse doAction(final String request) {

        String commandName;

        commandName = request.split(Pattern.quote("|"))[0];

        Command command = provider.getCommand(commandName);

        if (command == null) {
           return ServiceResponse.createUnsuccessful(ServiceResponseCode.BadRequest);
        }

        String params = request.substring(request.indexOf("|") + 1);

        return command.execute(params);
    }
}

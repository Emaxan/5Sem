package by.bsuir.MY.view;

import by.bsuir.MY.view.command.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO.
 */
public final class CommandProvider {
    /**
     * TODO.
     */
    private Map<String, Command> commands = new HashMap<>();

    /**
     * TODO.
     */
    public CommandProvider() {
    }

    /**
     * TODO.
     *
     * @param commandName TODO.
     * @return TODO.
     */
    Command getCommand(final String commandName) {
        Command command;
        command = commands.get(commandName);
        return command;

    }

}

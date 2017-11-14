package by.bsuir.MY.view;

import by.bsuir.MY.view.command.Command;
import by.bsuir.MY.view.command.CreateFileCommand;
import by.bsuir.MY.view.command.DeleteFile;
import by.bsuir.MY.view.command.EditFile;
import by.bsuir.MY.view.command.GetFile;

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
     *
     * @param application TODO.
     */
    public CommandProvider(final App application) {
        commands.put("getFile", new GetFile(application));
        commands.put("createFile", new CreateFileCommand(application));
        commands.put("deleteFile", new DeleteFile(application));
        commands.put("editFile", new EditFile(application));
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

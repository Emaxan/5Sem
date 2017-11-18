package by.bsuir.MY.view;

import by.bsuir.MY.view.command.*;

import java.util.HashMap;
import java.util.Map;

/**
 * .
 */
public final class CommandProvider {
    /**
     * .
     */
    private Map<String, Command> commands = new HashMap<>();

    /**
     * .
     *
     * @param application .
     */
    public CommandProvider(final App application) {
        commands.put("getFile", new GetFileCommand(application));
        commands.put("createFile", new CreateFileCommand(application));
        commands.put("deleteFile", new DeleteFileCommand(application));
        commands.put("editFile", new EditFileCommand(application));
        commands.put("getAllFiles", new GetAllFilesCommand(application));
        commands.put("signIn", new SignInCommand(application));
        commands.put("signOut", new SignOutCommand(application));
    }

    /**
     * .
     *
     * @param commandName .
     * @return .
     */
    Command getCommand(final String commandName) {
        Command command;
        command = commands.get(commandName);
        return command;

    }

}

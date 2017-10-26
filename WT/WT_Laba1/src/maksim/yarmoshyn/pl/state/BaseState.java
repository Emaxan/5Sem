////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.pl.state;

import maksim.yarmoshyn.pl.App;
import maksim.yarmoshyn.pl.command.Command;
import maksim.yarmoshyn.pl.command.WrongCommand;
import org.jetbrains.annotations.Contract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Base state.
 */
public abstract class BaseState implements State {

    /**
     * Contains commands to execute.
     */
    private final List<Command> commands = new ArrayList<>();
    /**
     * Instance of {@link WrongCommand} to decrease garbage accumulation.
     */
    private final WrongCommand wrongCommand;
    /**
     * Instance of application.
     */
    private App app;

    /**
     * Create new instance of base state.
     *
     * @param application Instance of application.
     */
    BaseState(final App application) {
        app = application;
        this.wrongCommand = new WrongCommand(application);
    }

    /**
     * Get instance of application.
     *
     * @return Instance of application.
     */
    @Contract(pure = true)
    public final App getApp() {
        return app;
    }

    /**
     * Get list of commands.
     *
     * @return List of commands.
     */
    @Contract(pure = true)
    final List<Command> getCommands() {
        return commands;
    }

    /**
     * Print actions attached to that state.
     */
    @Override
    public final void printActions() {
        System.out.print("Choose action:\n");
        int i = 1;
        for (Command c : commands) {
            System.out.println(i++ + ") " + c.getMessage());
        }
    }

    /**
     * Read input and execute command.
     */
    @Override
    public final void processAction() {
        BufferedReader rd =
                new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            line = rd.readLine();
        } catch (IOException e) {
            System.out.print("Input failed. " + e.getMessage());
            return;
        }
        cls();
        int s;
        try {
            s = Integer.parseInt(line) - 1;
        } catch (NumberFormatException e) {
            wrongCommand.execute();
            return;
        }
        if (s >= commands.size() || s < 0) {
            wrongCommand.execute();
        } else {
            commands.get(s).execute();
        }
    }

    /**
     * Clear screen.
     */
    private void cls() {
        try {
            new ProcessBuilder("cmd", "/c", "cls")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (InterruptedException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

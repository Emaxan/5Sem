////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.pl.command;

/**
 * Command pattern for interacting with user.
 */
public interface Command {
    /**
     * Execute the command.
     */
    void execute();

    /**
     * Get message, that describe command.
     *
     * @return Message, that describe command.
     */
    String getMessage();
}

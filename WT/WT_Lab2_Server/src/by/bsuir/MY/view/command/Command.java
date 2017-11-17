////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package by.bsuir.MY.view.command;

import by.bsuir.MY.domain.ServiceResponse;

/**
 * Command pattern for interacting with user.
 */
public interface Command {
    /**
     * Execute the command.
     *
     * @param request .
     */
    ServiceResponse execute(String request);

    /**
     * Get message, that describe command.
     *
     * @return Message, that describe command.
     */
    String getMessage();
}

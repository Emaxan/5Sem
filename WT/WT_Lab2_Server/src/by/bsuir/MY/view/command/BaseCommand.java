////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package by.bsuir.MY.view.command;

import by.bsuir.MY.dal.dbcontext.DBContext;

/**
 * Base functionality for all commands.
 */
abstract class BaseCommand {
    /**
     * Message, that describe command.
     */
    private final String message;
    /**
     * Database context.
     */
    private DBContext ctx;

    /**
     * Initialise message, that describe command.
     *
     * @param mes     Message, that describe command.
     * @param context Database context.
     */
    BaseCommand(final String mes, final DBContext context) {
        this.message = mes;
        this.ctx = context;
    }

    /**
     * Get message, that describe command.
     *
     * @return Message, that describe command.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Return database context.
     *
     * @return Database context.
     */
    public DBContext getCtx() {
        return ctx;
    }
}

////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.pl.command;

import maksim.yarmoshyn.pl.App;

/**
 * Wrong command for wrong inputs.
 */
public class WrongCommand extends BaseCommand implements Command {

    /**
     * Create new instance of {@link WrongCommand}.
     *
     * @param application Instance of application.
     */
    public WrongCommand(final App application) {
        super("\nWrong command\n", application.getCtx());
    }

    /**
     * Print message, that describe command.
     */
    @Override
    public void execute() {
        System.out.println(getMessage());
    }
}

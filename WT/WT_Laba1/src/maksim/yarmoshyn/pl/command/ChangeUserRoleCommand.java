package maksim.yarmoshyn.pl.command;

import maksim.yarmoshyn.bll.service.ServiceProvider;
import maksim.yarmoshyn.bll.service.UserService;
import maksim.yarmoshyn.pl.App;

/**
 * Command for changing role.
 */
public class ChangeUserRoleCommand extends BaseCommand implements Command {

    /**
     * TODO.
     */
    private UserService userService;

    /**
     * Initialise message, that describe command.
     *
     * @param app Instance of application.
     */
    public ChangeUserRoleCommand(final App app) {
        super("Change user role", app.getCtx());
        ServiceProvider sp = new ServiceProvider(getCtx());
        userService = sp.getUserService();
    }

    /**
     * Execute the command.
     */
    @Override
    public void execute() {
        //TODO.
    }
}

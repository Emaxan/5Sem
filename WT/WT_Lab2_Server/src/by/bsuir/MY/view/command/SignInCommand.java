package by.bsuir.MY.view.command;

import by.bsuir.MY.businessLogic.AuthenticationService;
import by.bsuir.MY.businessLogic.ServiceProvider;
import by.bsuir.MY.dal.model.User;
import by.bsuir.MY.domain.Interf.ServiceResponseCode;
import by.bsuir.MY.domain.ServiceResponse;
import by.bsuir.MY.view.App;

import java.util.regex.Pattern;

public class SignInCommand extends BaseCommand implements Command {

    private AuthenticationService authenticationService;
    private App app;

    /**
     * Initialise message, that describe command.
     *
     * @param application .
     */
    public SignInCommand(final App application) {
        super("Sign in", application.getCtx());
        app = application;

        ServiceProvider sp = new ServiceProvider(getCtx());
        authenticationService = sp.getAuthService();
    }

    /**
     * Execute the command.
     *
     * @param request .
     */
    @Override
    public ServiceResponse execute(String request) {
        User user = new User();
        String[] req = request.split(Pattern.quote("|"));
        user.setEmail(req[0]);
        user.setPassword(req[1]);
        User us = authenticationService.signIn(user);
        if (us == null) {
            return ServiceResponse.createUnsuccessful(ServiceResponseCode.EntityNotFount);
        }

        app.setUser(us);
        return ServiceResponse.createSuccessful();
    }
}

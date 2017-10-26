////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.pl.command;

import maksim.yarmoshyn.bll.service.AuthService;
import maksim.yarmoshyn.bll.dto.UserDto;
import maksim.yarmoshyn.bll.service.ServiceProvider;
import maksim.yarmoshyn.pl.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Command for singing in to the system.
 */
public class SignInCommand extends BaseCommand implements Command {

    /**
     * Instance of application.
     */
    private final App app;
    /**
     * Authentication service.
     */
    private AuthService authService;

    /**
     * Create new instance of {@link SignInCommand}.
     *
     * @param application Instance of application.
     */
    public SignInCommand(final App application) {
        super("Sign in", application.getCtx());
        this.app = application;
        ServiceProvider sp = new ServiceProvider(getCtx());
        this.authService = sp.getAuthService();
    }

    /**
     * Authenticates user data.
     */
    @Override
    public void execute() {
        try {
            BufferedReader rd =
                    new BufferedReader(new InputStreamReader(System.in));
            UserDto user = new UserDto();
            System.out.print("Email: ");
            user.setEmail(rd.readLine());
            System.out.print("Password: ");
            user.setPassword(rd.readLine());
            UserDto u = authService.signIn(user);
            if (u != null) {
                app.setUser(u);
                System.out.println(
                        "\nHello, "
                                + u.getName()
                                + ". You successfully signed in =)\n");
            } else {
                System.out.println("\nUser not found.\n");
            }
            app.returnToMenu();
        } catch (IOException e) {
            System.out.println("Input failed. " + e.getMessage());
        }
    }
}

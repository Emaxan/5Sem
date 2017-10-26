////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.pl.command;

import maksim.yarmoshyn.bll.service.AuthService;
import maksim.yarmoshyn.bll.dto.UserDto;
import maksim.yarmoshyn.bll.service.ServiceProvider;
import maksim.yarmoshyn.dal.model.Role;
import maksim.yarmoshyn.pl.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Command for register in the system.
 */
public class SignUpCommand extends BaseCommand implements Command {

    /**
     * Regexp to validate email.
     */
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile(
                    "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                    Pattern.CASE_INSENSITIVE);
    /**
     * Authentication service.
     */
    private AuthService authService;
    /**
     * Instance of Application.
     */
    private App app;

    /**
     * Create new instance of {@link SignUpCommand}.
     *
     * @param application Instance of application.
     */
    public SignUpCommand(final App application) {
        super("Sing up", application.getCtx());
        app = application;

        ServiceProvider sp = new ServiceProvider(getCtx());
        authService = sp.getAuthService();
    }

    /**
     * Save user credentials to database.
     */
    @Override
    public void execute() {
        try {
            BufferedReader rd =
                    new BufferedReader(new InputStreamReader(System.in));
            UserDto user = new UserDto();

            user.setEmail(readEmail(rd));

            if (user.getEmail().isEmpty()) {
                System.out.println("\nRegistration canceled.\n");
                return;
            }

            System.out.print("Password: ");
            user.setPassword(rd.readLine());
            System.out.print("Name: ");
            user.setName(rd.readLine());
            user.setRole(Role.USER);
            if (authService.signUp(user)) {
                app.setUser(authService.signIn(user));
                System.out.println("\nYou successfully registered\n");
                app.returnToMenu();
            } else {
                System.out.println("\nRegistration is failed\n");
            }
        } catch (IOException e) {
            System.out.println("Input failed. " + e.getMessage());
        }
    }

    /**
     * Get email from console input.
     *
     * @param rd Reader.
     * @return Valid email or empty string, to cancel registration.
     * @throws IOException on input error.
     */
    private String readEmail(final BufferedReader rd) throws IOException {
        boolean validEmail;
        String email;
        do {
            System.out.print("Email: ");
            email = rd.readLine();
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
            validEmail = matcher.find();
            if (!validEmail) {
                System.out.println(
                        "Wrong email. Enter correct email or empty line"
                                + " to cancel registration. ");
            }
        } while (!validEmail && !email.isEmpty());
        return email;
    }
}

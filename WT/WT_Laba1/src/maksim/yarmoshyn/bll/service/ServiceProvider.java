package maksim.yarmoshyn.bll.service;

import maksim.yarmoshyn.dal.dbcontext.DBContext;

/**
 * Provider for all services.
 */
public final class ServiceProvider {

    /**
     * Authentication service.
     */
    private static AuthService authService;
    /**
     * Book service.
     */
    private static BookService bookService;
    /**
     * TODO.
     */
    private static UserService userService;
    /**
     * Database context.
     */
    private DBContext context;

    /**
     * Create new instance of Provider.
     *
     * @param ctx Database context.
     */
    public ServiceProvider(final DBContext ctx) {
        context = ctx;
    }

    /**
     * TODO.
     *
     * @return TODO.
     */
    public UserService getUserService() {
        if (userService == null) {
            userService = new UsrService(context);
        }
        return userService;
    }

    /**
     * Get instance of Auth service.
     *
     * @return Auth service.
     */
    public AuthService getAuthService() {
        if (authService == null) {
            authService = new AuthenticationService(context);
        }
        return authService;
    }

    /**
     * Get instance of book service.
     *
     * @return Book service.
     */
    public BookService getBookService() {
        if (bookService == null) {
            bookService = new BkService(context);
        }
        return bookService;
    }
}

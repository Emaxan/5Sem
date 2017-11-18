package by.bsuir.MY.businessLogic;

import by.bsuir.MY.dal.dbcontext.DBContext;

/**
 * Provider for all services.
 */
public final class ServiceProvider {

    /**
     * File service.
     */
    private static FileService fileService;
    /**
     * Auth service.
     */
    private static AuthenticationService authenticationService;
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
     * Get instance of file service.
     *
     * @return File service.
     */
    public FileService getFileService() {
        if (fileService == null) {
            fileService = new FileService(context);
        }
        return fileService;
    }

    /**
     * Get instance of Auth service.
     *
     * @return Auth service.
     */
    public AuthenticationService getAuthService() {
        if (authenticationService == null) {
            authenticationService = new AuthenticationService(context);
        }
        return authenticationService;
    }
}

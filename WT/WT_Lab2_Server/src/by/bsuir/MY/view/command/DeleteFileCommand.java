package by.bsuir.MY.view.command;

import by.bsuir.MY.businessLogic.FileService;
import by.bsuir.MY.businessLogic.ServiceProvider;
import by.bsuir.MY.dal.model.Role;
import by.bsuir.MY.domain.Interf.ServiceResponseCode;
import by.bsuir.MY.domain.ServiceResponse;
import by.bsuir.MY.view.App;

/**
 * .
 */
public class DeleteFileCommand extends BaseCommand implements Command {

    /**
     * .
     */
    private FileService fileService;
    /**
     * .
     */
    private App app;

    /**
     * Initialise message, that describe command.
     *
     * @param application .
     */
    public DeleteFileCommand(final App application) {
        super("Delete file", application.getCtx());
        app = application;

        ServiceProvider sp = new ServiceProvider(getCtx());
        fileService = sp.getFileService();
    }

    /**
     * Execute the command.
     *
     * @param request .
     */
    @Override
    public ServiceResponse execute(final String request) {
        if (app.getUser() == null || app.getUser().getRole() == Role.USER) {
            return ServiceResponse.createUnsuccessful(ServiceResponseCode.AccessDenied);
        }
        try {
            fileService.delete(fileService.get(Integer.parseInt(request)));
        } catch (NumberFormatException e) {
            return ServiceResponse.createUnsuccessful(ServiceResponseCode.WrongParameters);
        }
        return ServiceResponse.createSuccessful();
    }
}

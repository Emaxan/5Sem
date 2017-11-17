package by.bsuir.MY.view.command;

import by.bsuir.MY.businessLogic.FileService;
import by.bsuir.MY.businessLogic.ServiceProvider;
import by.bsuir.MY.domain.File;
import by.bsuir.MY.domain.Interf.ServiceResponseCode;
import by.bsuir.MY.domain.ServiceResponse;
import by.bsuir.MY.view.App;

/**
 * .
 */
public class GetFileCommand extends BaseCommand implements Command {
    private FileService fileService;
    private App app;

    /**
     * Initialise message, that describe command.
     *
     * @param application .
     */
    public GetFileCommand(final App application) {
        super("Get file", application.getCtx());
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
        if (app.getUser() == null) {
            return ServiceResponse.createUnsuccessful(ServiceResponseCode.AccessDenied);
        }
        int id;
        try{
            id = Integer.parseInt(request);
        } catch (NumberFormatException e) {
            return ServiceResponse.createUnsuccessful(ServiceResponseCode.WrongParameters);
        }
        File file = fileService.get(id);
        if (file == null) {
            return ServiceResponse.createUnsuccessful(ServiceResponseCode.WrongParameters);
        }
        return ServiceResponse.createSuccessful(file.toString());
    }
}

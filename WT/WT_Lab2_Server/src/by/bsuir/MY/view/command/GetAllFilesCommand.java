package by.bsuir.MY.view.command;

import by.bsuir.MY.businessLogic.FileService;
import by.bsuir.MY.businessLogic.ServiceProvider;
import by.bsuir.MY.dal.model.Role;
import by.bsuir.MY.domain.File;
import by.bsuir.MY.domain.Interf.ServiceResponseCode;
import by.bsuir.MY.domain.ServiceResponse;
import by.bsuir.MY.view.App;

import java.util.ArrayList;
import java.util.List;

/**
 * .
 */
public class GetAllFilesCommand extends BaseCommand implements Command {

    /**
     * .
     */
    private FileService fileService;
    private App app;

    /**
     * Initialise message, that describe command.
     *
     * @param application .
     */
    public GetAllFilesCommand(final App application) {
        super("Get all files", application.getCtx());
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
    public ServiceResponse execute(String request) {
        if (app.getUser() == null) {
            return ServiceResponse.createUnsuccessful(ServiceResponseCode.AccessDenied);
        }

        List<File> files = fileService.getAll();

        StringBuilder result = new StringBuilder("<Array>");

        for (File f : files) {
            result.append(f.toString());
        }

        result.append("</Array>");

        return ServiceResponse.createSuccessful(result);
    }
}

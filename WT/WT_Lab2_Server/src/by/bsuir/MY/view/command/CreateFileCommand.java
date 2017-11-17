package by.bsuir.MY.view.command;

import by.bsuir.MY.businessLogic.FileService;
import by.bsuir.MY.businessLogic.ServiceProvider;
import by.bsuir.MY.dal.model.Role;
import by.bsuir.MY.domain.File;
import by.bsuir.MY.domain.Interf.ServiceResponseCode;
import by.bsuir.MY.domain.ServiceResponse;
import by.bsuir.MY.view.App;

import java.util.regex.Pattern;

/**
 * .
 */
public class CreateFileCommand extends BaseCommand implements Command {
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
    public CreateFileCommand(final App application) {
        super("Create file", application.getCtx());
        app = application;

        ServiceProvider sp = new ServiceProvider(getCtx());
        fileService = sp.getFileService();
    }

    /**
     * Execute the command.
     *
     * @param params .
     */
    @Override
    public ServiceResponse execute(final String params) {
        if (app.getUser() == null || app.getUser().getRole() == Role.USER) {
            return ServiceResponse.createUnsuccessful(ServiceResponseCode.AccessDenied);
        }
        File file = new File();
        String[] p = params.split(Pattern.quote("|"));
        file.setFirstName(p[0]);
        file.setLastName(p[1]);
        file.setSurnameName(p[2]);
        file.setPhrase(p[3]);

        fileService.create(file);
        return ServiceResponse.createSuccessful();
    }
}

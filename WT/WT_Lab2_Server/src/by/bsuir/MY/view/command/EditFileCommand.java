package by.bsuir.MY.view.command;

import by.bsuir.MY.businessLogic.FileService;
import by.bsuir.MY.businessLogic.ServiceProvider;
import by.bsuir.MY.dal.exception.EntityNotFoundException;
import by.bsuir.MY.dal.model.Role;
import by.bsuir.MY.domain.File;
import by.bsuir.MY.domain.Interf.ServiceResponseCode;
import by.bsuir.MY.domain.ServiceResponse;
import by.bsuir.MY.view.App;

import java.util.regex.Pattern;

/**
 * .
 */
public class EditFileCommand extends BaseCommand implements Command {

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
    public EditFileCommand(final App application) {
        super("Edit file", application.getCtx());
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
        String[] req = request.split(Pattern.quote("|"));
        File file = new File();
        try {
            file.setId(Integer.parseInt(req[0]));
            file.setFirstName(req[1]);
            file.setLastName(req[2]);
            file.setSurnameName(req[3]);
            file.setPhrase(req[4]);

            fileService.update(file);
        } catch (NumberFormatException e) {
            return ServiceResponse.createUnsuccessful(ServiceResponseCode.WrongParameters);
        } catch (EntityNotFoundException e) {
            return ServiceResponse.createUnsuccessful(ServiceResponseCode.EntityNotFount);
        }
        return ServiceResponse.createSuccessful();
    }
}

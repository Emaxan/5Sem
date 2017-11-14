package by.bsuir.MY.view.command;

import by.bsuir.MY.businessLogic.FileService;
import by.bsuir.MY.businessLogic.ServiceProvider;
import by.bsuir.MY.domain.File;
import by.bsuir.MY.domain.ServiceResponse;
import by.bsuir.MY.view.App;

/**
 * TODO.
 */
public class CreateFileCommand extends BaseCommand implements Command {
    /**
     * TODO.
     */
    private FileService fileService;

    /**
     * Initialise message, that describe command.
     *
     * @param application TODO.
     */
    public CreateFileCommand(final App application) {
        super("Create file", application.getCtx());

        ServiceProvider sp = new ServiceProvider(getCtx());
        fileService = sp.getFileService();
    }

    /**
     * Execute the command.
     *
     * @param params TODO.
     */
    @Override
    public ServiceResponse execute(final String params) {
        File file = new File();
        String[] p = params.split("|");
        file.setFirstName(p[1]);
        file.setLastName(p[2]);
        file.setSurnameName(p[3]);
        file.setPhrase(p[4]);

        fileService.create(file);
        return null;
    }
}

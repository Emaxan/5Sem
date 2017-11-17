package by.bsuir.MY.businessLogic;

import by.bsuir.MY.dal.dbcontext.DBContext;
import by.bsuir.MY.dal.exception.EntityNotFoundException;
import by.bsuir.MY.dal.unit_of_work.UnitOfWork;
import by.bsuir.MY.dal.unit_of_work.WebTechLabUnitOfWork;
import by.bsuir.MY.domain.File;
import by.bsuir.MY.domain.exception.WrongDataException;

import java.util.List;

/**
 * .
 */
public class FileService {
    /**
     * .
     */
    private WebTechLabUnitOfWork uow;

    /**
     * .
     * @param dbContext .
     */
    public FileService(final DBContext dbContext) {
        this.uow = new WebTechLabUnitOfWork(dbContext);
    }

    /**
     * .
     *
     * @param id .
     * @return .
     */
    public File get(final int id) {
        return uow.getRepository(File.class).getById(id);
    }

    /**
     * .
     *
     * @return .
     */
    public List<File> getAll() {
        return uow.getRepository(File.class).getAll();
    }

    /**
     * .
     *
     * @param file .
     * @throws EntityNotFoundException .
     */
    public void update(final File file) throws EntityNotFoundException, WrongDataException {
        uow.getRepository(File.class).update(file);
        uow.saveChanges();
    }

    /**
     * .
     *
     * @param file .
     */
    public void create(final File file) {
        uow.getRepository(File.class).create(file);
        uow.saveChanges();
    }

    /**
     * .
     *
     * @param file .
     */
    public void delete(final File file) {
        uow.getRepository(File.class).delete(file);
        uow.saveChanges();
    }
}

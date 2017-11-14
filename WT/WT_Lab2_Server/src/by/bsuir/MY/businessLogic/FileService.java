package by.bsuir.MY.businessLogic;

import by.bsuir.MY.dal.dbcontext.DBContext;
import by.bsuir.MY.dal.exception.EntityNotFoundException;
import by.bsuir.MY.dal.unit_of_work.UnitOfWork;
import by.bsuir.MY.dal.unit_of_work.WebTechLabUnitOfWork;
import by.bsuir.MY.domain.File;

import java.util.List;

/**
 * TODO.
 */
public class FileService {
    /**
     * TODO.
     */
    private UnitOfWork uow;

    /**
     * TODO.
     * @param dbContext TODO.
     */
    public FileService(final DBContext dbContext) {
        this.uow = new WebTechLabUnitOfWork(dbContext);
    }

    /**
     * TODO.
     *
     * @param id TODO.
     * @return TODO.
     */
    public File get(final int id) {
        return uow.getRepository(File.class).getById(id);
    }

    /**
     * TODO.
     *
     * @return TODO.
     */
    public List<File> getAll() {
        return uow.getRepository(File.class).getAll();
    }

    /**
     * TODO.
     *
     * @param file TODO.
     * @throws EntityNotFoundException TODO.
     */
    public void update(final File file) throws EntityNotFoundException {
        uow.getRepository(File.class).update(file);
    }

    /**
     * TODO.
     *
     * @param file TODO.
     */
    public void create(final File file) {
        uow.getRepository(File.class).create(file);
    }

    /**
     * TODO.
     *
     * @param file TODO.
     */
    public void delete(final File file) {
        uow.getRepository(File.class).delete(file);
    }
}

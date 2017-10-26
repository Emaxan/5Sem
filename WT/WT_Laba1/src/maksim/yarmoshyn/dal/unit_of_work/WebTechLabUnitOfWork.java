////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.dal.unit_of_work;

import maksim.yarmoshyn.dal.dbcontext.DBContext;
import maksim.yarmoshyn.dal.repository.BookRepository;
import maksim.yarmoshyn.dal.repository.UserRepository;
import maksim.yarmoshyn.dal.repository.interf.BkRepository;
import maksim.yarmoshyn.dal.repository.interf.UsrRepository;

/**
 * Unit of Work with repositories with special functionality.
 */
public class WebTechLabUnitOfWork extends UnitOfWork implements WebTechLabUoW {

    /**
     * {@link UserRepository}.
     */
    private UsrRepository userRepository;
    /**
     * {@link BookRepository}.
     */
    private BkRepository bookRepository;

    /**
     * Create new instance of Uow.
     *
     * @param context Database context.
     */
    public WebTechLabUnitOfWork(final DBContext context) {
        super(context);
    }

    /**
     * Return {@link UserRepository}.
     *
     * @return Return {@link UserRepository}.
     */
    @Override
    public UsrRepository getUserRepository() {
        if (userRepository == null) {
            userRepository = new UserRepository(getContext());
        }
        return userRepository;
    }

    /**
     * Return {@link BookRepository}.
     *
     * @return Return {@link BookRepository}.
     */
    @Override
    public BkRepository getBookRepository() {
        if (bookRepository == null) {
            bookRepository = new BookRepository(getContext());
        }
        return bookRepository;
    }

    /**
     * Save changes to the database.
     *
     * @return {@code True} on success, {@code False} otherwise.
     */
    @Override
    public boolean saveChanges() {
        return (bookRepository == null || bookRepository.saveChanges())
                & (userRepository == null || userRepository.saveChanges());
    }
}

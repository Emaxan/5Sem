////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package by.bsuir.MY.dal.unit_of_work;

import by.bsuir.MY.dal.dbcontext.DBContext;
import by.bsuir.MY.dal.repository.BaseRepository;
import by.bsuir.MY.dal.repository.UserRepository;
import by.bsuir.MY.dal.repository.interf.UsrRepository;

import java.util.Collection;

/**
 * Unit of Work with repositories with special functionality.
 */
public class WebTechLabUnitOfWork extends UnitOfWork implements WebTechLabUoW {

    /**
     * {@link UserRepository}.
     */
    private UsrRepository userRepository;

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
     * Save changes to the database.
     *
     * @return {@code True} on success, {@code False} otherwise.
     */
    @Override
    public boolean saveChanges() {
        boolean result = true;
        for(Object rep : (repositoriesMap.values())) {
            result &= ((BaseRepository) rep).saveChanges();
        }
        return (userRepository == null || userRepository.saveChanges()) && result;
    }
}

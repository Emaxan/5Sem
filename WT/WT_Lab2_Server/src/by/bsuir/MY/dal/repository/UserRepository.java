////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package by.bsuir.MY.dal.repository;

import by.bsuir.MY.dal.dbcontext.DBContext;
import by.bsuir.MY.dal.model.User;
import by.bsuir.MY.dal.repository.interf.UsrRepository;

import java.util.List;

/**
 * Repository for {@link User}.
 */
public class UserRepository
        extends BaseRepository<User>
        implements UsrRepository {

    /**
     * Create new instance of {@link UserRepository}.
     *
     * @param context Database context.
     */
    public UserRepository(final DBContext context) {
        super(context, User.class);
    }

    /**
     * Return {@link User} by email.
     *
     * @param email Email of {@link User}.
     * @return {@link User} if it present in database, {@code null} otherwise.
     */
    @Override
    public User getByEmail(final String email) {
        List<User> users = getDbSet().getAll();
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }

        return null;
    }
}

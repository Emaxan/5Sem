////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.dal.repository;

import maksim.yarmoshyn.dal.dbcontext.DBContext;
import maksim.yarmoshyn.dal.model.Book;
import maksim.yarmoshyn.dal.repository.interf.BkRepository;

import java.util.List;
import java.util.function.Predicate;

/**
 * Repository for {@link Book}.
 */
public class BookRepository
        extends BaseRepository<Book>
        implements BkRepository {

    /**
     * Create new instance of repository for books.
     *
     * @param context Database context.
     */
    public BookRepository(final DBContext context) {
        super(context, Book.class);
    }

    /**
     * Return range of books.
     *
     * @param start First index to return.
     * @param count Count to return.
     * @return List of books.
     */
    @Override
    public List<Book> getRange(final int start, final int count) {
        return getDbSet().take(start, count);
    }

    /**
     * Search books with condition.
     *
     * @param predicate Condition to search.
     * @return List of books.
     */
    @Override
    public List<Book> search(final Predicate<Book> predicate) {
        return getDbSet().where(predicate).getAll();
    }
}

////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2017.                                                         /
// Developed by Maksim Ermoshyn.                                               /
////////////////////////////////////////////////////////////////////////////////

package maksim.yarmoshyn.dal.repository.interf;

import maksim.yarmoshyn.dal.model.Book;

import java.util.List;
import java.util.function.Predicate;

/**
 * Interface fro repository for {@link Book}.
 */
public interface BkRepository extends Repository<Book> {

    /**
     * Return range of books.
     *
     * @param start First index to return.
     * @param count Count to return.
     * @return List of books.
     */
    List<Book> getRange(int start, int count);

    /**
     * Search books with condition.
     *
     * @param predicate Condition to search.
     * @return List of books.
     */
    List<Book> search(Predicate<Book> predicate);
}

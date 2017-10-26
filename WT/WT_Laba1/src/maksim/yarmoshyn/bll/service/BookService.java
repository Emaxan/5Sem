package maksim.yarmoshyn.bll.service;

import maksim.yarmoshyn.bll.dto.BookDto;
import maksim.yarmoshyn.dal.model.Book;

import java.util.List;
import java.util.function.Predicate;

/**
 * Book service interface.
 */
public interface BookService {

    /**
     * Get all books from the database.
     *
     * @return List of books from the database.
     */
    List<BookDto> getAll();

    /**
     * Get page size for service.
     *
     * @return Current page size.
     */
    int getPageSize();

    /**
     * Set page size for service.
     *
     * @param size New size of pages. Min 5, max 20.
     */
    void setPageSize(int size);

    /**
     * Return page with specified number.
     *
     * @param number Number of the page.
     * @return List of books.
     */
    List<BookDto> getPage(int number);

    /**
     * Get page count.
     *
     * @return Page count.
     */
    int getPageCount();

    /**
     * Search books with specified conditions.
     *
     * @param predicate Condition to find.
     * @return List of matched books.
     */
    List<BookDto> search(Predicate<Book> predicate);

    /**
     * Edit books with specified primary key.
     *
     * @param number Number of edited book in list.
     * @param book Changed book.
     */
    void editBook(int number, BookDto book);

    /**
     * Add book to the database.
     *
     * @param book Book to add.
     */
    void addBook(BookDto book);

    /**
     * Delete book from database.
     *
     * @param number Number of book to delete.
     */
    void deleteBook(int number);
}

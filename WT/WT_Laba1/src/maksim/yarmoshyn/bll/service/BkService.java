package maksim.yarmoshyn.bll.service;

import maksim.yarmoshyn.bll.dto.BookDto;
import maksim.yarmoshyn.dal.dbcontext.DBContext;
import maksim.yarmoshyn.dal.exception.EntityNotFoundException;
import maksim.yarmoshyn.dal.model.Book;
import maksim.yarmoshyn.dal.unit_of_work.WebTechLabUnitOfWork;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Book service for the app.
 */
public class BkService implements BookService {

    /**
     * Current page size.
     */
    private int pageSize = 5;
    /**
     * Unit of work object.
     */
    private WebTechLabUnitOfWork uow;
    /**
     * Mapper to map models.
     */
    private ModelMapper mapper;

    /**
     * Create new instance of {@link BookService}.
     *
     * @param context Database context.
     */
    public BkService(final DBContext context) {
        uow = new WebTechLabUnitOfWork(context);
        this.mapper = new ModelMapper();
    }

    /**
     * Get all books from the database.
     *
     * @return List of books from the database.
     */
    @Override
    public List<BookDto> getAll() {
        List<BookDto> dtoBooks = new ArrayList<>();
        List<Book> books = uow.getBookRepository().getAll();
        for (Book book : books) {
            dtoBooks.add(mapper.map(book, BookDto.class));
        }

        return dtoBooks;
    }

    /**
     * Get page size for service.
     *
     * @return Current page size.
     */
    @Override
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Set page size for service.
     *
     * @param size New size of pages. Min 5, max 20.
     */
    @Override
    public void setPageSize(final int size) {
        this.pageSize = size;
    }

    /**
     * Return page with specified number.
     *
     * @param number Number of the page.
     * @return List of books.
     */
    @Override
    public List<BookDto> getPage(final int number) {
        List<Book> books = uow.getBookRepository()
                .getRange(number * pageSize, pageSize);
        List<BookDto> dtoBooks = new ArrayList<>();

        for (Book book : books) {
            dtoBooks.add(mapper.map(book, BookDto.class));
        }

        return dtoBooks;
    }

    /**
     * Get page count based on database data and current page size.
     *
     * @return Page count.
     */
    @Override
    public int getPageCount() {
        List<Book> b = uow.getBookRepository().getAll();
        int mod = b.size() % getPageSize();
        int div = b.size() / getPageSize();
        if (mod > 0) {
            div++;
        }

        return div;
    }

    /**
     * Search books with specified conditions.
     *
     * @param predicate Condition to find.
     * @return List of matched books.
     */
    public List<BookDto> search(final Predicate<Book> predicate) {
        List<Book> books = uow.getBookRepository().search(predicate);
        List<BookDto> dtoBooks = new ArrayList<>();

        for (Book book : books) {
            dtoBooks.add(mapper.map(book, BookDto.class));
        }

        return dtoBooks;
    }

    /**
     * Edit books with specified primary key.
     *
     * @param number Number of edited book in list.
     * @param bookDto Changed book.
     */
    @Override
    public void editBook(final int number, final BookDto bookDto) {
        Book book = uow.getBookRepository().getAll().get(number);
        mapper.map(bookDto, book);
        try {
            uow.getBookRepository().update(book);
        } catch (EntityNotFoundException e) {
            //TODO.
        }
        uow.saveChanges();
    }

    /**
     * Add book to the database.
     *
     * @param bookDto Book to add.
     */
    @Override
    public void addBook(final BookDto bookDto) {
        Book book = mapper.map(bookDto, Book.class);
        uow.getBookRepository().setId(book);
        uow.getBookRepository().create(book);
        uow.saveChanges();
    }

    /**
     * Delete book from database.
     *
     * @param number Number of book to delete.
     */
    @Override
    public void deleteBook(final int number) {
        List<Book> books = uow.getBookRepository().getAll();
        if (number > books.size()) {
            return;
        }
        Book book = books.get(number);
        uow.getBookRepository().delete(book);
        uow.saveChanges();
    }
}

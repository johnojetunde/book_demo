package book.demo.book_demo.service;

import book.demo.book_demo.exception.NotFoundException;
import book.demo.book_demo.model.Book;
import book.demo.book_demo.model.BookSearch;
import book.demo.book_demo.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.ExampleMatcher.matchingAll;

@ExtendWith(MockitoExtension.class)
class BookDBServiceTest {

    @InjectMocks
    private BookDBService bookDBService;

    @Mock
    private BookRepository repository;

    @Mock
    BookSearch bookSearch;


    @Test
    void register() {
        Book sampleBook = new Book(2L, "Book1", "Author1", 1999, 10);
        when(repository.save(sampleBook)).thenReturn(sampleBook);

        Book book = bookDBService.register(sampleBook);
        assertEquals(book.getAuthor(), sampleBook.getAuthor());
        assertEquals(book.getId(), sampleBook.getId());
        assertEquals(book, sampleBook);

        verify(repository).save(sampleBook);

    }

    @Test
    void search() {

        when(bookSearch.getTitle()).thenReturn("Book1");
        when(bookSearch.getAuthor()).thenReturn("Author1");
        when(bookSearch.getYear()).thenReturn(null);
        when(bookSearch.getPages()).thenReturn(null);

        Book book = new Book();
        book.setTitle(bookSearch.getTitle());
        book.setAuthor(bookSearch.getAuthor());
        book.setYear(bookSearch.getYear());
        book.setPages(bookSearch.getPages());

        Example<Book> bookExample = Example.of(book, matchingAll().withIgnoreNullValues());

        when(repository.findAll(bookExample)).thenReturn(
                Arrays.asList(new Book(2L, "Book1", "Author1", 1999, 10),
                        new Book(3L, "Book1", "Author1", 1998, 20)));

        List<Book> books = bookDBService.search(bookSearch);

        assertThat(books).hasSize(2)
                .contains(new Book(2L, "Book1", "Author1", 1999, 10));
        assertEquals("Book1", books.get(0).getTitle());
        assertEquals("Book1", books.get(1).getTitle());
        assertNotEquals(books.get(1).getYear(), books.get(0).getYear());

        verify(repository).findAll(bookExample);
        verify(bookSearch, times(2)).getTitle();
        verify(bookSearch, times(2)).getAuthor();
        verify(bookSearch, times(2)).getYear();
        verify(bookSearch, times(2)).getPages();
    }

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(
                Arrays.asList(new Book(2L, "Book1", "Author1", 1999, 10),
                        new Book(3L, "Book2", "Author2", 1998, 20)));


        List<Book> books = bookDBService.getAll();
        assertEquals(2, books.size());
        assertEquals("Book1", books.get(0).getTitle());
        assertTrue(books.contains(new Book(2L, "Book1", "Author1", 1999, 10)));

        verify(repository).findAll();
    }

    @Test
    void getById() {
        Book book = new Book(24L, "Book1", "Author1", 1999, 100);
        when(repository.findById(24L))
                .thenReturn(Optional.of(book));

        Book findBook = bookDBService.getById(24L);

        assertEquals("Book1", findBook.getTitle());

        verify(repository).findById(24L);
    }

    @Test
    void getById_WhenBookNotFound() {
        when(repository.findById(24L))
                .thenReturn(Optional.empty());

        try {
            bookDBService.getById(24L);
            fail();
        } catch (Exception e) {
            assertEquals("Book record does not exist", e.getMessage());
        }

        verify(repository).findById(24L);
    }

    @Test
    void updateBook() {
        long id = 24L;
        Book old = new Book(id, "adasds", "asdasdsad", 1999, 100);
        Book newUpdate = new Book(id, "Book1", "Author1", 1999, 100);

        when(repository.findById(id)).thenReturn(Optional.of(old));
        old.setTitle(newUpdate.getTitle());
        old.setAuthor(newUpdate.getAuthor());
        old.setYear(newUpdate.getYear());
        old.setPages(newUpdate.getPages());

        when(repository.save(old)).thenReturn(old);

        Book updatedBook = bookDBService.updateBook(id, newUpdate);

        assertEquals(updatedBook, old);
        assertNotNull(updatedBook);

        verify(repository).save(old);
        verify(repository).findById(id);


    }

    @Test
    void update_WhenBookNotFound() {
        long id = 24L;
        Book newUpdate = new Book(id, "Book1", "Author1", 1999, 100);

        when(repository.findById(24L))
                .thenReturn(Optional.empty());

        try {
            bookDBService.updateBook(24L, newUpdate);
            fail();
        } catch (Exception e) {
            assertEquals("Invalid book id " + id, e.getMessage());
        }

        verify(repository).findById(24L);
    }


    @Test
    void delete() {
        Book delete = new Book(24L, "Book1", "Author1", 1999, 100);
        doNothing().when(repository).deleteById(24L);
        bookDBService.delete(24L);

        verify(repository).deleteById(24L);
    }

    @Test
    void findAllByTitle() {
        when(repository.findAllByTitle("Book1")).thenReturn(
                Arrays.asList(new Book(2L, "Book1", "Author1", 1999, 10),
                        new Book(3L, "Book1", "Author2", 1998, 20)));

        List <Book> books = bookDBService.findAllByTitle("Book1");

        assertEquals(books.get(0).getTitle(), "Book1");
        assertThat(books).hasSize(2)
                .contains(new Book(2L, "Book1", "Author1", 1999, 10));

        verify(repository).findAllByTitle("Book1");

    }

    @Test
    void findAllByIdBetween() {

        when(repository.findAllByIdBetween(2L,4L)).thenReturn(
                Arrays.asList(new Book(2L, "Book1", "Author1", 1999, 10),
                        new Book(3L, "Book1", "Author2", 1998, 20)));

        List <Book> books = bookDBService.findAllByIdBetween(2L,4L);
        assertEquals(books.get(0).getId(), 2L);
        assertThat(books).hasSize(2)
                .contains(new Book(2L, "Book1", "Author1", 1999, 10));

        verify(repository).findAllByIdBetween(2L,4L);
    }

    @Test
    void findAllByIdBetween_WhenBookNotFound() {

        when(repository.findAllByIdBetween(2L,4L))
                .thenReturn(Collections.emptyList());

        try {
            bookDBService.findAllByIdBetween(2L,4L);
            fail();
        } catch (Exception e) {
            assertEquals("There is no book with such id", e.getMessage());
        }

        verify(repository).findAllByIdBetween(2L,4L);
    }

    @Test
    void findByTitleAndAuthor() {

        when(repository.findByTitleAndAuthor("Book1","Author1")).thenReturn(
                Arrays.asList(new Book(2L, "Book1", "Author1", 1999, 10),
                        new Book(3L, "Book1", "Author1", 1998, 20)));

        List <Book> books = bookDBService.findByTitleAndAuthor("Book1","Author1");
        assertEquals(books.get(0).getTitle(), "Book1");
        assertEquals(books.get(1).getAuthor(), "Author1");
        assertEquals(books.get (1).getAuthor(), books.get(0).getAuthor());
        assertNotEquals(books.get(1), books.get(0));
        assertThat(books).hasSize(2)
                .contains(new Book(2L, "Book1", "Author1", 1999, 10))
                .contains (new Book(3L, "Book1", "Author1", 1998, 20));

        verify(repository).findByTitleAndAuthor("Book1","Author1");
    }


    @Test
    void findByTitleAndAuthor_WhenBookNotFound() {

        when(repository.findByTitleAndAuthor("Book1","Author1"))
                .thenReturn(Collections.emptyList());

        try {
            bookDBService.findByTitleAndAuthor("Book1","Author1");
            fail();
        } catch (Exception e) {
            assertEquals("There is no such books", e.getMessage());
        }

        verify(repository).findByTitleAndAuthor("Book1","Author1");
    }
}
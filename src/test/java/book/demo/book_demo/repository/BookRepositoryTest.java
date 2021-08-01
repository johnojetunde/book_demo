package book.demo.book_demo.repository;

import book.demo.book_demo.model.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class BookRepositoryTest {


    @Autowired
    private BookRepository repository;


    @Test
    void findAllByTitle() {
        List<Book> books = repository.findAllByTitle("book1");
        Assertions.assertThat(books).hasSize(3)
                .noneMatch(x-> x.getTitle() == "book2");
        assertEquals("book1", books.get(0).getTitle());
        assertEquals("book1", books.get(1).getTitle());
    }

    @Test
    void findAllByIdBetween() {
        List<Book> books = repository.findAllByIdBetween(11L,17L);
        Assertions.assertThat(books).hasSize(4)
                .noneMatch(x-> x.getId() == 1);

        assertEquals(11, books.get(0).getId());
        assertEquals(17, books.get(3).getId());
    }

    @Test
    void findByTitleAndAuthor() {

        List<Book> findByTitleAndAuthor = repository.findByTitleAndAuthor("book1", "gogogo");
        Assertions.assertThat(findByTitleAndAuthor).hasSize(2)
                .noneMatch(x-> x.getTitle() == "book2");

    }
}
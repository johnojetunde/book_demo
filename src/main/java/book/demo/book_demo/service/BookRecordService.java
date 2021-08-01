package book.demo.book_demo.service;

import book.demo.book_demo.model.Book;
import book.demo.book_demo.model.BookSearch;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public interface BookRecordService {


    Book register(Book book);
    List<Book> getAll();
    Book getById(Long id);
    Book updateBook(Long id, Book updatedBook);
    void delete(Long id);
    List <Book> search (BookSearch bookSearch);
    List<Book> findAllByTitle(String title);
    List<Book> findAllByIdBetween(Long startId, Long endId);
    List<Book> findByTitleAndAuthor( String title, String author);

}

package book.demo.book_demo.controller;

import book.demo.book_demo.model.Book;
import book.demo.book_demo.model.BookSearch;
import book.demo.book_demo.service.BookRecordService;
import com.jayway.jsonpath.JsonPath;
import jdk.jfr.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRecordService bookRecordService;

    @Test
    void index() throws Exception {

        when(bookRecordService.getAll()).thenReturn(
                Arrays.asList(new Book(1L, "Book1", "Author1", 1999, 100),
                        new Book(2L, "Book2", "Author2", 1998, 101),
                        new Book(3L, "Book3", "Author3", 1997, 102)));

        //call "/books"
        RequestBuilder request = MockMvcRequestBuilders
                .get("/books")
                .accept(MediaType.APPLICATION_JSON);


        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().json("[{id:1,title:Book1,author:Author1,year:1999,pages:100}," +
                        "{id:2,title:Book2,author:Author2,year:1998,pages:101}," +
                        "{id:3,title:Book3,author:Author3,year:1997,pages:102}]"))
                .andReturn();


    }



    @Test
    void search2() throws Exception {
        //call "/searches"
        RequestBuilder request = MockMvcRequestBuilders
                .get("/books/searches2")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("search2"))
                .andReturn();

    }



    @Test
    void getSearchedBook() throws Exception {
        when(bookRecordService.search(new BookSearch("Book1", "Author1", null, null)))
                .thenReturn(
                        Arrays.asList(new Book(1L, "Book1", "Author1", 1999, 100),
                                new Book(2L, "Book1", "Author1", 1998, 101)));

        //call "/searches"
        RequestBuilder request = MockMvcRequestBuilders
                .post("/books/searches2")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(content().json("[{id:1,title:Book1,author:Author1,year:1999,pages:100}," +
                        "{id:2,title:Book1,author:Author1,year:1998,pages:101}]"))
                .andReturn();


    }

    @Test
    void signUp() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .get("/books/book-add")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("book-add"))
                .andReturn();
    }

    @Test
    void deleteById() throws Exception {
        doNothing().when(bookRecordService).delete(11L);
        RequestBuilder request = MockMvcRequestBuilders
                .get("/books/delete/{id}", 11)
                .accept(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request)
//                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    void editById() throws Exception {
        when (bookRecordService.getById(11L)).thenReturn(new Book (11L, "Book1", "Author1", 1001,100));
        RequestBuilder request = MockMvcRequestBuilders
                .get("/books/edit/{id}", 11)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("book-edit"))
                .andReturn();

        verify(bookRecordService).getById(11L);
    }

    @Test
    void register() throws Exception {
        when(bookRecordService.register(new Book(1L,"Book1", "Author1", 1998, 100)))
                .thenReturn(new Book(1L, "Book1", "Author1", 1998, 100));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/books")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("book-add"))
                .andExpect(content().json("{id:1,title:Book1,author:Author1,year:1999,pages:100}"))
                .andReturn();
    }

    @Test
    void updateBook() throws Exception {
        Book updated = new Book (1L,"Book1", "Author1", 1998, 100 );
        when(bookRecordService.updateBook(1L, updated))
                .thenReturn(new Book(1L, "Book1", "Author1", 1998, 100));


        RequestBuilder request = MockMvcRequestBuilders
                .post("/books/update/{id}", 1)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("book-edit"))
                .andExpect(content().json("{id:1,title:Book1,author:Author1,year:1999,pages:100}"))
                .andReturn();

    }
}
package book.demo.book_demo.integrationTestController;


import book.demo.book_demo.BookDemoApplication;
import book.demo.book_demo.repository.BookRepository;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {BookDemoApplication.class})
public class BookControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

//    @MockBean
//    BookRepository repository;


    @Test
    public void getBooks() throws JSONException {
        String response = this.restTemplate.getForObject("/books",String.class) ;
        JSONAssert.assertEquals("[{id:11},{id:12},{id:15}]", response, false);
    }

}

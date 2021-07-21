package book.demo.book_demo.model;

import lombok.Data;
import org.apache.logging.log4j.util.Strings;

@Data
public class BookSearch {
    private String title;
    private String author;
    private Integer year;
    private Integer pages;

    public String getAuthor() {
        return Strings.isNotBlank(author) ? author : null;
    }

    public String getTitle() {
        return Strings.isNotBlank(title) ? title : null;
    }
}

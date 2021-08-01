package book.demo.book_demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

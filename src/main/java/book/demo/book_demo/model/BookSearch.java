package book.demo.book_demo.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Data
public class BookSearch {
    private String title;
    private String author;
    @Positive(message = "year must be positive")
    @Min(value=1000, message = "invalid year")
    @Max(value=2021, message = "invalid year")
    private int year;
    private int pages;
}

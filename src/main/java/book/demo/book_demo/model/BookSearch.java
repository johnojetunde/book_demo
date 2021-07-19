package book.demo.book_demo.model;

import lombok.Data;

@Data
public class BookSearch {
    private String title;
    private String author;
    private int year;
    private int pages;
}

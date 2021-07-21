package book.demo.book_demo.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Author is required")
    private String author;
    @Positive (message = "year must be positive")
    @Min (value=1000, message = "invalid year")
    @Max (value=2021, message = "invalid year")
    private Integer year;
    @NotNull(message = "Pages are required")
    private Integer pages;


}

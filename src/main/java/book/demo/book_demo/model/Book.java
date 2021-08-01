package book.demo.book_demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Author is required")
    private String author;
    @Positive(message = "year must be positive")
    @Min(value = 1000, message = "invalid year")
    @Max(value = 2021, message = "invalid year")
    private Integer year;
    @NotNull(message = "Pages are required")
    private Integer pages;


}

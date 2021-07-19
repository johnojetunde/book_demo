package book.demo.book_demo.controller;

import book.demo.book_demo.service.BookRecordService;
import book.demo.book_demo.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Controller
@RequestMapping (value = "/books", produces = APPLICATION_JSON_VALUE)
public class BookController {


    private final BookRecordService bookRecordService;

    @GetMapping("/welcome")
    public String welcome(Model map, Book book) {
        map.addAttribute("welcomeMessage", "Hello! Welcome to book inventory!");

        return "welcome";
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookRecordService.getAll());
        return "index";
    }

    @GetMapping("/book-add")
    public String signUp(Model map, Book user) {
        map.addAttribute("pageName", "Add New Book");

        return "book-add";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id, Model model) {
        bookRecordService.delete(id);
        return "redirect:/books";//index(model);
    }

    @GetMapping("/edit/{id}")
    public String editById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("pageName", "Edit Book");

        Book book = bookRecordService.getById(id);
        model.addAttribute("book", book);

        return "book-edit";
    }

    @PostMapping
    public String register(@Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "book-add";
        }

        bookRecordService.register(book);

        return "redirect:/books";//index(model);
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "book-edit";
        }

        bookRecordService.updateBook(id, book);

        return "redirect:/books";//index(model);
    }
}
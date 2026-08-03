package edu.mum.cs.cs425.elibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Serves the eLibrary homepage.
 */
@Controller
public class HomeController {

    /** Lab 7 step 3: the banner text carries my name. */
    private static final String BANNER = "Ziad El Fatih's elibrary - a digital library for everyone";

    @GetMapping({"/", "/index", "/home"})
    public String home(Model model) {
        model.addAttribute("banner", BANNER);
        model.addAttribute("today",
                LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));
        model.addAttribute("featuredBooks", featuredBooks());
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("banner", BANNER);
        return "about";
    }

    private List<Book> featuredBooks() {
        return Arrays.asList(
            new Book("Clean Code", "Robert C. Martin", 2008),
            new Book("Design Patterns", "Gamma, Helm, Johnson, Vlissides", 1994),
            new Book("Refactoring", "Martin Fowler", 2018),
            new Book("Domain-Driven Design", "Eric Evans", 2003)
        );
    }

    /** Simple view model for a featured book. */
    public static class Book {
        private final String title;
        private final String author;
        private final int year;

        public Book(String title, String author, int year) {
            this.title = title;
            this.author = author;
            this.year = year;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public int getYear() {
            return year;
        }
    }
}

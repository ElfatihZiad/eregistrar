package edu.mum.cs.cs425.eregistrar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Serves the eRegistrar homepage.
 *
 * The content shown here is the published-schedule view of use case UC6 from
 * the SRS, rendered from static sample data — the persistence and registration
 * logic come in later iterations.
 */
@Controller
public class HomeController {

    private static final String BANNER =
            "Ziad El Fatih's eRegistrar - course scheduling and registration";

    @GetMapping({"/", "/index", "/home"})
    public String home(Model model) {
        model.addAttribute("banner", BANNER);
        model.addAttribute("today",
                LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));
        model.addAttribute("term", "Fall 2026 — Entry 3");
        model.addAttribute("sections", sampleSections());
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("banner", BANNER);
        return "about";
    }

    private List<Section> sampleSections() {
        return Arrays.asList(
            new Section("CS425", "Software Engineering", 1, "Prof. Ahmed", 30, 24),
            new Section("CS472", "Web Application Architecture", 1, "Prof. Nakamura", 25, 25),
            new Section("CS488", "Data Science", 2, "Prof. Okafor", 30, 12),
            new Section("CS544", "Enterprise Architecture", 2, "Prof. Lindqvist", 28, 19),
            new Section("CS582", "Machine Learning", 3, "Prof. Rossi", 24, 8)
        );
    }

    /** View model for one section of the published schedule. */
    public static class Section {
        private final String courseCode;
        private final String courseTitle;
        private final int block;
        private final String faculty;
        private final int capacity;
        private final int registered;

        public Section(String courseCode, String courseTitle, int block,
                       String faculty, int capacity, int registered) {
            this.courseCode = courseCode;
            this.courseTitle = courseTitle;
            this.block = block;
            this.faculty = faculty;
            this.capacity = capacity;
            this.registered = registered;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public String getCourseTitle() {
            return courseTitle;
        }

        public int getBlock() {
            return block;
        }

        public String getFaculty() {
            return faculty;
        }

        public int getCapacity() {
            return capacity;
        }

        public int getRegistered() {
            return registered;
        }

        public int getRemainingSeats() {
            return capacity - registered;
        }

        public boolean isFull() {
            return getRemainingSeats() <= 0;
        }
    }
}

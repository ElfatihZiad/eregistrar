package edu.mum.cs.cs425.eregistrar.controller;

import edu.mum.cs.cs425.eregistrar.service.ScheduleService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Serves the eRegistrar homepage: the published-schedule view of UC4
 * (Register for Course) from the SRS, backed by the H2 database.
 */
@Controller
public class HomeController {

    private static final String BANNER =
            "Ziad El Fatih's eRegistrar - course scheduling and registration";

    private final ScheduleService scheduleService;

    public HomeController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping({"/", "/index", "/home"})
    public String home(Model model) {
        model.addAttribute("banner", BANNER);
        model.addAttribute("today",
                LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));
        model.addAttribute("term", "Fall 2026, Entry 3");
        model.addAttribute("sections", scheduleService.getPublishedSchedule());
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("banner", BANNER);
        return "about";
    }
}

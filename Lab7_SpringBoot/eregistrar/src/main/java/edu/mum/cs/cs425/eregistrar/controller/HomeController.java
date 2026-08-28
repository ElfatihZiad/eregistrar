package edu.mum.cs.cs425.eregistrar.controller;

import edu.mum.cs.cs425.eregistrar.model.Student;
import edu.mum.cs.cs425.eregistrar.repository.StudentRepository;
import edu.mum.cs.cs425.eregistrar.service.RegistrationService;
import edu.mum.cs.cs425.eregistrar.service.ScheduleService;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Serves the eRegistrar homepage: the published-schedule view of UC4
 * (Register for Course) from the SRS, backed by the H2 database.
 *
 * Every route here requires a signed-in student (see SecurityConfig), so
 * {@code principal} is always present and identifies who's looking at the
 * page and who a "Register" click would register.
 */
@Controller
public class HomeController {

    private static final String BANNER =
            "Ziad El Fatih's eRegistrar - course scheduling and registration";

    private final ScheduleService scheduleService;
    private final RegistrationService registrationService;
    private final StudentRepository studentRepository;

    public HomeController(ScheduleService scheduleService, RegistrationService registrationService,
                           StudentRepository studentRepository) {
        this.scheduleService = scheduleService;
        this.registrationService = registrationService;
        this.studentRepository = studentRepository;
    }

    @GetMapping({"/", "/index", "/home"})
    public String home(Model model, Principal principal) {
        model.addAttribute("banner", BANNER);
        model.addAttribute("today",
                LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy")));
        model.addAttribute("term", "Fall 2026, Entry 3");
        model.addAttribute("sections", scheduleService.getPublishedSchedule());

        String studentId = principal.getName();
        model.addAttribute("studentId", studentId);
        model.addAttribute("studentName", studentRepository.findByStudentId(studentId)
                .map(Student::getName)
                .orElse(studentId));
        model.addAttribute("registeredSectionIds", registrationService.getRegisteredSectionIds(studentId));

        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("banner", BANNER);
        return "about";
    }
}

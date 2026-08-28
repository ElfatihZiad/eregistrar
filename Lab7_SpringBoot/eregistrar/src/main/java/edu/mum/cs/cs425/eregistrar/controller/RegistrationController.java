package edu.mum.cs.cs425.eregistrar.controller;

import edu.mum.cs.cs425.eregistrar.service.AlreadyRegisteredException;
import edu.mum.cs.cs425.eregistrar.service.RegistrationService;
import edu.mum.cs.cs425.eregistrar.service.SectionFullException;
import java.util.NoSuchElementException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** UC4 (Register for Course): the write side of the schedule the homepage displays. */
@Controller
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public String register(@RequestParam String studentId,
                            @RequestParam Long sectionId,
                            RedirectAttributes redirectAttributes) {
        try {
            registrationService.register(studentId, sectionId);
            redirectAttributes.addFlashAttribute("flashType", "success");
            redirectAttributes.addFlashAttribute("flashMessage",
                    "Registered " + studentId + " for the section.");
        } catch (SectionFullException | AlreadyRegisteredException e) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashMessage", e.getMessage());
        } catch (OptimisticLockingFailureException e) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashMessage",
                    "That seat was taken by another student just now. Please try again.");
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("flashType", "error");
            redirectAttributes.addFlashAttribute("flashMessage",
                    "Unknown student ID. Try S1001 through S1005 (seeded sample students).");
        }
        return "redirect:/";
    }
}

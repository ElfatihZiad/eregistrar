package edu.mum.cs.cs425.eregistrar.service;

import edu.mum.cs.cs425.eregistrar.model.Registration;
import edu.mum.cs.cs425.eregistrar.model.RegistrationStatus;
import edu.mum.cs.cs425.eregistrar.model.Section;
import edu.mum.cs.cs425.eregistrar.model.Student;
import edu.mum.cs.cs425.eregistrar.repository.RegistrationRepository;
import edu.mum.cs.cs425.eregistrar.repository.SectionRepository;
import edu.mum.cs.cs425.eregistrar.repository.StudentRepository;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implements UC4 (Register for Course) from the SRS.
 *
 * The seat check and the seat increment happen inside one {@code @Transactional}
 * method on the same {@code Section}, which carries a {@code @Version} column.
 * If two requests race for the last seat, the second commit fails with an
 * optimistic-locking exception rather than letting both succeed, so capacity
 * (BR7) can never be exceeded.
 */
@Service
public class RegistrationService {

    private final StudentRepository studentRepository;
    private final SectionRepository sectionRepository;
    private final RegistrationRepository registrationRepository;

    public RegistrationService(StudentRepository studentRepository,
                                SectionRepository sectionRepository,
                                RegistrationRepository registrationRepository) {
        this.studentRepository = studentRepository;
        this.sectionRepository = sectionRepository;
        this.registrationRepository = registrationRepository;
    }

    @Transactional
    public Registration register(String studentId, Long sectionId) {
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new NoSuchElementException("No student with id " + studentId));
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new NoSuchElementException("No section with id " + sectionId));

        registrationRepository.findByStudentAndSectionAndStatus(student, section, RegistrationStatus.CONFIRMED)
                .ifPresent(existing -> {
                    throw new AlreadyRegisteredException(
                            student.getName() + " is already registered for " + section.getCourse().getCode());
                });

        if (!section.hasAvailableSeat()) {
            throw new SectionFullException(section.getCourse().getCode() + " has no seats left");
        }

        section.incrementRegisteredCount();
        sectionRepository.save(section);

        Registration registration = new Registration(student, section);
        return registrationRepository.save(registration);
    }

    @Transactional
    public void drop(Long registrationId) {
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new NoSuchElementException("No registration with id " + registrationId));
        registration.drop();
        registration.getSection().decrementRegisteredCount();
    }
}

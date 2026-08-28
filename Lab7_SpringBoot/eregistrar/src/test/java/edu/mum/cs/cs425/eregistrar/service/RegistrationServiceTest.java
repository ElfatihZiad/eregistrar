package edu.mum.cs.cs425.eregistrar.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import edu.mum.cs.cs425.eregistrar.model.Block;
import edu.mum.cs.cs425.eregistrar.model.Course;
import edu.mum.cs.cs425.eregistrar.model.Faculty;
import edu.mum.cs.cs425.eregistrar.model.Registration;
import edu.mum.cs.cs425.eregistrar.model.RegistrationStatus;
import edu.mum.cs.cs425.eregistrar.model.Section;
import edu.mum.cs.cs425.eregistrar.model.Student;
import edu.mum.cs.cs425.eregistrar.repository.BlockRepository;
import edu.mum.cs.cs425.eregistrar.repository.CourseRepository;
import edu.mum.cs.cs425.eregistrar.repository.FacultyRepository;
import edu.mum.cs.cs425.eregistrar.repository.SectionRepository;
import edu.mum.cs.cs425.eregistrar.repository.StudentRepository;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Unit-level coverage of UC4 (Register for Course), specifically the rule
 * that a section's capacity may never be exceeded (BR7) and that a student
 * cannot register twice for the same section.
 *
 * Each test runs inside a transaction that is rolled back afterwards, so
 * tests do not interfere with one another or with the data DataSeeder loads.
 */
@SpringBootTest
@Transactional
class RegistrationServiceTest {

    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private StudentRepository studentRepository;

    private Section oneSeatSection;
    private Student alice;
    private Student bob;

    @BeforeEach
    void setUp() {
        Course course = courseRepository.save(new Course("CS999", "Test Course", 500));
        Faculty faculty = facultyRepository.save(new Faculty("Prof. Test", "test@mum.edu"));
        Block block = blockRepository.save(new Block(1, "Block 1"));
        oneSeatSection = sectionRepository.save(new Section(course, block, faculty, 1));

        // The password hash isn't exercised by these tests; any non-null value works.
        alice = studentRepository.save(new Student("T-ALICE", "Alice", "alice@mum.edu", "n/a"));
        bob = studentRepository.save(new Student("T-BOB", "Bob", "bob@mum.edu", "n/a"));
    }

    @Test
    void registerSucceedsAndFillsTheSeat() {
        Registration registration = registrationService.register(alice.getStudentId(), oneSeatSection.getId());

        assertThat(registration.getStatus()).isEqualTo(RegistrationStatus.CONFIRMED);
        assertThat(registration.getStudent().getStudentId()).isEqualTo("T-ALICE");

        Section reloaded = sectionRepository.findById(oneSeatSection.getId()).orElseThrow();
        assertThat(reloaded.getRegisteredCount()).isEqualTo(1);
        assertThat(reloaded.hasAvailableSeat()).isFalse();
    }

    @Test
    void registrationIsRejectedOnceTheSectionIsFull() {
        registrationService.register(alice.getStudentId(), oneSeatSection.getId());

        assertThatThrownBy(() -> registrationService.register(bob.getStudentId(), oneSeatSection.getId()))
                .isInstanceOf(SectionFullException.class);

        Section reloaded = sectionRepository.findById(oneSeatSection.getId()).orElseThrow();
        assertThat(reloaded.getRegisteredCount()).isEqualTo(1);
    }

    @Test
    void aStudentCannotRegisterTwiceForTheSameSection() {
        registrationService.register(alice.getStudentId(), oneSeatSection.getId());

        assertThatThrownBy(() -> registrationService.register(alice.getStudentId(), oneSeatSection.getId()))
                .isInstanceOf(AlreadyRegisteredException.class);
    }

    @Test
    void registeringAnUnknownStudentFails() {
        assertThatThrownBy(() -> registrationService.register("NOT-A-STUDENT", oneSeatSection.getId()))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void droppingARegistrationFreesTheSeat() {
        Registration registration = registrationService.register(alice.getStudentId(), oneSeatSection.getId());

        registrationService.drop(registration.getId());

        Section reloaded = sectionRepository.findById(oneSeatSection.getId()).orElseThrow();
        assertThat(reloaded.getRegisteredCount()).isEqualTo(0);
        assertThat(reloaded.hasAvailableSeat()).isTrue();
    }
}

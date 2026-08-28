package edu.mum.cs.cs425.eregistrar;

import edu.mum.cs.cs425.eregistrar.model.Block;
import edu.mum.cs.cs425.eregistrar.model.Course;
import edu.mum.cs.cs425.eregistrar.model.Faculty;
import edu.mum.cs.cs425.eregistrar.model.Section;
import edu.mum.cs.cs425.eregistrar.model.Student;
import edu.mum.cs.cs425.eregistrar.repository.BlockRepository;
import edu.mum.cs.cs425.eregistrar.repository.CourseRepository;
import edu.mum.cs.cs425.eregistrar.repository.FacultyRepository;
import edu.mum.cs.cs425.eregistrar.repository.SectionRepository;
import edu.mum.cs.cs425.eregistrar.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** Loads sample courses, faculty, sections and students into H2 on startup. */
@Component
public class DataSeeder implements CommandLineRunner {

    private final CourseRepository courses;
    private final FacultyRepository faculty;
    private final BlockRepository blocks;
    private final SectionRepository sections;
    private final StudentRepository students;

    public DataSeeder(CourseRepository courses, FacultyRepository faculty, BlockRepository blocks,
                       SectionRepository sections, StudentRepository students) {
        this.courses = courses;
        this.faculty = faculty;
        this.blocks = blocks;
        this.sections = sections;
        this.students = students;
    }

    @Override
    public void run(String... args) {
        Block block1 = blocks.save(new Block(1, "Block 1"));
        Block block2 = blocks.save(new Block(2, "Block 2"));
        Block block3 = blocks.save(new Block(3, "Block 3"));

        Faculty ahmed = faculty.save(new Faculty("Prof. Ahmed", "ahmed@mum.edu"));
        Faculty nakamura = faculty.save(new Faculty("Prof. Nakamura", "nakamura@mum.edu"));
        Faculty okafor = faculty.save(new Faculty("Prof. Okafor", "okafor@mum.edu"));
        Faculty lindqvist = faculty.save(new Faculty("Prof. Lindqvist", "lindqvist@mum.edu"));
        Faculty rossi = faculty.save(new Faculty("Prof. Rossi", "rossi@mum.edu"));

        Course cs425 = courses.save(new Course("CS425", "Software Engineering", 500));
        Course cs472 = courses.save(new Course("CS472", "Web Application Architecture", 400));
        Course cs488 = courses.save(new Course("CS488", "Data Science", 500));
        Course cs544 = courses.save(new Course("CS544", "Enterprise Architecture", 500));
        Course cs582 = courses.save(new Course("CS582", "Machine Learning", 500));

        sections.save(section(cs425, block1, ahmed, 30, 24));
        sections.save(section(cs472, block1, nakamura, 25, 25));
        sections.save(section(cs488, block2, okafor, 30, 12));
        sections.save(section(cs544, block2, lindqvist, 28, 19));
        sections.save(section(cs582, block3, rossi, 24, 8));

        students.save(new Student("S1001", "Alex Rivera", "arivera@mum.edu"));
        students.save(new Student("S1002", "Priya Nair", "pnair@mum.edu"));
        students.save(new Student("S1003", "Jonas Weber", "jweber@mum.edu"));
        students.save(new Student("S1004", "Mei Lin", "mlin@mum.edu"));
        students.save(new Student("S1005", "Tariq Hassan", "thassan@mum.edu"));
    }

    private Section section(Course course, Block block, Faculty facultyMember,
                             int capacity, int alreadyRegistered) {
        Section section = new Section(course, block, facultyMember, capacity);
        for (int i = 0; i < alreadyRegistered; i++) {
            section.incrementRegisteredCount();
        }
        return section;
    }
}

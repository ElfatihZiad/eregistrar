package edu.mum.cs.cs425.demos.studentrecordsmgmtapp;

import edu.mum.cs.cs425.demos.studentrecordsmgmtapp.model.Student;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * CS425 Lab 6 — Student records management, plus the two further coding
 * practice exercises (printHelloWorld and findSecondBiggest).
 *
 * Ziad El Fatih (618971)
 */
public class MyStudentRecordsMgmtApp {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");

    /** A student who was admitted at least this many years ago is a PlatinumAlumni. */
    private static final int PLATINUM_ALUMNI_YEARS = 30;

    public static void main(String[] args) {
        Student[] students = {
            new Student(110001, "Dave", LocalDate.parse("11/18/1951", DATE_FORMAT)),
            new Student(110002, "Anna", LocalDate.parse("12/07/1990", DATE_FORMAT)),
            new Student(110003, "Erica", LocalDate.parse("01/31/1974", DATE_FORMAT)),
            new Student(110004, "Carlos", LocalDate.parse("08/22/2009", DATE_FORMAT)),
            new Student(110005, "Bob", LocalDate.parse("03/05/1990", DATE_FORMAT))
        };

        System.out.println("=== Task 1: all students, ascending by name ===");
        printListOfStudents(students);

        System.out.println();
        System.out.println("=== Task 2: platinum alumni (admitted "
                + PLATINUM_ALUMNI_YEARS + "+ years ago), descending by admission date ===");
        List<Student> platinumAlumni = getListOfPlatinumAlumniStudents(students);
        platinumAlumni.sort(Comparator.comparing(Student::getDateOfAdmission).reversed());
        printHeader();
        platinumAlumni.forEach(System.out::println);
        System.out.println(platinumAlumni.size() + " platinum alumni of "
                + students.length + " students.");

        System.out.println();
        System.out.println("=== Task 3: printHelloWorld ===");
        int[] numbers = {5, 7, 35, 10, 14, 3, 70, 21, 25, 1};
        System.out.println("Input: " + Arrays.toString(numbers));
        printHelloWorld(numbers);

        System.out.println();
        System.out.println("=== Task 4: findSecondBiggest ===");
        int[][] samples = {
            {1, 2, 3, 4, 5},
            {19, 9, 11, 0, 12},
            {5, 5, 4},
            {-3, -1, -7}
        };
        for (int[] sample : samples) {
            System.out.printf("findSecondBiggest(%s) = %d%n",
                    Arrays.toString(sample), findSecondBiggest(sample));
        }
    }

    /**
     * Prints every student, in ascending order of name.
     *
     * The array is copied before sorting so the caller's array keeps its order.
     */
    public static void printListOfStudents(Student[] students) {
        Student[] sorted = Arrays.copyOf(students, students.length);
        Arrays.sort(sorted, Comparator.comparing(Student::getName));

        printHeader();
        for (Student student : sorted) {
            System.out.println(student);
        }
    }

    /**
     * Returns the students admitted at least {@value #PLATINUM_ALUMNI_YEARS}
     * years ago.
     */
    public static List<Student> getListOfPlatinumAlumniStudents(Student[] students) {
        List<Student> platinumAlumni = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Student student : students) {
            int yearsSinceAdmission =
                    Period.between(student.getDateOfAdmission(), today).getYears();
            if (yearsSinceAdmission >= PLATINUM_ALUMNI_YEARS) {
                platinumAlumni.add(student);
            }
        }
        return platinumAlumni;
    }

    /**
     * Prints "Hello" for multiples of 5, "World" for multiples of 7, and
     * "HelloWorld" for multiples of both.
     */
    public static void printHelloWorld(int[] numbers) {
        for (int number : numbers) {
            boolean multipleOfFive = number % 5 == 0;
            boolean multipleOfSeven = number % 7 == 0;

            if (multipleOfFive && multipleOfSeven) {
                System.out.println(number + " -> HelloWorld");
            } else if (multipleOfFive) {
                System.out.println(number + " -> Hello");
            } else if (multipleOfSeven) {
                System.out.println(number + " -> World");
            }
        }
    }

    /**
     * Returns the second biggest of the integers, in a single pass and without
     * sorting.
     *
     * Duplicates of the maximum are ignored, so {5, 5, 4} gives 4.
     */
    public static int findSecondBiggest(int[] numbers) {
        if (numbers == null || numbers.length < 2) {
            throw new IllegalArgumentException("Need at least two integers");
        }

        // Boxed so that Integer.MIN_VALUE is a legitimate value rather than a
        // sentinel meaning "not seen yet".
        Integer biggest = null;
        Integer secondBiggest = null;

        for (int number : numbers) {
            if (biggest == null || number > biggest) {
                secondBiggest = biggest;
                biggest = number;
            } else if (number < biggest && (secondBiggest == null || number > secondBiggest)) {
                secondBiggest = number;
            }
        }

        if (secondBiggest == null) {
            throw new IllegalArgumentException("All the integers are equal");
        }
        return secondBiggest;
    }

    private static void printHeader() {
        System.out.printf("%-10s %-10s %s%n", "StudentId", "Name", "DateOfAdmission");
        System.out.println("-----------------------------------------");
    }
}

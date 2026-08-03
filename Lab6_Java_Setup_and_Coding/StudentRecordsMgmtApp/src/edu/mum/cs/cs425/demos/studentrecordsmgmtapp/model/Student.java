package edu.mum.cs.cs425.demos.studentrecordsmgmtapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A university student record.
 *
 * CS425 Lab 6 — Ziad El Fatih (618971)
 */
public class Student {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private int studentId;
    private String name;
    private LocalDate dateOfAdmission;

    /** Default constructor. */
    public Student() {
        this(0, "", LocalDate.now());
    }

    /** Convenience constructor without an admission date; today's date is used. */
    public Student(int studentId, String name) {
        this(studentId, name, LocalDate.now());
    }

    /** Full constructor. */
    public Student(int studentId, String name, LocalDate dateOfAdmission) {
        this.studentId = studentId;
        this.name = name;
        this.dateOfAdmission = dateOfAdmission;
    }

    // ---------------- getters (accessors) ----------------

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfAdmission() {
        return dateOfAdmission;
    }

    // ---------------- setters (mutators) ----------------

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfAdmission(LocalDate dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }

    @Override
    public String toString() {
        return String.format("%-10d %-10s %s",
                studentId, name, dateOfAdmission.format(DATE_FORMAT));
    }
}

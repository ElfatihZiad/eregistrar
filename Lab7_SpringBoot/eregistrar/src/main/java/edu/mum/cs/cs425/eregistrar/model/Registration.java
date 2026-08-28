package edu.mum.cs.cs425.eregistrar.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/** A Student's registration for a Section, created by UC4 (Register for Course). */
@Entity
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Section section;

    private LocalDateTime registeredOn;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;

    protected Registration() {
    }

    public Registration(Student student, Section section) {
        this.student = student;
        this.section = section;
        this.registeredOn = LocalDateTime.now();
        this.status = RegistrationStatus.CONFIRMED;
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Section getSection() {
        return section;
    }

    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public void drop() {
        this.status = RegistrationStatus.DROPPED;
    }
}

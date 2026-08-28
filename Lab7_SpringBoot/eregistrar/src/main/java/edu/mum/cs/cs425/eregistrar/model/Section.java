package edu.mum.cs.cs425.eregistrar.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

/**
 * A specific offering of a Course in a Block, with an assigned Faculty
 * member and a seat capacity.
 *
 * {@code version} backs optimistic locking: the seat check and seat
 * decrement in RegistrationService.register() run in one transaction, so a
 * lost update raises an OptimisticLockingFailureException instead of letting
 * two concurrent registrations both claim the last seat (BR7).
 */
@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Block block;

    @ManyToOne
    private Faculty faculty;

    private int capacity;
    private int registeredCount;

    @Version
    private long version;

    protected Section() {
    }

    public Section(Course course, Block block, Faculty faculty, int capacity) {
        this.course = course;
        this.block = block;
        this.faculty = faculty;
        this.capacity = capacity;
        this.registeredCount = 0;
    }

    public Long getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public Block getBlock() {
        return block;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getRegisteredCount() {
        return registeredCount;
    }

    public int getRemainingSeats() {
        return capacity - registeredCount;
    }

    public boolean hasAvailableSeat() {
        return registeredCount < capacity;
    }

    public void incrementRegisteredCount() {
        if (!hasAvailableSeat()) {
            throw new IllegalStateException("Section " + id + " is already full");
        }
        registeredCount++;
    }

    public void decrementRegisteredCount() {
        if (registeredCount > 0) {
            registeredCount--;
        }
    }
}

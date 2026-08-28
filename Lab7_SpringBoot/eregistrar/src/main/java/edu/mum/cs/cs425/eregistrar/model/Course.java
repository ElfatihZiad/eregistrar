package edu.mum.cs.cs425.eregistrar.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String title;
    private int level;

    protected Course() {
    }

    public Course(String code, String title, int level) {
        this.code = code;
        this.title = title;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getLevel() {
        return level;
    }
}

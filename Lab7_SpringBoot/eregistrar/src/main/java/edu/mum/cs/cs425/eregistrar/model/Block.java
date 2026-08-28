package edu.mum.cs.cs425.eregistrar.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/** An 8-week teaching period; a Section is offered within exactly one Block. */
@Entity
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int sequence;
    private String label;

    protected Block() {
    }

    public Block(int sequence, String label) {
        this.sequence = sequence;
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public int getSequence() {
        return sequence;
    }

    public String getLabel() {
        return label;
    }
}

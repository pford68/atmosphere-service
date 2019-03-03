package com.masterpeace.atmosphere.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The current running state of the entity--on, off, deleted.
 */
@Entity
public class State {
    public static final int IDLE_STATE = -1;

    @Id
    private int id;
    private String value;
    private String description;

    protected State() {  }

    public State(int id, String value, String description) {
        this.id = id;
        this.value = value;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

}

package com.masterpeace.atmosphere.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 */
@Entity
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String value;

    protected Alarm() {
    }

    public Alarm(String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}

package com.masterpeace.atmosphere.model;

import javax.persistence.*;

/**
 *
 */
@Entity
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String value;

    // Required by JPA
    protected Region() {}

    public Region(String value){
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}

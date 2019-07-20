package com.masterpeace.atmosphere.model;

import javax.persistence.*;

/**
 *
 */
@Entity
public class Dns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "tinyint default 0")
    private boolean exposed = false;
    private String value;

    // Required by JPA
    protected Dns() { }

    public Dns(boolean exposed, String value) {
        this.exposed = exposed;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public boolean isExposed() {
        return exposed;
    }
}

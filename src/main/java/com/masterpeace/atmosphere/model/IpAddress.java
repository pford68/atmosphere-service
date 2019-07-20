package com.masterpeace.atmosphere.model;

import javax.persistence.*;

/**
 * Represents an IP address, which can be marked as public or private, or conceivable as something else.
 */
@Entity
public class IpAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String value;
    @Column(columnDefinition = "tinyint default 0")
    private boolean exposed = false;

    protected IpAddress() {
    }

    public IpAddress(String value, boolean exposed) {
        this.value = value;
        this.exposed = exposed;
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

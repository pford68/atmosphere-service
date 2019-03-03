package com.masterpeace.atmosphere.model;


import javax.persistence.*;

/**
 * Represents the status or priority of an instance or volume.  Currently we have only
 * default or protected.  However, in the future, we might have more.  That was the primary
 * reason for not simply adding a "protect" or "guarded" boolean property to Instance or Volume.
 */
@Entity
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String value;
    @Column(columnDefinition = "VARCHAR(255) default 'SAVE,UPDATE,DELETE'")
    private String rules;

    protected Status(){}


    public Status(String value, String rules) {
        this.value = value;
        this.rules = rules;
    }

    public String getValue() {
        return value;
    }

    public long getId() {
        return id;
    }

    public String getRules() {
        return rules;
    }
}

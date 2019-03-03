package com.masterpeace.atmosphere.model;


import javax.persistence.*;

/**
 * Represents the Atmosphere's available instance types, which are translated into the
 * comparable underlying service provider's instance type.
 */
@Entity
public class InstanceType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String value;
    private String description;

    protected InstanceType(){}

    public InstanceType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}

package com.masterpeace.atmosphere.model;


import javax.persistence.*;

/**
 * Represents the volume type
 */
@Entity
public class VolumeType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String value;

    @ManyToOne
    private Provider provider;

    protected VolumeType(){}

    public VolumeType(String value, Provider provider) {
        this.provider = provider;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public long getId() {
        return id;
    }

    public Provider getProvider() {
        return provider;
    }
}

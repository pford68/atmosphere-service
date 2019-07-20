package com.masterpeace.atmosphere.model;

import javax.persistence.*;

/**
 * Represents a KeyPair for SSH.  This may not be used by all service providers.
 * It is primarily used by EC2.
 */
@Entity
public class KeyPair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String value;
    @OneToOne
    private Region region;
    @OneToOne
    private Provider provider;

    // Required by JPA
    protected KeyPair(){}

    public KeyPair(String value, Region region, Provider provider) {
        this.value = value;
        this.region = region;
        this.provider = provider;
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

    public Region getRegion() {
        return region;
    }
}

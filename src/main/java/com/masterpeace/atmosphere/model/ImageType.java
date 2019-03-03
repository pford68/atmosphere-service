package com.masterpeace.atmosphere.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents an Image type, brand, and/or configuration.
 */
@Entity
public class ImageType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String value;
    private String description;

    protected ImageType(){}

    public ImageType(String value, String description) {
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

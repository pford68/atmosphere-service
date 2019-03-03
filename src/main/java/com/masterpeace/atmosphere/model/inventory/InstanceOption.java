package com.masterpeace.atmosphere.model.inventory;

import com.masterpeace.atmosphere.model.ImageType;
import com.masterpeace.atmosphere.model.InstanceType;

import javax.persistence.*;
import java.util.List;

/**
 * Represents an instance configuraiton option in the inventory.
 */
@Entity
public class InstanceOption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private ImageType image;
    @OneToOne
    private InstanceType instance;

    protected InstanceOption(){}

    public InstanceOption(ImageType image, InstanceType instanceType) {
        this.image = image;
        this.instance = instanceType;
    }

    public long getId() {
        return id;
    }

    public ImageType getImage() {
        return image;
    }

    public InstanceType getInstance() {
        return instance;
    }
}

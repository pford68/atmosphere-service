package com.masterpeace.atmosphere.model.inventory;

import javax.persistence.*;

/**
 *  Represents a standard configuration option for all instance types in the inventory.
 */
@Entity
public class InstanceConfigurationOption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String defaultValue;
    @Column(columnDefinition = "tinyint default 1")
    private boolean active = true;

    protected InstanceConfigurationOption(){}

    public InstanceConfigurationOption(String name, String defaultValue, boolean active) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isActive() {
        return active;
    }
}

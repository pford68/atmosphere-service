package com.masterpeace.atmosphere.model;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name="usage_limit_type")
public class LimitType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String family;
    @Column(name="size")
    private int defaultSize;

    protected LimitType(){}

    public LimitType(String value, String family, int defaultSize) {
        this.name = value;
        this.family = family;
        this.defaultSize = defaultSize;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDefaultSize() {
        return defaultSize;
    }

    public String getFamily() {
        return family;
    }
}

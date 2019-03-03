package com.masterpeace.atmosphere.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Represents a snapshot.  Having a separate table for snapshots allows us to store multiple ones
 * per volume or per other entity.
 */
@Entity
public class Snapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long createdDate;

    protected Snapshot() {}

    public Snapshot(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getId() {
        return id;
    }

    public long getCreatedDate() {
        return createdDate;
    }

}

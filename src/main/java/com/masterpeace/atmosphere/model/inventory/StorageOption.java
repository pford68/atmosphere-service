package com.masterpeace.atmosphere.model.inventory;

import com.masterpeace.atmosphere.model.Snapshot;
import com.masterpeace.atmosphere.model.VolumeType;

import javax.persistence.*;

/**
 * Represents a storage option in the inventory
 */
@Entity
public class StorageOption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String type;
    private String device;
    private int size;
    @OneToOne
    private VolumeType volumeType;
    private String iOPs;
    @OneToOne
    private Snapshot snapshot;
    @Column(columnDefinition = "tinyint default 0")
    private boolean deleteOnTermination = false;
    @Column(columnDefinition = "tinyint default 0")
    private boolean encrypted = false;

    protected StorageOption(){}

    public StorageOption(String type, String device, int size, VolumeType volumeType, String iOPs, Snapshot snapshot, boolean deleteOnTermination, boolean encrypted) {
        this.type = type;
        this.device = device;
        this.size = size;
        this.volumeType = volumeType;
        this.iOPs = iOPs;
        this.snapshot = snapshot;
        this.deleteOnTermination = deleteOnTermination;
        this.encrypted = encrypted;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getDevice() {
        return device;
    }

    public int getSize() {
        return size;
    }

    public VolumeType getVolumeType() {
        return volumeType;
    }

    public String getiOPs() {
        return iOPs;
    }

    public Snapshot getSnapshot() {
        return snapshot;
    }

    public boolean isDeleteOnTermination() {
        return deleteOnTermination;
    }

    public boolean isEncrypted() {
        return encrypted;
    }
}

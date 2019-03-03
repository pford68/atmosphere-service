package com.masterpeace.atmosphere.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.masterpeace.atmosphere.serializers.VolumeSerializer;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * Represents a virtual storage volume
 */
@Entity
@JsonSerialize(using = VolumeSerializer.class)
public class Volume implements Protectable {

    public static final int ATTACHED_STATE = 1;
    public static final int DETACHED_STATE = 0;
    public static final int SUSPENDED_STATE = -1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @OneToOne
    private VolumeType type;
    private int size;
    @OneToOne
    private UserGroup userGroup;
    @OneToOne
    private Region region;
    @OneToOne
    private State state;
    @OneToOne
    private Status status;
    @OneToOne
    private Alarm alarmStatus;
    private long createdDate;
    @Column(columnDefinition = "bigint default 0")
    private long lastModifiedDate = 0;
    @Column(columnDefinition = "tinyint default 0")
    private boolean monitoringEnabled = false;
    @Column(columnDefinition = "tinyint default 0")
    private boolean encrypted = false;
    @OneToOne
    private Snapshot snapshot;
    @ManyToOne
    private Instance instance;

    protected Volume() {
    }

    public Volume(VolumeBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.type = builder.type;
        this.size = builder.size;
        this.region = builder.region;
        this.userGroup = builder.userGroup;
        this.state = builder.state;
        this.status = builder.status;
        this.alarmStatus = builder.alarmStatus;
        this.createdDate = builder.createdDate;
        this.monitoringEnabled = builder.monitoringEnabled;
        this.encrypted = builder.encrypted;
        this.snapshot = builder.snapshot;
        this.instance = builder.instance;
        this.lastModifiedDate = new Date().getTime();
    }

    @PrePersist
    protected void prePersist() {
        this.createdDate = new Date().getTime();
    }

    @PreUpdate
    protected void preUpdate(){
        this.lastModifiedDate = new Date().getTime();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public VolumeType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public Region getRegion() {
        return region;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public State getState() {
        return state;
    }

    public Status getStatus() {
        return status;
    }

    public Alarm getAlarmStatus() {
        return alarmStatus;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    @Override
    public long getLastModifiedDate() {
        return lastModifiedDate;
    }

    public boolean isMonitoringEnabled() {
        return monitoringEnabled;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public Snapshot getSnapshot() {
        return snapshot;
    }

    public Instance getInstance() {
        return instance;
    }

    /**
     * Simplifies creating Volumes, and does so without using setters, thus reducing the mutability of the class.
     */
    public static class VolumeBuilder {

        private long id;
        private String name;
        private VolumeType type;
        private int size;
        private UserGroup userGroup;
        private Region region;
        private State state;
        private Status status;
        private Alarm alarmStatus;
        private long createdDate;
        private boolean monitoringEnabled = false;
        private boolean encrypted = false;
        private Snapshot snapshot;
        private Instance instance;

        public VolumeBuilder(){
            this.createdDate = new Date().getTime();
        }
        public VolumeBuilder(Volume volume){
            this.id = volume.getId();
            this.name = volume.getName();
            this.type = volume.getType();
            this.size = volume.getSize();
            this.region = volume.getRegion();
            this.userGroup = volume.getUserGroup();
            this.state = volume.getState();
            this.status = volume.getStatus();
            this.alarmStatus = volume.getAlarmStatus();
            this.createdDate = volume.getCreatedDate();
            this.monitoringEnabled = volume.isMonitoringEnabled();
            this.encrypted = volume.isEncrypted();
            this.snapshot = volume.getSnapshot();
            this.instance = volume.getInstance();
        }

        public VolumeBuilder setName(String name){
            this.name = name;
            return this;
        }
        public VolumeBuilder setVolumeType(VolumeType type){
            this.type = type;
            return this;
        }
        public VolumeBuilder setTimezone(Region value){
            this.region = value;
            return this;
        }
        public VolumeBuilder setSize(int value){
            this.size = value;
            return this;
        }
        public VolumeBuilder setUserGroup(UserGroup value){
            this.userGroup = value;
            return this;
        }
        public VolumeBuilder setState(State value){
            this.state = value;
            return this;
        }
        public VolumeBuilder setStatus(Status value){
            this.status = value;
            return this;
        }
        public VolumeBuilder setAlarmStatus(Alarm value){
            this.alarmStatus = value;
            return this;
        }
        public VolumeBuilder setMonitoringEnabled(boolean value){
            this.monitoringEnabled = value;
            return this;
        }
        public VolumeBuilder setEncrypted(boolean value){
            this.encrypted = value;
            return this;
        }
        public VolumeBuilder setSnapshot(Snapshot snapshot){
            this.snapshot = snapshot;
            return this;
        }
        public VolumeBuilder setInstance(Instance instance){
            this.instance = instance;
            return this;
        }
        public Volume build(){
            return new Volume(this);
        }
    }
}

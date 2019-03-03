package com.masterpeace.atmosphere.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a VM
 */

@Entity
public class Instance implements Protectable{

    public static final int ACTIVE_STATE = 1;
    public static final int INACTIVE_STATE = 0;
    public static final int SUSPENDED_STATE = -1;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @OneToOne
    private UserGroup userGroup;
    @OneToOne
    private Status status;
    @OneToOne
    private InstanceType type;
    @OneToOne
    private Region region;
    @OneToOne
    private State state;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Dns> dns;
    @OneToMany(cascade = CascadeType.ALL)
    private List<IpAddress> ip;
    private Long createdDate;
    @Column(columnDefinition = "bigint default 0")
    private Long lastModifiedDate = 0L;
    @OneToOne
    private KeyPair keyPair;
    @Column(columnDefinition = "tinyint default 0")
    private boolean monitoringEnabled;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<SecurityGroup> securityGroups;
    @OneToOne
    private Provider provider;
    @OneToMany
    private List<Volume> volumes;
    
    // Required by JPA
    protected Instance(){}

    public Instance(InstanceBuilder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.userGroup = builder.userGroup;
        this.status = builder.status;
        this.type = builder.type;
        this.region = builder.region;
        this.state = builder.state;
        this.dns = builder.dns;
        this.ip = builder.ip;
        this.createdDate = builder.createdDate;
        this.keyPair = builder.keyPair;
        this.monitoringEnabled = builder.monitoringEnabled;
        this.securityGroups = builder.securityGroups;
        this.provider = builder.provider;
        this.volumes = builder.volumes;
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

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public Status getStatus() {
        return status;
    }

    public InstanceType getType() {
        return type;
    }

    public Region getRegion() {
        return region;
    }

    public State getState() {
        return state;
    }

    public List<Dns> getDns() {
        return dns;
    }

    public List<IpAddress> getIp() {
        return ip;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public long getLastModifiedDate() {
        return lastModifiedDate;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public boolean isMonitoringEnabled() {
        return monitoringEnabled;
    }

    public List<SecurityGroup> getSecurityGroups() {
        return securityGroups;
    }

    public Provider getProvider() {
        return provider;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }



    /**
     * Simplifies creating new Instances.  It also creates new instances without requiring setters.
     * In other words, Instances remain mostly (though not completely) immutable outside the
     * Instance class.
     */
    public static class InstanceBuilder {
        private long id;
        private String name;
        private UserGroup userGroup;
        private Status status;
        private InstanceType type;
        private Region region;
        private State state;
        private List<Dns> dns;
        private List<IpAddress> ip;
        private Long createdDate;
        private KeyPair keyPair;
        private boolean monitoringEnabled;
        private List<SecurityGroup> securityGroups;
        private Provider provider;
        private List<Volume> volumes;

        public InstanceBuilder(){
            this.createdDate = new Date().getTime();
            this.dns = new ArrayList<Dns>();
            this.ip = new ArrayList<IpAddress>();
            this.securityGroups = new ArrayList<SecurityGroup>();
            this.volumes = new ArrayList<Volume>();
        }
        public InstanceBuilder(Instance instance){
            this();
            this.id = instance.getId();
            this.name = instance.name;
            this.userGroup = instance.userGroup;
            this.status = instance.status;
            this.type = instance.type;
            this.region = instance.region;
            this.state = instance.state;
            this.dns = instance.dns;
            this.ip = instance.ip;
            this.createdDate = new Date().getTime();
            this.keyPair = instance.keyPair;
            this.monitoringEnabled = instance.monitoringEnabled;
            this.securityGroups = instance.securityGroups;
            this.provider = instance.provider;
            this.volumes = instance.volumes;
        }

        public static InstanceBuilder getBuilderInstance(){
            return new InstanceBuilder();
        }

        public Instance build(){
            return new Instance(this);
        }
        public InstanceBuilder setId(long id){
            this.id = id;
            return this;
        }
        public InstanceBuilder setName(String name){
            this.name = name;
            return this;
        }
        public InstanceBuilder setStatus(Status status){
            this.status = status;
            return this;
        }
        public InstanceBuilder setState(State state){
            this.state = state;
            return this;
        }
        public InstanceBuilder setType(InstanceType value){
            this.type = value;
            return this;
        }
        public InstanceBuilder setTimezone(Region value){
            this.region = value;
            return this;
        }
        public InstanceBuilder setProvider(Provider value){
            this.provider = value;
            return this;
        }
        public InstanceBuilder setUserGroup(UserGroup value){
            this.userGroup = value;
            return this;
        }
        public InstanceBuilder enableMonitoring(boolean enable){
            this.monitoringEnabled = enable;
            return this;
        }
        public InstanceBuilder addDns(Dns dns){
            if (this.dns == null){
                this.dns = new ArrayList<Dns>();
            }
            this.dns.add(dns);
            return this;
        }
        public InstanceBuilder addIp(IpAddress ip){
            if (this.ip == null){
                this.ip = new ArrayList<IpAddress>();
            }
            this.ip.add(ip);
            return this;
        }
        public InstanceBuilder addSecurityGroup(SecurityGroup group){
            this.securityGroups.add(group);
            return this;
        }
        public InstanceBuilder setRegion(Region region) {
            this.region = region;
            return this;
        }
        public InstanceBuilder addVolume(Volume volume){
            this.volumes.add(volume);
            return this;
        }
        public InstanceBuilder setVolumes(List<Volume> volumes){
            if (volumes == null){
                volumes = new ArrayList<Volume>();
            }
            this.volumes = volumes;
            return this;
        }
    }
}

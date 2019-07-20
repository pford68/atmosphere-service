package com.masterpeace.atmosphere.model;

import javax.persistence.*;
import java.util.List;

/**
 *
 */
@Entity
public class LoadBalancer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToOne
    private Dns dns;
    @OneToOne
    private Region region;
    @OneToOne
    private UserGroup userGroup;
    @ManyToMany
    private List<SecurityGroup> securityGroups;
    private int instanceCount;
    private long createdDate;
    @Column(columnDefinition = "tinyint default 0")
    private boolean monitoringEnabled = false;

    protected LoadBalancer(){}

    public LoadBalancer(String name, Dns dns, Region region, UserGroup userGroup, List<SecurityGroup> securityGroups, int instanceCount, long createdDate, boolean monitoringEnabled) {
        this.name = name;
        this.dns = dns;
        this.region = region;
        this.userGroup = userGroup;
        this.securityGroups = securityGroups;
        this.instanceCount = instanceCount;
        this.createdDate = createdDate;
        this.monitoringEnabled = monitoringEnabled;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Dns getDns() {
        return dns;
    }

    public Region getRegion() {
        return region;
    }

    public int getInstanceCount() {
        return instanceCount;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public boolean isMonitoringEnabled() {
        return monitoringEnabled;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public List<SecurityGroup> getSecurityGroups() {
        return securityGroups;
    }
}

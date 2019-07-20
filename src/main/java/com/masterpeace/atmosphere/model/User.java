package com.masterpeace.atmosphere.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.SortedSet;

/**
 * Represents an Atmosphere user
 */
@Entity
public class User implements Comparable<User>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String first;
    private String middle;
    private String last;
    @NotNull
    private String email;
    @JsonIgnore
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @OrderBy("id")
    private SortedSet<SecurityGroup> securityGroups;
    @ManyToOne
    private KeyPair keyPair;
    @Column(columnDefinition = "tinyint default 1")
    private boolean active = true;
    @ManyToMany(targetEntity = UserGroup.class, mappedBy = "users", fetch = FetchType.EAGER)
    @OrderBy("id")
    private SortedSet<UserGroup> userGroups;

    protected User(){}

    public User(String email, String first, String last, String middle, String password,
                SortedSet<SecurityGroup> securityGroups, KeyPair keyPair, boolean active,
                SortedSet<UserGroup> groups){
        this.email = email;
        this.first = first;
        this.last = last;
        this.middle = middle;
        this.password = password;
        this.keyPair = keyPair;
        this.active = active;
        this.userGroups = groups;
        this.securityGroups = securityGroups;
    }

    public long getId() {
        return id;
    }

    public String getFirst() {
        return first;
    }

    public String getMiddle() {
        return middle;
    }

    public String getLast() {
        return last;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public SortedSet<SecurityGroup> getSecurityGroups() {
        return securityGroups;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public boolean isActive() {
        return active;
    }

    public SortedSet<UserGroup> getUserGroups() {
        return userGroups;
    }

    @Transient
    public boolean isGroupAdmin(Protectable protectable){
        for (SecurityGroup securityGroup : this.securityGroups){
            if (securityGroup.canDelete(protectable)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int compareTo(User that){
        return this.last.compareTo(that.last);
    }
}

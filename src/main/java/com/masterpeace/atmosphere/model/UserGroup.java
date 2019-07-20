package com.masterpeace.atmosphere.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.masterpeace.atmosphere.serializers.UserGroupSerializer;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;

/**
 * Represents a collection of users who have access to an instance or volume.
 */
@Entity
@JsonSerialize(using = UserGroupSerializer.class)
public class UserGroup implements Comparable<UserGroup>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_group_users", joinColumns=@JoinColumn(name="user_group"), inverseJoinColumns=@JoinColumn(name="users"))
    @OrderBy("last")
    private SortedSet<User> users;
    @Column(columnDefinition = "BIGINT")
    private long createdDate;

    protected UserGroup(){}

    public UserGroup(String name, SortedSet<User> users, long createdDate) {
        this.name = name;
        this.users = users;
        this.createdDate = createdDate;
    }

    @PrePersist
    protected void prePersist() {
        this.createdDate = new Date().getTime();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SortedSet<User> getUsers() {
        return users;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    @Override
    public int compareTo(UserGroup that){
        if (this.id == that.id) return 0;
        return this.id > that.id ? 1 : -1;
    }
}

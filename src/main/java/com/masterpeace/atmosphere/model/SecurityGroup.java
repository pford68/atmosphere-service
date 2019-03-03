package com.masterpeace.atmosphere.model;

import javax.persistence.*;

/**
 * Represents a security group, which assigned users to a security level.  Security groups are
 * created for a user group.  In this way, a user can have privileges in different user groups.
 */
@Entity
public class SecurityGroup implements Comparable<SecurityGroup>{

    public static final String DELETE_PERMISSION = "DELETE";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String value;
    @Column(columnDefinition = "VARCHAR(255) default 'SELECT,INSERT,UPDATE'")
    private String permissions;
    private String subscriptions;
    @OneToOne
    private UserGroup userGroup;

    // Required by JPA
    protected SecurityGroup(){}

    public SecurityGroup(SecurityGroupBuilder builder) {
        this.value = builder.value;
        this.permissions = builder.permissions;
        this.subscriptions = builder.subscriptions;
        this.userGroup = builder.userGroup;
    }

    public String getValue() {
        return value;
    }

    public long getId() {
        return id;
    }

    public String getPermissions() {
        return permissions;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setSubscriptions(String subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Transient
    public boolean canDelete(Protectable protectable){
        UserGroup userGroup = protectable.getUserGroup();
        return (this.userGroup.getId() == userGroup.getId()) && this.permissions.contains(DELETE_PERMISSION);
    }


    @Override
    public int compareTo(SecurityGroup that){
        if (this.id == that.id) return 0;
        return this.id > that.id ? 1 : -1;
    }



    public static class SecurityGroupBuilder {
        private long id;
        private String value;
        private String permissions;
        private String subscriptions;
        private UserGroup userGroup;

        public SecurityGroupBuilder(){

        }

        public SecurityGroupBuilder(SecurityGroup group){
            this.value = group.value;
            this.permissions = group.permissions;
            this.subscriptions = group.subscriptions;
            this.userGroup = group.userGroup;
        }

        public SecurityGroupBuilder setPermissions(String permissions){
            this.permissions = permissions;
            return this;
        }
        public SecurityGroupBuilder addPermissions(String[] permissions){
            this.permissions += String.join(",", permissions);
            return this;
        }
        public SecurityGroupBuilder setSubscriptions(String subscriptions){
            this.subscriptions = subscriptions;
            return this;
        }
        public SecurityGroupBuilder addSubscriptions(String[] subscriptions){
            this.subscriptions += String.join(",", subscriptions);
            return this;
        }
        public SecurityGroupBuilder setValue(String value){
            this.value = value;
            return this;
        }
        public SecurityGroup build(){
            return new SecurityGroup(this);
        }
    }
}

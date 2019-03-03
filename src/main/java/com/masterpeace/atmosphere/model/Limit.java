package com.masterpeace.atmosphere.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Represents an individual usage limit--ip addresses, storage volumes, scaling, etc.
 */
@Entity
@Table(name="usage_limit")
public class Limit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int size;
    @OneToOne
    private LimitType type;
    @ManyToOne
    private Provider provider;
    @JsonIgnore
    @OneToOne
    private User user;

    protected Limit(){}

    public Limit(LimitBuilder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.size = builder.size;
        this.provider = builder.provider;
        this.user = builder.user;
    }

    public long getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public Provider getProvider() {
        return provider;
    }

    public LimitType getType() {
        return type;
    }

    public User getUser() {
        return user;
    }



    public static class LimitBuilder {
        private long id;
        private int size;
        private LimitType type;
        private Provider provider;
        private User user;

        public LimitBuilder(){
        }
        public LimitBuilder(Limit limit){
            this.type = limit.type;
            this.size = limit.size;
            this.provider = limit.provider;
            this.user = limit.user;
        }

        public LimitBuilder setSize(int size){
            this.size = size;
            return this;
        }
        public LimitBuilder setType(LimitType type){
            this.type = type;
            return this;
        }
        public LimitBuilder setProvider(Provider provider){
            this.provider = provider;
            return this;
        }
        public LimitBuilder setUser(User user) {
            this.user = user;
            return this;
        }
        public Limit build(){
            return new Limit(this);
        }
    }
}

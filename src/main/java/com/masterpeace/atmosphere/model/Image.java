package com.masterpeace.atmosphere.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Represents an image
 */
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String source;
    private String owner;
    @OneToOne
    private UserGroup userGroup;
    @OneToOne
    private ImageType type;
    @Column(columnDefinition = "tinyint default 0")
    private boolean exposed = false;
    @OneToOne
    private State state;
    private String platform;   //TODO: this probably needs a class:  we may have a fixed set of options
    private String rootDevice;
    private String architecture;
    private String description;

    protected Image(){}


    public Image(ImageBuilder builder) {
        this.name = builder.name;
        this.source = builder.source;
        this.owner = builder.owner;
        this.type = builder.type;
        this.exposed = builder.exposed;
        this.state = builder.state;
        this.platform = builder.platform;
        this.rootDevice = builder.rootDevice;
        this.architecture = builder.architecture;
        this.description = builder.description;
        this.userGroup = builder.userGroup;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String getOwner() {
        return owner;
    }

    public ImageType getType() {
        return type;
    }

    public boolean isExposed() {
        return exposed;
    }

    public State getState() {
        return state;
    }

    public String getPlatform() {
        return platform;
    }

    public String getRootDevice() {
        return rootDevice;
    }

    public String getArchitecture() {
        return architecture;
    }

    public String getDescription() {
        return description;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    /**
     * Facilitates creating images, and without setters, to reduce mutability.
     */
    public static class ImageBuilder {

        private long id;
        private String name;
        private String source;
        private String owner;
        private UserGroup userGroup;
        private ImageType type;
        private boolean exposed = false;
        private State state;
        private String platform;
        private String rootDevice;
        private String architecture;
        private String description;

        public ImageBuilder(){

        }

        public ImageBuilder(Image image){
            this.name = image.name;
            this.source = image.source;
            this.owner = image.owner;
            this.type = image.type;
            this.exposed = image.exposed;
            this.state = image.state;
            this.platform = image.platform;
            this.rootDevice = image.rootDevice;
            this.architecture = image.architecture;
            this.description = image.description;
            this.userGroup = image.userGroup;
        }

        public ImageBuilder setState(State state){
            this.state = state;
            return this;
        }

        public ImageBuilder setVisbility(boolean visible){
            this.exposed = visible;
            return this;
        }

        public Image build(){
            return new Image(this);
        }
    }
}

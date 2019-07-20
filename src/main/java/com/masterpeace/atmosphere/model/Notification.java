package com.masterpeace.atmosphere.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Represents a message sent to a user.
 */
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private User user;
    private String message;
    @Column(columnDefinition = "tinyint default 0")
    private boolean opened = false;   // Calling this "read" caused a problem in MySQL, as I recall.
    private long createdDate;
    @Column(columnDefinition = "bigint default 0")
    private long openedDate;

    protected Notification(){}

    public Notification(User recipient, String message, boolean read, long createdDate, long openedDate) {
        this.user = recipient;
        this.message = message;
        this.opened = read;
        this.createdDate = createdDate;
        this.openedDate = openedDate;
    }

    public Notification(NotificationBuilder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.message = builder.message;
        this.opened = builder.opened;
        this.createdDate = builder.createdDate;
        this.openedDate = builder.openedDate;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public boolean isOpened() {
        return opened;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public long getOpenedDate() {
        return openedDate;
    }



    /**
     * Facilitates creating a Notification, without using setters, in order to maintain the immutability of
     * Yes, only one property is exposed, so writing this builder (instead of one simple setter) might seem
     * to be a lot of pain for not much gain, but that may change.
     */
    public static class NotificationBuilder {
        private long id;
        private User user;
        private String message;
        private boolean opened = false;   // Calling this "read" caused a problem in MySQL, as I recall.
        private long createdDate;
        private long openedDate;

        public NotificationBuilder(){
            this.createdDate = new Date().getTime();  //PF 2015/04/11:  Not UTC.  Will have to fix that in all builders.
        }
        public NotificationBuilder(Notification notification){
            this.id = notification.getId();
            this.user = notification.getUser();
            this.message = notification.getMessage();
            this.opened = notification.isOpened();
            this.createdDate = notification.getCreatedDate();
            this.openedDate = notification.getOpenedDate();
        }

        public NotificationBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public NotificationBuilder setOpened(boolean value){
            this.opened = value;
            this.openedDate = new Date().getTime();  //PF 2015/04/11:  Not UTC.  Will have to fix that in all builders.
            return this;
        }

        public NotificationBuilder setMessage(String value){
            this.message = value;
            return this;
        }

        public NotificationBuilder setCreatedDate(long value){
            this.createdDate = value;
            return this;
        }

        public NotificationBuilder setOpenedDate(long value){
            this.openedDate = value;
            return this;
        }

        public NotificationBuilder setUser(User user){
            this.user = user;
            return this;
        }


        public Notification build(){
            return new Notification(this);
        }
    }
}

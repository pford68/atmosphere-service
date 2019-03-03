package com.masterpeace.atmosphere.model;

/**
 * Created by philip on 4/12/15.
 */
public interface Protectable {

    Status getStatus();
    State getState();
    UserGroup getUserGroup();
    long getId();
    long getLastModifiedDate();
}

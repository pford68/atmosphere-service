package com.masterpeace.atmosphere.dto;

import com.masterpeace.atmosphere.model.User;
import com.masterpeace.atmosphere.model.UserGroup;

/**
 *
 */
public class UserGroupDto {

    private final long id;
    private final String name;

    public UserGroupDto(UserGroup userGroup){
        this.id = userGroup.getId();
        this.name = userGroup.getName();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

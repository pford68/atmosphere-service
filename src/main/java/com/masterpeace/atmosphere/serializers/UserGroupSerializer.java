package com.masterpeace.atmosphere.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.masterpeace.atmosphere.model.User;
import com.masterpeace.atmosphere.model.UserGroup;

import java.io.IOException;

/**
 * Custom serializer for the UserGroup class
 */
public class UserGroupSerializer extends JsonSerializer<UserGroup>{

    @Override
    public void serialize(UserGroup value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("name", value.getName());
        gen.writeNumberField("createdDate", value.getCreatedDate());

        Iterable<User> users = value.getUsers();
        gen.writeArrayFieldStart("users");
        if (users != null) {
            for (User user : users) {
                gen.writeObject(new UserSummary(user));
            }
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }


}

@JsonSerialize(using = UserSummarySerializer.class)
class UserSummary {
    long id;
    String firstName;
    String lastName;
    String email;
    boolean active = true;

    public UserSummary(User user) {
        this.id = user.getId();
        this.firstName = user.getFirst();
        this.lastName = user.getLast();
        this.email = user.getEmail();
        this.active = user.isActive();
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActive() {
        return active;
    }
}


class UserSummarySerializer extends JsonSerializer<UserSummary>{

    @Override
    public void serialize(UserSummary value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("firstName", value.getFirstName());
        gen.writeStringField("lastName", value.getLastName());
        gen.writeStringField("email", value.getEmail());
        gen.writeBooleanField("active", value.isActive());
        gen.writeEndObject();
    }
}

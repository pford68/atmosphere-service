package com.masterpeace.atmosphere.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.masterpeace.atmosphere.model.Instance;
import com.masterpeace.atmosphere.model.Volume;

import java.io.IOException;

/**
 * Custom serializer for the Volume class.
 */
public class VolumeSerializer extends JsonSerializer<Volume>{


    @Override
    public void serialize(Volume value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeNumberField("size", value.getSize());
        gen.writeNumberField("createdDate", value.getCreatedDate());
        gen.writeStringField("name", value.getName());
        gen.writeBooleanField("encrypted", value.isEncrypted());
        gen.writeBooleanField("monitoringEnabled", value.isMonitoringEnabled());
        gen.writeObjectField("userGroup", value.getUserGroup());
        gen.writeObjectField("region", value.getRegion());
        gen.writeObjectField("state", value.getState());
        gen.writeObjectField("status", value.getStatus());
        gen.writeObjectField("alarmStatus", value.getAlarmStatus());
        gen.writeObjectField("snapshot", value.getSnapshot());
        gen.writeObjectField("type", value.getType());

        Instance instance = value.getInstance();
        if (instance != null){
            gen.writeObjectField("instance", new InstanceSummary(instance.getId(), instance.getName()));
        }
        gen.writeEndObject();
    }
}

@JsonSerialize(using = InstanceSummarySerializer.class)
class InstanceSummary {
    long id;
    String name;

    InstanceSummary(long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}


class InstanceSummarySerializer extends JsonSerializer<InstanceSummary>{

    @Override
    public void serialize(InstanceSummary value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("name", value.getName());
        gen.writeEndObject();
    }
}

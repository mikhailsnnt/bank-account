package com.sainnt.cqrs.core.entity;

import com.sainnt.cqrs.core.event.BaseEvent;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document(collection = "eventStore")
public class EventModel {
    @Id
    private String id;
    private Date timeStamp;
    private String aggregateId;
    private String aggregateType;
    private int version;
    private String eventType;
    private BaseEvent event;
}

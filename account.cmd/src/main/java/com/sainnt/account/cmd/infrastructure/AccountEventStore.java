package com.sainnt.account.cmd.infrastructure;

import com.sainnt.account.cmd.domain.AccountAggregate;
import com.sainnt.account.cmd.repository.EventModelRepository;
import com.sainnt.cqrs.core.entity.EventModel;
import com.sainnt.cqrs.core.event.BaseEvent;
import com.sainnt.cqrs.core.exception.ConcurrencyException;
import com.sainnt.cqrs.core.infrastracture.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountEventStore implements EventStore {
    private final EventModelRepository repository;

    @Autowired
    public AccountEventStore(EventModelRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        List<EventModel> eventModels = repository.findAllByAggregateId(aggregateId);
        if(expectedVersion!=-1 && eventModels.get(eventModels.size()-1).getVersion() != expectedVersion)
            throw new ConcurrencyException();
        int version = expectedVersion;
        for (BaseEvent event :
                events) {
            event.setVersion(version++);
            EventModel model = EventModel.builder()
                    .id(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .eventType(event.getClass().getTypeName())
                    .event(event)
                    .timeStamp(new Date())
                    .version(event.getVersion())
                    .build();
            EventModel persistedModel = repository.save(model);
            // TODO: produce event to  Kafka
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        return null;
    }
}

package com.sainnt.account.cmd.infastructure;

import com.sainnt.account.cmd.domain.AccountAggregate;
import com.sainnt.cqrs.core.domain.AggregateRoot;
import com.sainnt.cqrs.core.event.BaseEvent;
import com.sainnt.cqrs.core.handler.EventSourcingHandler;
import com.sainnt.cqrs.core.infrastracture.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {
    private final EventStore eventStore;

    @Autowired
    public AccountEventSourcingHandler(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        AccountAggregate aggregate = new AccountAggregate();
        List<BaseEvent> events = eventStore.getEvents(id);
        if(events!=null && !events.isEmpty())
            aggregate.replayEvents(events);
        return aggregate;
    }
}

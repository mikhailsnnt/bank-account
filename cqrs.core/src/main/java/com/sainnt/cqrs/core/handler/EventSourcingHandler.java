package com.sainnt.cqrs.core.handler;

import com.sainnt.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T extends AggregateRoot> {
    void save(AggregateRoot aggregate);
    T getById(String id);
}

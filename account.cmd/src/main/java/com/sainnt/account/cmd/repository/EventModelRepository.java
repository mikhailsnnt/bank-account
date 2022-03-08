package com.sainnt.account.cmd.repository;

import com.sainnt.cqrs.core.entity.EventModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EventModelRepository extends MongoRepository<EventModel, String > {
    List<EventModel> findAllByAggregateId(String id);
}

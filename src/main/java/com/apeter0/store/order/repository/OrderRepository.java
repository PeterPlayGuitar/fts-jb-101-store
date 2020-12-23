package com.apeter0.store.order.repository;

import com.apeter0.store.order.model.OrderDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<OrderDoc, ObjectId> {
}

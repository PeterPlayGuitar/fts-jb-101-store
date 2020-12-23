package com.apeter0.store.deliveryTime.repository;

import com.apeter0.store.deliveryTime.model.DeliveryTimeDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryTimeRepository extends MongoRepository<DeliveryTimeDoc, ObjectId> {
}

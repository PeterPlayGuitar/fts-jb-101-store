package com.apeter0.store.street.repository;

import com.apeter0.store.street.model.StreetDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StreetRepository extends MongoRepository<StreetDoc, ObjectId> {
    Optional<StreetDoc> findByName(String name);
}

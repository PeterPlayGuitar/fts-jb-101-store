package com.apeter0.store.city.repository;

import com.apeter0.store.city.model.CityDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends MongoRepository<CityDoc, ObjectId> {
    Optional<CityDoc> findByName(String name);
}

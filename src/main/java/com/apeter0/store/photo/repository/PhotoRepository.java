package com.apeter0.store.photo.repository;

import com.apeter0.store.photo.model.PhotoDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends MongoRepository<PhotoDoc, ObjectId> {
}

package com.apeter0.store.image.repository;

import com.apeter0.store.image.model.ImageDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends MongoRepository<ImageDoc, ObjectId> {
}

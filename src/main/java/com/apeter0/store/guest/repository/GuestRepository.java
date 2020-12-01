package com.apeter0.store.guest.repository;

import com.apeter0.store.guest.model.GuestDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends MongoRepository<GuestDoc, ObjectId> {
}

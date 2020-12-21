package com.apeter0.store.cart.repository;

import com.apeter0.store.cart.model.CartDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<CartDoc, ObjectId> {
}

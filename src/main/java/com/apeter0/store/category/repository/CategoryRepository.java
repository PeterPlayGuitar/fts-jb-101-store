package com.apeter0.store.category.repository;

import com.apeter0.store.category.model.CategoryDoc;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<CategoryDoc, ObjectId> {
}

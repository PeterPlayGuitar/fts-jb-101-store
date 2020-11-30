package com.apeter0.store.category.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@Builder
public class CategoryDoc {

    private ObjectId id;
    private String name;
}

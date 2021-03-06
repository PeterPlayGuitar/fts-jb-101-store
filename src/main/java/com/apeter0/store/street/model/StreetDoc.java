package com.apeter0.store.street.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@Builder
public class StreetDoc {

    @Id
    private ObjectId id;
    private String name;
    private ObjectId cityId;
}

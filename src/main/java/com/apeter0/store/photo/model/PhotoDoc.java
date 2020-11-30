package com.apeter0.store.photo.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Builder
public class PhotoDoc {
    @Id
    private ObjectId id;
    private String contentType;
}

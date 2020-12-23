package com.apeter0.store.deliveryTime.model;

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
public class DeliveryTimeDoc {

    @Id
    private ObjectId id;
    private String name;
    private Integer index;
}

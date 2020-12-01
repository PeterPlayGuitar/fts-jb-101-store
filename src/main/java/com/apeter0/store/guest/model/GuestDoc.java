package com.apeter0.store.guest.model;

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
public class GuestDoc {

    @Id
    private ObjectId id;
    private String firstName;
    private String phoneNumber;
    private ObjectId cityId;
    private ObjectId streetId;
    private String house;
    private Integer apartment;
    private Integer entrance;
    private Integer floor;
}

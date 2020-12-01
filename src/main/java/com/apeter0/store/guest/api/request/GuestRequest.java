package com.apeter0.store.guest.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
@ApiModel(value = "Guest request")
public class GuestRequest {
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

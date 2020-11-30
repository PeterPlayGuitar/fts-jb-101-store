package com.apeter0.store.street.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
@ApiModel(value = "Street request")
public class StreetRequest {
    private ObjectId id;
    private String name;
    private ObjectId cityId;
}

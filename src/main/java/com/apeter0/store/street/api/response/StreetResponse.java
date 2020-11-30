package com.apeter0.store.street.api.response;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

@Builder
@Getter
@ApiModel(value = "Street response")
public class StreetResponse {
    private String id;
    private String name;
    private String cityId;
}

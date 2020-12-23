package com.apeter0.store.city.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
@ApiModel(value = "City request", description = "Model for creating new cities or updating existing")
public class CityRequest {

    private ObjectId id;

    @ApiParam(name = "City name", value = "City with same names can't be created", required = true)
    private String name;
}

package com.apeter0.store.street.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
@ApiModel(value = "Street request")
public class StreetRequest {
    private ObjectId id;

    @ApiParam(name = "Street name", value = "Streets with same name in same city can't be created", required = true)
    private String name;

    @ApiParam(name = "Id of the city this street belongs to", required = true)
    private ObjectId cityId;
}

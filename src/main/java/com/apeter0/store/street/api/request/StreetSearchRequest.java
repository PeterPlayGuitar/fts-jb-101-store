package com.apeter0.store.street.api.request;

import com.apeter0.store.base.api.request.SearchRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@ApiModel(value = "Street search request", description = "Represents parameters for street search")
public class StreetSearchRequest extends SearchRequest {

    @ApiParam(name = "city id", value = "Search streets by city, if null search among all streets", required = false)
    private ObjectId cityId;

}

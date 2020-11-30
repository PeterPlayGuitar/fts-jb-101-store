package com.apeter0.store.street.api.request;

import com.apeter0.store.base.api.request.SearchRequest;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
public class StreetSearchRequest extends SearchRequest {

    @ApiParam(name = "city id", value = "Search streets by city, if null search among all streets", required = false)
    private ObjectId cityId;

}

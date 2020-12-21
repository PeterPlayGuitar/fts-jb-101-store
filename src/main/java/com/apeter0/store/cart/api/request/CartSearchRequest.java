package com.apeter0.store.guest.api.request;

import com.apeter0.store.base.api.request.SearchRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@ApiModel(value = "Guest search request", description = "Represent parameters for guest search")
public class CartSearchRequest extends SearchRequest {

    @ApiParam(name = "guest id", value = "Search by guest id, if null then parameter is ignored", required = false)
    private ObjectId guestId;

}

package com.apeter0.store.order.api.request;

import com.apeter0.store.base.api.request.SearchRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@ApiModel(value = "Order search request", description = "Represent parameters for order search")
public class OrderSearchRequest extends SearchRequest {

    @ApiParam(name = "phone number", value = "Search by phone number with complete match, if null then parameter is ignored", required = false)
    private String phoneNumber;

    @ApiParam(name = "city id", value = "Search by city with complete match, if null then parameter is ignored", required = false)
    private ObjectId cityId;

    @ApiParam(name = "street id", value = "Search by street with complete match, if null then parameter is ignored", required = false)
    private ObjectId streetId;

    @ApiParam(name = "house number", value = "Search by house number with complete match, if null then parameter is ignored", required = false)
    private String house;

    @ApiParam(name = "apartment number", value = "Search by apartment number with complete match, if null then parameter is ignored", required = false)
    private Integer apartment;

    @ApiParam(name = "entrance number", value = "Search by entrance number with complete match, if null then parameter is ignored", required = false)
    private Integer entrance;

    @ApiParam(name = "floor number", value = "Search by floor number with complete match, if null then parameter is ignored", required = false)
    private Integer floor;
}

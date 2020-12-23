package com.apeter0.store.deliveryTime.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
@ApiModel(value = "DeliveryTime request")
public class DeliveryTimeRequest {

    private ObjectId id;

    @ApiParam(name = "delivery time displayed name", value = "example: Поскорее or 12:45 - 13:00", required = true)
    private String name;

    @ApiParam(name = "delivery time order index", value = "list of delivery time entities will be shown in order by indexes", required = false)
    private Integer index;
}

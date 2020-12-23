package com.apeter0.store.order.api.request;

import com.apeter0.store.order.model.PaymentMethod;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
@ApiModel(value = "Order request")
public class OrderRequest {

    private ObjectId id;

    @ApiParam(name = "Phone number", required = true)
    private String phoneNumber;

    @ApiParam(name = "Street id", required = true)
    private ObjectId streetId;

    @ApiParam(name = "House number", required = true)
    private String house;

    @ApiParam(name = "Apartment number", required = false)
    private Integer apartment;

    @ApiParam(name = "Entrance number", required = false)
    private Integer entrance;

    @ApiParam(name = "Floor number", required = false)
    private Integer floor;

    @ApiParam(name = "Cart id", required = true)
    private ObjectId cartId;

    @ApiParam(name = "Delivery time", value = "Database entity id which specifies delivery time", required = true)
    private ObjectId deliveryTimeId;

    @ApiParam(name = "Payment method", required = true)
    private PaymentMethod paymentMethod;
}

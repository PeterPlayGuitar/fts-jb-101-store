package com.apeter0.store.guest.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
@ApiModel(value = "Guest request")
public class GuestRequest {
    private ObjectId id;

    @ApiParam(name = "First name of guest", required = true)
    private String firstName;

    @ApiParam(name = "Phone number", required = true)
    private String phoneNumber;

    @ApiParam(name = "Original city of the guest", required = true)
    private ObjectId cityId;

    @ApiParam(name = "Original street of the guest", required = true)
    private ObjectId streetId;

    @ApiParam(name = "Original house number of the guest", required = true)
    private String house;

    @ApiParam(name = "Original apartment number of the guest", required = true)
    private Integer apartment;

    @ApiParam(name = "Original entrance number of the guest", required = true)
    private Integer entrance;

    @ApiParam(name = "Original floor number of the guest", required = true)
    private Integer floor;
}

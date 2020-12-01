package com.apeter0.store.guest.api.response;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@ApiModel(value = "Guest response")
public class GuestResponse {
    private String id;
    private String firstName;
    private String phoneNumber;
    private String cityId;
    private String streetId;
    private String house;
    private Integer apartment;
    private Integer entrance;
    private Integer floor;
}

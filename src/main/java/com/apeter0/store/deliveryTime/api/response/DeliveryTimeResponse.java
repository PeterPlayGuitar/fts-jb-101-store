package com.apeter0.store.deliveryTime.api.response;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@ApiModel(value = "DeliveryTime response")
public class DeliveryTimeResponse {
    private String id;
    private String name;
    private Integer index;
}

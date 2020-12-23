package com.apeter0.store.order.api.response;

import com.apeter0.store.order.model.PaymentMethod;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@ApiModel(value = "Order response")
public class OrderResponse {
    private String id;
    private String phoneNumber;
    private String streetId;
    private String house;
    private Integer apartment;
    private Integer entrance;
    private Integer floor;
    private String cartId;
    private String deliveryTimeId;
    private PaymentMethod paymentMethod;
    private Integer totalPrice;
}

package com.apeter0.store.order.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@Builder
public class OrderDoc {

    @Id
    private ObjectId id;
    private String phoneNumber;
    private ObjectId streetId;
    private String house;
    private Integer apartment;
    private Integer entrance;
    private Integer floor;
    private ObjectId cartId;
    private ObjectId deliveryTimeId;
    private PaymentMethod paymentMethod;
    private Integer totalPrice;
}

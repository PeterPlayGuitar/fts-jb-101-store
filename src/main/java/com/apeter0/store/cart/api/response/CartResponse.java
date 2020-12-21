package com.apeter0.store.cart.api.response;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.util.List;

@Builder
@Getter
@ApiModel(value = "Basket response")
public class CartResponse {

    @Builder
    @Getter
    public static class ProductIdWithQuantityResponse {
        private String productId;
        private Integer quantity;
    }

    private String id;
    private ObjectId guestId;
    private List<ProductIdWithQuantityResponse> products;
}

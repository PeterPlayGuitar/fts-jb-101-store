package com.apeter0.store.cart.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;

@Builder
@Getter
@ApiModel(value = "Basket response")
public class CartResponse {

    @ApiModel(value = "Compliance of the product with its quantity")
    @Builder
    @Setter
    @Getter
    public static class ProductIdWithQuantity {
        private String productId;
        private Integer quantity;
    }

    private String id;
    private String guestId;

    @ApiParam(name = "list of ProductWithQuantity entities", value = "ProductWithQuantity represents class with fields: product id, quantity", required = false)
    private List<ProductIdWithQuantity> products;
}

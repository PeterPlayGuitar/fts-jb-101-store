package com.apeter0.store.cart.api.request;

import com.apeter0.store.cart.model.CartDoc;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.List;

@ApiModel(value = "Basket request")
@Getter
public class CartRequest {

    @ApiModel(value = "Compliance of the product with its quantity")
    @Getter
    @Setter
    @Builder
    public static class ProductIdWithQuantity {
        private ObjectId productId;
        private Integer quantity;
    }

    private ObjectId id;

    @ApiParam(name = "Guest id", value = "Who owns this cart", required = false)
    private ObjectId guestId;

    @ApiParam(name = "list of ProductWithQuantity entities", value = "ProductWithQuantity represents class with fields: product id, quantity", required = false)
    private List<ProductIdWithQuantity> products;
}

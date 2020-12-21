package com.apeter0.store.cart.api.request;

import com.apeter0.store.cart.model.CartDoc;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@ApiModel(value = "Basket request")
public class CartRequest {
    private ObjectId id;
    private ObjectId guestId;
    private List<CartDoc.ProductIdWithQuantity> products;
}

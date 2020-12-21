package com.apeter0.store.cart.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
@Builder
public class CartDoc {

    @Getter
    @Setter
    @Builder
    public static class ProductIdWithQuantity{
        private ObjectId productId;
        private Integer quantity;
    }

    @Getter
    @Setter
    @Builder
    public static class ProductStringIdWithQuantity{
        private String productId;
        private Integer quantity;
    }

    @Getter
    @Setter
    @Builder
    public static class ListOfCartProducts{
        private List<ProductStringIdWithQuantity> products;
    }

    @Id
    private ObjectId id;
    private ObjectId guestId;
    private List<ProductIdWithQuantity> products;
}

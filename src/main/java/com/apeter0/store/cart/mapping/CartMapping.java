package com.apeter0.store.cart.mapping;

import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.base.mapping.BaseMapping;
import com.apeter0.store.cart.api.response.CartResponse;
import com.apeter0.store.cart.model.CartDoc;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CartMapping {

    public static class DocToResponseMapper extends BaseMapping<CartDoc, CartResponse> {

        private static CartResponse.ProductIdWithQuantityResponse convert(CartDoc.ProductIdWithQuantity productIdWithQuantity) {
            return CartResponse.ProductIdWithQuantityResponse.builder()
                    .productId(productIdWithQuantity.toString())
                    .quantity(productIdWithQuantity.getQuantity())
                    .build();
        }

        private static List<CartResponse.ProductIdWithQuantityResponse> convert(List<CartDoc.ProductIdWithQuantity> productsWithQuantity) {
            return productsWithQuantity.stream().map(DocToResponseMapper::convert).collect(Collectors.toList());
        }

        @Override
        public CartResponse convert(CartDoc cartDoc) {
            return CartResponse.builder()
                    .id(cartDoc.getId().toString())
                    .products(convert(cartDoc.getProducts()))
                    .guestId(cartDoc.getGuestId())
                    .build();
        }
    }

    public static class ListOfCartProductsToObjectListOfCartProducts extends BaseMapping<List<CartDoc.ProductIdWithQuantity>, CartDoc.ListOfCartProducts> {

        private CartDoc.ProductStringIdWithQuantity convert(CartDoc.ProductIdWithQuantity productIdWithQuantity) {
            return CartDoc.ProductStringIdWithQuantity.builder()
                    .productId(productIdWithQuantity.toString())
                    .quantity(productIdWithQuantity.getQuantity())
                    .build();
        }

        @Override
        public CartDoc.ListOfCartProducts convert(List<CartDoc.ProductIdWithQuantity> productIdWithQuantities) {
            return CartDoc.ListOfCartProducts.builder()
                    .products(productIdWithQuantities.stream().map(this::convert).collect(Collectors.toList()))
                    .build();
        }
    }

    public static class ObjectListOfCartProductsToListOfCartProducts extends BaseMapping<CartDoc.ListOfCartProducts, List<CartDoc.ProductIdWithQuantity>> {

        private CartDoc.ProductIdWithQuantity convert(CartDoc.ProductStringIdWithQuantity productIdWithQuantity) {
            return CartDoc.ProductIdWithQuantity.builder()
                    .productId(new ObjectId(productIdWithQuantity.getProductId()))
                    .quantity(productIdWithQuantity.getQuantity())
                    .build();
        }

        @Override
        public List<CartDoc.ProductIdWithQuantity> convert(CartDoc.ListOfCartProducts listOfCartProducts) {
            return listOfCartProducts.getProducts().stream().map(this::convert).collect(Collectors.toList());
        }
    }

    public static class DocsListToResponseListMapper extends BaseMapping<SearchResponse<CartDoc>, SearchResponse<CartResponse>> {

        private DocToResponseMapper docToResponseMapper = new DocToResponseMapper();

        @Override
        public SearchResponse<CartResponse> convert(SearchResponse<CartDoc> basketDocs) {
            return SearchResponse.of(basketDocs.getCount(), basketDocs.getItems().stream().map(docToResponseMapper::convert).collect(Collectors.toList()));
        }
    }

    private final DocToResponseMapper docToResponseMapper = new DocToResponseMapper();
    private final DocsListToResponseListMapper docsListToResponseListMapper = new DocsListToResponseListMapper();
    private final ListOfCartProductsToObjectListOfCartProducts listOfCartProductsToObjectListOfCartProducts = new ListOfCartProductsToObjectListOfCartProducts();
    private final ObjectListOfCartProductsToListOfCartProducts objectListOfCartProductsToListOfCartProducts = new ObjectListOfCartProductsToListOfCartProducts();

    public static CartMapping getInstance() {
        return new CartMapping();
    }
}

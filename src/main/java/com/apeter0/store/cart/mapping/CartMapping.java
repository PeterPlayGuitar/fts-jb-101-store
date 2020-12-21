package com.apeter0.store.cart.mapping;

import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.base.mapping.BaseMapping;
import com.apeter0.store.cart.api.request.CartRequest;
import com.apeter0.store.cart.api.response.CartResponse;
import com.apeter0.store.cart.model.CartDoc;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CartMapping {

    public static class DocToResponseMapper extends BaseMapping<CartDoc, CartResponse> {

        @Override
        public CartResponse convert(CartDoc cartDoc) {
            List<CartResponse.ProductIdWithQuantity> products = cartDoc.getProducts()
                    .stream()
                    .map(
                            (doc) -> CartResponse.ProductIdWithQuantity.builder()
                                    .productId(doc.getProductId().toString())
                                    .quantity(doc.getQuantity())
                                    .build()
                    )
                    .collect(Collectors.toList());

            return CartResponse.builder()
                    .id(cartDoc.getId().toString())
                    .products(products)
                    .guestId(cartDoc.getGuestId().toString())
                    .build();
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

    public static CartMapping getInstance() {
        return new CartMapping();
    }
}

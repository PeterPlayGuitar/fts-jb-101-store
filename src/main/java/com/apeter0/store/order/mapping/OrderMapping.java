package com.apeter0.store.order.mapping;

import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.base.mapping.BaseMapping;
import com.apeter0.store.order.api.response.OrderResponse;
import com.apeter0.store.order.model.OrderDoc;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter
public class OrderMapping {

    public static class DocToResponseMapper extends BaseMapping<OrderDoc, OrderResponse> {

        @Override
        public OrderResponse convert(OrderDoc orderDoc) {
            return OrderResponse.builder()
                    .id(orderDoc.getId().toString())
                    .phoneNumber(orderDoc.getPhoneNumber())
                    .streetId(orderDoc.getStreetId().toString())
                    .house(orderDoc.getHouse())
                    .apartment(orderDoc.getApartment())
                    .entrance(orderDoc.getEntrance())
                    .floor(orderDoc.getFloor())
                    .cartId(orderDoc.getCartId().toString())
                    .deliveryTimeId(orderDoc.getDeliveryTimeId().toString())
                    .paymentMethod(orderDoc.getPaymentMethod())
                    .totalPrice(orderDoc.getTotalPrice())
                    .build();
        }
    }

    public static class DocsListToResponseListMapper extends BaseMapping<SearchResponse<OrderDoc>, SearchResponse<OrderResponse>> {

        private DocToResponseMapper docToResponseMapper = new DocToResponseMapper();

        @Override
        public SearchResponse<OrderResponse> convert(SearchResponse<OrderDoc> orderDocs) {
            return SearchResponse.of(orderDocs.getCount(), orderDocs.getItems().stream().map(docToResponseMapper::convert).collect(Collectors.toList()));
        }
    }

    private final DocToResponseMapper docToResponseMapper = new DocToResponseMapper();
    private final DocsListToResponseListMapper docsListToResponseListMapper = new DocsListToResponseListMapper();

    public static OrderMapping getInstance() {
        return new OrderMapping();
    }
}

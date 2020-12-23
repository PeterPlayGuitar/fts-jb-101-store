package com.apeter0.store.deliveryTime.mapping;

import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.base.mapping.BaseMapping;
import com.apeter0.store.deliveryTime.api.response.DeliveryTimeResponse;
import com.apeter0.store.deliveryTime.model.DeliveryTimeDoc;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DeliveryTimeMapping {

    public static class DocToResponseMapper extends BaseMapping<DeliveryTimeDoc, DeliveryTimeResponse> {

        @Override
        public DeliveryTimeResponse convert(DeliveryTimeDoc deliveryTimeDoc) {
            return DeliveryTimeResponse.builder()
                    .id(deliveryTimeDoc.getId().toString())
                    .name(deliveryTimeDoc.getName())
                    .index(deliveryTimeDoc.getIndex())
                    .build();
        }
    }

    public static class DocsListToResponseListMapper extends BaseMapping<SearchResponse<DeliveryTimeDoc>, SearchResponse<DeliveryTimeResponse>> {

        private DocToResponseMapper docToResponseMapper = new DocToResponseMapper();

        @Override
        public SearchResponse<DeliveryTimeResponse> convert(SearchResponse<DeliveryTimeDoc> deliveryTimeDocs) {
            return SearchResponse.of(deliveryTimeDocs.getCount(), deliveryTimeDocs.getItems().stream().map(docToResponseMapper::convert).collect(Collectors.toList()));
        }
    }

    private final DocToResponseMapper docToResponseMapper = new DocToResponseMapper();
    private final DocsListToResponseListMapper docsListToResponseListMapper = new DocsListToResponseListMapper();

    public static DeliveryTimeMapping getInstance() {
        return new DeliveryTimeMapping();
    }
}

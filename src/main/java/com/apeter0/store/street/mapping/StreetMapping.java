package com.apeter0.store.street.mapping;

import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.base.mapping.BaseMapping;
import com.apeter0.store.street.api.response.StreetResponse;
import com.apeter0.store.street.model.StreetDoc;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class StreetMapping {

    public static class DocToResponseMapper extends BaseMapping<StreetDoc, StreetResponse> {

        @Override
        public StreetResponse convert(StreetDoc streetDoc) {
            return StreetResponse.builder()
                    .id(streetDoc.getId().toString())
                    .name(streetDoc.getName())
                    .cityId(streetDoc.getCityId().toString())
                    .build();
        }
    }

    public static class DocsListToResponseListMapper extends BaseMapping<SearchResponse<StreetDoc>, SearchResponse<StreetResponse>> {

        private DocToResponseMapper docToResponseMapper = new DocToResponseMapper();

        @Override
        public SearchResponse<StreetResponse> convert(SearchResponse<StreetDoc> streetDocs) {
            return SearchResponse.of(streetDocs.getCount(), streetDocs.getItems().stream().map(docToResponseMapper::convert).collect(Collectors.toList()));
        }
    }

    private final DocToResponseMapper docToResponseMapper = new DocToResponseMapper();
    private final DocsListToResponseListMapper docsListToResponseListMapper = new DocsListToResponseListMapper();

    public static StreetMapping getInstance() {
        return new StreetMapping();
    }
}

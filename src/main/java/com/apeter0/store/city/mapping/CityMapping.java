package com.apeter0.store.city.mapping;

import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.base.mapping.BaseMapping;
import com.apeter0.store.city.api.response.CityResponse;
import com.apeter0.store.city.model.CityDoc;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CityMapping {

    public static class DocToResponseMapper extends BaseMapping<CityDoc, CityResponse> {

        @Override
        public CityResponse convert(CityDoc cityDoc) {
            return CityResponse.builder()
                    .id(cityDoc.getId().toString())
                    .name(cityDoc.getName())
                    .build();
        }
    }

    public static class DocsListToResponseListMapper extends BaseMapping<SearchResponse<CityDoc>, SearchResponse<CityResponse>> {

        private DocToResponseMapper docToResponseMapper = new DocToResponseMapper();

        @Override
        public SearchResponse<CityResponse> convert(SearchResponse<CityDoc> cityDocs) {
            return SearchResponse.of(cityDocs.getCount(), cityDocs.getItems().stream().map(docToResponseMapper::convert).collect(Collectors.toList()));
        }
    }

    private final DocToResponseMapper docToResponseMapper = new DocToResponseMapper();
    private final DocsListToResponseListMapper docsListToResponseListMapper = new DocsListToResponseListMapper();

    public static CityMapping getInstance() {
        return new CityMapping();
    }
}

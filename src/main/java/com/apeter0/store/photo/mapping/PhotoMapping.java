package com.apeter0.store.photo.mapping;

import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.base.mapping.BaseMapping;
import com.apeter0.store.city.api.response.CityResponse;
import com.apeter0.store.city.mapping.CityMapping;
import com.apeter0.store.city.model.CityDoc;
import com.apeter0.store.photo.api.response.PhotoResponse;
import com.apeter0.store.photo.model.PhotoDoc;
import com.apeter0.store.street.api.response.StreetResponse;
import com.apeter0.store.street.model.StreetDoc;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter
public class PhotoMapping {

    public static class DocToResponseMapper extends BaseMapping<PhotoDoc, PhotoResponse> {

        @Override
        public PhotoResponse convert(PhotoDoc photoDoc) {
            return PhotoResponse.builder()
                    .id(photoDoc.getId().toString())
                    .contentType(photoDoc.getContentType())
                    .build();
        }
    }

    public static class DocsListToResponseListMapper extends BaseMapping<SearchResponse<PhotoDoc>, SearchResponse<PhotoResponse>> {

        private DocToResponseMapper docToResponseMapper = new DocToResponseMapper();

        @Override
        public SearchResponse<PhotoResponse> convert(SearchResponse<PhotoDoc> photoDocs) {
            return SearchResponse.of(photoDocs.getCount(), photoDocs.getItems().stream().map(docToResponseMapper::convert).collect(Collectors.toList()));
        }
    }

    private final DocToResponseMapper docToResponseMapper = new DocToResponseMapper();
    private final DocsListToResponseListMapper docsListToResponseListMapper = new DocsListToResponseListMapper();

    public static PhotoMapping getInstance() {
        return new PhotoMapping();
    }
}

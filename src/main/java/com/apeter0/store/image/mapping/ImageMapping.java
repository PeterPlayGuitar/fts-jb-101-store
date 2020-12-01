package com.apeter0.store.image.mapping;

import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.base.mapping.BaseMapping;
import com.apeter0.store.image.api.response.ImageResponse;
import com.apeter0.store.image.model.ImageDoc;
import lombok.Getter;

import java.util.stream.Collectors;

@Getter
public class ImageMapping {

    public static class DocToResponseMapper extends BaseMapping<ImageDoc, ImageResponse> {

        @Override
        public ImageResponse convert(ImageDoc imageDoc) {
            return ImageResponse.builder()
                    .id(imageDoc.getId().toString())
                    .contentType(imageDoc.getContentType())
                    .build();
        }
    }

    public static class DocsListToResponseListMapper extends BaseMapping<SearchResponse<ImageDoc>, SearchResponse<ImageResponse>> {

        private DocToResponseMapper docToResponseMapper = new DocToResponseMapper();

        @Override
        public SearchResponse<ImageResponse> convert(SearchResponse<ImageDoc> imageDocs) {
            return SearchResponse.of(imageDocs.getCount(), imageDocs.getItems().stream().map(docToResponseMapper::convert).collect(Collectors.toList()));
        }
    }

    private final DocToResponseMapper docToResponseMapper = new DocToResponseMapper();
    private final DocsListToResponseListMapper docsListToResponseListMapper = new DocsListToResponseListMapper();

    public static ImageMapping getInstance() {
        return new ImageMapping();
    }
}

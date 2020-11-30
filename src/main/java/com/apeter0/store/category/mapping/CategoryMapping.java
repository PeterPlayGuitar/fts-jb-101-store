package com.apeter0.store.category.mapping;

import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.base.mapping.BaseMapping;
import com.apeter0.store.category.api.response.CategoryResponse;
import com.apeter0.store.category.model.CategoryDoc;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CategoryMapping {

    public static class DocToResponseMapper extends BaseMapping<CategoryDoc, CategoryResponse> {

        @Override
        public CategoryResponse convert(CategoryDoc categoryDoc) {
            return CategoryResponse.builder()
                    .id(categoryDoc.getId().toString())
                    .name(categoryDoc.getName())
                    .build();
        }
    }

    public static class DocsListToResponseListMapper extends BaseMapping<SearchResponse<CategoryDoc>, SearchResponse<CategoryResponse>> {

        private DocToResponseMapper docToResponseMapper = new DocToResponseMapper();

        @Override
        public SearchResponse<CategoryResponse> convert(SearchResponse<CategoryDoc> categoryDocs) {
            return SearchResponse.of(categoryDocs.getCount(), categoryDocs.getItems().stream().map(docToResponseMapper::convert).collect(Collectors.toList()));
        }
    }

    private final DocToResponseMapper docToResponseMapper = new DocToResponseMapper();
    private final DocsListToResponseListMapper docsListToResponseListMapper = new DocsListToResponseListMapper();

    public static CategoryMapping getInstance() {
        return new CategoryMapping();
    }
}

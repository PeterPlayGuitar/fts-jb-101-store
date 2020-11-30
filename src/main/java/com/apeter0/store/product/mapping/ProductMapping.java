package com.apeter0.store.product.mapping;

import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.base.mapping.BaseMapping;
import com.apeter0.store.product.api.response.ProductResponse;
import com.apeter0.store.product.model.ProductDoc;
import lombok.Getter;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProductMapping {

    public static class DocToResponseMapper extends BaseMapping<ProductDoc, ProductResponse> {

        private static ProductResponse.CityPrice convert(ProductDoc.CityPrice cityPrice) {
            return new ProductResponse.CityPrice(cityPrice.getCityId().toString(), cityPrice.getPrice());
        }


        private static ProductResponse.CityImage convert(ProductDoc.CityImage cityImage) {
            return new ProductResponse.CityImage(cityImage.getCityId().toString(), cityImage.getImageId().toString());
        }


        @Override
        public ProductResponse convert(ProductDoc productDoc) {
            return ProductResponse.builder()
                    .id(productDoc.getId().toString())
                    .name(productDoc.getName())
                    .categoryId(productDoc.getCategoryId().toString())
                    .description(productDoc.getDescription())
                    .defaultPrice(productDoc.getDefaultPrice())
                    .prices(productDoc.getPrices().stream().map(DocToResponseMapper::convert).collect(Collectors.toList()))
                    .proteins(productDoc.getProteins())
                    .fats(productDoc.getFats())
                    .carbohydrates(productDoc.getCarbohydrates())
                    .calories(productDoc.getCalories())
                    .defaultImageId(productDoc.getDefaultImageId().toString())
                    .images(productDoc.getImages().stream().map(DocToResponseMapper::convert).collect(Collectors.toList()))
                    .build();
        }
    }

    public static class DocsListToResponseListMapper extends BaseMapping<SearchResponse<ProductDoc>, SearchResponse<ProductResponse>> {

        private DocToResponseMapper docToResponseMapper = new DocToResponseMapper();

        @Override
        public SearchResponse<ProductResponse> convert(SearchResponse<ProductDoc> productDocs) {
            return SearchResponse.of(productDocs.getCount(), productDocs.getItems().stream().map(docToResponseMapper::convert).collect(Collectors.toList()));
        }
    }

    private final DocToResponseMapper docToResponseMapper = new DocToResponseMapper();
    private final DocsListToResponseListMapper docsListToResponseListMapper = new DocsListToResponseListMapper();

    public static ProductMapping getInstance() {
        return new ProductMapping();
    }
}

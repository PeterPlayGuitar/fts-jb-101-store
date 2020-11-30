package com.apeter0.store.product.api.response;

import com.apeter0.store.product.model.ProductDoc;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.util.List;

@Builder
@Getter
@ApiModel(value = "Product response")
public class ProductResponse {

    @Getter
    @ApiModel(value = "Price for each city")
    @AllArgsConstructor
    public static class CityPrice {
        private String cityId;
        private Integer price;
    }

    @Getter
    @ApiModel(value = "Image for each city")
    @AllArgsConstructor
    public static class CityImage {
        private String cityId;
        private String imageId;
    }

    private String id;
    private String name;
    private String categoryId;
    private String description;
    private Integer defaultPrice;
    private List<CityPrice> prices;
    private Float proteins;
    private Float fats;
    private Float carbohydrates;
    private Float calories;
    private String defaultImageId;
    private List<CityImage> images;
}

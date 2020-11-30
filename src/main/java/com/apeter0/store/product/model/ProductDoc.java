package com.apeter0.store.product.model;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Getter
@Setter
@Builder
public class ProductDoc {

    @Getter
    @ApiModel(value = "Price for each city")
    public static class CityPrice {
        private ObjectId cityId;
        private Integer price;
    }

    @Getter
    @ApiModel(value = "Image for each city")
    public static class CityImage {
        private ObjectId cityId;
        private ObjectId imageId;
    }

    @Id
    private ObjectId id;
    private String name;
    private ObjectId categoryId;
    private String description;
    private Integer defaultPrice;
    private List<CityPrice> prices;
    private Float proteins;
    private Float fats;
    private Float carbohydrates;
    private Float calories;
    private ObjectId defaultImageId;
    private List<CityImage> images;
}

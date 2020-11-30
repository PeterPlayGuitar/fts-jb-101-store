package com.apeter0.store.product.api.request;

import com.apeter0.store.product.model.ProductDoc;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@ApiModel(value = "Product request")
public class ProductRequest {

    private ObjectId id;
    private String name;
    private ObjectId categoryId;
    private String description;
    private Integer defaultPrice;
    private List<ProductDoc.CityPrice> prices;
    private Float proteins;
    private Float fats;
    private Float carbohydrates;
    private Float calories;
    private ObjectId defaultImageId;
    private List<ProductDoc.CityImage> images;
}

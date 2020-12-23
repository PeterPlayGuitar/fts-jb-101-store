package com.apeter0.store.product.api.request;

import com.apeter0.store.product.model.ProductDoc;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@ApiModel(value = "Product request")
public class ProductRequest {

    private ObjectId id;

    @ApiParam(name = "Name of the product", required = true)
    private String name;

    @ApiParam(name = "Category, this product belongs to", required = true)
    private ObjectId categoryId;

    @ApiParam(name = "Description of the product", required = true)
    private String description;

    @ApiParam(name = "Default price for all cities", required = true)
    private Integer defaultPrice;

    @ApiParam(name = "List of prices for individual cities", required = false)
    private List<ProductDoc.CityPrice> prices;

    @ApiParam(name = "Proteins of the product", required = true)
    private Float proteins;

    @ApiParam(name = "Fats of the product", required = true)
    private Float fats;

    @ApiParam(name = "Carbohydrates of the product", required = true)
    private Float carbohydrates;

    @ApiParam(name = "Calories of the product", required = true)
    private Float calories;

    @ApiParam(name = "Default image for all cities", required = true)
    private ObjectId defaultImageId;

    @ApiParam(name = "Default price for individual cities", required = false)
    private List<ProductDoc.CityImage> images;
}

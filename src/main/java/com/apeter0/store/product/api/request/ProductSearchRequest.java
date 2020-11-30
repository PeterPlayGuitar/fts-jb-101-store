package com.apeter0.store.product.api.request;

import com.apeter0.store.base.api.request.SearchRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@ApiModel(value = "Product search request", description = "Represent parameters for product search")
public class ProductSearchRequest extends SearchRequest {

    @ApiParam(name = "city id", value = "Search by city, if null then search among all products", required = false)
    private ObjectId cityId = null;

    @ApiParam(name = "category id", value = "Search by category, if null then search among all products", required = false)
    private ObjectId categoryId = null;
}

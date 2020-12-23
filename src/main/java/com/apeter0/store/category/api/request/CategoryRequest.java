package com.apeter0.store.category.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
@ApiModel(value = "Category request")
public class CategoryRequest {
    private ObjectId id;

    @ApiParam(name = "Category name", value = "Example: суши, супы", required = true)
    private String name;
}

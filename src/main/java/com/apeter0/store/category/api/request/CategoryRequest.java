package com.apeter0.store.category.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
@ApiModel(value = "Category request")
public class CategoryRequest {
    private ObjectId id;
    private String name;
}

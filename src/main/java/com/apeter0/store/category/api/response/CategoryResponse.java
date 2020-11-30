package com.apeter0.store.category.api.response;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@ApiModel(value = "Category response")
public class CategoryResponse {
    private String id;
    private String name;
}

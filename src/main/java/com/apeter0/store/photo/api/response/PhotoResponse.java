package com.apeter0.store.photo.api.response;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "Photo response")
@Getter
@Builder
public class PhotoResponse {
    private String id;
    private String contentType;
}

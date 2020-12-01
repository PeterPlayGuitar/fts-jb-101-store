package com.apeter0.store.image.api.response;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "Image response")
@Getter
@Builder
public class ImageResponse {
    private String id;
    private String contentType;
}

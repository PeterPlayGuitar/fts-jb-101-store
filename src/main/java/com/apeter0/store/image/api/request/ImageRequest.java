package com.apeter0.store.image.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
@ApiModel(value = "Image request", description = "Model for creating new image")
public class ImageRequest {
    private ObjectId id;

    @ApiParam(name = "Content type of the image", required = true)
    private String contentType;
}

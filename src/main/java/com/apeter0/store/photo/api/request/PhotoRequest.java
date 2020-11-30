package com.apeter0.store.photo.api.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Getter
@ApiModel(value = "Photo request", description = "Model for creating new photo")
public class PhotoRequest {
    private ObjectId id;
    private String contentType;
}

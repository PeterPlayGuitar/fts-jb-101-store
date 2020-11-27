package com.apeter0.store.city.api.response;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@ApiModel(value = "City response")
public class CityResponse {
    private String id;
    private String name;
}

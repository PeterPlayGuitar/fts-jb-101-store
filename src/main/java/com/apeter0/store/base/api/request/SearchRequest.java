package com.apeter0.store.base.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value = "Search request", description = "Represent parameters for search")
public class SearchRequest {

    @ApiParam(name = "query", value = "Search by fields of entity", required = false)
    protected String query = null;

    @ApiParam(name = "size of result list", value = "Number of elements in result list (default 100)", required = false)
    protected Integer size = 100;

    @ApiParam(name = "skip", value = "Skip first n elements", required = false)
    protected Long skip = 0l;
}

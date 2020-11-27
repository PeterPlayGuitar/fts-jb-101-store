package com.apeter0.store.city.controller;

import com.apeter0.store.base.api.request.SearchRequest;
import com.apeter0.store.base.api.response.OkResponse;
import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.city.api.request.CityRequest;
import com.apeter0.store.city.api.response.CityResponse;
import com.apeter0.store.city.exception.CityExistsException;
import com.apeter0.store.city.exception.CityNotExistsException;
import com.apeter0.store.city.mapping.CityMapping;
import com.apeter0.store.city.model.CityDoc;
import com.apeter0.store.city.routes.CityApiRoutes;
import com.apeter0.store.city.service.CityApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "City API only for admin")
class CityApiController {

    private final CityApiService cityApiService;

    @ApiOperation(value = "Cities search")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @GetMapping(CityApiRoutes.ROOT)
    OkResponse<SearchResponse<CityResponse>> search(
            @ApiParam(value = "City search parameters") @ModelAttribute SearchRequest request
    ) {
        SearchResponse<CityDoc> cityDocSearchResponse = cityApiService.search(request);
        SearchResponse<CityResponse> cityResponseSearchResponse = CityMapping.getInstance().getDocsListToResponseListMapper().convert(cityDocSearchResponse);
        return OkResponse.of(cityResponseSearchResponse);
    }

    @ApiOperation(value = "Find city by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "City not found")
    })
    @GetMapping(CityApiRoutes.BY_ID)
    OkResponse<CityResponse> getById(
            @ApiParam(value = "City id") @PathVariable ObjectId id
    ) throws ChangeSetPersister.NotFoundException {
        CityDoc cityDoc = cityApiService.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        CityResponse cityResponse = CityMapping.getInstance().getDocToResponseMapper().convert(cityDoc);
        return OkResponse.of(cityResponse);
    }

    @ApiOperation(value = "Create city")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "City already exists")
    })
    @PostMapping(CityApiRoutes.ROOT)
    OkResponse<CityResponse> create(
            @ApiParam(value = "Parameters for new city") @RequestBody CityRequest request
    ) throws CityExistsException {
        CityDoc cityDoc = cityApiService.create(request);
        CityResponse cityResponse = CityMapping.getInstance().getDocToResponseMapper().convert(cityDoc);
        return OkResponse.of(cityResponse);
    }

    @ApiOperation(value = "Update city information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "City with this parameters already exists, or there is no city with such id")
    })
    @PutMapping(CityApiRoutes.ROOT)
    OkResponse<CityResponse> update(
            @ApiParam(value = "Parameters for updating city") @RequestBody CityRequest request
    ) throws CityNotExistsException, CityExistsException {
        CityDoc cityDoc = cityApiService.update(request);
        CityResponse cityResponse = CityMapping.getInstance().getDocToResponseMapper().convert(cityDoc);
        return OkResponse.of(cityResponse);
    }

    @ApiOperation(value = "Delete city")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @DeleteMapping(CityApiRoutes.BY_ID)
    OkResponse<String> delete(
            @ApiParam(value = "City id") @PathVariable ObjectId id
    ) {
        cityApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
    }

}

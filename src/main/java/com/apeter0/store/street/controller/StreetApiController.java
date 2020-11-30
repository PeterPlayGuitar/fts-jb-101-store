package com.apeter0.store.street.controller;

import com.apeter0.store.base.api.request.SearchRequest;
import com.apeter0.store.base.api.response.OkResponse;
import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.city.exception.CityNotExistsException;
import com.apeter0.store.street.api.request.StreetRequest;
import com.apeter0.store.street.api.request.StreetSearchRequest;
import com.apeter0.store.street.api.response.StreetResponse;
import com.apeter0.store.street.exception.StreetExistsException;
import com.apeter0.store.street.exception.StreetNotExistsException;
import com.apeter0.store.street.mapping.StreetMapping;
import com.apeter0.store.street.model.StreetDoc;
import com.apeter0.store.street.routes.StreetApiRoutes;
import com.apeter0.store.street.service.StreetApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Street API")
class StreetApiController {

    private final StreetApiService streetApiService;

    @ApiOperation(value = "Street search")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Such city does not exist")
    })
    @GetMapping(StreetApiRoutes.ROOT)
    OkResponse<SearchResponse<StreetResponse>> search(
            @ApiParam(value = "Street search parameters") @ModelAttribute StreetSearchRequest request
    ) throws CityNotExistsException {
        SearchResponse<StreetDoc> streetDocSearchResponse = streetApiService.search(request);
        SearchResponse<StreetResponse> streetResponseSearchResponse = StreetMapping.getInstance().getDocsListToResponseListMapper().convert(streetDocSearchResponse);
        return OkResponse.of(streetResponseSearchResponse);
    }

    @ApiOperation(value = "Find street by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Street not found")
    })
    @GetMapping(StreetApiRoutes.BY_ID)
    OkResponse<StreetResponse> getById(
            @ApiParam(value = "Street id") @PathVariable ObjectId id
    ) throws ChangeSetPersister.NotFoundException {
        StreetDoc streetDoc = streetApiService.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        StreetResponse streetResponse = StreetMapping.getInstance().getDocToResponseMapper().convert(streetDoc);
        return OkResponse.of(streetResponse);
    }

    @ApiOperation(value = "Create street")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Such street already exists in this city, or this city does not exist")
    })
    @PostMapping(StreetApiRoutes.ADMIN_ROOT)
    OkResponse<StreetResponse> create(
            @ApiParam(value = "Parameters for new street") @RequestBody StreetRequest request
    ) throws CityNotExistsException, StreetExistsException {
        StreetDoc streetDoc = streetApiService.create(request);
        StreetResponse streetResponse = StreetMapping.getInstance().getDocToResponseMapper().convert(streetDoc);
        return OkResponse.of(streetResponse);
    }

    @ApiOperation(value = "Update street information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Street with such name already exists in this city, or can't fine a street with this id")
    })
    @PutMapping(StreetApiRoutes.ADMIN_ROOT)
    OkResponse<StreetResponse> update(
            @ApiParam(value = "Parameters for updating street") @RequestBody StreetRequest request
    ) throws StreetNotExistsException, StreetExistsException {
        StreetDoc streetDoc = streetApiService.update(request);
        StreetResponse streetResponse = StreetMapping.getInstance().getDocToResponseMapper().convert(streetDoc);
        return OkResponse.of(streetResponse);
    }

    @ApiOperation(value = "Delete street")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @DeleteMapping(StreetApiRoutes.ADMIN_BY_ID)
    OkResponse<String> delete(
            @ApiParam(value = "Street id") @PathVariable ObjectId id
    ) {
        streetApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
    }

}

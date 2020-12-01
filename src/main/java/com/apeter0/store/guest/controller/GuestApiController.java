package com.apeter0.store.guest.controller;

import com.apeter0.store.base.api.response.OkResponse;
import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.city.exception.CityNotExistsException;
import com.apeter0.store.guest.api.request.GuestRequest;
import com.apeter0.store.guest.api.request.GuestSearchRequest;
import com.apeter0.store.guest.api.response.GuestResponse;
import com.apeter0.store.guest.exception.GuestNotExistsException;
import com.apeter0.store.guest.mapping.GuestMapping;
import com.apeter0.store.guest.model.GuestDoc;
import com.apeter0.store.guest.routes.GuestApiRoutes;
import com.apeter0.store.guest.service.GuestApiService;
import com.apeter0.store.street.exception.StreetNotExistsException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Guest API")
class GuestApiController {

    private final GuestApiService guestApiService;

    @ApiOperation(value = "Guest search")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @GetMapping(GuestApiRoutes.ADMIN_ROOT)
    OkResponse<SearchResponse<GuestResponse>> search(
            @ApiParam(value = "Guest search parameters") @ModelAttribute GuestSearchRequest request
    ) {
        SearchResponse<GuestDoc> guestDocSearchResponse = guestApiService.search(request);
        SearchResponse<GuestResponse> guestResponseSearchResponse = GuestMapping.getInstance().getDocsListToResponseListMapper().convert(guestDocSearchResponse);
        return OkResponse.of(guestResponseSearchResponse);
    }

    @ApiOperation(value = "Find guest by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Guest not found")
    })
    @GetMapping(GuestApiRoutes.ADMIN_BY_ID)
    OkResponse<GuestResponse> getById(
            @ApiParam(value = "Guest id") @PathVariable ObjectId id
    ) throws ChangeSetPersister.NotFoundException {
        GuestDoc guestDoc = guestApiService.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        GuestResponse guestResponse = GuestMapping.getInstance().getDocToResponseMapper().convert(guestDoc);
        return OkResponse.of(guestResponse);
    }

    @ApiOperation(value = "Create guest")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "This street does not exist, or this city does not exist, or this city does not have street with this id")
    })
    @PostMapping(GuestApiRoutes.ROOT)
    OkResponse<GuestResponse> create(
            @ApiParam(value = "Parameters for new guest") @RequestBody GuestRequest request
    ) throws StreetNotExistsException, CityNotExistsException {
        GuestDoc guestDoc = guestApiService.create(request);
        GuestResponse guestResponse = GuestMapping.getInstance().getDocToResponseMapper().convert(guestDoc);
        return OkResponse.of(guestResponse);
    }

    @ApiOperation(value = "Update guest information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "There is no guest with such id, or there is no street with this name, or this city does not exist, or this city does not have street with this id")
    })
    @PutMapping(GuestApiRoutes.ADMIN_ROOT)
    OkResponse<GuestResponse> update(
            @ApiParam(value = "Parameters for updating guest") @RequestBody GuestRequest request
    ) throws GuestNotExistsException, StreetNotExistsException, CityNotExistsException {
        GuestDoc guestDoc = guestApiService.update(request);
        GuestResponse guestResponse = GuestMapping.getInstance().getDocToResponseMapper().convert(guestDoc);
        return OkResponse.of(guestResponse);
    }

    @ApiOperation(value = "Delete guest")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @DeleteMapping(GuestApiRoutes.ADMIN_BY_ID)
    OkResponse<String> delete(
            @ApiParam(value = "Guest id") @PathVariable ObjectId id
    ) {
        guestApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
    }

}

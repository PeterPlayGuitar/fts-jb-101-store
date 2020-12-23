package com.apeter0.store.deliveryTime.controller;

import com.apeter0.store.base.api.request.SearchRequest;
import com.apeter0.store.base.api.response.OkResponse;
import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.deliveryTime.api.request.DeliveryTimeRequest;
import com.apeter0.store.deliveryTime.api.response.DeliveryTimeResponse;
import com.apeter0.store.deliveryTime.exception.DeliveryTimeExistsException;
import com.apeter0.store.deliveryTime.exception.DeliveryTimeNameNotSpecifiedException;
import com.apeter0.store.deliveryTime.exception.DeliveryTimeNotExistsException;
import com.apeter0.store.deliveryTime.mapping.DeliveryTimeMapping;
import com.apeter0.store.deliveryTime.model.DeliveryTimeDoc;
import com.apeter0.store.deliveryTime.routes.DeliveryTimeApiRoutes;
import com.apeter0.store.deliveryTime.service.DeliveryTimeApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "DeliveryTime API")
class DeliveryTimeApiController {

    private final DeliveryTimeApiService deliveryTimeApiService;

    @ApiOperation(value = "DeliveryTime search")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @GetMapping(DeliveryTimeApiRoutes.ROOT)
    OkResponse<SearchResponse<DeliveryTimeResponse>> search(
            @ApiParam(value = "DeliveryTime search parameters") @ModelAttribute SearchRequest request
    ) {
        SearchResponse<DeliveryTimeDoc> deliveryTimeDocSearchResponse = deliveryTimeApiService.search(request);
        SearchResponse<DeliveryTimeResponse> deliveryTimeResponseSearchResponse = DeliveryTimeMapping.getInstance().getDocsListToResponseListMapper().convert(deliveryTimeDocSearchResponse);
        return OkResponse.of(deliveryTimeResponseSearchResponse);
    }

    @ApiOperation(value = "Find deliveryTime by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "DeliveryTime not found")
    })
    @GetMapping(DeliveryTimeApiRoutes.ADMIN_BY_ID)
    OkResponse<DeliveryTimeResponse> getById(
            @ApiParam(value = "DeliveryTime id") @PathVariable ObjectId id
    ) throws ChangeSetPersister.NotFoundException {
        DeliveryTimeDoc deliveryTimeDoc = deliveryTimeApiService.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        DeliveryTimeResponse deliveryTimeResponse = DeliveryTimeMapping.getInstance().getDocToResponseMapper().convert(deliveryTimeDoc);
        return OkResponse.of(deliveryTimeResponse);
    }

    @ApiOperation(value = "Create deliveryTime")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "DeliveryTime name was not specified")
    })
    @PostMapping(DeliveryTimeApiRoutes.ADMIN_ROOT)
    OkResponse<DeliveryTimeResponse> create(
            @ApiParam(value = "Parameters for new deliveryTime") @RequestBody DeliveryTimeRequest request
    ) throws DeliveryTimeNameNotSpecifiedException {
        DeliveryTimeDoc deliveryTimeDoc = deliveryTimeApiService.create(request);
        DeliveryTimeResponse deliveryTimeResponse = DeliveryTimeMapping.getInstance().getDocToResponseMapper().convert(deliveryTimeDoc);
        return OkResponse.of(deliveryTimeResponse);
    }

    @ApiOperation(value = "Update deliveryTime information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "this DeliveryTime entity does not exist, or delivery time name was not specified")
    })
    @PutMapping(DeliveryTimeApiRoutes.ADMIN_ROOT)
    OkResponse<DeliveryTimeResponse> update(
            @ApiParam(value = "Parameters for updating deliveryTime") @RequestBody DeliveryTimeRequest request
    ) throws DeliveryTimeNotExistsException, DeliveryTimeNameNotSpecifiedException {
        DeliveryTimeDoc deliveryTimeDoc = deliveryTimeApiService.update(request);
        DeliveryTimeResponse deliveryTimeResponse = DeliveryTimeMapping.getInstance().getDocToResponseMapper().convert(deliveryTimeDoc);
        return OkResponse.of(deliveryTimeResponse);
    }

    @ApiOperation(value = "Delete deliveryTime")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @DeleteMapping(DeliveryTimeApiRoutes.ADMIN_BY_ID)
    OkResponse<String> delete(
            @ApiParam(value = "DeliveryTime id") @PathVariable ObjectId id
    ) {
        deliveryTimeApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
    }

}

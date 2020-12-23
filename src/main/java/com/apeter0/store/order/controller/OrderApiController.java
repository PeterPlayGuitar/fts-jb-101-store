package com.apeter0.store.order.controller;

import com.apeter0.store.base.api.request.SearchRequest;
import com.apeter0.store.base.api.response.OkResponse;
import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.cart.exception.CartNotExistsException;
import com.apeter0.store.city.exception.CityNotExistsException;
import com.apeter0.store.deliveryTime.exception.DeliveryTimeNotExistsException;
import com.apeter0.store.order.api.request.OrderRequest;
import com.apeter0.store.order.api.request.OrderSearchRequest;
import com.apeter0.store.order.api.response.OrderResponse;
import com.apeter0.store.order.exception.OrderExistsException;
import com.apeter0.store.order.exception.OrderNotExistsException;
import com.apeter0.store.order.mapping.OrderMapping;
import com.apeter0.store.order.model.OrderDoc;
import com.apeter0.store.order.routes.OrderApiRoutes;
import com.apeter0.store.order.service.OrderApiService;
import com.apeter0.store.product.exception.ProductNotExistsException;
import com.apeter0.store.street.exception.StreetNotExistsException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Order API")
class OrderApiController {

    private final OrderApiService orderApiService;

    @ApiOperation(value = "Order search")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @GetMapping(OrderApiRoutes.ADMIN_ROOT)
    OkResponse<SearchResponse<OrderResponse>> search(
            @ApiParam(value = "Order search parameters") @ModelAttribute OrderSearchRequest request
    ) {
        SearchResponse<OrderDoc> orderDocSearchResponse = orderApiService.search(request);
        SearchResponse<OrderResponse> orderResponseSearchResponse = OrderMapping.getInstance().getDocsListToResponseListMapper().convert(orderDocSearchResponse);
        return OkResponse.of(orderResponseSearchResponse);
    }

    @ApiOperation(value = "Find order by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Order not found")
    })
    @GetMapping(OrderApiRoutes.ADMIN_BY_ID)
    OkResponse<OrderResponse> getById(
            @ApiParam(value = "Order id") @PathVariable ObjectId id
    ) throws ChangeSetPersister.NotFoundException {
        OrderDoc orderDoc = orderApiService.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        OrderResponse orderResponse = OrderMapping.getInstance().getDocToResponseMapper().convert(orderDoc);
        return OkResponse.of(orderResponse);
    }

    @ApiOperation(value = "Create order")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Cart, city, product, delivery time or street does not exist")
    })
    @PostMapping(OrderApiRoutes.ROOT)
    OkResponse<OrderResponse> create(
            @ApiParam(value = "Parameters for new order") @RequestBody OrderRequest request
    ) throws CartNotExistsException, CityNotExistsException, ProductNotExistsException, StreetNotExistsException, DeliveryTimeNotExistsException {
        OrderDoc orderDoc = orderApiService.create(request);
        OrderResponse orderResponse = OrderMapping.getInstance().getDocToResponseMapper().convert(orderDoc);
        return OkResponse.of(orderResponse);
    }

    @ApiOperation(value = "Update order information, cart information can't be updated")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Order, street, delivery time or city does not exist")
    })
    @PutMapping(OrderApiRoutes.ADMIN_ROOT)
    OkResponse<OrderResponse> update(
            @ApiParam(value = "Parameters for updating order") @RequestBody OrderRequest request
    ) throws OrderNotExistsException, StreetNotExistsException, CityNotExistsException, DeliveryTimeNotExistsException {
        OrderDoc orderDoc = orderApiService.update(request);
        OrderResponse orderResponse = OrderMapping.getInstance().getDocToResponseMapper().convert(orderDoc);
        return OkResponse.of(orderResponse);
    }

    @ApiOperation(value = "Delete order")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @DeleteMapping(OrderApiRoutes.ADMIN_BY_ID)
    OkResponse<String> delete(
            @ApiParam(value = "Order id") @PathVariable ObjectId id
    ) {
        orderApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
    }

}

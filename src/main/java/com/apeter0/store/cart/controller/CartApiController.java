package com.apeter0.store.cart.controller;

import com.apeter0.store.base.api.response.OkResponse;
import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.cart.api.request.CartRequest;
import com.apeter0.store.cart.api.response.CartResponse;
import com.apeter0.store.cart.exception.CartIsEmptyException;
import com.apeter0.store.cart.exception.CartNotExistsException;
import com.apeter0.store.cart.mapping.CartMapping;
import com.apeter0.store.cart.model.CartDoc;
import com.apeter0.store.cart.routes.CartApiRoutes;
import com.apeter0.store.cart.service.CartApiService;
import com.apeter0.store.cart.api.request.CartSearchRequest;
import com.apeter0.store.guest.exception.GuestNotExistsException;
import com.apeter0.store.product.exception.ProductNotExistsException;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Basket API")
class CartApiController {

    private final CartApiService cartApiService;

    @ApiOperation(value = "Cart search")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @GetMapping(CartApiRoutes.ADMIN_ROOT)
    OkResponse<SearchResponse<CartResponse>> search(
            @ApiParam(value = "Cart search parameters") @ModelAttribute CartSearchRequest request
    ) {
        SearchResponse<CartDoc> basketDocSearchResponse = cartApiService.search(request);
        SearchResponse<CartResponse> basketResponseSearchResponse = CartMapping.getInstance().getDocsListToResponseListMapper().convert(basketDocSearchResponse);
        return OkResponse.of(basketResponseSearchResponse);
    }

    @ApiOperation(value = "Find cart by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Cart not found")
    })
    @GetMapping(CartApiRoutes.ADMIN_BY_ID)
    OkResponse<CartResponse> getById(
            @ApiParam(value = "Cart id") @PathVariable ObjectId id
    ) throws ChangeSetPersister.NotFoundException {
        CartDoc cartDoc = cartApiService.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        CartResponse cartResponse = CartMapping.getInstance().getDocToResponseMapper().convert(cartDoc);
        return OkResponse.of(cartResponse);
    }

    @ApiOperation(value = "Create cart")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Cart was empty, or this product id or this guest id does not exist")
    })
    @PostMapping(CartApiRoutes.ROOT)
    OkResponse<CartResponse> create(
            @ApiParam(value = "Parameters for new cart") @RequestBody CartRequest request
    ) throws CartIsEmptyException, ProductNotExistsException, GuestNotExistsException {
        CartDoc cartDoc = cartApiService.create(request);
        CartResponse cartResponse = CartMapping.getInstance().getDocToResponseMapper().convert(cartDoc);
        return OkResponse.of(cartResponse);
    }

    @ApiOperation(value = "Update cart information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Cart, product or guest with such id does not exist, or cart was empty")
    })
    @PutMapping(CartApiRoutes.ADMIN_ROOT)
    OkResponse<CartResponse> update(
            @ApiParam(value = "Parameters for updating cart") @RequestBody CartRequest request
    ) throws CartNotExistsException, ProductNotExistsException, GuestNotExistsException, CartIsEmptyException {
        CartDoc cartDoc = cartApiService.update(request);
        CartResponse cartResponse = CartMapping.getInstance().getDocToResponseMapper().convert(cartDoc);
        return OkResponse.of(cartResponse);
    }

    @ApiOperation(value = "Delete cart")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @DeleteMapping(CartApiRoutes.ADMIN_BY_ID)
    OkResponse<String> delete(
            @ApiParam(value = "Cart id") @PathVariable ObjectId id
    ) {
        cartApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
    }
}

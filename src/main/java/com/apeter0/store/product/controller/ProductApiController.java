package com.apeter0.store.product.controller;

import com.apeter0.store.base.api.response.OkResponse;
import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.category.exception.CategoryNotExistsException;
import com.apeter0.store.city.exception.CityNotExistsException;
import com.apeter0.store.image.exception.ImageNotExistException;
import com.apeter0.store.product.api.request.ProductRequest;
import com.apeter0.store.product.api.request.ProductSearchRequest;
import com.apeter0.store.product.api.response.ProductResponse;
import com.apeter0.store.product.exception.ProductNotExistsException;
import com.apeter0.store.product.mapping.ProductMapping;
import com.apeter0.store.product.model.ProductDoc;
import com.apeter0.store.product.routes.ProductApiRoutes;
import com.apeter0.store.product.service.ProductApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Product API")
class ProductApiController {

    private final ProductApiService productApiService;

    @ApiOperation(value = "Product search")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @GetMapping(ProductApiRoutes.ROOT)
    OkResponse<SearchResponse<ProductResponse>> search(
            @ApiParam(value = "Product search parameters") @ModelAttribute ProductSearchRequest request
    ) {
        SearchResponse<ProductDoc> productDocSearchResponse = productApiService.search(request);
        SearchResponse<ProductResponse> productResponseSearchResponse = ProductMapping.getInstance().getDocsListToResponseListMapper().convert(productDocSearchResponse);
        return OkResponse.of(productResponseSearchResponse);
    }

    @ApiOperation(value = "Find product by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Product not found")
    })
    @GetMapping(ProductApiRoutes.BY_ID)
    OkResponse<ProductResponse> getById(
            @ApiParam(value = "Product id") @PathVariable ObjectId id
    ) throws ChangeSetPersister.NotFoundException {
        ProductDoc productDoc = productApiService.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        ProductResponse productResponse = ProductMapping.getInstance().getDocToResponseMapper().convert(productDoc);
        return OkResponse.of(productResponse);
    }

    @ApiOperation(value = "Create product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Category does not exist, or one of the cities do not exist, or this image with this id does not exist")
    })
    @PostMapping(ProductApiRoutes.ADMIN_ROOT)
    OkResponse<ProductResponse> create(
            @ApiParam(value = "Parameters for new product") @RequestBody ProductRequest request
    ) throws CategoryNotExistsException, CityNotExistsException, ImageNotExistException {
        ProductDoc productDoc = productApiService.create(request);
        ProductResponse productResponse = ProductMapping.getInstance().getDocToResponseMapper().convert(productDoc);
        return OkResponse.of(productResponse);
    }

    @ApiOperation(value = "Update product information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "There is no product with such id, or there is no such city, or there is no such category, or image with this id does not exist")
    })
    @PutMapping(ProductApiRoutes.ADMIN_ROOT)
    OkResponse<ProductResponse> update(
            @ApiParam(value = "Parameters for updating product") @RequestBody ProductRequest request
    ) throws ProductNotExistsException, CategoryNotExistsException, CityNotExistsException, ImageNotExistException {
        ProductDoc productDoc = productApiService.update(request);
        ProductResponse productResponse = ProductMapping.getInstance().getDocToResponseMapper().convert(productDoc);
        return OkResponse.of(productResponse);
    }

    @ApiOperation(value = "Delete product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @DeleteMapping(ProductApiRoutes.ADMIN_BY_ID)
    OkResponse<String> delete(
            @ApiParam(value = "Product id") @PathVariable ObjectId id
    ) {
        productApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
    }

}

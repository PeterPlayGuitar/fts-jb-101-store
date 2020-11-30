package com.apeter0.store.category.controller;

import com.apeter0.store.base.api.request.SearchRequest;
import com.apeter0.store.base.api.response.OkResponse;
import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.category.api.request.CategoryRequest;
import com.apeter0.store.category.api.response.CategoryResponse;
import com.apeter0.store.category.exception.CategoryNotExistsException;
import com.apeter0.store.category.mapping.CategoryMapping;
import com.apeter0.store.category.model.CategoryDoc;
import com.apeter0.store.category.routes.CategoryApiRoutes;
import com.apeter0.store.category.service.CategoryApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Category API")
class CategoryApiController {

    private final CategoryApiService categoryApiService;

    @ApiOperation(value = "Category search")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @GetMapping(CategoryApiRoutes.ROOT)
    OkResponse<SearchResponse<CategoryResponse>> search(
            @ApiParam(value = "Category search parameters") @ModelAttribute SearchRequest request
    ) {
        SearchResponse<CategoryDoc> categoryDocSearchResponse = categoryApiService.search(request);
        SearchResponse<CategoryResponse> categoryResponseSearchResponse = CategoryMapping.getInstance().getDocsListToResponseListMapper().convert(categoryDocSearchResponse);
        return OkResponse.of(categoryResponseSearchResponse);
    }

    @ApiOperation(value = "Find category by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Category not found")
    })
    @GetMapping(CategoryApiRoutes.BY_ID)
    OkResponse<CategoryResponse> getById(
            @ApiParam(value = "Category id") @PathVariable ObjectId id
    ) throws ChangeSetPersister.NotFoundException {
        CategoryDoc categoryDoc = categoryApiService.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        CategoryResponse categoryResponse = CategoryMapping.getInstance().getDocToResponseMapper().convert(categoryDoc);
        return OkResponse.of(categoryResponse);
    }

    @ApiOperation(value = "Create category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @PostMapping(CategoryApiRoutes.ADMIN_ROOT)
    OkResponse<CategoryResponse> create(
            @ApiParam(value = "Parameters for new category") @RequestBody CategoryRequest request
    ) {
        CategoryDoc categoryDoc = categoryApiService.create(request);
        CategoryResponse categoryResponse = CategoryMapping.getInstance().getDocToResponseMapper().convert(categoryDoc);
        return OkResponse.of(categoryResponse);
    }

    @ApiOperation(value = "Update category information")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "There is no category with such id")
    })
    @PutMapping(CategoryApiRoutes.ADMIN_ROOT)
    OkResponse<CategoryResponse> update(
            @ApiParam(value = "Parameters for updating category") @RequestBody CategoryRequest request
    ) throws CategoryNotExistsException {
        CategoryDoc categoryDoc = categoryApiService.update(request);
        CategoryResponse categoryResponse = CategoryMapping.getInstance().getDocToResponseMapper().convert(categoryDoc);
        return OkResponse.of(categoryResponse);
    }

    @ApiOperation(value = "Delete category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful")
    })
    @DeleteMapping(CategoryApiRoutes.ADMIN_BY_ID)
    OkResponse<String> delete(
            @ApiParam(value = "Category id") @PathVariable ObjectId id
    ) {
        categoryApiService.delete(id);
        return OkResponse.of(HttpStatus.OK.toString());
    }

}

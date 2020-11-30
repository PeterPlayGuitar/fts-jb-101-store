package com.apeter0.store.photo.controller;

import com.apeter0.store.base.api.request.SearchRequest;
import com.apeter0.store.base.api.response.OkResponse;
import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.photo.api.response.PhotoResponse;
import com.apeter0.store.photo.mapping.PhotoMapping;
import com.apeter0.store.photo.routes.PhotoApiRoutes;
import com.apeter0.store.photo.service.PhotoApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Photo API")
public class PhotoApiController {

    private final PhotoApiService photoApiService;

    @GetMapping(PhotoApiRoutes.ADMIN_ROOT)
    @ApiOperation(value = "Search photos")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success")
            }
    )
    public OkResponse<SearchResponse<PhotoResponse>> search(
            @ModelAttribute SearchRequest request
    ) {
        return OkResponse.of(PhotoMapping.getInstance().getDocsListToResponseListMapper().convert(
                photoApiService.search(request)
        ));
    }

    @ApiOperation(value = "Deletes photo", notes = "Deletes photo by id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success")
            }
    )
    @DeleteMapping(PhotoApiRoutes.ADMIN_BY_ID)
    OkResponse<String> deleteById(
            @ApiParam(value = "Photo id") @PathVariable ObjectId id
    ) {
        photoApiService.deleteById(id);
        return OkResponse.of(HttpStatus.OK.toString());
    }

}
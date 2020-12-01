package com.apeter0.store.image.controller;

import com.apeter0.store.base.api.request.SearchRequest;
import com.apeter0.store.base.api.response.OkResponse;
import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.image.api.response.ImageResponse;
import com.apeter0.store.image.mapping.ImageMapping;
import com.apeter0.store.image.routes.ImageApiRoutes;
import com.apeter0.store.image.service.ImageApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Image API")
public class ImageApiController {

    private final ImageApiService imageApiService;

    @GetMapping(ImageApiRoutes.ADMIN_ROOT)
    @ApiOperation(value = "Search images")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success")
            }
    )
    public OkResponse<SearchResponse<ImageResponse>> search(
            @ModelAttribute SearchRequest request
    ) {
        return OkResponse.of(ImageMapping.getInstance().getDocsListToResponseListMapper().convert(
                imageApiService.search(request)
        ));
    }

    @ApiOperation(value = "Deletes image", notes = "Deletes image by id")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Success")
            }
    )
    @DeleteMapping(ImageApiRoutes.ADMIN_BY_ID)
    OkResponse<String> deleteById(
            @ApiParam(value = "Image id") @PathVariable ObjectId id
    ) {
        imageApiService.deleteById(id);
        return OkResponse.of(HttpStatus.OK.toString());
    }

}
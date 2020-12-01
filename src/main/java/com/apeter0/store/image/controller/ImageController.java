package com.apeter0.store.image.controller;

import com.apeter0.store.base.api.response.OkResponse;
import com.apeter0.store.image.api.response.ImageResponse;
import com.apeter0.store.image.mapping.ImageMapping;
import com.apeter0.store.image.model.ImageDoc;
import com.apeter0.store.image.routes.ImageApiRoutes;
import com.apeter0.store.image.service.ImageApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Api(value = "Image API")
public class ImageController {
    private final ImageApiService imageApiService;

    @PostMapping(ImageApiRoutes.ADMIN_ROOT)
    @ApiOperation(value = "Create", notes = "Creates new image")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")
    })
    public @ResponseBody
    OkResponse<ImageResponse> create(
            @RequestParam MultipartFile file
    ) throws IOException {
        ImageDoc imageDoc = imageApiService.create(file);
        ImageResponse imageResponse = ImageMapping.getInstance().getDocToResponseMapper().convert(imageDoc);
        return OkResponse.of(imageResponse);
    }

    @GetMapping(ImageApiRoutes.DOWNLOAD)
    @ApiOperation(value = "Download image", notes = "Downloads image by id")
    public void byId(
            @ApiParam(value = "Image id") @PathVariable ObjectId id,
            HttpServletResponse response
    ) throws ChangeSetPersister.NotFoundException, IOException {
        ImageDoc imageDoc = imageApiService.findById(id).orElseThrow();
        response.addHeader("Content-Type", imageDoc.getContentType());
        response.addHeader("Content-Disposition", "inline; filename=\"" + imageDoc.getId().toString() + "\"");
        FileCopyUtils.copy(imageApiService.downloadById(id), response.getOutputStream());
    }
}

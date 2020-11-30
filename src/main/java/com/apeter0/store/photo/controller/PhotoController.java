package com.apeter0.store.photo.controller;

import com.apeter0.store.base.api.response.OkResponse;
import com.apeter0.store.photo.api.response.PhotoResponse;
import com.apeter0.store.photo.mapping.PhotoMapping;
import com.apeter0.store.photo.model.PhotoDoc;
import com.apeter0.store.photo.routes.PhotoApiRoutes;
import com.apeter0.store.photo.service.PhotoApiService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Api(value = "Photo API")
public class PhotoController {
    private final PhotoApiService photoApiService;

    @PostMapping(PhotoApiRoutes.ADMIN_ROOT)
    @ApiOperation(value = "Create", notes = "Creates new photo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")
    })
    public @ResponseBody
    OkResponse<PhotoResponse> create(
            @RequestParam MultipartFile file
    ) throws IOException {
        PhotoDoc photoDoc = photoApiService.create(file);
        PhotoResponse photoResponse = PhotoMapping.getInstance().getDocToResponseMapper().convert(photoDoc);
        return OkResponse.of(photoResponse);
    }

    @GetMapping(PhotoApiRoutes.DOWNLOAD)
    @ApiOperation(value = "Download photo", notes = "Downloads photo by id")
    public void byId(
            @ApiParam(value = "Photo id") @PathVariable ObjectId id,
            HttpServletResponse response
    ) throws ChangeSetPersister.NotFoundException, IOException {
        PhotoDoc photoDoc = photoApiService.findById(id).orElseThrow();
        response.addHeader("Content-Type", photoDoc.getContentType());
        response.addHeader("Content-Disposition", "inline; filename=\"" + photoDoc.getId().toString() + "\"");
        FileCopyUtils.copy(photoApiService.downloadById(id), response.getOutputStream());
    }
}

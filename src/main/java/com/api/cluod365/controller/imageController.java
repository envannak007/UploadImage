package com.api.cluod365.controller;

import com.api.cluod365.dto.request.ImageRequest;
import com.api.cluod365.dto.response.BaseResponse;
import com.api.cluod365.dto.response.ImageResponse;
import com.api.cluod365.service.ImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("api/images")
@RequiredArgsConstructor
public class imageController {
    private final ImageService imageService;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<BaseResponse<ImageResponse>> upload(
            @Valid @ModelAttribute ImageRequest request){
        ImageResponse response = imageService.upload(request);
        return new ResponseEntity<>(
                BaseResponse.created("Image uploaded successfully.",response),
                HttpStatus.CREATED
        );
    }
    @GetMapping
    public ResponseEntity<BaseResponse<List<ImageResponse>>> getAll(){
        List<ImageResponse> response = imageService.findAll();
        return ResponseEntity.ok(
                BaseResponse.ok("Get all image successfully.",response)
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ImageResponse>> getById(@PathVariable Integer id){
        ImageResponse response = imageService.findById(id);
        return ResponseEntity.ok(
                BaseResponse.ok("Get image successfully.",response)
        );
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<BaseResponse<ImageResponse>> update(@PathVariable Integer id,
                                                              @Valid @ModelAttribute ImageRequest request){
        ImageResponse response = imageService.update(id,request);
        return ResponseEntity.ok(
                BaseResponse.ok("Image updated successfully.",response)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<ImageResponse>> delete(@PathVariable Integer id){
        ImageResponse response = imageService.delete(id);
        return ResponseEntity.ok(
                BaseResponse.ok("Image deleted successfully.",response)
        );
    }
}

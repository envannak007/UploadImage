package com.api.cluod365.service;

import com.api.cluod365.dto.request.ImageRequest;
import com.api.cluod365.dto.response.ImageResponse;

import java.util.List;

public interface ImageService {
    ImageResponse upload(ImageRequest request);
    List<ImageResponse> findAll();
    ImageResponse findById(Integer id);
    ImageResponse update(Integer id, ImageRequest request);
    ImageResponse delete(Integer id);
}

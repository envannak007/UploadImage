package com.api.cluod365.mapper;

import com.api.cluod365.dto.response.ImageResponse;
import com.api.cluod365.entity.ImageEntity;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {
    public ImageResponse toResponse(ImageEntity imageEntity){
        return new ImageResponse(
                imageEntity.getId(),
                imageEntity.getName(),
                imageEntity.getImageUrl()
        );
    }
}

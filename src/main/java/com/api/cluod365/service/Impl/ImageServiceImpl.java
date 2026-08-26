package com.api.cluod365.service.Impl;

import com.api.cluod365.dto.request.ImageRequest;
import com.api.cluod365.dto.response.ImageResponse;
import com.api.cluod365.entity.ImageEntity;
import com.api.cluod365.mapper.ImageMapper;
import com.api.cluod365.repository.ImageRepository;
import com.api.cluod365.service.CloudinaryService;
import com.api.cluod365.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;
    private final ImageMapper imageMapper;

    @Override
    public ImageResponse upload(ImageRequest request) {

        if (request.file() == null || request.file().isEmpty()){
            throw new IllegalArgumentException("Image file is required.");
        }

        try {
            // Upload image to Cloudinary
            String imageUrl = cloudinaryService.uploadImage(request.file());

            // Create Entity
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setName(request.name());
            imageEntity.setImageUrl(imageUrl);

            // Save to database
            ImageEntity saved = imageRepository.save(imageEntity);

            // Entity to Response
            return imageMapper.toResponse(saved);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image to Cloudinary",e);
        }
    }

    @Override
    public List<ImageResponse> findAll() {
        return imageRepository.findAll()
                .stream()
                .map(imageMapper::toResponse)
                .toList();
    }

    @Override
    public ImageResponse findById(Integer id) {
        ImageEntity imageEntity = imageRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Image not found with id : "+ id));
        return imageMapper.toResponse(imageEntity);
    }

    @Override
    public ImageResponse update(Integer id, ImageRequest request) {

        ImageEntity imageEntity = imageRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Image not found with id : "+ id));
        try {
            imageEntity.setName(request.name());

            if (request.file() !=null && !request.file().isEmpty()){
                String imageUrl = cloudinaryService.uploadImage(request.file());
                imageEntity.setImageUrl(imageUrl);
            }

            ImageEntity updated = imageRepository.save(imageEntity);

            return imageMapper.toResponse(updated);
        } catch (IOException e) {
            throw new RuntimeException("Failed to update image",e);
        }
    }

    @Override
    public ImageResponse delete(Integer id) {
        ImageEntity imageEntity = imageRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Image not found with id : "+ id));

        // Convert to Response before deleting
        ImageResponse response = imageMapper.toResponse(imageEntity);
        imageRepository.delete(imageEntity);
        return response;
    }
}

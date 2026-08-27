package com.api.cluod365.service.Impl;

import com.api.cluod365.dto.request.ImageRequest;
import com.api.cluod365.dto.response.ImageResponse;
import com.api.cluod365.entity.ImageEntity;
import com.api.cluod365.exception.FileUploadException;
import com.api.cluod365.exception.ResourceNotFoundException;
import com.api.cluod365.mapper.ImageMapper;
import com.api.cluod365.repository.ImageRepository;
import com.api.cluod365.service.CloudinaryService;
import com.api.cluod365.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;
    private final ImageMapper imageMapper;

    @Override
    public ImageResponse upload(ImageRequest request) {

        if (request.file() == null || request.file().isEmpty()) {
            throw new IllegalArgumentException("Image file is required.");
        }
        try {
            // Upload image to Cloudinary
            Map<String, String> result =
                    cloudinaryService.uploadImage(request.file());

            // Create Entity
            ImageEntity imageEntity = new ImageEntity();

            imageEntity.setName(request.name());
            imageEntity.setImageUrl(result.get("imageUrl"));
            imageEntity.setPublicId(result.get("publicId"));

            // Save database
            ImageEntity saved = imageRepository.save(imageEntity);

            return imageMapper.toResponse(saved);

        } catch (IOException e) {

            throw new FileUploadException(
                    "Failed to upload image to Cloudinary",
                    e
            );
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
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Image not found with id : " + id
                        )
                );

        return imageMapper.toResponse(imageEntity);
    }

    @Override
    public ImageResponse update(Integer id, ImageRequest request) {

        ImageEntity imageEntity = imageRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Image not found with id : " + id
                        )
                );
        try {

            // Update name
            imageEntity.setName(request.name());

            // Update image if new file is provided
            if (request.file() != null && !request.file().isEmpty()) {

                // Old Cloudinary public ID
                String oldPublicId = imageEntity.getPublicId();

                // Upload new image
                Map<String, String> result =
                        cloudinaryService.uploadImage(request.file());

                // Set new image data
                imageEntity.setImageUrl(result.get("imageUrl"));
                imageEntity.setPublicId(result.get("publicId"));

                // Save database
                ImageEntity updated = imageRepository.save(imageEntity);

                // Delete old image from Cloudinary
                if (oldPublicId != null && !oldPublicId.isBlank()) {
                    cloudinaryService.deleteImage(oldPublicId);
                }

                return imageMapper.toResponse(updated);
            }

            // Update name only
            ImageEntity updated = imageRepository.save(imageEntity);

            return imageMapper.toResponse(updated);

        } catch (IOException e) {

            throw new FileUploadException(
                    "Failed to update image",
                    e
            );
        }
    }

    @Override
    public ImageResponse delete(Integer id) {

        ImageEntity imageEntity = imageRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Image not found with id : " + id
                        )
                );

        // Keep response before delete
        ImageResponse response =
                imageMapper.toResponse(imageEntity);

        try {

            // Get Cloudinary public ID
            String publicId = imageEntity.getPublicId();

            // Delete from database
            imageRepository.delete(imageEntity);

            // Delete from Cloudinary
            if (publicId != null && !publicId.isBlank()) {
                cloudinaryService.deleteImage(publicId);
            }

            return response;

        } catch (IOException e) {

            throw new FileUploadException(
                    "Failed to delete image from Cloudinary",
                    e
            );
        }
    }
}
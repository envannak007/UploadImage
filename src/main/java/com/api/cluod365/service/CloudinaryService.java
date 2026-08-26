package com.api.cluod365.service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) throws IOException{
        Map result = cloudinary.uploader().upload(
                file.getBytes(),
                Map.of("resource_type", "image")
        );
        return result.get("secure_url").toString();
    }
}

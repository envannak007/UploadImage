package com.api.cluod365.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record ImageRequest(

        @NotBlank(message = "Name is required.")
        String name,

        @NotNull(message = "Image file is required.")
        MultipartFile file
) {
}

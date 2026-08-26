package com.api.cluod365.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record ImageRequest(

        String name,
        MultipartFile file
) {
}

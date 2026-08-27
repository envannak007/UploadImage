package com.api.cluod365.exception;

import com.api.cluod365.dto.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public class GlobalExceptionHandler{
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse<Void>> handleIllegalArgumentException(
            IllegalArgumentException exception
    ){
        BaseResponse<Void> response = BaseResponse.<Void>builder()
                .success(false)
                .message(exception.getMessage())
                .payload(null)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<BaseResponse<Void>> handleFileUploadException(
            FileUploadException exception
    ){
        BaseResponse<Void> response = BaseResponse.<Void>builder()
                .success(false)
                .message(exception.getMessage())
                .payload(null)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleResourceNotFoundException(
            ResourceNotFoundException exception
    ){
        BaseResponse<Void> response = BaseResponse.<Void>builder()
                .success(false)
                .message(exception.getMessage())
                .payload(null)
                .time(LocalDateTime.now())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }
}

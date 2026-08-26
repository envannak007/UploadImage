package com.api.cluod365.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private Boolean success;
    private String message;
    private T payload;
    private LocalDateTime time;

    // Helper method for Success Response
    public static <T> BaseResponse<T> ok(String message,T payload){
        return BaseResponse.<T>builder()
                .success(true)
                .message(message)
                .payload(payload)
                .time(LocalDateTime.now())
                .build();
    }

    public static <T> BaseResponse<T> created (String message,T payload){
        return BaseResponse.<T>builder()
                .success(true)
                .message(message)
                .payload(payload)
                .time(LocalDateTime.now())
                .build();
    }
}

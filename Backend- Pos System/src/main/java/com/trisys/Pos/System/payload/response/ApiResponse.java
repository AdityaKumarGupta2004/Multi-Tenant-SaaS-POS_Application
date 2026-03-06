package com.trisys.Pos.System.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;

    public static ApiResponse success(Object data) {
        return new ApiResponse(true, "Request successful", data);
    }

    public static ApiResponse failure(String message) {
        return new ApiResponse(false, message, null);
    }
}
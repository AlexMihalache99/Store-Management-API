package com.alexandru.store_management_api.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
                LocalDateTime timestamp,
                int status,
                String error,
                Map<String, String> fieldErrors) {
        public ErrorResponse(LocalDateTime timestamp, int status, String error) {
                this(timestamp, status, error, null);
        }
}
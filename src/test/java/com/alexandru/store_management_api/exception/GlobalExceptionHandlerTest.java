package com.alexandru.store_management_api.exception;

import com.alexandru.store_management_api.dto.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleProductNotFound_returnsNotFoundResponse() {
        ProductNotFoundException ex = new ProductNotFoundException(99L);
        ResponseEntity<ErrorResponse> resp = handler.handleProductNotFound(ex);
        assertThat(resp.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.NOT_FOUND);
        assertThat(resp.getBody().error()).contains("99");
    }

    @Test
    void handleValidationErrors_buildsFieldMap() {
        BindingResult binding = mock(BindingResult.class);
        FieldError fe = new FieldError("object","field","must not be blank");
        when(binding.getFieldErrors()).thenReturn(List.of(fe));

        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(binding);

        ResponseEntity<ErrorResponse> resp = handler.handleValidationErrors(ex);
        assertThat(resp.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.BAD_REQUEST);
        assertThat(resp.getBody().fieldErrors()).containsKey("field");
    }
}

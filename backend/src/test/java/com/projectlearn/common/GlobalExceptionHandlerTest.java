package com.projectlearn.common;

import com.projectlearn.common.exception.BusinessException;
import com.projectlearn.common.exception.GlobalExceptionHandler;
import com.projectlearn.common.response.ApiResponse;
import com.projectlearn.common.response.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void businessErrorUsesBusinessCodeAndMessage() {
        ResponseEntity<ApiResponse<Void>> response =
                handler.handleBusinessException(new BusinessException("project not found"));

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo(
                ApiResponse.error(ErrorCode.BUSINESS_ERROR, "project not found"));
    }

    @Test
    void unknownErrorDoesNotExposeInternalDetails() {
        ResponseEntity<ApiResponse<Void>> response =
                handler.handleUnknownException(new RuntimeException("secret database details"));

        assertThat(response.getStatusCode().value()).isEqualTo(500);
        assertThat(response.getBody()).isEqualTo(
                ApiResponse.error(ErrorCode.INTERNAL_ERROR, "Internal server error"));
    }

    @Test
    void validationErrorReturnsFieldMessage() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(
                List.of(new FieldError("request", "name", "must not be blank")));
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(
                mock(MethodParameter.class), bindingResult);

        ResponseEntity<ApiResponse<Void>> response = handler.handleValidationException(exception);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo(
                ApiResponse.error(ErrorCode.BAD_REQUEST, "name: must not be blank"));
    }
}

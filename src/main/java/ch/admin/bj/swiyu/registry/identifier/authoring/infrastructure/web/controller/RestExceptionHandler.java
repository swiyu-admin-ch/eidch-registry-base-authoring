/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.identifier.authoring.infrastructure.web.controller;

import ch.admin.bj.swiyu.registry.identifier.authoring.api.ApiErrorDto;
import ch.admin.bj.swiyu.registry.identifier.authoring.common.exception.ResourceNotFoundException;
import ch.admin.bj.swiyu.registry.identifier.authoring.common.exception.ResourceNotReadyException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleResourceNotFoundException(
            final ResourceNotFoundException exception,
            final HttpServletRequest request
    ) {
        var apiError = new ApiErrorDto(HttpStatus.NOT_FOUND, exception.getMessage());
        log.trace("Resource not found for URL {}", request.getRequestURI());
        return new ResponseEntity<>(apiError, apiError.status());
    }

    @ExceptionHandler(ResourceNotReadyException.class)
    public ResponseEntity<ApiErrorDto> handleResourceNotReadyException(
            final ResourceNotReadyException exception,
            final HttpServletRequest request
    ) {
        var apiError = new ApiErrorDto(HttpStatus.TOO_EARLY, exception.getMessage());
        log.trace("Resource not ready for URL {}", request.getRequestURI());
        return new ResponseEntity<>(apiError, apiError.status());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDto> handle(final Exception exception, final HttpServletRequest request) {
        final ApiErrorDto apiError = new ApiErrorDto(HttpStatus.INTERNAL_SERVER_ERROR);
        log.error("Detected unhandled exception for url {}", request.getRequestURL(), exception);
        return new ResponseEntity<>(apiError, apiError.status());
    }
}

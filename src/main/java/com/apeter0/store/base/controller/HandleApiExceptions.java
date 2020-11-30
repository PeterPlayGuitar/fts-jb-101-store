package com.apeter0.store.base.controller;

import com.apeter0.store.base.api.response.ErrorResponse;
import com.apeter0.store.category.exception.CategoryExistsException;
import com.apeter0.store.category.exception.CategoryNotExistsException;
import com.apeter0.store.city.exception.CityExistsException;
import com.apeter0.store.city.exception.CityNotExistsException;
import com.apeter0.store.product.exception.ProductExistsException;
import com.apeter0.store.product.exception.ProductNotExistsException;
import com.apeter0.store.street.exception.StreetExistsException;
import com.apeter0.store.street.exception.StreetNotExistsException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HandleApiExceptions extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(ErrorResponse response) {
        return new ResponseEntity<>(response, response.getHttpStatus());
    }


    @ExceptionHandler(CityExistsException.class)
    public ResponseEntity<Object> cityExistException(CityExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This city already exists", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(CityNotExistsException.class)
    public ResponseEntity<Object> cityNotExistException(CityNotExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This city does not exist", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(StreetExistsException.class)
    public ResponseEntity<Object> cityExistException(StreetExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This street already exists", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(StreetNotExistsException.class)
    public ResponseEntity<Object> cityNotExistException(StreetNotExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This street does not exist", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(CategoryExistsException.class)
    public ResponseEntity<Object> categoryExistException(CategoryExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This category already exists", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(CategoryNotExistsException.class)
    public ResponseEntity<Object> categoryNotExistException(CategoryNotExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This category does not exist", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ProductExistsException.class)
    public ResponseEntity<Object> productExistsException(ProductExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This product already exists", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ProductNotExistsException.class)
    public ResponseEntity<Object> productNotExistsException(ProductNotExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This product does not exist", HttpStatus.BAD_REQUEST));
    }


    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<Object> notFoundException(ChangeSetPersister.NotFoundException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("Can't find the entity", HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("Unknown error", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}

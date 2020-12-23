package com.apeter0.store.base.controller;

import com.apeter0.store.base.api.response.ErrorResponse;
import com.apeter0.store.cart.exception.CartExistsException;
import com.apeter0.store.cart.exception.CartIsEmptyException;
import com.apeter0.store.cart.exception.CartNotExistsException;
import com.apeter0.store.category.exception.CategoryExistsException;
import com.apeter0.store.category.exception.CategoryNotExistsException;
import com.apeter0.store.city.exception.CityExistsException;
import com.apeter0.store.city.exception.CityNotExistsException;
import com.apeter0.store.deliveryTime.exception.DeliveryTimeExistsException;
import com.apeter0.store.deliveryTime.exception.DeliveryTimeNameNotSpecifiedException;
import com.apeter0.store.deliveryTime.exception.DeliveryTimeNotExistsException;
import com.apeter0.store.guest.exception.GuestExistsException;
import com.apeter0.store.guest.exception.GuestNotExistsException;
import com.apeter0.store.image.exception.ImageExistException;
import com.apeter0.store.image.exception.ImageNotExistException;
import com.apeter0.store.order.exception.OrderExistsException;
import com.apeter0.store.order.exception.OrderNotExistsException;
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

    // city

    @ExceptionHandler(CityExistsException.class)
    public ResponseEntity<Object> cityExistException(CityExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This city already exists", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(CityNotExistsException.class)
    public ResponseEntity<Object> cityNotExistException(CityNotExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This city does not exist", HttpStatus.BAD_REQUEST));
    }

    // street

    @ExceptionHandler(StreetExistsException.class)
    public ResponseEntity<Object> streetExistException(StreetExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This street already exists", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(StreetNotExistsException.class)
    public ResponseEntity<Object> streetNotExistException(StreetNotExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This street does not exist", HttpStatus.BAD_REQUEST));
    }

    // category

    @ExceptionHandler(CategoryExistsException.class)
    public ResponseEntity<Object> categoryExistException(CategoryExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This category already exists", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(CategoryNotExistsException.class)
    public ResponseEntity<Object> categoryNotExistException(CategoryNotExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This category does not exist", HttpStatus.BAD_REQUEST));
    }

    // product

    @ExceptionHandler(ProductExistsException.class)
    public ResponseEntity<Object> productExistsException(ProductExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This product already exists", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ProductNotExistsException.class)
    public ResponseEntity<Object> productNotExistsException(ProductNotExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This product does not exist", HttpStatus.BAD_REQUEST));
    }

    // image

    @ExceptionHandler(ImageExistException.class)
    public ResponseEntity<Object> imageExistException(ImageExistException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This image already exists", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ImageNotExistException.class)
    public ResponseEntity<Object> imageNotExistsException(ImageNotExistException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This image does not exist", HttpStatus.BAD_REQUEST));
    }

    // guest

    @ExceptionHandler(GuestExistsException.class)
    public ResponseEntity<Object> guestExistsException(GuestExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This guest already exists", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(GuestNotExistsException.class)
    public ResponseEntity<Object> guestNotExistsException(GuestNotExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This guest does not exist", HttpStatus.BAD_REQUEST));
    }

    // cart

    @ExceptionHandler(CartExistsException.class)
    public ResponseEntity<Object> cartExistsException(CartExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This cart already exists", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(CartNotExistsException.class)
    public ResponseEntity<Object> cartNotExistsException(CartNotExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This cart does not exist", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(CartIsEmptyException.class)
    public ResponseEntity<Object> cartIsEmptyException(CartIsEmptyException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("Can't process request because cart was empty", HttpStatus.BAD_REQUEST));
    }

    // order

    @ExceptionHandler(OrderExistsException.class)
    public ResponseEntity<Object> orderExistsException(OrderExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This order already exists", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(OrderNotExistsException.class)
    public ResponseEntity<Object> orderNotExistsException(OrderNotExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This order does not exist", HttpStatus.BAD_REQUEST));
    }

    // delivery time

    @ExceptionHandler(DeliveryTimeExistsException.class)
    public ResponseEntity<Object> deliveryTimeExistsException(DeliveryTimeExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This delivery time already exists", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(DeliveryTimeNotExistsException.class)
    public ResponseEntity<Object> deliveryTimeNotExistsException(DeliveryTimeNotExistsException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This delivery time does not exist", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(DeliveryTimeNameNotSpecifiedException.class)
    public ResponseEntity<Object> deliveryTimeNameNotSpecifiedException(DeliveryTimeNameNotSpecifiedException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("This delivery time name was not specified", HttpStatus.BAD_REQUEST));
    }

    // not found

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<Object> notFoundException(ChangeSetPersister.NotFoundException ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("Can't find the entity", HttpStatus.NOT_FOUND));
    }

    // base

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex, WebRequest request) {
        return buildResponseEntity(ErrorResponse.of("Unknown error", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}

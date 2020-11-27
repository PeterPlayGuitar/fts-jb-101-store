package com.apeter0.store.base.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResponse extends OkResponse<String> {
    private String errorMessage;
    private HttpStatus httpStatus;

    public static <T> ErrorResponse of(String errorMessage, HttpStatus httpStatus) {
        ErrorResponse response = new ErrorResponse();
        response.setErrorMessage(errorMessage);
        response.setHttpStatus(httpStatus);
        response.setResult(null);
        response.setStatus(Status.ERROR);
        return response;
    }
}

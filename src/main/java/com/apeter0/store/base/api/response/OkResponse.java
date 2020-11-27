package com.apeter0.store.base.api.response;

import lombok.*;

@Getter
@Setter
public class OkResponse<T> {

    public enum Status {
        SUCCESS, ERROR
    }

    protected T result;
    protected Status status;

    public static <T> OkResponse of(T result) {
        var response = new OkResponse();
        response.setResult(result);
        response.setStatus(Status.SUCCESS);
        return response;
    }
}

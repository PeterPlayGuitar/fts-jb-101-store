package com.apeter0.store.base.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResponse<T> {
    private Long count;
    private List<T> items;

    public static <T> SearchResponse of(Long count, List<T> items) {
        var response = new SearchResponse<T>();
        response.setCount(count);
        response.setItems(items);
        return response;
    }
}

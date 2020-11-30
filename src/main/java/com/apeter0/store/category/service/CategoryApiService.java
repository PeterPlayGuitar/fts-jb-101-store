package com.apeter0.store.category.service;

import com.apeter0.store.base.api.request.SearchRequest;
import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.category.api.request.CategoryRequest;
import com.apeter0.store.category.exception.CategoryExistsException;
import com.apeter0.store.category.exception.CategoryNotExistsException;
import com.apeter0.store.category.model.CategoryDoc;
import com.apeter0.store.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryApiService {

    private final CategoryRepository categoryRepository;
    private final MongoTemplate mongoTemplate;

    public SearchResponse<CategoryDoc> search(SearchRequest request) {

        Criteria criteria = new Criteria();
        if (request.getQuery() != null && !request.getQuery().isEmpty())
            criteria = criteria.orOperator(
                    Criteria.where("name").regex(request.getQuery(), "i")
            );

        Query query = new Query(criteria);

        Long count = mongoTemplate.count(query, CategoryDoc.class);

        query.limit(request.getSize());
        query.skip(request.getSkip());

        List<CategoryDoc> categoryDocs = mongoTemplate.find(query, CategoryDoc.class);
        return SearchResponse.of(count, categoryDocs);
    }

    public Optional<CategoryDoc> findById(ObjectId id) {
        return categoryRepository.findById(id);
    }

    public CategoryDoc create(CategoryRequest categoryRequest) {

        CategoryDoc categoryDoc = CategoryDoc.builder()
                .name(categoryRequest.getName())
                .build();

        return categoryRepository.save(categoryDoc);
    }

    public CategoryDoc update(CategoryRequest categoryRequest) throws CategoryNotExistsException {

        val categoryDocOptional = categoryRepository.findById(categoryRequest.getId());

        if (categoryDocOptional.isEmpty())
            throw new CategoryNotExistsException();

        val categoryDoc = categoryDocOptional.get();

        categoryDoc.setName(categoryRequest.getName());

        return categoryRepository.save(categoryDoc);
    }

    public void delete(ObjectId id) {
        categoryRepository.deleteById(id);
    }
}

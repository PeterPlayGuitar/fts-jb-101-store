package com.apeter0.store.deliveryTime.service;

import com.apeter0.store.base.api.request.SearchRequest;
import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.deliveryTime.api.request.DeliveryTimeRequest;
import com.apeter0.store.deliveryTime.exception.DeliveryTimeNameNotSpecifiedException;
import com.apeter0.store.deliveryTime.exception.DeliveryTimeNotExistsException;
import com.apeter0.store.deliveryTime.model.DeliveryTimeDoc;
import com.apeter0.store.deliveryTime.repository.DeliveryTimeRepository;
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
public class DeliveryTimeApiService {

    private final DeliveryTimeRepository deliveryTimeRepository;
    private final MongoTemplate mongoTemplate;

    public SearchResponse<DeliveryTimeDoc> search(SearchRequest request) {

        Criteria criteria = new Criteria();
        if (request.getQuery() != null && !request.getQuery().isEmpty())
            criteria = criteria.orOperator(
                    Criteria.where("name").regex(request.getQuery(), "i")
            );

        Query query = new Query(criteria);

        Long count = mongoTemplate.count(query, DeliveryTimeDoc.class);

        query.limit(request.getSize());
        query.skip(request.getSkip());

        List<DeliveryTimeDoc> deliveryTimeDocs = mongoTemplate.find(query, DeliveryTimeDoc.class);
        return SearchResponse.of(count, deliveryTimeDocs);
    }

    public Optional<DeliveryTimeDoc> findById(ObjectId id) {
        return deliveryTimeRepository.findById(id);
    }

    public DeliveryTimeDoc create(DeliveryTimeRequest deliveryTimeRequest) throws DeliveryTimeNameNotSpecifiedException {

        if (deliveryTimeRequest.getName() == null)
            throw new DeliveryTimeNameNotSpecifiedException();

        DeliveryTimeDoc deliveryTimeDoc = DeliveryTimeDoc.builder()
                .name(deliveryTimeRequest.getName())
                .index(deliveryTimeRequest.getIndex() != null ? deliveryTimeRequest.getIndex() : 0)
                .build();

        return deliveryTimeRepository.save(deliveryTimeDoc);
    }

    public DeliveryTimeDoc update(DeliveryTimeRequest deliveryTimeRequest) throws DeliveryTimeNotExistsException, DeliveryTimeNameNotSpecifiedException {

        if (deliveryTimeRequest.getName() == null)
            throw new DeliveryTimeNameNotSpecifiedException();

        val deliveryTimeDocOptional = deliveryTimeRepository.findById(deliveryTimeRequest.getId());

        if (deliveryTimeDocOptional.isEmpty())
            throw new DeliveryTimeNotExistsException();

        var deliveryTimeDoc = deliveryTimeDocOptional.get();

        deliveryTimeDoc.setName(deliveryTimeRequest.getName());
        deliveryTimeDoc.setIndex(deliveryTimeRequest.getIndex() != null ? deliveryTimeRequest.getIndex() : 0);

        return deliveryTimeRepository.save(deliveryTimeDoc);
    }

    public void delete(ObjectId id) {
        deliveryTimeRepository.deleteById(id);
    }
}

package com.apeter0.store.street.service;

import com.apeter0.store.base.api.request.SearchRequest;
import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.city.exception.CityNotExistsException;
import com.apeter0.store.city.model.CityDoc;
import com.apeter0.store.city.repository.CityRepository;
import com.apeter0.store.street.api.request.StreetRequest;
import com.apeter0.store.street.api.request.StreetSearchRequest;
import com.apeter0.store.street.exception.StreetExistsException;
import com.apeter0.store.street.exception.StreetNotExistsException;
import com.apeter0.store.street.model.StreetDoc;
import com.apeter0.store.street.repository.StreetRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StreetApiService {

    private final StreetRepository streetRepository;
    private final MongoTemplate mongoTemplate;
    private final CityRepository cityRepository;

    public SearchResponse<StreetDoc> search(StreetSearchRequest request) throws CityNotExistsException {

        Criteria cityCriteria = new Criteria();
        Criteria streetNameCriteria = new Criteria();

        if (request.getCityId() != null) {
            if (cityRepository.findById(request.getCityId()).isEmpty())
                throw new CityNotExistsException();

            cityCriteria = cityCriteria.andOperator(Criteria.where("cityId").is(request.getCityId()));
        }

        if (request.getQuery() != null && !request.getQuery().isEmpty()) {
            streetNameCriteria = streetNameCriteria.andOperator(Criteria.where("name").regex(request.getQuery(), "i"));
        }

        Query query = new Query(new Criteria().andOperator(cityCriteria, streetNameCriteria));

        Long count = mongoTemplate.count(query, StreetDoc.class);

        query.limit(request.getSize());
        query.skip(request.getSkip());

        List<StreetDoc> streetDocs = mongoTemplate.find(query, StreetDoc.class);
        return SearchResponse.of(count, streetDocs);
    }

    public Optional<StreetDoc> findById(ObjectId id) {
        return streetRepository.findById(id);
    }

    public StreetDoc create(StreetRequest streetRequest) throws CityNotExistsException, StreetExistsException {

        if (streetRequest.getCityId() == null || cityRepository.findById(streetRequest.getCityId()).isEmpty())
            throw new CityNotExistsException();

        Criteria criteria = new Criteria();
        criteria = criteria.andOperator(
                Criteria.where("name").is(streetRequest.getName()),
                Criteria.where("cityId").is(streetRequest.getCityId())
        );
        Long sameStreetDocsCount = mongoTemplate.count(new Query(criteria), StreetDoc.class);
        if (sameStreetDocsCount != 0)
            throw new StreetExistsException();

        StreetDoc streetDoc = StreetDoc.builder()
                .name(streetRequest.getName())
                .cityId(streetRequest.getCityId())
                .build();

        return streetRepository.save(streetDoc);
    }

    public StreetDoc update(StreetRequest streetRequest) throws StreetNotExistsException, StreetExistsException {

        val streetDocOptional = streetRepository.findById(streetRequest.getId());

        if (streetDocOptional.isEmpty())
            throw new StreetNotExistsException();

        val streetDoc = streetDocOptional.get();

        streetDoc.setName(streetRequest.getName());

        Criteria criteria = new Criteria();
        criteria = criteria.andOperator(
                Criteria.where("name").is(streetDoc.getName()),
                Criteria.where("cityId").is(streetDoc.getCityId())
        );
        Long sameStreetDocsCount = mongoTemplate.count(new Query(criteria), StreetDoc.class);
        if (sameStreetDocsCount != 0)
            throw new StreetExistsException();

        return streetRepository.save(streetDoc);
    }

    public void delete(ObjectId id) {
        streetRepository.deleteById(id);
    }
}


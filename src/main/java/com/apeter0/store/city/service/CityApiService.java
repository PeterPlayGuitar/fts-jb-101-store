package com.apeter0.store.city.service;

import com.apeter0.store.base.api.request.SearchRequest;
import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.city.api.request.CityRequest;
import com.apeter0.store.city.exception.CityExistsException;
import com.apeter0.store.city.exception.CityNotExistsException;
import com.apeter0.store.city.model.CityDoc;
import com.apeter0.store.city.repository.CityRepository;
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
public class CityApiService {

    private final MongoTemplate mongoTemplate;
    private final CityRepository cityRepository;

    public SearchResponse<CityDoc> search(SearchRequest request) {

        Criteria criteria = new Criteria();
        if (request.getQuery() != null && !request.getQuery().isEmpty())
            criteria = criteria.orOperator(
                    Criteria.where("name").regex(request.getQuery(), "i")
            );

        Query query = new Query(criteria);

        Long count = mongoTemplate.count(query, CityDoc.class);

        query.limit(request.getSize());
        query.skip(request.getSkip());

        List<CityDoc> cityDocs = mongoTemplate.find(query, CityDoc.class);
        return SearchResponse.of(count, cityDocs);
    }

    public Optional<CityDoc> findById(ObjectId id) {
        return cityRepository.findById(id);
    }

    public CityDoc create(CityRequest cityRequest) throws CityExistsException {

        if (cityRepository.findByName(cityRequest.getName()).isPresent())
            throw new CityExistsException();

        CityDoc cityDoc = CityDoc.builder()
                .name(cityRequest.getName())
                .build();

        return cityRepository.save(cityDoc);

    }

    public CityDoc update(CityRequest cityRequest) throws CityNotExistsException, CityExistsException {

        val cityDocOptional = cityRepository.findById(cityRequest.getId());

        if (cityDocOptional.isEmpty())
            throw new CityNotExistsException();

        if (cityRepository.findByName(cityRequest.getName()).isPresent())
            throw new CityExistsException();

        var cityDoc = cityDocOptional.get();

        cityDoc.setName(cityRequest.getName());

        return cityRepository.save(cityDoc);
    }

    public void delete(ObjectId id) {
        cityRepository.deleteById(id);
    }
}

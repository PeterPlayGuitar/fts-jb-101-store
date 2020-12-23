package com.apeter0.store.guest.service;

import com.apeter0.store.base.api.request.SearchRequest;
import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.city.exception.CityNotExistsException;
import com.apeter0.store.city.model.CityDoc;
import com.apeter0.store.city.repository.CityRepository;
import com.apeter0.store.guest.api.request.GuestRequest;
import com.apeter0.store.guest.api.request.GuestSearchRequest;
import com.apeter0.store.guest.exception.GuestExistsException;
import com.apeter0.store.guest.exception.GuestNotExistsException;
import com.apeter0.store.guest.model.GuestDoc;
import com.apeter0.store.guest.repository.GuestRepository;
import com.apeter0.store.street.api.request.StreetSearchRequest;
import com.apeter0.store.street.api.response.StreetResponse;
import com.apeter0.store.street.exception.StreetNotExistsException;
import com.apeter0.store.street.model.StreetDoc;
import com.apeter0.store.street.repository.StreetRepository;
import com.apeter0.store.street.service.StreetApiService;
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
public class GuestApiService {

    private final GuestRepository guestRepository;
    private final MongoTemplate mongoTemplate;
    private final StreetRepository streetRepository;
    private final CityRepository cityRepository;

    public SearchResponse<GuestDoc> search(GuestSearchRequest request) {

        // process all search request criteria's

        Criteria firstNameCriteria = new Criteria();
        if (request.getQuery() != null && !request.getQuery().isEmpty())
            firstNameCriteria = firstNameCriteria.orOperator(
                    Criteria.where("firstName").regex(request.getQuery(), "i")
            );

        Criteria phoneNumberCriteria = new Criteria();
        if (request.getPhoneNumber() != null)
            phoneNumberCriteria = phoneNumberCriteria.orOperator(
                    Criteria.where("phoneNumber").is(request.getPhoneNumber())
            );

        Criteria cityIdCriteria = new Criteria();
        if (request.getCityId() != null)
            cityIdCriteria = cityIdCriteria.orOperator(
                    Criteria.where("cityId").is(request.getCityId())
            );

        Criteria streetIdCriteria = new Criteria();
        if (request.getStreetId() != null)
            streetIdCriteria = streetIdCriteria.orOperator(
                    Criteria.where("streetId").is(request.getStreetId())
            );

        Criteria houseCriteria = new Criteria();
        if (request.getHouse() != null)
            houseCriteria = houseCriteria.orOperator(
                    Criteria.where("house").is(request.getHouse())
            );

        Criteria apartmentCriteria = new Criteria();
        if (request.getApartment() != null)
            apartmentCriteria = apartmentCriteria.orOperator(
                    Criteria.where("apartment").is(request.getApartment())
            );

        Criteria entranceCriteria = new Criteria();
        if (request.getEntrance() != null)
            entranceCriteria = entranceCriteria.orOperator(
                    Criteria.where("entrance").is(request.getEntrance())
            );

        Criteria floorCriteria = new Criteria();
        if (request.getFloor() != null)
            floorCriteria = floorCriteria.orOperator(
                    Criteria.where("floor").is(request.getFloor())
            );


        Query query = new Query(new Criteria().andOperator(
                firstNameCriteria,
                phoneNumberCriteria,
                cityIdCriteria,
                streetIdCriteria,
                houseCriteria,
                apartmentCriteria,
                entranceCriteria,
                floorCriteria
        ));

        Long count = mongoTemplate.count(query, GuestDoc.class);

        query.limit(request.getSize());
        query.skip(request.getSkip());

        List<GuestDoc> guestDocs = mongoTemplate.find(query, GuestDoc.class);
        return SearchResponse.of(count, guestDocs);
    }

    public Optional<GuestDoc> findById(ObjectId id) {
        return guestRepository.findById(id);
    }

    public GuestDoc create(GuestRequest guestRequest) throws StreetNotExistsException, CityNotExistsException {

        if (guestRequest.getCityId() == null)
            throw new CityNotExistsException();
        if (guestRequest.getStreetId() == null)
            throw new StreetNotExistsException();

        CityDoc cityDoc = cityRepository.findById(guestRequest.getCityId()).orElseThrow(CityNotExistsException::new);
        StreetDoc streetDoc = streetRepository.findById(guestRequest.getStreetId()).orElseThrow(StreetNotExistsException::new);
        if (!streetDoc.getCityId().equals(cityDoc.getId()))
            throw new StreetNotExistsException();

        GuestDoc guestDoc = GuestDoc.builder()
                .firstName(guestRequest.getFirstName())
                .phoneNumber(guestRequest.getPhoneNumber())
                .streetId(guestRequest.getStreetId())
                .house(guestRequest.getHouse())
                .apartment(guestRequest.getApartment())
                .entrance(guestRequest.getEntrance())
                .floor(guestRequest.getFloor())
                .build();

        return guestRepository.save(guestDoc);
    }

    public GuestDoc update(GuestRequest guestRequest) throws GuestNotExistsException, StreetNotExistsException, CityNotExistsException {

        if (guestRequest.getCityId() == null)
            throw new CityNotExistsException();
        if (guestRequest.getStreetId() == null)
            throw new StreetNotExistsException();

        CityDoc cityDoc = cityRepository.findById(guestRequest.getCityId()).orElseThrow(CityNotExistsException::new);
        StreetDoc streetDoc = streetRepository.findById(guestRequest.getStreetId()).orElseThrow(StreetNotExistsException::new);
        if (!streetDoc.getCityId().equals(cityDoc.getId()))
            throw new StreetNotExistsException();

        val guestDocOptional = guestRepository.findById(guestRequest.getId());

        if (guestDocOptional.isEmpty()) {
            throw new GuestNotExistsException();
        }

        val guestDoc = guestDocOptional.get();

        guestDoc.setFirstName(guestRequest.getFirstName());
        guestDoc.setPhoneNumber(guestRequest.getPhoneNumber());
        guestDoc.setStreetId(streetDoc.getId());
        guestDoc.setHouse(guestRequest.getHouse());
        guestDoc.setApartment(guestRequest.getApartment());
        guestDoc.setEntrance(guestRequest.getEntrance());
        guestDoc.setFloor(guestRequest.getFloor());

        return guestRepository.save(guestDoc);
    }

    public void delete(ObjectId id) {
        guestRepository.deleteById(id);
    }
}

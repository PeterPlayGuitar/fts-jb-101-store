package com.apeter0.store.order.service;

import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.cart.exception.CartNotExistsException;
import com.apeter0.store.cart.model.CartDoc;
import com.apeter0.store.cart.repository.CartRepository;
import com.apeter0.store.city.exception.CityNotExistsException;
import com.apeter0.store.city.model.CityDoc;
import com.apeter0.store.city.repository.CityRepository;
import com.apeter0.store.deliveryTime.exception.DeliveryTimeNotExistsException;
import com.apeter0.store.deliveryTime.repository.DeliveryTimeRepository;
import com.apeter0.store.order.api.request.OrderRequest;
import com.apeter0.store.order.api.request.OrderSearchRequest;
import com.apeter0.store.order.exception.OrderNotExistsException;
import com.apeter0.store.order.model.OrderDoc;
import com.apeter0.store.order.repository.OrderRepository;
import com.apeter0.store.product.exception.ProductNotExistsException;
import com.apeter0.store.product.model.ProductDoc;
import com.apeter0.store.product.repository.ProductRepository;
import com.apeter0.store.street.exception.StreetNotExistsException;
import com.apeter0.store.street.model.StreetDoc;
import com.apeter0.store.street.repository.StreetRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderApiService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CityRepository cityRepository;
    private final StreetRepository streetRepository;
    private final ProductRepository productRepository;
    private final DeliveryTimeRepository deliveryTimeRepository;
    private final MongoTemplate mongoTemplate;

    public SearchResponse<OrderDoc> search(OrderSearchRequest request) {

        // process all search request criteria's

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
                phoneNumberCriteria,
                cityIdCriteria,
                streetIdCriteria,
                houseCriteria,
                apartmentCriteria,
                entranceCriteria,
                floorCriteria
        ));

        Long count = mongoTemplate.count(query, OrderDoc.class);

        query.limit(request.getSize());
        query.skip(request.getSkip());

        List<OrderDoc> orderDocs = mongoTemplate.find(query, OrderDoc.class);
        return SearchResponse.of(count, orderDocs);
    }

    public Optional<OrderDoc> findById(ObjectId id) {
        return orderRepository.findById(id);
    }

    public OrderDoc create(OrderRequest orderRequest) throws CartNotExistsException, CityNotExistsException, StreetNotExistsException, ProductNotExistsException, DeliveryTimeNotExistsException {

        if(orderRequest.getDeliveryTimeId() == null || deliveryTimeRepository.findById(orderRequest.getDeliveryTimeId()).isEmpty())
            throw new DeliveryTimeNotExistsException();

        if(orderRequest.getStreetId() == null)
            throw new StreetNotExistsException();
        Optional<StreetDoc> streetDocOptional = streetRepository.findById(orderRequest.getStreetId());
        if (streetDocOptional.isEmpty())
            throw new StreetNotExistsException();

        if(orderRequest.getCartId() == null)
            throw new CartNotExistsException();
        Optional<CartDoc> optionalCartDoc = cartRepository.findById(orderRequest.getCartId());
        if (optionalCartDoc.isEmpty())
            throw new CartNotExistsException();

        CartDoc cart = optionalCartDoc.get();
        Optional<CityDoc> optionalCityDoc = cityRepository.findById(streetDocOptional.get().getCityId());
        if(optionalCityDoc.isEmpty())
            throw new CityNotExistsException();
        CityDoc city = optionalCityDoc.get();

        Integer totalPrice = 0;
        for (var productWithQuantity : cart.getProducts()) {

            Optional<ProductDoc> optionalProductDoc = productRepository.findById(productWithQuantity.getProductId());
            if (optionalProductDoc.isEmpty())
                throw new ProductNotExistsException();

            ProductDoc product = optionalProductDoc.get();

            Integer price = product.getDefaultPrice();

            for (var cityPrice : product.getPrices())
                if (cityPrice.getCityId().equals(city.getId())) {
                    price = cityPrice.getPrice();
                    break;
                }

            totalPrice += productWithQuantity.getQuantity() * price;
        }

        OrderDoc orderDoc = OrderDoc.builder()
                .phoneNumber(orderRequest.getPhoneNumber())
                .streetId(orderRequest.getStreetId())
                .house(orderRequest.getHouse())
                .apartment(orderRequest.getApartment())
                .entrance(orderRequest.getEntrance())
                .floor(orderRequest.getFloor())
                .cartId(orderRequest.getCartId())
                .deliveryTimeId(orderRequest.getDeliveryTimeId())
                .paymentMethod(orderRequest.getPaymentMethod())
                .totalPrice(totalPrice)
                .build();

        return orderRepository.save(orderDoc);
    }

    public OrderDoc update(OrderRequest orderRequest) throws OrderNotExistsException, StreetNotExistsException, CityNotExistsException, DeliveryTimeNotExistsException {

        if(orderRequest.getDeliveryTimeId() == null || deliveryTimeRepository.findById(orderRequest.getDeliveryTimeId()).isEmpty())
            throw new DeliveryTimeNotExistsException();
        if (orderRequest.getStreetId() == null || streetRepository.findById(orderRequest.getStreetId()).isEmpty())
            throw new StreetNotExistsException();

        val orderDocOptional = orderRepository.findById(orderRequest.getId());

        if (orderDocOptional.isEmpty())
            throw new OrderNotExistsException();

        var orderDoc = orderDocOptional.get();

        orderDoc.setPhoneNumber(orderRequest.getPhoneNumber());
        orderDoc.setStreetId(orderRequest.getStreetId());
        orderDoc.setPhoneNumber(orderRequest.getPhoneNumber());
        orderDoc.setHouse(orderRequest.getHouse());
        orderDoc.setApartment(orderRequest.getApartment());
        orderDoc.setEntrance(orderRequest.getEntrance());
        orderDoc.setFloor(orderRequest.getFloor());
        orderDoc.setDeliveryTimeId(orderRequest.getDeliveryTimeId());
        orderDoc.setPaymentMethod(orderRequest.getPaymentMethod());

        return orderRepository.save(orderDoc);
    }

    public void delete(ObjectId id) {
        orderRepository.deleteById(id);
    }
}

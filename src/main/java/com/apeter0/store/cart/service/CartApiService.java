package com.apeter0.store.cart.service;

import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.cart.api.request.CartRequest;
import com.apeter0.store.cart.exception.CartIsEmptyException;
import com.apeter0.store.cart.exception.CartNotExistsException;
import com.apeter0.store.cart.model.CartDoc;
import com.apeter0.store.cart.repository.CartRepository;
import com.apeter0.store.cart.api.request.CartSearchRequest;
import com.apeter0.store.guest.exception.GuestNotExistsException;
import com.apeter0.store.guest.repository.GuestRepository;
import com.apeter0.store.product.exception.ProductNotExistsException;
import com.apeter0.store.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartApiService {

    private final CartRepository cartRepository;
    private final GuestRepository guestRepository;
    private final ProductRepository productRepository;
    private final MongoTemplate mongoTemplate;

    public SearchResponse<CartDoc> search(CartSearchRequest request) {
        Criteria criteria = new Criteria();
        if (request.getGuestId() != null)
            criteria = criteria.orOperator(
                    Criteria.where("guestId").is(request.getGuestId())
            );

        Query query = new Query(criteria);

        Long count = mongoTemplate.count(query, CartDoc.class);

        query.limit(request.getSize());
        query.skip(request.getSkip());

        List<CartDoc> cartDocs = mongoTemplate.find(query, CartDoc.class);
        return SearchResponse.of(count, cartDocs);
    }

    public Optional<CartDoc> findById(ObjectId id) {
        return cartRepository.findById(id);
    }

    public CartDoc create(CartRequest request) throws GuestNotExistsException, ProductNotExistsException, CartIsEmptyException {

        if (!guestRepository.findById(request.getGuestId()).isPresent())
            throw new GuestNotExistsException();

        if(request.getProducts() == null || request.getProducts().isEmpty())
            throw new CartIsEmptyException();

        for (var product : request.getProducts()) {
            if (!productRepository.findById(product.getProductId()).isPresent())
                throw new ProductNotExistsException();
        }

        List<CartDoc.ProductIdWithQuantity> products = request
                .getProducts()
                .stream()
                .map(
                        (req) -> CartDoc.ProductIdWithQuantity.builder()
                                .quantity(req.getQuantity())
                                .productId(req.getProductId())
                                .build()
                )
                .collect(Collectors.toList());

        CartDoc cartDoc = CartDoc.builder()
                .products(products)
                .guestId(request.getGuestId())
                .build();

        return cartRepository.save(cartDoc);
    }

    public CartDoc update(CartRequest request) throws ProductNotExistsException, CartNotExistsException, GuestNotExistsException, CartIsEmptyException {

        if (!guestRepository.findById(request.getGuestId()).isPresent())
            throw new GuestNotExistsException();

        if(request.getProducts() == null || request.getProducts().isEmpty())
            throw new CartIsEmptyException();

        for (var product : request.getProducts()) {
            if (!productRepository.findById(product.getProductId()).isPresent())
                throw new ProductNotExistsException();
        }

        Optional<CartDoc> optionalCartDoc = cartRepository.findById(request.getId());
        if (!optionalCartDoc.isPresent())
            throw new CartNotExistsException();

        CartDoc oldDoc = optionalCartDoc.get();

        List<CartDoc.ProductIdWithQuantity> products = request
                .getProducts()
                .stream()
                .map(
                        (req) -> CartDoc.ProductIdWithQuantity.builder()
                                .quantity(req.getQuantity())
                                .productId(req.getProductId())
                                .build()
                )
                .collect(Collectors.toList());

        oldDoc.setProducts(products);
        oldDoc.setGuestId(request.getGuestId());

        return cartRepository.save(oldDoc);
    }

    public void delete(ObjectId id) {
        cartRepository.deleteById(id);
    }
}

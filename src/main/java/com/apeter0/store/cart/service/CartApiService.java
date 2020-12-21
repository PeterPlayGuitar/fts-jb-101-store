package com.apeter0.store.cart.service;

import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.base.cookie.CookieConfig;
import com.apeter0.store.base.cookie.CookieNames;
import com.apeter0.store.cart.api.request.CartRequest;
import com.apeter0.store.cart.exception.CartIsEmptyException;
import com.apeter0.store.cart.exception.CartNotExistsException;
import com.apeter0.store.cart.mapping.CartMapping;
import com.apeter0.store.cart.model.CartDoc;
import com.apeter0.store.cart.repository.CartRepository;
import com.apeter0.store.guest.api.request.CartSearchRequest;
import com.apeter0.store.guest.exception.GuestNotExistsException;
import com.apeter0.store.guest.model.GuestDoc;
import com.apeter0.store.guest.repository.GuestRepository;
import com.apeter0.store.product.exception.ProductNotExistsException;
import com.apeter0.store.product.repository.ProductRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartApiService {

    private final CartRepository cartRepository;
    private final GuestRepository guestRepository;
    private final ProductRepository productRepository;
    private final MongoTemplate mongoTemplate;

    private Gson gson = new Gson();

    public SearchResponse<CartDoc> search(CartSearchRequest request) {
        Criteria criteria = new Criteria();
        if (request.getGuestId() != null)
            criteria = criteria.orOperator(
                    Criteria.where("guestId").is(request.getGuestId())
            );

        Query query = new Query(criteria);

        Long count = mongoTemplate.count(query, GuestDoc.class);

        query.limit(request.getSize());
        query.skip(request.getSkip());

        List<CartDoc> cartDocs = mongoTemplate.find(query, CartDoc.class);
        return SearchResponse.of(count, cartDocs);
    }

    public Optional<CartDoc> findById(ObjectId id) {
        return cartRepository.findById(id);
    }

    public CartDoc create(CartRequest request, HttpServletRequest httpServletRequest) throws CartIsEmptyException {

        Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies == null)
            throw new CartIsEmptyException();

        boolean exists = false;

        CartDoc.ListOfCartProducts listOfCartProducts = null;
        for (var c : cookies)
            if (c.getValue().equals(CookieNames.CART_PRODUCTS)) {
                exists = true;
                listOfCartProducts = gson.fromJson(c.getValue(), CartDoc.ListOfCartProducts.class);
            }

        if (!exists)
            throw new CartIsEmptyException();

        CartDoc cartDoc = CartDoc.builder()
                .products(CartMapping.getInstance().getObjectListOfCartProductsToListOfCartProducts().convert(listOfCartProducts))
                .guestId(request.getGuestId())
                .build();

        return cartRepository.save(cartDoc);
    }

    public CartDoc update(CartRequest request) throws ProductNotExistsException, CartNotExistsException, GuestNotExistsException {

        if (!guestRepository.findById(request.getGuestId()).isPresent())
            throw new GuestNotExistsException();

        for (var product : request.getProducts()) {
            if (!productRepository.findById(product.getProductId()).isPresent())
                throw new ProductNotExistsException();
        }

        Optional<CartDoc> optionalCartDoc = cartRepository.findById(request.getId());
        if (!optionalCartDoc.isPresent())
            throw new CartNotExistsException();

        CartDoc oldDoc = optionalCartDoc.get();

        oldDoc.setProducts(request.getProducts());
        oldDoc.setGuestId(request.getGuestId());

        return cartRepository.save(oldDoc);
    }

    public void addProduct(ObjectId productId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ProductNotExistsException {

        if (!productRepository.findById(productId).isPresent())
            throw new ProductNotExistsException();

        if (httpServletRequest.getCookies() == null) {
            List<CartDoc.ProductIdWithQuantity> products = new LinkedList<>();
            products.add(CartDoc.ProductIdWithQuantity.builder()
                    .productId(productId)
                    .quantity(1)
                    .build());

            CartDoc.ListOfCartProducts listOfCartProducts = CartMapping.getInstance().getListOfCartProductsToObjectListOfCartProducts().convert(products);

            Cookie cookie = new Cookie(CookieNames.CART_PRODUCTS, gson.toJson(listOfCartProducts));
            cookie.setMaxAge(CookieConfig.MAX_AGE);
            httpServletResponse.addCookie(cookie);

        } else {
            boolean cookieExists = false;
            for (Cookie c : httpServletRequest.getCookies()) {
                if (c.getName().equals(CookieNames.CART_PRODUCTS)) {
                    cookieExists = true;

                    CartDoc.ListOfCartProducts listOfCartProducts = gson.fromJson(c.getValue(), CartDoc.ListOfCartProducts.class);

                    boolean productExists = false;
                    for (var p : listOfCartProducts.getProducts()) {
                        if (p.getProductId().equals(productId.toString())) {
                            productExists = true;
                            p.setQuantity(p.getQuantity() + 1);
                            break;
                        }
                    }

                    if (!productExists) {
                        listOfCartProducts.getProducts().add(CartDoc.ProductStringIdWithQuantity.builder()
                                .productId(productId.toString())
                                .quantity(1)
                                .build());
                    }

                    Cookie cookie = new Cookie(CookieNames.CART_PRODUCTS, gson.toJson(listOfCartProducts));
                    cookie.setMaxAge(CookieConfig.MAX_AGE);
                    httpServletResponse.addCookie(cookie);

                    break;
                }
            }

            if (!cookieExists) {
                List<CartDoc.ProductIdWithQuantity> products = new LinkedList<>();
                products.add(CartDoc.ProductIdWithQuantity.builder()
                        .productId(productId)
                        .quantity(1)
                        .build());

                CartDoc.ListOfCartProducts listOfCartProducts = CartMapping.getInstance().getListOfCartProductsToObjectListOfCartProducts().convert(products);

                Cookie cookie = new Cookie(CookieNames.CART_PRODUCTS, gson.toJson(listOfCartProducts));
                cookie.setMaxAge(CookieConfig.MAX_AGE);
                httpServletResponse.addCookie(cookie);
            }
        }
    }

    public void delete(ObjectId id) {
        cartRepository.deleteById(id);
    }
}

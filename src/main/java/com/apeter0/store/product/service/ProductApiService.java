package com.apeter0.store.product.service;

import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.category.exception.CategoryNotExistsException;
import com.apeter0.store.category.repository.CategoryRepository;
import com.apeter0.store.city.exception.CityNotExistsException;
import com.apeter0.store.city.repository.CityRepository;
import com.apeter0.store.image.exception.ImageNotExistException;
import com.apeter0.store.image.repository.ImageRepository;
import com.apeter0.store.product.api.request.ProductRequest;
import com.apeter0.store.product.api.request.ProductSearchRequest;
import com.apeter0.store.product.exception.ProductNotExistsException;
import com.apeter0.store.product.model.ProductDoc;
import com.apeter0.store.product.repository.ProductRepository;
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
public class ProductApiService {

    private final ProductRepository productRepository;
    private final MongoTemplate mongoTemplate;
    private final CityRepository cityRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;

    public SearchResponse<ProductDoc> search(ProductSearchRequest request) {

        Criteria nameCriteria = new Criteria();
        if (request.getQuery() != null && !request.getQuery().isEmpty())
            nameCriteria = nameCriteria.orOperator(
                    Criteria.where("name").regex(request.getQuery(), "i")
            );

        Criteria cityCriteria = new Criteria();
        if (request.getCityId() != null)
            cityCriteria = cityCriteria.orOperator(
                    Criteria.where("cityId").is(request.getCityId())
            );

        Criteria categoryCriteria = new Criteria();
        if (request.getCategoryId() != null)
            categoryCriteria = categoryCriteria.orOperator(
                    Criteria.where("categoryId").is(request.getCategoryId())
            );

        Query query = new Query(new Criteria().andOperator(nameCriteria, cityCriteria, categoryCriteria));

        Long count = mongoTemplate.count(query, ProductDoc.class);

        query.limit(request.getSize());
        query.skip(request.getSkip());

        List<ProductDoc> productDocs = mongoTemplate.find(query, ProductDoc.class);
        return SearchResponse.of(count, productDocs);
    }

    public Optional<ProductDoc> findById(ObjectId id) {
        return productRepository.findById(id);
    }

    public ProductDoc create(ProductRequest productRequest) throws CityNotExistsException, CategoryNotExistsException, ImageNotExistException {

        for (var cityImage : productRequest.getImages()) {
            if (cityRepository.findById(cityImage.getCityId()).isEmpty())
                throw new CityNotExistsException();
            else if (imageRepository.findById(cityImage.getImageId()).isEmpty())
                throw new ImageNotExistException();
        }

        for (var cityImage : productRequest.getPrices()) {
            if (cityRepository.findById(cityImage.getCityId()).isEmpty())
                throw new CityNotExistsException();
        }

        if (categoryRepository.findById(productRequest.getCategoryId()).isEmpty())
            throw new CategoryNotExistsException();

        if (imageRepository.findById(productRequest.getDefaultImageId()).isEmpty())
            throw new ImageNotExistException();

        ProductDoc productDoc = ProductDoc.builder()
                .name(productRequest.getName())
                .categoryId(productRequest.getCategoryId())
                .description(productRequest.getDescription())
                .defaultPrice(productRequest.getDefaultPrice())
                .prices(productRequest.getPrices())
                .proteins(productRequest.getProteins())
                .fats(productRequest.getFats())
                .carbohydrates(productRequest.getCarbohydrates())
                .calories(productRequest.getCalories())
                .defaultImageId(productRequest.getDefaultImageId())
                .images(productRequest.getImages())
                .build();

        return productRepository.save(productDoc);
    }

    public ProductDoc update(ProductRequest productRequest) throws ProductNotExistsException, CityNotExistsException, CategoryNotExistsException, ImageNotExistException {

        val productDocOptional = productRepository.findById(productRequest.getId());

        if (productDocOptional.isEmpty())
            throw new ProductNotExistsException();

        var productDoc = productDocOptional.get();

        productDoc.setName(productRequest.getName());
        productDoc.setCategoryId(productRequest.getCategoryId());
        productDoc.setDescription(productRequest.getDescription());
        productDoc.setDefaultPrice(productRequest.getDefaultPrice());
        productDoc.setPrices(productRequest.getPrices());
        productDoc.setProteins(productRequest.getProteins());
        productDoc.setFats(productRequest.getFats());
        productDoc.setCarbohydrates(productRequest.getCarbohydrates());
        productDoc.setCalories(productRequest.getCalories());
        productDoc.setDefaultImageId(productRequest.getDefaultImageId());
        productDoc.setImages(productRequest.getImages());

        for (var cityImage : productDoc.getImages()) {
            if (cityRepository.findById(cityImage.getCityId()).isEmpty())
                throw new CityNotExistsException();
            else if (imageRepository.findById(cityImage.getImageId()).isEmpty())
                throw new ImageNotExistException();
        }

        for (var cityImage : productDoc.getPrices()) {
            if (cityRepository.findById(cityImage.getCityId()).isEmpty())
                throw new CityNotExistsException();
        }

        if (categoryRepository.findById(productDoc.getCategoryId()).isEmpty())
            throw new CategoryNotExistsException();

        if (imageRepository.findById(productDoc.getDefaultImageId()).isEmpty())
            throw new ImageNotExistException();

        return productRepository.save(productDoc);
    }

    public void delete(ObjectId id) {
        productRepository.deleteById(id);
    }
}

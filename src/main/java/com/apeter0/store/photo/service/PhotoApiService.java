package com.apeter0.store.photo.service;

import com.apeter0.store.base.api.request.SearchRequest;
import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.photo.model.PhotoDoc;
import com.apeter0.store.photo.repository.PhotoRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhotoApiService {
    private final PhotoRepository photoRepository;
    private final MongoTemplate mongoTemplate;

    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations gridFsOperations;

    public SearchResponse<PhotoDoc> search(SearchRequest request) {

        Query query = new Query();
        Long count = mongoTemplate.count(query, PhotoDoc.class);

        query.limit(request.getSize());
        query.skip(request.getSkip());

        List<PhotoDoc> photoDocs = mongoTemplate.find(query, PhotoDoc.class);
        return SearchResponse.of(count, photoDocs);
    }

    public PhotoDoc create(MultipartFile file) throws IOException {

        DBObject metaData = new BasicDBObject();
        metaData.put("type", file.getContentType());
        metaData.put("title", file.getOriginalFilename());

        ObjectId id = gridFsTemplate.store(
                file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metaData
        );

        PhotoDoc photoDoc = PhotoDoc.builder()
                .id(id)
                .contentType(file.getContentType())
                .build();

        photoRepository.save(photoDoc);
        return photoDoc;
    }

    public Optional<PhotoDoc> findById(ObjectId id) {
        return photoRepository.findById(id);
    }

    public InputStream downloadById(ObjectId id) throws ChangeSetPersister.NotFoundException, IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(
                Criteria.where("_id").is(id)
        ));
        if (file == null) throw new ChangeSetPersister.NotFoundException();
        return gridFsOperations.getResource(file).getInputStream();
    }

    public void deleteById(ObjectId id) {

        gridFsTemplate.delete(new Query(
                Criteria.where("_id").is(id)
        ));
        photoRepository.deleteById(id);
    }
}

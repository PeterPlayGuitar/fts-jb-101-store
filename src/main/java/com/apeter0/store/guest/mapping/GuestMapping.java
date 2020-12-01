package com.apeter0.store.guest.mapping;

import com.apeter0.store.base.api.response.SearchResponse;
import com.apeter0.store.base.mapping.BaseMapping;
import com.apeter0.store.guest.api.response.GuestResponse;
import com.apeter0.store.guest.model.GuestDoc;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class GuestMapping {

    public static class DocToResponseMapper extends BaseMapping<GuestDoc, GuestResponse> {

        @Override
        public GuestResponse convert(GuestDoc guestDoc) {
            return GuestResponse.builder()
                    .id(guestDoc.getId().toString())
                    .firstName(guestDoc.getFirstName())
                    .phoneNumber(guestDoc.getPhoneNumber())
                    .cityId(guestDoc.getCityId().toString())
                    .streetId(guestDoc.getStreetId().toString())
                    .house(guestDoc.getHouse())
                    .apartment(guestDoc.getApartment())
                    .entrance(guestDoc.getEntrance())
                    .floor(guestDoc.getFloor())
                    .build();
        }
    }

    public static class DocsListToResponseListMapper extends BaseMapping<SearchResponse<GuestDoc>, SearchResponse<GuestResponse>> {

        private DocToResponseMapper docToResponseMapper = new DocToResponseMapper();

        @Override
        public SearchResponse<GuestResponse> convert(SearchResponse<GuestDoc> guestDocs) {
            return SearchResponse.of(guestDocs.getCount(), guestDocs.getItems().stream().map(docToResponseMapper::convert).collect(Collectors.toList()));
        }
    }

    private final DocToResponseMapper docToResponseMapper = new DocToResponseMapper();
    private final DocsListToResponseListMapper docsListToResponseListMapper = new DocsListToResponseListMapper();

    public static GuestMapping getInstance() {
        return new GuestMapping();
    }
}

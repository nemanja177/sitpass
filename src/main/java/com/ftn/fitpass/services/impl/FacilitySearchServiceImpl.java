package com.ftn.fitpass.services.impl;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;

import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import com.ftn.fitpass.DTO.FacilityDocument;
import com.ftn.fitpass.DTO.FacilitySearchRequest;
import com.ftn.fitpass.services.FacilitySearchService;

@Service
public class FacilitySearchServiceImpl implements FacilitySearchService {
	
	private final ElasticsearchOperations elasticsearchOperations;

    public FacilitySearchServiceImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public SearchPage<FacilityDocument> searchFacilities(FacilitySearchRequest request, Pageable pageable) {
        Query query = BoolQuery.of(b -> {
            if (request.getName() != null && !request.getName().isEmpty()) {
                b.must(m -> m.match(t -> t.field("name").query(request.getName())));
            }

            if (request.getDescription() != null && !request.getDescription().isEmpty()) {
                b.must(m -> m.match(t -> t.field("description").query(request.getDescription())));
            }

            if (request.getPdfText() != null && !request.getPdfText().isEmpty()) {
                b.must(m -> m.match(t -> t.field("pdfDescriptionText").query(request.getPdfText())));
            }

            if (request.getCities() != null && !request.getCities().isEmpty()) {
                b.filter(f -> f.terms(t -> t.field("city.keyword").terms(terms ->
                        terms.value(request.getCities()
                                .stream()
                                .map(FieldValue::of)
                                .collect(Collectors.toList())
                        )))
                );
            }

            if (request.getDisciplines() != null && !request.getDisciplines().isEmpty()) {
                b.filter(f -> f.terms(t -> t.field("disciplines.keyword").terms(terms ->
                        terms.value(request.getDisciplines()
                                .stream()
                                .map(FieldValue::of)
                                .collect(Collectors.toList())
                        )))
                );
            }

            if (request.getMinReviewCount() != null || request.getMaxReviewCount() != null) {
                int min = request.getMinReviewCount() != null ? request.getMinReviewCount() : Integer.MIN_VALUE;
                int max = request.getMaxReviewCount() != null ? request.getMaxReviewCount() : Integer.MAX_VALUE;

                b.filter(f -> f.range(r -> r.field("reviewCount").gte(JsonData.of(min)).lte(JsonData.of(max))));
            }

            if (request.getMinAvgRating() != null || request.getMaxAvgRating() != null) {
                double min = request.getMinAvgRating() != null ? request.getMinAvgRating() : Double.MIN_VALUE;
                double max = request.getMaxAvgRating() != null ? request.getMaxAvgRating() : Double.MAX_VALUE;

                b.filter(f -> f.range(r -> r.field("avgEquipmentGrade").gte(JsonData.of(min)).lte(JsonData.of(max))));
            }

            return b;
        })._toQuery();

        NativeQuery nativeQuery = new NativeQueryBuilder()
                .withQuery(query)
                .withPageable(pageable)
                .build();

        SearchHits<FacilityDocument> searchHits = elasticsearchOperations.search(nativeQuery, FacilityDocument.class);

        return SearchHitSupport.searchPageFor(searchHits, pageable);
    }
}

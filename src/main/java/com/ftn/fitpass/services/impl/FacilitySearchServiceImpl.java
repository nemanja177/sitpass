package com.ftn.fitpass.services.impl;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
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
    	System.out.println("Upit za ES: " + request.toString());
        Query query = BoolQuery.of(b -> {
        	
        	if (request.getName() != null && !request.getName().isEmpty()) {
                switch (request.getNameSearchType()) {
                    case "phrase":
                        b.must(m -> m.matchPhrase(mp -> mp.field("name").query(request.getName())));
                        break;
                    case "prefix":
                        b.must(m -> m.prefix(p -> p.field("name").value(request.getName())));
                        break;
                    case "fuzzy":
                        b.must(m -> m.match(mb -> mb.field("name").fuzziness("AUTO").query(request.getName())));
                        break;
                    default:
                        b.must(m -> m.match(mb -> mb.field("name").query(request.getName())));
                        break;
                }
            }

            if (request.getDescription() != null && !request.getDescription().isEmpty()) {
                switch (request.getDescriptionSearchType()) {
                    case "phrase":
                        b.must(m -> m.matchPhrase(mp -> mp.field("description").query(request.getDescription())));
                        break;
                    case "prefix":
                        b.must(m -> m.prefix(p -> p.field("description").value(request.getDescription())));
                        break;
                    case "fuzzy":
                        b.must(m -> m.match(mb -> mb.field("description").fuzziness("AUTO").query(request.getDescription())));
                        break;
                    default:
                        b.must(m -> m.match(mb -> mb.field("description").query(request.getDescription())));
                        break;
                }
            }

            if (request.getPdfText() != null && !request.getPdfText().isEmpty()) {
                switch (request.getPdfTextSearchType()) {
                    case "phrase":
                        b.must(m -> m.matchPhrase(mp -> mp.field("pdfDescriptionText").query(request.getPdfText())));
                        break;
                    case "prefix":
                        b.must(m -> m.prefix(p -> p.field("pdfDescriptionText").value(request.getPdfText())));
                        break;
                    case "fuzzy":
                        b.must(m -> m.match(mb -> mb.field("pdfDescriptionText").fuzziness("AUTO").query(request.getPdfText())));
                        break;
                    default:
                        b.must(m -> m.match(mb -> mb.field("pdfDescriptionText").query(request.getPdfText())));
                        break;
                }
            }
        	
        	
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
                
                Double minDouble = (double) min;
                Double maxDouble = (double) max;
                
                b.filter(f -> f.range(r -> r .number(n -> n 
                        .field("reviewCount") // OVDE SE POSTAVLJA POLJE!
                        .gte(minDouble) // direktno prosleđujemo int/Integer
                        .lte(maxDouble)
                    )
                ));
                
//                b.filter(f -> f.range(r -> r
//                        .field("reviewCount") // Očekujete da je ovo deo RangeQuery.Builder-a
//                        .gte(JsonData.of(min))
//                        .lte(JsonData.of(max))
//                	)
//                );
//                
//                b.filter(f -> f.range(r -> r.field("reviewCount").gte(JsonData.of(min)).lte(JsonData.of(max))));
            }

            if (request.getMinAvgRating() != null || request.getMaxAvgRating() != null) {
                double min = request.getMinAvgRating() != null ? request.getMinAvgRating() : Double.MIN_VALUE;
                double max = request.getMaxAvgRating() != null ? request.getMaxAvgRating() : Double.MAX_VALUE;

                b.filter(f -> f.range(r -> r .number(n -> n 
                        .field("avgEquipmentGrade") // OVDE SE POSTAVLJA POLJE!
                        .gte(min) // direktno prosleđujemo int/Integer
                        .lte(max)
                    )
                ));
                
//                b.filter(f -> f.range(r -> r.field("avgEquipmentGrade").gte(JsonData.of(min)).lte(JsonData.of(max))));
            }
            
            return b;
        })._toQuery();
        
//        System.out.println("Upit za ES: " + query._toQuery().toString());
        
        NativeQueryBuilder builder = new NativeQueryBuilder()
                .withQuery(query)
                .withPageable(pageable);

        if (request.getSortField() != null && !request.getSortField().isEmpty()) {
            SortOrder order = "desc".equalsIgnoreCase(request.getSortOrder()) ? SortOrder.Desc : SortOrder.Asc;
            builder.withSort(s -> s
                .field(f -> f
                    .field(request.getSortField())
                    .order(order)
                ));
        }

        NativeQuery nativeQuery = builder.build();

        SearchHits<FacilityDocument> searchHits = elasticsearchOperations.search(nativeQuery, FacilityDocument.class);
        return SearchHitSupport.searchPageFor(searchHits, pageable);
    }
}

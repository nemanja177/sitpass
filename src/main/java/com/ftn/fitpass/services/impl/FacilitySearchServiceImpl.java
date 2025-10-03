package com.ftn.fitpass.services.impl;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ftn.fitpass.DTO.AdvancedSearchRequest;
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
    public SearchPage<FacilityDocument> basicSearch(List<String> keywords, Pageable pageable) {
        Query query = null;
        if (keywords == null || keywords.isEmpty()) {
            query = MatchAllQuery.of(m -> m)._toQuery();
        } else {
            query = BoolQuery.of(b -> b.must(mb -> mb.bool(bq -> {
                for (String token : keywords) {
                    bq.should(sb -> sb.match(m -> m.field("name").query(token)));
                    bq.should(sb -> sb.match(m -> m.field("description").query(token)));
                    bq.should(sb -> sb.match(m -> m.field("pdfDescriptionText").query(token)));
                }
                return bq;
            })))._toQuery();
        }

        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(query)
                .withPageable(pageable)
                .build();

        SearchHits<FacilityDocument> searchHits = elasticsearchOperations.search(nativeQuery, FacilityDocument.class);

        return SearchHitSupport.searchPageFor(searchHits, pageable);
    }

    
    @Override
    public SearchPage<FacilityDocument> advancedSearch(AdvancedSearchRequest request, Pageable pageable) {
        Query query = BoolQuery.of(b -> {

            List<Query> textQueries = new ArrayList<>();

            if (request.getName() != null && !request.getName().isEmpty()) {
                Query nameQuery = switch (request.getNameSearchType()) {
                    case "phrase" -> Query.of(m -> m.matchPhrase(mp -> mp.field("name").query(request.getName())));
                    case "prefix" -> Query.of(m -> m.prefix(p -> p.field("name").value(request.getName())));
                    case "fuzzy" -> Query.of(m -> m.match(mb -> mb.field("name").fuzziness("AUTO").query(request.getName())));
                    default -> Query.of(m -> m.match(mb -> mb.field("name").query(request.getName())));
                };
                textQueries.add(nameQuery);
            }

            if (request.getDescription() != null && !request.getDescription().isEmpty()) {
                Query descriptionQuery = switch (request.getDescriptionSearchType()) {
                    case "phrase" -> Query.of(m -> m.matchPhrase(mp -> mp.field("description").query(request.getDescription())));
                    case "prefix" -> Query.of(m -> m.prefix(p -> p.field("description").value(request.getDescription())));
                    case "fuzzy" -> Query.of(m -> m.match(mb -> mb.field("description").fuzziness("AUTO").query(request.getDescription())));
                    default -> Query.of(m -> m.match(mb -> mb.field("description").query(request.getDescription())));
                };
                textQueries.add(descriptionQuery);
            }

            if (request.getPdfText() != null && !request.getPdfText().isEmpty()) {
                Query pdfQuery = switch (request.getPdfTextSearchType()) {
                    case "phrase" -> Query.of(m -> m.matchPhrase(mp -> mp.field("pdfDescriptionText").query(request.getPdfText())));
                    case "prefix" -> Query.of(m -> m.prefix(p -> p.field("pdfDescriptionText").value(request.getPdfText())));
                    case "fuzzy" -> Query.of(m -> m.match(mb -> mb.field("pdfDescriptionText").fuzziness("AUTO").query(request.getPdfText())));
                    default -> Query.of(m -> m.match(mb -> mb.field("pdfDescriptionText").query(request.getPdfText())));
                };
                textQueries.add(pdfQuery);
            }
            
            if (request.getMinReviewCount() != null || request.getMaxReviewCount() != null) {
                int min = request.getMinReviewCount() != null ? request.getMinReviewCount() : Integer.MIN_VALUE;
                int max = request.getMaxReviewCount() != null ? request.getMaxReviewCount() : Integer.MAX_VALUE;
                Double minD = (double) min;
                Double maxD = (double) max;
                
                // Kreiranje range upita
                Query reviewCountQuery = Query.of(f -> f.range(r -> r.number(n -> n.field("reviewCount").gte(minD).lte(maxD))));
                textQueries.add(reviewCountQuery); // DODATO U LISTU
            }

            // 5. Prikupljanje upita za AVG RATING (range query)
            if (request.getAvgGradeCategory() != null) {
                String field = switch (request.getAvgGradeCategory()) {
                    case "equipment" -> "avgEquipmentGrade";
                    case "staff" -> "avgStaffGrade";
                    case "hygiene" -> "avgHygieneGrade";
                    case "space" -> "avgSpaceGrade";
                    default -> null;
                };
                if (field != null && (request.getMinAvgRating() != null || request.getMaxAvgRating() != null)) {
                    double min = request.getMinAvgRating() != null ? request.getMinAvgRating() : 0.0;
                    double max = request.getMaxAvgRating() != null ? request.getMaxAvgRating() : 5.0;

                    // Kreiranje range upita
                    Query avgRatingQuery = Query.of(f -> f.range(r -> r.number(n -> n.field(field).gte(min).lte(max))));
                    textQueries.add(avgRatingQuery); // DODATO U LISTU
                }
            }
            
            if (!textQueries.isEmpty()) {
                
                // Podrazumevani operator je AND
                String operator = request.getTextSearchOperator() != null ? request.getTextSearchOperator().toUpperCase() : "AND";
                System.out.println("REQUEST CEO: " + request.toString());
                System.out.println("OPERATOR CEO: " + operator);
                if ("OR".equals(operator)) {
                    Query orQuery = BoolQuery.of(innerB -> {
                        textQueries.forEach(innerB::should);
                        // Dodaj min_should_match ako zelis, npr. innerB.minimumShouldMatch("1")
                        return innerB; 
                    })._toQuery();
                    
                    b.must(orQuery);
                } else { 
                    textQueries.forEach(b::must);
                }
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
            
//            if (request.getMinReviewCount() != null || request.getMaxReviewCount() != null) {
//                int min = request.getMinReviewCount() != null ? request.getMinReviewCount() : Integer.MIN_VALUE;
//                int max = request.getMaxReviewCount() != null ? request.getMaxReviewCount() : Integer.MAX_VALUE;
//                Double minD = (double) min;
//                Double maxD = (double) max;
//                b.filter(f -> f.range(r -> r.number(n -> n.field("reviewCount").gte(minD).lte(maxD))));
//            }
//
//            if (request.getAvgGradeCategory() != null) {
//                String field = switch (request.getAvgGradeCategory()) {
//                    case "equipment" -> "avgEquipmentGrade";
//                    case "staff" -> "avgStaffGrade";
//                    case "hygiene" -> "avgHygieneGrade";
//                    case "space" -> "avgSpaceGrade";
//                    default -> null;
//                };
//                if (field != null && (request.getMinAvgRating() != null || request.getMaxAvgRating() != null)) {
//                    double min = request.getMinAvgRating() != null ? request.getMinAvgRating() : 0.0;
//                    double max = request.getMaxAvgRating() != null ? request.getMaxAvgRating() : 5.0;
//                    b.filter(f -> f.range(r -> r.number(n -> n.field(field).gte(min).lte(max))));
//                }
//            }

            return b;
        })._toQuery();

        NativeQueryBuilder builder = new NativeQueryBuilder()
              .withQuery(query)
              .withPageable(pageable);

        if (request.getSortField() != null && !request.getSortField().isEmpty()) {        
            SortOrder order = "desc".equalsIgnoreCase(request.getSortOrder()) ? SortOrder.Desc : SortOrder.Asc;
            String originalField = request.getSortField();
            String finalFieldToSort; 
            if ("name".equalsIgnoreCase(originalField)) {
                finalFieldToSort = originalField + ".keyword";
            } else {
                finalFieldToSort = originalField;
            }
            
            builder.withSort(s -> s.field(f -> f.field(finalFieldToSort).order(order)));
        }
        
//        if (request.getSortField() != null && !request.getSortField().isEmpty()) {
//            SortOrder order = "desc".equalsIgnoreCase(request.getSortOrder()) ? SortOrder.Desc : SortOrder.Asc;
//            builder.withSort(s -> s.field(f -> f.field(request.getSortField()).order(order)));
//        }

        NativeQuery nativeQuery = builder.build();
        SearchHits<FacilityDocument> searchHits = elasticsearchOperations.search(nativeQuery, FacilityDocument.class);

        return SearchHitSupport.searchPageFor(searchHits, pageable);
    }
    
//    @Override
//    public SearchPage<FacilityDocument> advancedSearch(AdvancedSearchRequest request, Pageable pageable) {
//        Query query = BoolQuery.of(b -> {
//
//            if (request.getName() != null && !request.getName().isEmpty()) {
//                switch (request.getNameSearchType()) {
//                    case "phrase":
//                        b.must(m -> m.matchPhrase(mp -> mp.field("name").query(request.getName())));
//                        break;
//                    case "prefix":
//                        b.must(m -> m.prefix(p -> p.field("name").value(request.getName())));
//                        break;
//                    case "fuzzy":
//                        b.must(m -> m.match(mb -> mb.field("name").fuzziness("AUTO").query(request.getName())));
//                        break;
//                    default:
//                        b.must(m -> m.match(mb -> mb.field("name").query(request.getName())));
//                        break;
//                }
//            }
//
//            if (request.getDescription() != null && !request.getDescription().isEmpty()) {
//                switch (request.getDescriptionSearchType()) {
//                    case "phrase":
//                        b.must(m -> m.matchPhrase(mp -> mp.field("description").query(request.getDescription())));
//                        break;
//                    case "prefix":
//                        b.must(m -> m.prefix(p -> p.field("description").value(request.getDescription())));
//                        break;
//                    case "fuzzy":
//                        b.must(m -> m.match(mb -> mb.field("description").fuzziness("AUTO").query(request.getDescription())));
//                        break;
//                    default:
//                        b.must(m -> m.match(mb -> mb.field("description").query(request.getDescription())));
//                        break;
//                }
//            }
//
//            if (request.getPdfText() != null && !request.getPdfText().isEmpty()) {
//                switch (request.getPdfTextSearchType()) {
//                    case "phrase":
//                        b.must(m -> m.matchPhrase(mp -> mp.field("pdfDescriptionText").query(request.getPdfText())));
//                        break;
//                    case "prefix":
//                        b.must(m -> m.prefix(p -> p.field("pdfDescriptionText").value(request.getPdfText())));
//                        break;
//                    case "fuzzy":
//                        b.must(m -> m.match(mb -> mb.field("pdfDescriptionText").fuzziness("AUTO").query(request.getPdfText())));
//                        break;
//                    default:
//                        b.must(m -> m.match(mb -> mb.field("pdfDescriptionText").query(request.getPdfText())));
//                        break;
//                }
//            }
//
//            if (request.getCities() != null && !request.getCities().isEmpty()) {
//                b.filter(f -> f.terms(t -> t.field("city.keyword").terms(terms ->
//                        terms.value(request.getCities()
//                                .stream()
//                                .map(FieldValue::of)
//                                .collect(Collectors.toList())
//                        )))
//                );
//            }
//
//            if (request.getDisciplines() != null && !request.getDisciplines().isEmpty()) {
//                b.filter(f -> f.terms(t -> t.field("disciplines.keyword").terms(terms ->
//                        terms.value(request.getDisciplines()
//                                .stream()
//                                .map(FieldValue::of)
//                                .collect(Collectors.toList())
//                        )))
//                );
//            }
//
//            if (request.getMinReviewCount() != null || request.getMaxReviewCount() != null) {
//            	System.out.println("REQUEST CEO ZA REVIEW COUNT: " + request.toString());
//                int min = request.getMinReviewCount() != null ? request.getMinReviewCount() : Integer.MIN_VALUE;
//                int max = request.getMaxReviewCount() != null ? request.getMaxReviewCount() : Integer.MAX_VALUE;
//                Double minD = (double) min;
//                Double maxD = (double) max;
//
//                b.filter(f -> f.range(r -> r.number(n -> n.field("reviewCount").gte(minD).lte(maxD))));
//            }
//
//            if (request.getAvgGradeCategory() != null) {
//            	System.out.println("REQUEST CATEGORY: " + request.getAvgGradeCategory());
//                String field = switch (request.getAvgGradeCategory()) {
//                    case "equipment" -> "avgEquipmentGrade";
//                    case "staff" -> "avgStaffGrade";
//                    case "hygiene" -> "avgHygieneGrade";
//                    case "space" -> "avgSpaceGrade";
//                    default -> null;
//                };
//                System.out.println("REQUEST CEO: " + request.toString());
//                System.out.println("FIELD: " + field);
//                if (field != null && (request.getMinAvgRating() != null || request.getMaxAvgRating() != null)) {
//                    double min = request.getMinAvgRating() != null ? request.getMinAvgRating() : 0.0;
//                    double max = request.getMaxAvgRating() != null ? request.getMaxAvgRating() : 5.0;
//                    b.filter(f -> f.range(r -> r.number(n -> n.field(field).gte(min).lte(max))));
//                }
//                
//                
//            }
//            
////            if (request.getMinAvgRating() != null || request.getMaxAvgRating() != null) {
////                double min = request.getMinAvgRating() != null ? request.getMinAvgRating() : Double.MIN_VALUE;
////                double max = request.getMaxAvgRating() != null ? request.getMaxAvgRating() : Double.MAX_VALUE;
////
////                b.filter(f -> f.range(r -> r.number(n -> n.field("avgEquipmentGrade").gte(min).lte(max))));
////            }
//
////            if (request.getSortField() != null && !request.getSortField().isEmpty()) {
////                SortOrder order = "desc".equalsIgnoreCase(request.getSortOrder()) ? SortOrder.Desc : SortOrder.Asc;
////                b.withSort(s -> s.field(f -> f.field(request.getSortField()).order(order)));
////            }
//
//            return b;
//        })._toQuery();
//
//        NativeQueryBuilder builder = new NativeQueryBuilder()
//              .withQuery(query)
//              .withPageable(pageable);
//
//      if (request.getSortField() != null && !request.getSortField().isEmpty()) {
//          SortOrder order = "desc".equalsIgnoreCase(request.getSortOrder()) ? SortOrder.Desc : SortOrder.Asc;
//          builder.withSort(s -> s.field(f -> f.field(request.getSortField()).order(order)));
//      }
//
//      NativeQuery nativeQuery = builder.build();
//
//        SearchHits<FacilityDocument> searchHits = elasticsearchOperations.search(nativeQuery, FacilityDocument.class);
//
//        return SearchHitSupport.searchPageFor(searchHits, pageable);
//    }
    
    
    
    
//    @Override
//    public SearchPage<FacilityDocument> searchFacilities(FacilitySearchRequest request, Pageable pageable) {
//        System.out.println("Upit za ES: " + request.toString());
//
//        Query query = BoolQuery.of(b -> {
//
//            // Zadrži postojeću detaljnu pretragu po poljima sa različitim tipovima
//            if (request.getName() != null && !request.getName().isEmpty()) {
//                switch (request.getNameSearchType()) {
//                    case "phrase":
//                        b.must(m -> m.matchPhrase(mp -> mp.field("name").query(request.getName())));
//                        break;
//                    case "prefix":
//                        b.must(m -> m.prefix(p -> p.field("name").value(request.getName())));
//                        break;
//                    case "fuzzy":
//                        b.must(m -> m.match(mb -> mb.field("name").fuzziness("AUTO").query(request.getName())));
//                        break;
//                    default:
//                        b.must(m -> m.match(mb -> mb.field("name").query(request.getName())));
//                        break;
//                }
//            }
//
//            // Isto za description
//            if (request.getDescription() != null && !request.getDescription().isEmpty()) {
//                switch (request.getDescriptionSearchType()) {
//                    case "phrase":
//                        b.must(m -> m.matchPhrase(mp -> mp.field("description").query(request.getDescription())));
//                        break;
//                    case "prefix":
//                        b.must(m -> m.prefix(p -> p.field("description").value(request.getDescription())));
//                        break;
//                    case "fuzzy":
//                        b.must(m -> m.match(mb -> mb.field("description").fuzziness("AUTO").query(request.getDescription())));
//                        break;
//                    default:
//                        b.must(m -> m.match(mb -> mb.field("description").query(request.getDescription())));
//                        break;
//                }
//            }
//
//            // Isto za pdfDescriptionText
//            if (request.getPdfText() != null && !request.getPdfText().isEmpty()) {
//                switch (request.getPdfTextSearchType()) {
//                    case "phrase":
//                        b.must(m -> m.matchPhrase(mp -> mp.field("pdfDescriptionText").query(request.getPdfText())));
//                        break;
//                    case "prefix":
//                        b.must(m -> m.prefix(p -> p.field("pdfDescriptionText").value(request.getPdfText())));
//                        break;
//                    case "fuzzy":
//                        b.must(m -> m.match(mb -> mb.field("pdfDescriptionText").fuzziness("AUTO").query(request.getPdfText())));
//                        break;
//                    default:
//                        b.must(m -> m.match(mb -> mb.field("pdfDescriptionText").query(request.getPdfText())));
//                        break;
//                }
//            }
//
//            // Dodatno: fulltext pretraga po listi ključnih reči (ako ih ima)
//            if (request.getKeywords() != null && !request.getKeywords().isEmpty()) {
//                b.must(mb -> mb.bool(bq -> {
//                    for (String token : request.getKeywords()) {
//                        bq.should(sb -> sb.match(m -> m.field("name").query(token).fuzziness("AUTO")));
//                        bq.should(sb -> sb.match(m -> m.field("description").query(token).fuzziness("AUTO")));
//                        bq.should(sb -> sb.match(m -> m.field("pdfDescriptionText").query(token).fuzziness("AUTO")));
//                    }
//                    return bq;
//                }));
//            }
//
//            // Ostali filteri kao do sada
//            if (request.getCities() != null && !request.getCities().isEmpty()) {
//                b.filter(f -> f.terms(t -> t.field("city.keyword").terms(terms ->
//                    terms.value(request.getCities()
//                        .stream()
//                        .map(FieldValue::of)
//                        .collect(Collectors.toList())
//                    )))
//                );
//            }
//
//            if (request.getDisciplines() != null && !request.getDisciplines().isEmpty()) {
//                b.filter(f -> f.terms(t -> t.field("disciplines.keyword").terms(terms ->
//                    terms.value(request.getDisciplines()
//                        .stream()
//                        .map(FieldValue::of)
//                        .collect(Collectors.toList())
//                    )))
//                );
//            }
//
//            if (request.getMinReviewCount() != null || request.getMaxReviewCount() != null) {
//                int min = request.getMinReviewCount() != null ? request.getMinReviewCount() : Integer.MIN_VALUE;
//                int max = request.getMaxReviewCount() != null ? request.getMaxReviewCount() : Integer.MAX_VALUE;
//                Double minD = (double) min;
//                Double maxD = (double) max;
//
//                b.filter(f -> f.range(r -> r.number(n -> n.field("reviewCount").gte(minD).lte(maxD))));
//            }
//
//            if (request.getMinAvgRating() != null || request.getMaxAvgRating() != null) {
//                double min = request.getMinAvgRating() != null ? request.getMinAvgRating() : Double.MIN_VALUE;
//                double max = request.getMaxAvgRating() != null ? request.getMaxAvgRating() : Double.MAX_VALUE;
//
//                b.filter(f -> f.range(r -> r.number(n -> n.field("avgEquipmentGrade").gte(min).lte(max))));
//            }
//
//            return b;
//        })._toQuery();
//
//        NativeQueryBuilder builder = new NativeQueryBuilder()
//                .withQuery(query)
//                .withPageable(pageable);
//
//        if (request.getSortField() != null && !request.getSortField().isEmpty()) {
//            SortOrder order = "desc".equalsIgnoreCase(request.getSortOrder()) ? SortOrder.Desc : SortOrder.Asc;
//            builder.withSort(s -> s.field(f -> f.field(request.getSortField()).order(order)));
//        }
//
//        NativeQuery nativeQuery = builder.build();
//
//        SearchHits<FacilityDocument> searchHits = elasticsearchOperations.search(nativeQuery, FacilityDocument.class);
//        return SearchHitSupport.searchPageFor(searchHits, pageable);
//    }
}

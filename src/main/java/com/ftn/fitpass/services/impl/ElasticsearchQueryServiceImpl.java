package com.ftn.fitpass.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ftn.fitpass.model.Objekat;
import com.ftn.fitpass.services.ElasticsearchQueryService;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;

@Service
public class ElasticsearchQueryServiceImpl implements ElasticsearchQueryService {

	private ElasticsearchClient elasticsearchClient;

    private static final String INDEX_NAME = "sportski_objekti";

    public List<Objekat> searchCombined(
            String textQuery,
            Integer minReview, Integer maxReview,
            Double minOcena, Double maxOcena
    ) throws IOException {
        List<Query> mustQueries = new ArrayList<>();

        if (textQuery != null && !textQuery.isBlank()) {
            mustQueries.add(Query.of(q -> q
                    .multiMatch(m -> m
                            .fields("naziv", "opis", "pdfContent")
                            .query(textQuery)
                    )
            ));
        }

        // range za brojReview
        if (minReview != null || maxReview != null) {
            mustQueries.add(Query.of(q -> q
                .range(r -> r
                    .field("brojReviewa")
                    .gte(minReview != null ? JsonData.of(minReview) : null)
                    .lte(maxReview != null ? JsonData.of(maxReview) : null)
                )
            ));
        }

        if (minOcena != null || maxOcena != null) {
            mustQueries.add(Query.of(q -> q
                .range(r -> r
                    .field("prosecnaOcena")
                    .gte(minOcena != null ? JsonData.of(minOcena) : null)
                    .lte(maxOcena != null ? JsonData.of(maxOcena) : null)
                )
            ));
        }

        SearchRequest request = SearchRequest.of(s -> s
                .index(INDEX_NAME)
                .query(q -> q
                        .bool(b -> b.must(mustQueries))
                )
        );

        SearchResponse<Objekat> response =
                elasticsearchClient.search(request, Objekat.class);

        List<Objekat> results = new ArrayList<>();
        for (Hit<Objekat> hit : response.hits().hits()) {
            results.add(hit.source());
        }
        return results;
    }
    
    public List<Objekat> searchWithSpecialQuery(String query) throws IOException {
        Query esQuery;

        if (query.startsWith("~")) {
            String term = query.substring(1);
            esQuery = Query.of(q -> q
                    .fuzzy(f -> f
                            .field("opis") 
                            .value(term)
                            .fuzziness("AUTO")
                    )
            );
        } else if (query.endsWith("*")) {
            String term = query.substring(0, query.length() - 1);
            esQuery = Query.of(q -> q
                    .prefix(p -> p
                            .field("naziv")
                            .value(term)
                    )
            );
        } else if (query.startsWith("\"") && query.endsWith("\"")) {
            String phrase = query.substring(1, query.length() - 1);
            esQuery = Query.of(q -> q
                    .matchPhrase(mp -> mp
                            .field("opis")
                            .query(phrase)
                    )
            );
        } else {
            esQuery = Query.of(q -> q
                    .multiMatch(m -> m
                            .fields("naziv", "opis", "pdfContent")
                            .query(query)
                    )
            );
        }

        SearchRequest request = SearchRequest.of(s -> s
                .index(INDEX_NAME)
                .query(esQuery)
        );

        SearchResponse<Objekat> response =
                elasticsearchClient.search(request, Objekat.class);

        List<Objekat> results = new ArrayList<>();
        for (Hit<Objekat> hit : response.hits().hits()) {
            results.add(hit.source());
        }
        return results;
    }
   

}

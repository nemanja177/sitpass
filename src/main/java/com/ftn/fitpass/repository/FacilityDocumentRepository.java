package com.ftn.fitpass.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.ftn.fitpass.DTO.FacilityDocument;

@Repository
public interface FacilityDocumentRepository extends ElasticsearchRepository<FacilityDocument, String> {

}

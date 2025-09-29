package com.ftn.fitpass.services.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ftn.fitpass.model.Objekat;
import com.ftn.fitpass.repository.ObjekatRepository;
import com.ftn.fitpass.services.ObjekatService;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;

@Service
public class ObjekatServiceImpl implements ObjekatService{

	private ObjekatRepository objekatRepository;
    private ElasticsearchClient elasticsearchClient;

    public Objekat save(Objekat objekat) throws IOException {
        Objekat saved = objekatRepository.save(objekat);

        IndexRequest<Objekat> request = IndexRequest.of(i -> i
                .index("objekti")
                .id(saved.getId().toString())
                .document(saved)
        );
        IndexResponse response = elasticsearchClient.index(request);

        System.out.println("Indeksiran objekat u ES, id = " + response.id());

        return saved;
    }

    @Override
    public List<Objekat> findAll() {
        return objekatRepository.findAll();
    }

    @Override
    public Objekat findById(Long id) {
        return objekatRepository.findById(id).orElse(null);
    }


}

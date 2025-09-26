package com.ftn.fitpass.services.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ftn.fitpass.model.SportskiObjekat;
import com.ftn.fitpass.services.SportskiObjekatService;

import co.elastic.clients.elasticsearch.core.IndexRequest;

@Service
public class SportskiObjekatServiceImpl implements SportskiObjekatService{

	private final RestHighLevelClient esClient;
    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    public SportskiObjekatServiceImpl(RestHighLevelClient esClient, MinioClient minioClient) {
        this.esClient = esClient;
        this.minioClient = minioClient;
    }

    // upload PDF i slika u MinIO
    public String uploadFile(MultipartFile file, String folder) throws Exception {
        String fileName = folder + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );

        return fileName;
    }

    public String parsePdf(MultipartFile file) throws IOException {
        PDDocument document = PDDocument.load(file.getInputStream());
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        document.close();
        return text;
    }

    public void indexSportskiObjekat(SportskiObjekat objekat) throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("naziv", objekat.getNaziv());
        jsonMap.put("opis", objekat.getOpis());
        jsonMap.put("brojReviewa", objekat.getBrojReviewa());
        jsonMap.put("prosecnaOcena", objekat.getProsecnaOcena());
        jsonMap.put("kategorija", objekat.getKategorija());
        jsonMap.put("pdfText", objekat.getPdfText());

        IndexRequest<TDocument> request = new IndexRequest("objekti")
                .id(objekat.getId().toString())
                .source(jsonMap);

        esClient.index(request, RequestOptions.DEFAULT);
    }



}

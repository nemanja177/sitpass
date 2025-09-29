package com.ftn.fitpass.DTO;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "facility")
public class FacilityDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String city;

    @Field(type = FieldType.Text)
    private String address;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Text)
    private String pdfDescriptionText; 

    @Field(type = FieldType.Keyword)
    private List<String> disciplines;

    @Field(type = FieldType.Keyword)
    private List<String> imageFilenames;

    @Field(type = FieldType.Integer)
    private int reviewCount;

    @Field(type = FieldType.Double)
    private double avgEquipmentGrade;

    @Field(type = FieldType.Double)
    private double avgStaffGrade;

    @Field(type = FieldType.Double)
    private double avgHygieneGrade;

    @Field(type = FieldType.Double)
    private double avgSpaceGrade;
}
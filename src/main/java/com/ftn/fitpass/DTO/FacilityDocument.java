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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPdfDescriptionText() {
		return pdfDescriptionText;
	}

	public void setPdfDescriptionText(String pdfDescriptionText) {
		this.pdfDescriptionText = pdfDescriptionText;
	}

	public List<String> getDisciplines() {
		return disciplines;
	}

	public void setDisciplines(List<String> disciplines) {
		this.disciplines = disciplines;
	}

	public List<String> getImageFilenames() {
		return imageFilenames;
	}

	public void setImageFilenames(List<String> imageFilenames) {
		this.imageFilenames = imageFilenames;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}

	public double getAvgEquipmentGrade() {
		return avgEquipmentGrade;
	}

	public void setAvgEquipmentGrade(double avgEquipmentGrade) {
		this.avgEquipmentGrade = avgEquipmentGrade;
	}

	public double getAvgStaffGrade() {
		return avgStaffGrade;
	}

	public void setAvgStaffGrade(double avgStaffGrade) {
		this.avgStaffGrade = avgStaffGrade;
	}

	public double getAvgHygieneGrade() {
		return avgHygieneGrade;
	}

	public void setAvgHygieneGrade(double avgHygieneGrade) {
		this.avgHygieneGrade = avgHygieneGrade;
	}

	public double getAvgSpaceGrade() {
		return avgSpaceGrade;
	}

	public void setAvgSpaceGrade(double avgSpaceGrade) {
		this.avgSpaceGrade = avgSpaceGrade;
	}
    
    
}
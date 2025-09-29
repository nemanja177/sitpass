package com.ftn.fitpass.DTO;

import java.util.List;

public class FacilitySearchRequest {
    private String name;        
    private String description; 
    private String pdfText;     
    private List<String> cities;      
    private List<String> disciplines; 
    private Integer minReviewCount;   
    private Integer maxReviewCount;   
    private Double minAvgRating;      
    private Double maxAvgRating;      

    private String sortField;         
    private String sortOrder;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPdfText() {
		return pdfText;
	}
	public void setPdfText(String pdfText) {
		this.pdfText = pdfText;
	}
	public List<String> getCities() {
		return cities;
	}
	public void setCities(List<String> cities) {
		this.cities = cities;
	}
	public List<String> getDisciplines() {
		return disciplines;
	}
	public void setDisciplines(List<String> disciplines) {
		this.disciplines = disciplines;
	}
	public Integer getMinReviewCount() {
		return minReviewCount;
	}
	public void setMinReviewCount(Integer minReviewCount) {
		this.minReviewCount = minReviewCount;
	}
	public Integer getMaxReviewCount() {
		return maxReviewCount;
	}
	public void setMaxReviewCount(Integer maxReviewCount) {
		this.maxReviewCount = maxReviewCount;
	}
	public Double getMinAvgRating() {
		return minAvgRating;
	}
	public void setMinAvgRating(Double minAvgRating) {
		this.minAvgRating = minAvgRating;
	}
	public Double getMaxAvgRating() {
		return maxAvgRating;
	}
	public void setMaxAvgRating(Double maxAvgRating) {
		this.maxAvgRating = maxAvgRating;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
}

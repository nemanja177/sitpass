package com.ftn.fitpass.DTO;

import java.util.List;

public class FacilityCreateRequest {
	
    private String name;
    private String city;
    private String address;
    private String description;
    private List<String> disciplines;
    
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
	
	public List<String> getDisciplines() {
		return disciplines;
	}
	
	public void setDisciplines(List<String> disciplines) {
		this.disciplines = disciplines;
	}

	@Override
	public String toString() {
		return "FacilityCreateRequest [name=" + name + ", city=" + city + ", address=" + address + ", description="
				+ description + ", disciplines=" + disciplines + "]";
	}
}

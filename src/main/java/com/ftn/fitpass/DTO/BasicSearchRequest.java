package com.ftn.fitpass.DTO;

import java.util.List;

public class BasicSearchRequest {
    private List<String> keywords;

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	@Override
	public String toString() {
		return "BasicSearchRequest [keywords=" + keywords + "]";
	}
	
}

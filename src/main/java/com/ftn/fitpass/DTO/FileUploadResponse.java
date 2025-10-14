package com.ftn.fitpass.DTO;


public class FileUploadResponse {
	
    private String fileName;
    private String originalName;
    private String url;
    private Long size;
    private String contentType; 
        
	public FileUploadResponse() {
	}
	
	public FileUploadResponse(String fileName, String originalName, String url, Long size, String contentType) {
		super();
		this.fileName = fileName;
		this.originalName = originalName;
		this.url = url;
		this.size = size;
		this.contentType = contentType;
	}

	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getOriginalName() {
		return originalName;
	}
	
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Long getSize() {
		return size;
	}
	
	public void setSize(Long size) {
		this.size = size;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@Override
	public String toString() {
		return "FileUploadResponse [fileName=" + fileName + ", originalName=" + originalName + ", url=" + url
				+ ", size=" + size + ", contentType=" + contentType + "]";
	}
    
}

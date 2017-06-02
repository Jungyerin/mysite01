package com.jx372.mysite.vo;

public class AdminVo {
	private String title;
	private String message;
	private String file;
	private String description;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "AdminVo [title=" + title + ", message=" + message + ", file=" + file + ", description=" + description
				+ "]";
	}

	
	
	
}

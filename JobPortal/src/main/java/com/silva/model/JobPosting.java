package com.silva.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class JobPosting {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	private String title;
	private String description;
	
	public JobPosting() {
		
	}
	
	public JobPosting(Long id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

}
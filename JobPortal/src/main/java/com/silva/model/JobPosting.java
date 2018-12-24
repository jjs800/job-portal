package com.silva.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "job")
public class JobPosting {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	@Column(name = "employer_id")	
	private int employerId;
	
	@Column(name = "employer_name")	
	private String employerName;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	public JobPosting() {
		
	}
	
	public JobPosting(Long id, int employerId, String employerName, String title, String description) {
		this.id = id;
		this.employerId = employerId;
		this.employerName = employerName;
		this.title = title;
		this.description = description;
	}

	public Long getId() {
		return id;
	}
	
	public int getEmployerId() {
		return employerId;
	}
	
	public String getEmployerName() {
		return employerName;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setEmployerId(int employerId) {
		this.employerId = employerId;
	}
	
	public void setEmployerName(String employerName) {
		this.employerName = employerName;
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
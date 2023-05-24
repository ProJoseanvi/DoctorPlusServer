package com.doctorplus.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class RecipeRequest {
	
	protected String id;
	protected Integer patientId;
	protected String date;
	protected String med;
	protected String takes;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getPatientId() {
		return patientId;
	}
	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMed() {
		return med;
	}
	public void setMed(String med) {
		this.med = med;
	}
	public String getTakes() {
		return takes;
	}
	public void setTakes(String takes) {
		this.takes = takes;
	}
	
	

}

package com.doctorplus.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class ResponseRecipe {

	protected String id;
	protected String patientName;
	protected String date;
	protected String med;
	protected String takes;
	protected Integer state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("patient_name")
	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@JsonProperty("medication_name")
	public String getMed() {
		return med;
	}

	public void setMed(String med) {
		this.med = med;
	}

	@JsonProperty("number_takes")
	public String getTakes() {
		return takes;
	}

	public void setTakes(String takes) {
		this.takes = takes;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public Integer getState() {
		return this.state;
	}

}

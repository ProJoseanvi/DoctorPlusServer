package com.doctorplus.controller;

public class RecipeSearchRequest {
	
	private String id;
	private String date;
	private Integer patientId;

	public String getId() {
		return id;
		}
	public String getDate() {
		return date;
		}
	public Integer getPatientId() {
		return patientId;
		}
	public void setId(String id) {
		this.id = id;
		}
	public void setDate(String date) {
		this.date = date;
		}
	public void patientId(Integer patientId) {
		this.patientId = patientId;
		}
	
	
}

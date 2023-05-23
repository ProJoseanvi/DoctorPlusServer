package com.doctorplus.dto;

import com.doctorplus.controller.RecipeRequest;

public class Recipe extends RecipeRequest{
	
	private String userId;
	
	public Recipe(String userId, RecipeRequest r) {
		this.userId = userId;
		this.id = r.getId();
		this.patientId = r.getPatientId();
		this.date = r.getDate();
		this.med = r.getMed();
		this.takes = r.getTakes();
		this.med = r.getMed();
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}

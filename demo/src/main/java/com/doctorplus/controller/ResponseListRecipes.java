package com.doctorplus.controller;

import java.util.List;

import com.doctorplus.dto.Recipe;

public class ResponseListRecipes {
	
	private List<Recipe> recipes;

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}
	
	

}

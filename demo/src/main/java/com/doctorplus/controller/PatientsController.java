package com.doctorplus.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.doctorplus.dao.PatientsDao;
import com.doctorplus.dao.RecipesDao;
import com.doctorplus.dto.Patient;

//Este es el método pricipal que controla el login y autenticación del usuario

@RestController
public class PatientsController {
	
	private static final Logger logger = LogManager.getLogger(RecipeController.class);
	
	@Autowired
	private PatientsDao patientsDao;
	
	@Autowired
	private RecipesDao recipesDao;
		
	@GetMapping("/patient/list")
    public ListPatientsResponse list(@RequestHeader (name="Authorization") String bearerToken) {
		ListPatientsResponse response = new ListPatientsResponse();
		String token = bearerToken.substring(7);
    	logger.info("user token: " + token);
    	List<Patient> patients = patientsDao.list();
    	response.setPatients(patients);
        
        return response;
    }
	
	@PostMapping("/patient/listByRecipe")
    public ListPatientsResponse listByRecipe(@RequestHeader (name="Authorization") String bearerToken, @RequestBody RecipeRequest recipeRequest) {
		ListPatientsResponse response = new ListPatientsResponse();
		String token = bearerToken.substring(7);
    	logger.info("user token: " + token);
    	List<Patient> patients = recipesDao.listPatients(recipeRequest);
    	response.setPatients(patients);
        
        return response;
    }
	
}

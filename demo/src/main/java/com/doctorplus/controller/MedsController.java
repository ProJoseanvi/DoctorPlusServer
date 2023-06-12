package com.doctorplus.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.doctorplus.dao.MedsDao;
import com.doctorplus.dto.Med;

//Este es el método principal que controla el login y autenticación del usuario

@RestController
public class MedsController {
	
	private static final Logger logger = LogManager.getLogger(RecipeController.class);
	
	@Autowired
	private MedsDao medsDao;
		
	@GetMapping("/meds/list")
    public ListMedsResponse list(@RequestHeader (name="Authorization") String bearerToken) {
		ListMedsResponse response = new ListMedsResponse();
		String token = bearerToken.substring(7);
    	logger.info("user token: " + token);
    	
    	List<Med> meds = medsDao.list();
    	response.setMeds(meds);
    	
        
        return response;
    }
	
}

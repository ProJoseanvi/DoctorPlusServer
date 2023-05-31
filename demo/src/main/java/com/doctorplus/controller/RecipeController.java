package com.doctorplus.controller;

import java.util.Calendar;
import java.util.List;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;


import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.doctorplus.dao.RecipesDao;
import com.doctorplus.dao.UsersDao;
import com.doctorplus.dto.User;
import com.doctorplus.dto.Recipe;
import com.doctorplus.service.InMemoryUsers;
import com.doctorplus.service.JwtTokenService;
import com.doctorplus.service.JwtUserDetailsService;

//Este es el método pricipal que controla el login y autenticación del usuario

@RestController
public class RecipeController {
	
	private static final Logger logger = LogManager.getLogger(RecipeController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenService jwtTokenService;
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private RecipesDao recipesDao;;
		
	@GetMapping("/recipe/id")
    public RecetaIdResponse recetaId(@RequestHeader (name="Authorization") String bearerToken) {
		RecetaIdResponse response = new RecetaIdResponse();
		String token = bearerToken.substring(7);
    	logger.info("user token: " + token);
    	
    	String id = usersDao.getIdByToken(token);
        if (!id.isEmpty()) {
	    	Calendar cal = Calendar.getInstance();
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); 
	    	StringBuilder sb = new StringBuilder(sdf.format(cal.getTime()))
	    								.append(id);
	
	    	response.setId(sb.toString());
        }else {
        	response.setId("");
        }
        
        return response;
    }
	
	@PostMapping("/recipe/create")
	public ResponseEntity<ResponseAction> crearReceta(@RequestHeader (name="Authorization") String bearerToken, @RequestBody RecipeRequest recipeRequest) {
	    //ResponseD
		String token = bearerToken.substring(7);
		String id = usersDao.getIdByToken(token);
		Recipe recipe = new Recipe(id, recipeRequest);
		boolean result = recipesDao.create(recipe);
		if (result) {
			return ResponseEntity.ok(new ResponseAction("ok", "Receta creada correctamente."));
		}else {
			return ResponseEntity.ok(new ResponseAction("error", "Error al crear la receta."));
		}
	
	}
	
	@PostMapping("/recipe/ids")
	public ResponseListRecipes getListIds(@RequestHeader (name="Authorization") String bearerToken, @RequestBody RecipeRequest recipeRequest) {
		ResponseListRecipes result = new ResponseListRecipes();
		String token = bearerToken.substring(7);
		String idUser = usersDao.getIdByToken(token); 
		List<Recipe> recipes = recipesDao.list(recipeRequest, idUser);
		result.setRecipes(recipes);
		return result;
	}
	
	@PostMapping("/recipe/dates")
	public ResponseListRecipes getListDates(@RequestHeader (name="Authorization") String bearerToken, @RequestBody RecipeRequest recipeRequest) {
		ResponseListRecipes result = new ResponseListRecipes();
		String token = bearerToken.substring(7);
		String idUser = usersDao.getIdByToken(token); 
		List<Recipe> recipes = recipesDao.listDates(recipeRequest, idUser);
		result.setRecipes(recipes);
		return result;
	}
	
	@PostMapping("/recipe/get")
	public ResponseRecipe get(@RequestHeader (name="Authorization") String bearerToken, @RequestBody RecipeRequest recipeRequest) {
		ResponseRecipe result = new ResponseRecipe();
		String token = bearerToken.substring(7);
		String idUser = usersDao.getIdByToken(token); 
		result = recipesDao.get(recipeRequest, idUser);
		
		return result;
	}
	
	@DeleteMapping("/recipe/delete/{idRecipe}")
	public ResponseEntity<ResponseAction> get(@RequestHeader (name="Authorization") String bearerToken, @PathVariable  String idRecipe) {
		String token = bearerToken.substring(7);
		String idUser = usersDao.getIdByToken(token); 
		
		//boolean result = recipesDao.delete(idUser, idRecipe);
		boolean result = true;
		if (result) {
			return ResponseEntity.ok(new ResponseAction("ok", "Receta borrada correctamente: " + idRecipe));
		}else {
			return ResponseEntity.ok(new ResponseAction("error", "Error al borrar la receta."));
		}
	}
	
	//@PutMapping("/recipe/change")
	//public ResponseEntity<ResponseAction> change(@RequestHeader (name="Authorization") String bearerToken, @RequestBody RecipeRequest recipeRequest) {
	@PutMapping("/recipe/change/{idRecipe}/{state}")
	public ResponseEntity<ResponseAction> change(@RequestHeader (name="Authorization") String bearerToken, @PathVariable String idRecipe, @PathVariable Integer state) {
		String token = bearerToken.substring(7);
		String idUser = usersDao.getIdByToken(token); 
		
		//boolean result = recipesDao.changeState(recipeRequest.getState(), idRecipe);
		boolean result = true;
		if (result) {
			return ResponseEntity.ok(new ResponseAction("ok", "Receta cambiada de estado correctamente: " + idRecipe + " " + state));
		}else {
			return ResponseEntity.ok(new ResponseAction("error", "Error al cambiar de estado la receta."));
		}
	}


	private void getListRecipes(RecipeRequest recipeRequest) {
		// TODO Auto-generated method stub
		
	}
	
	
}

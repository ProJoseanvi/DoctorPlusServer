package com.doctorplus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.doctorplus.controller.LoginController;

// Aquí codoficamos el DAO de la clase medicamentos, en este caso un GET para obtener mediante la conexión a la API lo que queremos de nuestra BD


import com.doctorplus.dto.Medicamento;
import com.doctorplus.dto.Recipe;
import com.doctorplus.dto.User;

@Repository
public class RecipesDao extends ConnectionDao{ 
	
	private static final Logger logger = LogManager.getLogger(RecipesDao.class);

	public boolean create(Recipe recipe) {
		boolean result = false;
	    try (Connection conn = this.getConnection();
	         PreparedStatement ps = create(conn, recipe)){
	    	int rows = ps.executeUpdate(); 
	    	result = rows == 1; 
	    } catch (Exception e) {
	    	logger.error(e);
	    }
	    return result;
		
	} 
	
	private PreparedStatement create(Connection con, Recipe recipe) throws SQLException {
	    String sql = "INSERT INTO Recetas ( "
                + " Receta_id, Paciente_id, Tomas_diarias, Fecha_receta, Usuario_id, Nombre_medicamento " 
                + ") VALUES ("
                + "?,?,?,?,?,?"
                + ");";
	    PreparedStatement ps = con.prepareStatement(sql);
	    int i = 1;
	    ps.setString(i++, recipe.getId());
	    ps.setInt(i++, recipe.getPatientId());
	    ps.setString(i++, recipe.getTakes());
	    ps.setString(i++, recipe.getDate());
	    ps.setString(i++, recipe.getUserId());
	    ps.setString(i++, recipe.getMed());
	    return ps;
	}

}

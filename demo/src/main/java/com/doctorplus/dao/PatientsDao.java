package com.doctorplus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.doctorplus.dto.Patient;

@Repository
public class PatientsDao extends ConnectionDao{ 
		
	private static final Logger logger = LogManager.getLogger(UsersDao.class);	

	public List<Patient> list() {
		List<Patient> result = new ArrayList<>();
		
		try (Connection conn = this.getConnection();
	         PreparedStatement ps = list(conn); 
	         ResultSet rs = ps.executeQuery()){
	    	while (rs.next()) {
	    		Patient patient = new Patient();
	    		patient.setId(rs.getInt("Paciente_id"));
	    		patient.setName(rs.getString("Nombre_paciente"));
	        	result.add(patient);
	        }
	    } catch (Exception e) {
	    	logger.error(e);
	    }
		
		return result;
	}
	
	private PreparedStatement list(Connection con) throws SQLException {
	    String sql = "SELECT Paciente_id, Nombre_paciente FROM Pacientes";
	    PreparedStatement ps = con.prepareStatement(sql);
	    return ps;
	}
}

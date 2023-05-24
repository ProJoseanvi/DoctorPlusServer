package com.doctorplus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.doctorplus.dto.Id;

public class IdsDao {
	private static final Logger logger = LogManager.getLogger(UsersDao.class);	

	public List<Id> list() {
		List<Id> result = new ArrayList<>();
		
		try (Connection conn = this.getConnection();
	         PreparedStatement ps = list(conn); 
	         ResultSet rs = ps.executeQuery()){
	    	while (rs.next()) {
	    		Id id = new Id();
	    		id.setName(rs.getString("Receta_id"));
	        	result.add(id);
	        }
	    } catch (Exception e) {
	    	logger.error(e);
	    }
		
		return result;
	}
	
	private Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	private PreparedStatement list(Connection con) throws SQLException {
	    String sql = "SELECT Receta_id FROM Recetas";
	    PreparedStatement ps = con.prepareStatement(sql);
	    return ps;
	}

}

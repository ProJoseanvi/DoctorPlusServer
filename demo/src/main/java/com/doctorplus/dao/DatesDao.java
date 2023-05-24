package com.doctorplus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.doctorplus.dto.Date;

	@Repository
	public class DatesDao extends ConnectionDao{ 
			
		private static final Logger logger = LogManager.getLogger(UsersDao.class);	

		public List<Date> list() {
			List<Date> result = new ArrayList<>();
			
			try (Connection conn = this.getConnection();
		         PreparedStatement ps = list(conn); 
		         ResultSet rs = ps.executeQuery()){
		    	while (rs.next()) {
		    		Date date = new Date();
		    		date.setDate(rs.getString("Nombre_medicamento"));
		        	result.add(date);
		        }
		    } catch (Exception e) {
		    	logger.error(e);
		    }
			
			return result;
		}
		
		private PreparedStatement list(Connection con) throws SQLException {
		    String sql = "SELECT Fecha_receta FROM Recetas";
		    PreparedStatement ps = con.prepareStatement(sql);
		    return ps;
		}

}

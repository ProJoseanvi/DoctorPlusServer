package com.doctorplus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.doctorplus.dto.User;

@Repository
public class UsersDao extends ConnectionDao{ 
	
	private static final Logger logger = LogManager.getLogger(UsersDao.class);
		
	public User getById(String code) {
		User result = new User();
	    try (Connection conn = this.getConnection();
	         PreparedStatement ps = get(conn, code); 
	         ResultSet rs = ps.executeQuery()){
	    	if (rs.next()) {            	
            	result.setId(String.valueOf(rs.getInt("Usuario_id")));
            	result.setUsername(rs.getString("Nombre_usuario"));
            	result.setPassword(rs.getString("Password"));
            	result.setRole(rs.getString("Rol_usuario"));
            }

	    } catch (Exception e) {
	    	logger.error(e);
	    }
	    return result;
	}

	private PreparedStatement get (Connection con, String code) throws SQLException {
	    String sql = "SELECT Usuario_id, Nombre_usuario, Password, Rol_usuario "
				+ "   FROM Usuarios WHERE Usuario_id = ? ;";
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setString(1,code);
	    return ps;
	}
	
	public static void main(String[] args) {  
        UsersDao dao = new UsersDao(); 
        User user = dao.getById("1234");
        		System.out.println(user.getUsername());
    }

	public boolean saveToken(String accessToken, String id) {
		boolean result = false;
	    try (Connection conn = this.getConnection();
	         PreparedStatement ps = saveToken(conn, accessToken, id)){
	    	int rows = ps.executeUpdate(); 
	    	result = rows == 1; 
	    } catch (Exception e) {
	    	logger.error(e);
	    }
	    return result;
		
	} 
	
	private PreparedStatement saveToken(Connection con, String accessToken, String id) throws SQLException {
	    String sql = "UPDATE Usuarios SET token = ?, Last_login = strftime('%Y-%m-%d %H:%M:%S') "
				+ "   WHERE Usuario_id = ? ;";
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setString(1, accessToken);
	    ps.setString(2, id);
	    return ps;
	}

	public String getIdByToken(String token) {
		String result = "";
	    try (Connection conn = this.getConnection();
	         PreparedStatement ps = getIdByToken(conn, token); 
	         ResultSet rs = ps.executeQuery()){
	    	if (rs.next()) {            	
            	result = (String.valueOf(rs.getInt("Usuario_id")));
            }
	    } catch (Exception e) {
	    	logger.error(e);
	    }
	    return result;
	}
	
	private PreparedStatement getIdByToken(Connection con, String token) throws SQLException {
	    String sql = "SELECT Usuario_id "
				+ "   FROM Usuarios WHERE token = ? ;";
	    PreparedStatement ps = con.prepareStatement(sql);
	    ps.setString(1,token);
	    return ps;
	}
	
}

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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.doctorplus.controller.LoginController;
import com.doctorplus.controller.RecipeRequest;
import com.doctorplus.controller.RecipeSearchRequest;
import com.doctorplus.controller.ResponseRecipe;
import com.doctorplus.dto.Med;

// Aquí codoficamos el DAO de la clase medicamentos, en este caso un GET para obtener mediante la conexión a la API lo que queremos de nuestra BD

import com.doctorplus.dto.Medicamento;
import com.doctorplus.dto.Patient;
import com.doctorplus.dto.Recipe;
import com.doctorplus.dto.User;

@Repository
public class RecipesDao extends ConnectionDao {

	private static final Logger logger = LogManager.getLogger(RecipesDao.class);

	public boolean create(Recipe recipe) {
		boolean result = false;
		try (Connection conn = this.getConnection(); PreparedStatement ps = create(conn, recipe)) {
			int rows = ps.executeUpdate();
			result = rows == 1;
		} catch (Exception e) {
			logger.error(e);
		}
		return result;

	}

	private PreparedStatement create(Connection con, Recipe recipe) throws SQLException {
		String sql = "INSERT INTO Recetas ( "
				+ " Receta_id, Paciente_id, Tomas_diarias, Fecha_receta, Usuario_id, Nombre_medicamento " + ") VALUES ("
				+ "?,?,?,?,?,?" + ");";
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

	public List<Recipe> list(RecipeRequest recipeRequest) {
		List<Recipe> result = new ArrayList<>();

		try (Connection conn = this.getConnection();
				PreparedStatement ps = list(conn, recipeRequest);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Recipe rec = new Recipe();
				rec.setId(rs.getString("Receta_id"));
				result.add(rec);
			}
		} catch (Exception e) {
			logger.error(e);
		}

		return result;
	}

	private PreparedStatement list(Connection con, RecipeRequest recipeRequest) throws SQLException {
		String sql = "SELECT Receta_id " + "FROM Recetas " + "WHERE 1=1 ";
		if (StringUtils.hasLength(recipeRequest.getId())) {
			sql += "AND Receta_id = ?";
		}
		if (StringUtils.hasLength(recipeRequest.getDate())) {
			sql += "AND Fecha_receta = ?";
		}
		if (recipeRequest.getPatientId() != null) {
			sql += "AND Paciente_id = ?";
		}
		int i = 1;
		PreparedStatement ps = con.prepareStatement(sql);
		if (StringUtils.hasLength(recipeRequest.getId())) {
			ps.setString(i++, recipeRequest.getId());
		}
		if (StringUtils.hasLength(recipeRequest.getDate())) {
			ps.setString(i++, recipeRequest.getDate());
		}
		if (recipeRequest.getPatientId() != null) {
			ps.setInt(i++, recipeRequest.getPatientId());
		}
		return ps;
	}

	public List<Patient> listPatients(RecipeRequest recipeRequest) {
		List<Patient> result = new ArrayList<>();

		try (Connection conn = this.getConnection();
				PreparedStatement ps = listPatients(conn, recipeRequest);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Patient rec = new Patient();
				rec.setId(rs.getInt("Paciente_id"));
				rec.setName(rs.getString("Nombre_paciente"));
				result.add(rec);
			}
		} catch (Exception e) {
			logger.error(e);
		}

		return result;
	}

	private PreparedStatement listPatients(Connection con, RecipeRequest recipeRequest)
			throws SQLException {
		String sql = "SELECT DISTINCT p.Paciente_id, p.Nombre_paciente "
				+ "FROM Recetas r "
				+ "INNER JOIN Pacientes p ON p.Paciente_id = r.Paciente_id "
				+ "WHERE 1=1 ";
		if (StringUtils.hasLength(recipeRequest.getId())) {
			sql += "AND Receta_id = ? ";
		}
		if (StringUtils.hasLength(recipeRequest.getDate())) {
			sql += "AND Fecha_receta = ? ";
		}
		if (recipeRequest.getPatientId() != null) {
			sql += "AND p.Paciente_id = ? ";
		}
		int i = 1;
		PreparedStatement ps = con.prepareStatement(sql);
		if (StringUtils.hasLength(recipeRequest.getId())) {
			ps.setString(i++, recipeRequest.getId());
		}
		if (StringUtils.hasLength(recipeRequest.getDate())) {
			ps.setString(i++, recipeRequest.getDate());
		}
		if (recipeRequest.getPatientId() != null) {
			ps.setInt(i++, recipeRequest.getPatientId());
		}
		return ps;
	}

	public List<Recipe> listDates(RecipeRequest recipeRequest) {
		List<Recipe> result = new ArrayList<>();

		try (Connection conn = this.getConnection();
				PreparedStatement ps = listDates(conn, recipeRequest);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				Recipe rec = new Recipe();
				rec.setDate(rs.getString("Fecha_receta"));
				result.add(rec);
			}
		} catch (Exception e) {
			logger.error(e);
		}

		return result;
	}

	private PreparedStatement listDates(Connection con, RecipeRequest recipeRequest)
			throws SQLException {
		String sql = "SELECT DISTINCT Fecha_receta " + "FROM Recetas " + "WHERE 1=1 ";
		if (StringUtils.hasLength(recipeRequest.getId())) {
			sql += "AND Receta_id = ?";
		}
		if (recipeRequest.getPatientId() != null) {
			sql += "AND Paciente_id = ?";
		}
		int i = 1;
		PreparedStatement ps = con.prepareStatement(sql);
		if (StringUtils.hasLength(recipeRequest.getId())) {
			ps.setString(i++, recipeRequest.getId());
		}
		if (recipeRequest.getPatientId() != null) {
			ps.setInt(i++, recipeRequest.getPatientId());
		}
		return ps;
	}

	public ResponseRecipe get(RecipeRequest recipeRequest) {
		ResponseRecipe result = new ResponseRecipe();

		try (Connection conn = this.getConnection();
				PreparedStatement ps = get(conn, recipeRequest);
				ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				result.setId(rs.getString("Receta_id"));
				result.setMed(rs.getString("Nombre_medicamento"));
				result.setPatientName(rs.getString("Nombre_paciente"));
				result.setDate(rs.getString("Fecha_receta"));
				result.setTakes(rs.getString("Tomas_diarias"));
				result.setState(rs.getInt("Estado_receta"));
			}
		} catch (Exception e) {
			logger.error(e);
		}

		return result;
	}
	
	private PreparedStatement get(Connection con, RecipeRequest recipeRequest)
			throws SQLException {
		String sql = "select r.Receta_id, r.Paciente_id, r.Tomas_diarias, r.Fecha_receta, r.Estado_receta, r.Nombre_medicamento, p.Nombre_paciente " + 
				" from Recetas r " +
				" inner join Pacientes p on r.Paciente_id = p.Paciente_id " +
				" where r.Receta_id = ? " +
 				" and r.Paciente_id = ? " +
				" and r.Fecha_receta = ?";
		
		int i = 1;
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(i++, recipeRequest.getId());
		ps.setInt(i++, recipeRequest.getPatientId());
		ps.setString(i++, recipeRequest.getDate());

		return ps;
	}

	public boolean delete(String idRecipe) {
		boolean result = false;
		try (Connection conn = this.getConnection(); PreparedStatement ps = delete(conn, idRecipe)) {
			int rows = ps.executeUpdate();
			result = rows == 1;
		} catch (Exception e) {
			logger.error(e);
		}
		return result;
	}
	
	private PreparedStatement delete(Connection con, String recipe) throws SQLException {
		String sql = "DELETE FROM Recetas WHERE Receta_id = ? ";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, recipe);

		return ps;
	}

	public boolean changeState(Integer state, String idRecipe) {
		boolean result = false;
		try (Connection conn = this.getConnection(); PreparedStatement ps = changeState(conn, idRecipe, state)) {
			int rows = ps.executeUpdate();
			result = rows == 1;
		} catch (Exception e) {
			logger.error(e);
		}
		return result;
	}
	
	private PreparedStatement changeState(Connection con, String recipe, Integer state) throws SQLException {
		String sql = "UPDATE Recetas SET Estado_receta = ? WHERE Receta_id = ? ";
		PreparedStatement ps = con.prepareStatement(sql);
		int i = 1;
		ps.setInt(i++, state);
		ps.setString(i++, recipe);

		return ps;
	}

}

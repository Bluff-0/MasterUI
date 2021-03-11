package com.java.Training.Dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.java.Training.model.Movie;
import com.java.Training.model.SakilaPojo;

public interface SakilaDao {
	
	public List<Movie> getData(Integer start, Integer limit, String filter, String sort);
	public int getCount(Integer start, Integer limit, String filter);
	public SakilaPojo includeInPojo() throws SQLException;
	
	public Boolean addData(String title, String description, String release_year, String language,
			String rating, String special_features, String director);
	public Boolean editData(String title, String description, String release_year, String language, 
			String rating, String special_features, String director, int film_id);
	public Boolean softDeleteData(Integer film_id);
	public Boolean deleteData(Integer film_id);
	
}

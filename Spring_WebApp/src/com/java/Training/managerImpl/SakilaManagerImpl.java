package com.java.Training.managerImpl;

import java.util.List;

import com.java.Training.Dao.SakilaDao;
import com.java.Training.DaoImpl.SakilaDaoImpl;
import com.java.Training.manager.SakilaManager;
import com.java.Training.model.Movie;
import com.java.Training.model.SakilaPojo;

public class SakilaManagerImpl implements SakilaManager{
	
	SakilaDao dao;
	// = new SakilaDaoImpl();
	
	// public List<SakilaPojo> getData(Integer start, Integer limit, String filter, String sort) {
	// 	return dao.getData(start, limit, filter, sort);
	// }
	
	public List<Movie> getData(Integer start, Integer limit, String filter, String sort) {
		return dao.getData(start, limit, filter, sort);
	}
	
	public int getCount(Integer start, Integer limit, String filter) {
		return dao.getCount(start, limit, filter);
	}
	
	public Boolean addData(String title, String description, String release_year, String language,
			String rating, String special_features, String director)
	{
		return dao.addData(title, description, release_year, language, rating, special_features, director);
	}
	
	public Boolean editData(String title, String description, String release_year, String language, 
			String rating, String special_features, String director, int film_id)
	{
		return dao.editData(title, description, release_year, language, rating, special_features, director, film_id);
	}
	
	public Boolean deleteData(Integer film_id, Integer soft)
	{
		if(soft == 0) return dao.deleteData(film_id);
		else          return dao.softDeleteData(film_id);
	}
	
	
	public SakilaDao getDao() {
		return dao;
	}
	public void setDao(SakilaDao dao) {
		this.dao = dao;
	}
}

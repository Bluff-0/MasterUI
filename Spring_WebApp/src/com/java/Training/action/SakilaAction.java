package com.java.Training.action;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.java.Training.manager.SakilaManager;
import com.java.Training.managerImpl.SakilaManagerImpl;
import com.java.Training.model.Movie;
import com.java.Training.model.SakilaPojo;

public class SakilaAction {
	
	// PARAMETERS
	private String start;
	private String limit;
	private String sort;
	private String filter;
	
	// RETURN VALUE
	private String result;
	
	// DB Data
	private String qType;
	private String title;
	private String description;
	private String release_year;
	private String language;
	private String rating;
	private String special_features;
	private String director;
	private String soft;
	private String film_id;
	

	private HashMap <String, Object> dataMap= new HashMap <String, Object>();
	
	
	
	public String getData() {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		SakilaManager manager = (SakilaManager) context.getBean("manager");
		
		try {
				
			// ArrayList <SakilaPojo> jArray= (ArrayList<SakilaPojo>) manager.getData(Integer.parseInt(start), Integer.parseInt(limit), filter, sort);
			ArrayList <Movie> movies= (ArrayList<Movie>) manager.getData(Integer.parseInt(start), Integer.parseInt(limit), filter, sort);
			List<Object> temp = new ArrayList();
			for(Movie movie : movies) {
				HashMap m = new HashMap();
				m.put("film_id", movie.getFilm_id());
				m.put("title", movie.getTitle());
				m.put("language", movie.getLanguage().getName());
				m.put("description", movie.getDescription());
				m.put("release_year", movie.getRelease_year());
				m.put("director", movie.getDirector());
				m.put("rating", movie.getRating());
				m.put("special_features", movie.getSpecial_features());
				
				
				temp.add(m);
			}
			Integer count= manager.getCount(Integer.parseInt(start), Integer.parseInt(limit), filter);
			
	    	// dataMap.put("Data", jArray);
			dataMap.put("Data", temp);
	    	dataMap.put("Total", count);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		Gson gson = new Gson();
		setResult(gson.toJson(dataMap));
		return "success";
	}
	
	
	
	public String modifyData()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		SakilaManager manager = (SakilaManager) context.getBean("manager");
		
		try
		{
			switch(getqType())
			{
				case "insert":
					
					manager.addData(title, description, release_year, language, rating, special_features, director);
			    	break;
				case "update":
					
					manager.editData(title, description, release_year, language, rating, special_features, director, Integer.parseInt(film_id));
			    	break;
				case "delete":
					
					manager.deleteData(Integer.parseInt(film_id), Integer.parseInt(soft));
			    	break;
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		setResult("{\"status\" : \"Success\"}");
		return "success";
	}
	
	
	
	public String getqType() {
		return qType;
	}
	public void setqType(String qType) {
		this.qType = qType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRelease_year() {
		return release_year;
	}
	public void setRelease_year(String release_year) {
		this.release_year = release_year;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getSpecial_features() {
		return special_features;
	}
	public void setSpecial_features(String special_features) {
		this.special_features = special_features;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getSoft() {
		return soft;
	}
	public void setSoft(String soft) {
		this.soft = soft;
	}
	public String getFilm_id() {
		return film_id;
	}
	public void setFilm_id(String film_id) {
		this.film_id = film_id;
	}
	
	
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
}

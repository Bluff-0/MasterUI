package com.highradius.training.struts.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.opensymphony.xwork2.Action;

public class SakilaManager{
	
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
	
	private Boolean success;
	private String result;
	private String ErrorMessage;
	private Integer StatusCode;
	
	
	// DB CONNECTOR
	private String url= "jdbc:mysql://127.0.0.1:3306/sakila";
	private String username= "root";
	private String password= "root";
	
	private static final long serialVersionUID= 1L;
	private Connection dbcon= null;
	private PreparedStatement statement= null;
	private String query= null;
	
	
	public String modifyData()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.dbcon= DriverManager.getConnection(url, username, password);
			
			switch(getqType())
			{
				case "insert":
					
					this.query= "INSERT INTO \r\n"
							+ "`film`(`title`,`description`,`release_year`,`language_id`,`rating`,`special_features`,`director`) \r\n"
							+ "VALUES( \r\n"
							+ "	?, ?, ?,\r\n"
							+ "	(SELECT `language_id` FROM `language` WHERE `name`= ?),\r\n"
							+ "	?, ?, ?)";
					
					statement = this.dbcon.prepareStatement(query);
					
					statement.setString(1, getTitle());
					statement.setString(2, getDescription());
					statement.setString(3, getRelease_year());
					statement.setString(4, getLanguage());
					statement.setString(5, getRating());
					statement.setString(6, getSpecial_features());
					statement.setString(7, getDirector());
					
					statement.executeUpdate();
					setResult("{\"status\" : \"Added\"}");
					
			    	break;
				case "update":
					
					this.query= "UPDATE `film`\r\n"
							+ "SET `title`= ?, `description`= ?, `release_year`= ?, \r\n"
							+ "`language_id`= (SELECT `language_id` FROM `language` WHERE `name`= ?),\r\n"
							+ "`rating`= ?, `special_features`= ?, `director`= ?\r\n"
							+ "WHERE `film_id`= ?";
					
					statement = this.dbcon.prepareStatement(query);
					
					statement.setString(1, getTitle());
					statement.setString(2, getDescription());
					statement.setString(3, getRelease_year());
					statement.setString(4, getLanguage());
					statement.setString(5, getRating());
					statement.setString(6, getSpecial_features());
					statement.setString(7, getDirector());
					statement.setString(8, getFilm_id());
					
					statement.executeUpdate();
					setResult("{\"status\" : \"Updated\"}");
					
			    	break;
				case "delete":
					switch(getSoft())
					{
						case "1":
							this.query= "UPDATE `film` SET `isDeleted`=1 WHERE `film_id` IN (?);";
							this.query= this.query.replace("?", getFilm_id());
							
							statement = this.dbcon.prepareStatement(query);
							
					    	statement.executeUpdate();
					    	setResult("{\"status\" : \"Soft Deleted\"}");
					    	
							break;
							
						case "0":
						default:
							
							this.query= "DELETE FROM `film` WHERE `film_id` IN (?)";
							this.query= this.query.replace("?", getFilm_id());
							
							statement = this.dbcon.prepareStatement(query);
							
					    	statement.executeUpdate();
					    	setResult("{\"status\" : \"Deleted\"}");
					    	
							break;
					}
			    	break;
				default:
					setErrorMessage("Operation Failure. Check again");
					setStatusCode(500);
					return "error";
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			
		}
		setSuccess(true);
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
	public String getErrorMessage() {
		return ErrorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		ErrorMessage = errorMessage;
	}
	public Integer getStatusCode() {
		return StatusCode;
	}
	public void setStatusCode(Integer statusCode) {
		StatusCode = statusCode;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
}

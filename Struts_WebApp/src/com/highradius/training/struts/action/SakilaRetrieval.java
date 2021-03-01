package com.highradius.training.struts.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.highradius.training.struts.model.SakilaPojo;

public class SakilaRetrieval {
	
	
	// PARAMETERS
	private String start;
	private String limit;
	private String sort;
	private String filter;
	
	// RETURN VALUE
	private String result;
	
	// DB CONNECTOR
	private Connection dbcon= null;
	private String url= "jdbc:mysql://127.0.0.1:3306/sakila";
	private String username= "root";
	private String password= "root";
	
	// EXECUTION VARIABLES
	private String query= null;
	private PreparedStatement statement;
	private ResultSet rs;
	private SakilaPojo row= null;
	private ArrayList <SakilaPojo> jArray= new ArrayList<SakilaPojo>();
	private HashMap <String, Object> dataMap= new HashMap <String, Object>();
	
	
	public SakilaPojo includeInPojo() throws SQLException
	{
		row= new SakilaPojo();
		row.setFilm_id(rs.getString("film_id"));
		row.setTitle(rs.getString("title"));
		row.setDescription(rs.getString("description"));
		row.setRelease_year(rs.getInt("release_year"));
		row.setLanguage(rs.getString("language"));
		row.setDirector(rs.getString("director"));
		row.setRating(rs.getString("rating"));
		row.setSpecial_features(rs.getString("special_features"));
		return row;
	}
	
	public String getData() {
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.dbcon= DriverManager.getConnection(url, username, password);
			
			this.query= "SELECT `film_id`, `title`, `description`, `release_year`, `name` AS `language`, `director`, `rating`, `special_features`\r\n"
					+ "FROM `film` JOIN `language`\r\n"
					+ "ON `language`.`language_id` = `film`.`language_id`\r\n"
					+ "WHERE `isDeleted` = 0";
			
			this.query= "SELECT * FROM ( " + this.query + " ) AS tbl";
			
			
			
			if(filter != null && !filter.isEmpty())
			{
				JsonArray filter= JsonParser.parseString(this.filter).getAsJsonArray();

				this.query+= " WHERE";
				for (JsonElement data : filter)
				{
					JsonObject e= data.getAsJsonObject();
					this.query+= " "+e.get("property").getAsString()+" LIKE '%"+e.get("value").getAsString()+"%' AND";
				}
				this.query= this.query.substring(0, this.query.length()-3);
			}
			
			
			
			if(sort != null && !sort.isEmpty())
			{
				JsonArray sort= JsonParser.parseString(this.sort).getAsJsonArray();
				
				this.query+= " ORDER BY";
				
				for (JsonElement data : sort)
				{
					JsonObject e= data.getAsJsonObject();
					this.query+= " "+e.get("property").getAsString()+" "+e.get("direction").getAsString()+",";
				}
				this.query= this.query.substring(0, this.query.length()-1);
			}
			else
			{
				this.query+= " ORDER BY `title` ASC";
			}
			
			String masterquery= this.query;
			
			
			if(start != null && !start.isEmpty() && limit != null && !limit.isEmpty())
				this.query+= " LIMIT "+start+", "+limit;
			
			
			
			
			// DEBUG: System.out.println(this.query);
			statement = this.dbcon.prepareStatement(query);
	    	rs = statement.executeQuery();
	    	
	    	while (rs.next())
			{
				jArray.add(includeInPojo());
			}
	    	
	    	dataMap.put("Data", jArray);
	    	rs.close();
	    	
	    	statement = this.dbcon.prepareStatement("SELECT COUNT(*) AS `total` FROM (" + masterquery + ") as `masterquery`");
	    	rs = statement.executeQuery();
	    	
	    	if(rs.next()) dataMap.put("Total", rs.getString("total"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				rs.close();
				statement.close();
				dbcon.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		Gson gson = new Gson();
		setResult(gson.toJson(dataMap));
		return "success";
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

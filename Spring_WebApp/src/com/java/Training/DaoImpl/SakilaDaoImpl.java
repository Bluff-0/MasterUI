package com.java.Training.DaoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.java.Training.Dao.SakilaDao;
import com.java.Training.model.Movie;
import com.java.Training.model.SakilaPojo;

public class SakilaDaoImpl implements SakilaDao{
	
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
	private List <Movie> movies= new ArrayList<Movie>();
	
	//
	private SessionFactory sessionFactory;
	
	
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
	
	public SessionFactory createSessionFactory() {
		try {
			
			Configuration config = new Configuration();
			config.configure();
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
			sessionFactory = config.buildSessionFactory(serviceRegistry);
			
		}catch(Throwable ex) {
			System.err.println("Failed to create sessionFactory object" + ex);
			throw new ExceptionInInitializerError(ex);
		}
		
		return sessionFactory;
	}
	
	@SuppressWarnings("deprecation")
	public List<Movie> getData(Integer start, Integer limit, String filter, String sort) 
	{
		
//		try {
//			
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			this.dbcon= DriverManager.getConnection(url, username, password);
//			
//			this.query= "SELECT `film_id`, `title`, `description`, `release_year`, `name` AS `language`, `director`, `rating`, `special_features`\r\n"
//					+ "FROM `film` JOIN `language`\r\n"
//					+ "ON `language`.`language_id` = `film`.`language_id`\r\n"
//					+ "WHERE `isDeleted` = 0";
//			
//			this.query= "SELECT * FROM ( " + this.query + " ) AS tbl";
//			
//			
//			
//			if(filter != null && !filter.isEmpty())
//			{
//				JsonArray filterJson= JsonParser.parseString(filter).getAsJsonArray();
//
//				this.query+= " WHERE";
//				for (JsonElement data : filterJson)
//				{
//					JsonObject e= data.getAsJsonObject();
//					this.query+= " "+e.get("property").getAsString()+" LIKE '%"+e.get("value").getAsString()+"%' AND";
//				}
//				this.query= this.query.substring(0, this.query.length()-3);
//			}
//			
//			
//			
//			if(sort != null && !sort.isEmpty())
//			{
//				JsonArray sortJson= JsonParser.parseString(sort).getAsJsonArray();
//				
//				this.query+= " ORDER BY";
//				
//				for (JsonElement data : sortJson)
//				{
//					JsonObject e= data.getAsJsonObject();
//					this.query+= " "+e.get("property").getAsString()+" "+e.get("direction").getAsString()+",";
//				}
//				this.query= this.query.substring(0, this.query.length()-1);
//			}
//			else
//			{
//				this.query+= " ORDER BY `title` ASC";
//			}
//			
//			if(start != null && limit != null)
//				this.query+= " LIMIT "+start+", "+limit;
//			
//			
//			
//			
//			// DEBUG: System.out.println(this.query);
//			statement = this.dbcon.prepareStatement(query);
//	    	rs = statement.executeQuery();
//	    	
//	    	while (rs.next())
//			{
//				jArray.add(includeInPojo());
//			}
//	    	
//	    	rs.close();
//	    	
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		finally
//		{
//			try
//			{
//				rs.close();
//				statement.close();
//				dbcon.close();
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//		}
		
		
		Session session = createSessionFactory().openSession();
		Transaction tx = null;

		try {
		   tx = session.beginTransaction();
		   
		   String hql = "FROM Movie m WHERE m.isDeleted = 0";
		   
		   if (filter != null) {
				String filterQuery = " and ";
				JsonParser parser1 = new JsonParser();
				JsonElement json1 = parser1.parse(filter);
				if (json1.isJsonArray()) {
					JsonArray jarr1 = json1.getAsJsonArray();
					int i = 1;
					for (JsonElement je1 : jarr1) {
						JsonObject jsonobj1 = je1.getAsJsonObject();
						if (i == 1) {
							filterQuery += jsonobj1.get("property").getAsString() + " LIKE '%"
									+ jsonobj1.get("value").getAsString() + "%'";
							i++;
						} else {
							filterQuery += " and " + jsonobj1.get("property").getAsString() + " LIKE '%"
									+ jsonobj1.get("value").getAsString() + "%'";
						}

					}
				}
				filterQuery = filterQuery.replaceAll("title", "m.title");
				filterQuery = filterQuery.replaceAll("director", "m.director");
				filterQuery = filterQuery.replaceAll("language", "m.language.name");
				filterQuery = filterQuery.replaceAll("releaseyear", "m.releaseyear");
				hql = hql + filterQuery;

			}
		   
		   if (sort != null) {
				String sortQuery = " ORDER BY ";
				JsonParser parser = new JsonParser();
				JsonElement json = parser.parse(sort);
				if (json.isJsonArray()) {
					JsonArray jarr = json.getAsJsonArray();
					for (JsonElement je : jarr) {
						JsonObject jsonobj = je.getAsJsonObject();
						sortQuery += jsonobj.get("property").getAsString() + " "
								+ jsonobj.get("direction").getAsString();

					}
				}
				
				sortQuery = sortQuery.replaceAll("title", "m.title");
				sortQuery = sortQuery.replaceAll("release_year", "m.release_year");
				sortQuery = sortQuery.replaceAll("language", "m.language.name");
				sortQuery = sortQuery.replaceAll("director", "m.director");
				sortQuery = sortQuery.replaceAll("rating", "m.rating");
				sortQuery = sortQuery.replaceAll("special_features", "m.special_features");
				sortQuery = sortQuery.replaceAll("description", "m.description");
				hql = hql + sortQuery;
			}
		   
		   Query query = session.createQuery(hql);
		   query.setFirstResult(start);
		   query.setMaxResults(limit);
		   
		   movies= query.list();
		   
		   tx.commit();
		}

		catch (Exception e) {
		   if (tx!=null) tx.rollback();
		   e.printStackTrace(); 
		} finally {
		   session.close();
		}
		
		return movies;
	}
	
	public int getCount(Integer start, Integer limit, String filter) 
	{
		int count= 0;
		
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
				JsonArray filterJson= JsonParser.parseString(filter).getAsJsonArray();

				this.query+= " WHERE";
				for (JsonElement data : filterJson)
				{
					JsonObject e= data.getAsJsonObject();
					this.query+= " "+e.get("property").getAsString()+" LIKE '%"+e.get("value").getAsString()+"%' AND";
				}
				this.query= this.query.substring(0, this.query.length()-3);
			}
						
			String masterquery= this.query;
			
			if(start != null && limit != null)
				this.query+= " LIMIT "+start+", "+limit;
			
			statement = this.dbcon.prepareStatement("SELECT COUNT(*) AS `total` FROM (" + masterquery + ") as `masterquery`");
			
	    	rs = statement.executeQuery();
	    	
	    	if(rs.next()) count= Integer.parseInt(rs.getString("total"));
	    	
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
		return count;
	}
	
	public Boolean addData(String title, String description, String release_year, String language, 
			String rating, String special_features, String director) {
		
		int parity= 1;
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.dbcon= DriverManager.getConnection(url, username, password);
			
			this.query= "INSERT INTO \r\n"
					+ "`film`(`title`,`description`,`release_year`,`language_id`,`rating`,`special_features`,`director`) \r\n"
					+ "VALUES( \r\n"
					+ "	?, ?, ?,\r\n"
					+ "	(SELECT `language_id` FROM `language` WHERE `name`= ?),\r\n"
					+ "	?, ?, ?)";
			
			statement = this.dbcon.prepareStatement(query);
			
			statement.setString(1, title);
			statement.setString(2, description);
			statement.setString(3, release_year);
			statement.setString(4, language);
			statement.setString(5, rating);
			statement.setString(6, special_features);
			statement.setString(7, director);
			statement.executeUpdate();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			parity= 0;
		}
		finally
		{
			try 
			{	
				statement.close();
				this.dbcon.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		if (parity== 1) return true; else return false;
	}
	
	public Boolean editData(String title, String description, String release_year, String language, 
			String rating, String special_features, String director, int film_id) {
		
		int parity= 1;
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.dbcon= DriverManager.getConnection(url, username, password);
			
			this.query= "UPDATE `film`\r\n"
					+ "SET `title`= ?, `description`= ?, `release_year`= ?, \r\n"
					+ "`language_id`= (SELECT `language_id` FROM `language` WHERE `name`= ?),\r\n"
					+ "`rating`= ?, `special_features`= ?, `director`= ?\r\n"
					+ "WHERE `film_id`= ?";
			
			statement = this.dbcon.prepareStatement(query);
			
			statement.setString(1, title);
			statement.setString(2, description);
			statement.setString(3, release_year);
			statement.setString(4, language);
			statement.setString(5, rating);
			statement.setString(6, special_features);
			statement.setString(7, director);
			statement.setInt(8, film_id);
			
			statement.executeUpdate();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			parity= 0;
		}
		finally
		{
			try 
			{	
				statement.close();
				this.dbcon.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		if (parity== 1) return true; else return false;
	}
	
	public Boolean softDeleteData(Integer film_id)
	{
		int parity= 1;
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.dbcon= DriverManager.getConnection(url, username, password);
			
			this.query= "UPDATE `film` SET `isDeleted`=1 WHERE `film_id` IN (?);";
			this.query= this.query.replace("?", String.valueOf(film_id));
			
			statement = this.dbcon.prepareStatement(query);
			
	    	statement.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			parity= 0;
		}
		finally
		{
			try 
			{	
				statement.close();
				this.dbcon.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		if (parity== 1) return true; else return false;
	
	}
	
	public Boolean deleteData(Integer film_id)
	{
		int parity= 1;
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.dbcon= DriverManager.getConnection(url, username, password);
			
			this.query= "DELETE FROM `rental` WHERE `inventory_id` IN ( SELECT `inventory_id` FROM `inventory` WHERE film_id IN (?) )";
			this.query= this.query.replace("?", String.valueOf(film_id));
			
			statement = this.dbcon.prepareStatement(query);
			
	    	statement.executeUpdate();
	    	statement.close();
	    	
	    	this.query= "DELETE FROM `inventory` WHERE `film_id` IN (?)";
			this.query= this.query.replace("?", String.valueOf(film_id));
			
			statement = this.dbcon.prepareStatement(query);
			
	    	statement.executeUpdate();
	    	statement.close();

	    	this.query= "DELETE FROM `film_category` WHERE `film_id` IN (?)";
			this.query= this.query.replace("?", String.valueOf(film_id));
			
			statement = this.dbcon.prepareStatement(query);
			
	    	statement.executeUpdate();
	    	statement.close();
	    	
	    	this.query= "DELETE FROM `film_actor` WHERE `film_id` IN (?)";
			this.query= this.query.replace("?", String.valueOf(film_id));
			
			statement = this.dbcon.prepareStatement(query);
			
	    	statement.executeUpdate();
	    	statement.close();
	    	
	    	this.query= "DELETE FROM `film` WHERE `film_id` IN (?)";
			this.query= this.query.replace("?", String.valueOf(film_id));
			
			statement = this.dbcon.prepareStatement(query);
			
	    	statement.executeUpdate();
	    	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			parity= 0;
		}
		finally
		{
			try 
			{	
				statement.close();
				this.dbcon.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		if (parity== 1) return true; else return false;
	
	}

}

package hrc.dbData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hrc.tools.DebugLogger;
import hrc.tools.ReadQuery;
import hrc.tools.SQLConnect;

import com.google.gson.Gson;

@WebServlet("/api/getSakilaData")
public class SakilaRetrieve extends HttpServlet 
{
	private static final long serialVersionUID= 1L;
	Connection dbcon= null;
	PreparedStatement statement= null;
	ResultSet rs= null;
	private String query= null;
	String b_code= null;
	SakilaPojo row= null;
	
	
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
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		SQLConnect sc= new SQLConnect("sakila");
		this.dbcon= sc.newConnection();
		ArrayList <SakilaPojo> jArray= new ArrayList<SakilaPojo>();
		HashMap <String, Object> dataMap= new HashMap <String, Object>();
		
		try
		{
			ReadQuery rq= new ReadQuery("extractSakila.sql");
			this.query= rq.getQuery();
			this.query= rq.addDefinedLimiter(request, this.query);
			statement = this.dbcon.prepareStatement(query);
	    	rs = statement.executeQuery();
	    	DebugLogger.createLog("Data Query executed successfully");
	    	while (rs.next())
			{
				jArray.add(includeInPojo());
			}
	    	
	    	dataMap.put("Data", jArray);
	    	rs.close();
	    	
	    	statement = this.dbcon.prepareStatement(rq.getTotalCalculateQuery());
	    	rs = statement.executeQuery();
	    	DebugLogger.createLog("Total Query executed successfully");
	    	
	    	if(rs.next())
	    		dataMap.put("Total", rs.getString("total"));
	    	
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			response.setContentType("application/json");
			response.getWriter().write(new Gson().toJson(dataMap));
			response.getWriter().flush();
			response.getWriter().close();
			try
			{
				rs.close();
				statement.close();
				dbcon.close();
				
				DebugLogger.createLog("Connection Closes");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}

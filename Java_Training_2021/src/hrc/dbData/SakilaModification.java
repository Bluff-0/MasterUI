package hrc.dbData;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hrc.tools.DebugLogger;
import hrc.tools.ReadQuery;
import hrc.tools.SQLConnect;

@WebServlet("/api/sakilaModification")
public class SakilaModification extends HttpServlet 
{
	private static final long serialVersionUID= 1L;
	Connection dbcon= null;
	PreparedStatement statement= null;
	private String query= null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		SQLConnect sc= new SQLConnect("sakila");
		this.dbcon= sc.newConnection();
		try
		{
			switch(request.getParameter("qType"))
			{
				case "insert":
					ReadQuery rqInsert= new ReadQuery("insertMovie.sql");
					this.query= rqInsert.getQuery();
					statement = this.dbcon.prepareStatement(query);
					
					statement.setString(1, request.getParameter("title"));
					statement.setString(2, request.getParameter("description"));
					statement.setString(3, request.getParameter("release_year"));
					statement.setString(4, request.getParameter("language"));
					statement.setString(5, request.getParameter("rating"));
					statement.setString(6, request.getParameter("special_features"));
					statement.setString(7, request.getParameter("director"));
					
			    	Integer insertStatus= statement.executeUpdate();
			    	DebugLogger.createLog("Insert Query executed successfully");
			    	DebugLogger.createLog("Execution Status: " + insertStatus);
			    	break;
				case "update":
					ReadQuery rqUpdate= new ReadQuery("updateMovie.sql");
					this.query= rqUpdate.getQuery();
					statement = this.dbcon.prepareStatement(query);
					
					statement.setString(1, request.getParameter("title"));
					statement.setString(2, request.getParameter("description"));
					statement.setString(3, request.getParameter("release_year"));
					statement.setString(4, request.getParameter("language"));
					statement.setString(5, request.getParameter("rating"));
					statement.setString(6, request.getParameter("special_features"));
					statement.setString(7, request.getParameter("director"));
					statement.setInt(8, Integer.parseInt(request.getParameter("film_id")));
					
			    	Integer updateStatus= statement.executeUpdate();
			    	DebugLogger.createLog("Update Query executed successfully");
			    	DebugLogger.createLog("Execution Status: " + updateStatus);
			    	break;
				case "delete":
					ReadQuery rqDelete= new ReadQuery("deleteMovie.sql");
					this.query= rqDelete.getQuery();
					this.query= this.query.replace("?", request.getParameter("film_id"));
					statement = this.dbcon.prepareStatement(query);
					
			    	Integer deleteStatus= statement.executeUpdate();
			    	DebugLogger.createLog("Delete Query executed successfully");
			    	DebugLogger.createLog("Execution Status: " + deleteStatus);
			    	break;
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
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

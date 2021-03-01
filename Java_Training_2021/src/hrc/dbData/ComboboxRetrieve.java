package hrc.dbData;

import java.util.HashSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hrc.tools.ExtractDistinct;

@WebServlet("/api/getComboboxData")
public class ComboboxRetrieve extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		HashSet<String> returnValue= null;
		try
		{
			String db= request.getParameter("db");
			String table= request.getParameter("table");
			String column= request.getParameter("column");
			String type= request.getParameter("type");
			
			switch(type)
			{
				case "set":
					returnValue= new ExtractDistinct(db).extractFromSet(table, column);
					break;
				case "enum":
					returnValue= new ExtractDistinct(db).extractFromEnum(table, column);
					break;
				case "table":
					returnValue= new ExtractDistinct(db).extractFromTable(table, column);
					break;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				response.setContentType("application/json");
				response.getWriter().write(new Gson().toJson(returnValue));
				response.getWriter().flush();
				response.getWriter().close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}

// http://localhost:8080/Java_Training_2021/api/getComboboxData?db=sakila&table=film&column=special_features&type=set

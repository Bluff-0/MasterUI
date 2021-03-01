package hrc.tools;

import java.io.BufferedReader;
import java.io.FileReader;
//import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReadQuery 
{
	private String filepath;
	private String query;
	// private String workingdir= Paths.get(".").normalize().toAbsolutePath().toString();
	private String workingdir= "C:\\Users\\saptarshi.mazumdar\\Documents\\Training Workspace\\Java_Training_2021";
	
	public ReadQuery(String filepath)
	{
		BufferedReader bufferedReader= null;
		StringBuilder sb= null;
		this.filepath= this.workingdir+"\\WebContent\\db\\"+filepath;
		try
		{
			bufferedReader = new BufferedReader(new FileReader(this.filepath));
			sb = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null)
			{
			    sb.append(line);
			}
			this.query = sb.toString();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				bufferedReader.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public String getQuery()
	{
		return this.query;
	}
	
	public String getTotalCalculateQuery()
	{
		return "SELECT COUNT(*) AS `total` FROM ( " + this.query + " ) AS complete";
	}
	
	public String getFilepath()
	{
		return this.filepath;
	}
	
	public String addVariableLimiter(String query)
	{
		DebugLogger.createLog("Adding Query Limit");
		return query+" LIMIT ?, ?";
	}
	
	public String addDefinedLimiter(HttpServletRequest request, String query)
	{
		String start= request.getParameter("start");
		String limit= request.getParameter("limit");
		DebugLogger.createLog("Adding Query Limit for Ext Pagination Support");
		return query+" LIMIT "+start+", "+limit;
	}
	
	public static void main(String[] args) 
	{	
		System.out.println(new ReadQuery("").getFilepath());
	}
}
/**
 * 
 */
package hrc.tools;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLConnect {

	Connection dbcon= null;
	String url= "jdbc:mysql://127.0.0.1:3306/";
	String username= null;
	String password= null;
	
	public SQLConnect(String dbname, String username, String password) 
	{
		this.url= this.url+dbname;
		this.username= username;
		this.password= password;
	}
	
	public SQLConnect(String dbname) 
	{
		this.url= this.url+dbname;
		this.username= "root";
		this.password= "root";
	}
	
	public Connection newConnection()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.dbcon= DriverManager.getConnection(url, username, password);
			DebugLogger.createLog("Connection Established");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return dbcon;
	}

}

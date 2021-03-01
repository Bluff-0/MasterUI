package hrc.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;

public class ExtractDistinct {
	
	private Connection dbcon= null;
	
	public ExtractDistinct(String db)
	{
		SQLConnect sc= new SQLConnect(db);
		this.dbcon= sc.newConnection();
	}
	
	public HashSet<String> extractFromEnum(String table, String column) throws SQLException
	{
		String query= "SELECT `COLUMN_TYPE` AS `enumvalues` FROM `INFORMATION_SCHEMA`.`COLUMNS` WHERE `TABLE_NAME` = ? AND `COLUMN_NAME` = ? ";
		PreparedStatement statement = this.dbcon.prepareStatement(query);
		statement.setString(1, table);
		statement.setString(2, column);
		ResultSet rs = statement.executeQuery();
		HashSet<String> enumvalues= null;
		if(rs.next())
		{
			String resultVal= rs.getString("enumvalues");
			resultVal= resultVal.replaceAll("'","");
			enumvalues = new HashSet<String>(Arrays.asList(resultVal.substring(5, resultVal.length() - 1).split(",")));
		}
		DebugLogger.createLog("ENUM Extracted \t " + enumvalues.toString());
		
		rs.close();
		statement.close();
		this.dbcon.close();
		
		return enumvalues;
	}
	
	public HashSet<String> extractFromSet(String table, String column) throws SQLException
	{
		String query= "SELECT `COLUMN_TYPE` AS `enumvalues` FROM `INFORMATION_SCHEMA`.`COLUMNS` WHERE `TABLE_NAME` = ? AND `COLUMN_NAME` = ? ";
		PreparedStatement statement = this.dbcon.prepareStatement(query);
		statement.setString(1, table);
		statement.setString(2, column);
		ResultSet rs = statement.executeQuery();
		HashSet<String> enumvalues= null;
		if(rs.next())
		{
			String resultVal= rs.getString("enumvalues");
			resultVal= resultVal.replaceAll("'","");
			enumvalues = new HashSet<String>(Arrays.asList(resultVal.substring(4, resultVal.length() - 1).split(",")));
		}
		DebugLogger.createLog("SET Extracted \t " + enumvalues.toString());
		
		rs.close();
		statement.close();
		this.dbcon.close();
		
		return enumvalues;
	}
	
	public HashSet<String> extractFromTable(String table, String column) throws SQLException
	{
		String query= "SELECT DISTINCT `"+column+"` AS `values` FROM `"+table+"` ";
		
		PreparedStatement statement = this.dbcon.prepareStatement(query);
		ResultSet rs = statement.executeQuery();
		HashSet<String> enumvalues= new HashSet<String>();
		while(rs.next())
		{
			enumvalues.add(rs.getString("values"));
		}
		DebugLogger.createLog("Column Distinct Extracted \t " + enumvalues.toString());
		
		rs.close();
		statement.close();
		this.dbcon.close();
		
		return enumvalues;
	}
	
	public static void main(String[] args) throws SQLException 
	{
		new ExtractDistinct("sakila").extractFromSet("film", "special_features");
		new ExtractDistinct("sakila").extractFromEnum("film", "rating");
		new ExtractDistinct("sakila").extractFromTable("language", "name");
	}

}

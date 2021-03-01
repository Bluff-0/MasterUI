package hrc.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DebugLogger {
	
	private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	DebugLogger()
	{
		System.out.println("---------\t Debug Logger \t-----------");
		System.out.println("Starting at: "+ formatter.format(new Date()));
		System.out.println();
		System.out.println();
	}  
	
	public static void createLog(String msg) 
	{
		String date= formatter.format(new Date());
		System.out.println(date + "\t::  " + msg);
	}
}

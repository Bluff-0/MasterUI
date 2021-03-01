package com.highradius.training.struts.action;

import java.util.Date;

import com.opensymphony.xwork2.Action;

public class DummyAction implements Action {
	
	private Integer StatusCode;
	
	public Integer getStatusCode() {
		return StatusCode;
	}

	public void setStatusCode(Integer statusCode) {
		StatusCode = statusCode;
	}

	public String execute()
	{
		String output;
		
		if(Math.ceil(Math.random() * 10) % 2 == 0)
		{
			output= ERROR;
			if(StatusCode == null) StatusCode= 404;
		}
		else
		{
			output= SUCCESS;
			setStatusCode(200);
		}
		
		System.out.println("-- " + output.toUpperCase() + "\t" + getStatusCode() + ":\t" + new Date().toString() );
		
		return output;
	}
}

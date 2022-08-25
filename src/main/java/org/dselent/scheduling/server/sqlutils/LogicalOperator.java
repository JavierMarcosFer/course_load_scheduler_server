package org.dselent.scheduling.server.sqlutils;

public enum LogicalOperator
{
	AND("AND"),
	OR("OR"),
	NOT("NOT");
	
	private String stringFormat;
	
	private LogicalOperator(String stringFormat)
	{
		this.stringFormat = stringFormat;
	}
	
	public String getStringFormat()
	{
		return stringFormat;
	}
}

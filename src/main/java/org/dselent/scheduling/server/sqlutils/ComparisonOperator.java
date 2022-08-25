package org.dselent.scheduling.server.sqlutils;

public enum ComparisonOperator
{
	EQUAL("="),
	NOT_EQUAL("<>"),
	GREATER_THAN(">"),
	GREATER_THAN_OR_EQUAL(">="),
	LESS_THAN("<"),
	LESS_THAN_OR_EQUAL("<="),
	IS_NULL("IS NULL"),
	IS_NOT_NULL("IS NOT NULL");
	
	private String stringFormat;
	
	private ComparisonOperator(String stringFormat)
	{
		this.stringFormat = stringFormat;
	}
	
	public String getStringFormat()
	{
		return stringFormat;
	}
	
}

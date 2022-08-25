package org.dselent.scheduling.server.miscellaneous;

import org.springframework.util.StringUtils;

public class RequestParameterConverter
{
	// TODO
	// ^(For Doug)
	// code duplicated from QueryStringBuilder
	// consider refactoring
	public static String convertKeyName(Enum<?> request_enum)
	{
		String enumString = request_enum.toString().toLowerCase();
		String[] variableNameParts = enumString.split("_");
		String variableName = variableNameParts[0];
		
		for(int i=1; i<variableNameParts.length; i++)
		{
			variableName = variableName.concat(StringUtils.capitalize(variableNameParts[i]));
		}
		
		return variableName;
	}
}

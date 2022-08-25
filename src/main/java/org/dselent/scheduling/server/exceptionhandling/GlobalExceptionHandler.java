package org.dselent.scheduling.server.exceptionhandling;

import org.dselent.scheduling.server.miscellaneous.JsonResponseCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Central exception handler
 * Applies to all methods annotated with ResponseMapping
 * Add/modify methods if needed for specific exceptions
 * 
 * @author dselent
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception exception)
	{
		String response = "";

		// TODO
		// log exception in error_logs table
		exception.printStackTrace(); // for now
		
		try
		{
			response = JsonResponseCreator.getJSONResponse(JsonResponseCreator.ResponseKey.ERROR, exception);
		}
		catch (JsonProcessingException jpe)
		{
			// exception in exception handler...
			jpe.printStackTrace();
		}
		
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		return responseEntity;
	}
}
package org.dselent.scheduling.server.service;

import java.sql.SQLException;
import java.util.List;

import org.dselent.scheduling.server.dto.RegistrationCartDto;

import org.springframework.stereotype.Service;

@Service
public interface RegistrationCartService {
	/**
	 * Displays the given user's registration cart
	 * 
	 * @param user_id String designating user
	 * @return A list of the necessary information from referenced courses
	 * @throws SQLException
	 */
	public List<RegistrationCartDto> registrationCart(Integer user_id) throws SQLException, Exception;
	
	public void removeCourse(Integer instanceId) throws SQLException, Exception;
	
	public void submitCart(Integer userId) throws Exception;

}

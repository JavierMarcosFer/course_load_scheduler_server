package org.dselent.scheduling.server.dao.impl;

import org.dselent.scheduling.server.dao.Dao;
import org.dselent.scheduling.server.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Abstract class for all Daos.  Contains the database connection objects and implements Dao.
 * Subclasses will specify the implementations for the functions in the Dao interface (how to get the data).
 * 
 * @author dselent
 *
 * @param <M> The model for the Dao
 */
public abstract class BaseDaoImpl<M extends Model> implements Dao<M>
{
	@Autowired
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	// perhaps find a nice way to abstract some of the extending classes here

}

package org.dselent.scheduling.server.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.ResultSetExtractor;

/**
 * Abstract base class for all subclasses to extend from.
 * Each subclass will implement their own data extraction from the result set
 * and store the data into their respective model objects.  Dao implementations
 * are expected to use one of the jdbc template objects to query the database.
 * These methods are expected to create an Extractor object.
 * When the query completes the extractData method of the Extractor object is called.
 * 
 * @author dselent
 *
 * @param <E> Model being populated
 */
public abstract class Extractor<E> implements ResultSetExtractor<E>
{
	@Override
	public abstract E extractData(ResultSet rs) throws SQLException;
}

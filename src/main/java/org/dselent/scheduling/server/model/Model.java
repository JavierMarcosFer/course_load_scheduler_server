package org.dselent.scheduling.server.model;

/*
 * I dislike an empty abstract class however...
 * 1.  This is very useful in the design of the dao package
 * 2.  The only attributes all models have in common are static and not inherited
 * 3.  I couldn't think of a better way (open to suggestions)
 */

/**
 * Models are 1-to-1 mappings from database tables to Java classes.
 * Each database table will have a corresponding model object holding
 * the table data (in object form) and other useful functions/attributes.
 * 
 * @author dselent
 *
 */
public abstract class Model
{
	
}

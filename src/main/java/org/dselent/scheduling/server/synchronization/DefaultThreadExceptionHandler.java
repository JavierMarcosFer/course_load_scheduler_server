package org.dselent.scheduling.server.synchronization;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Might need this class if you start spawning threads server-side to ensure exceptions get thrown
 * back to the parent thread (they don't do this by default)
 * 
 * @author dselent
 *
 */
// open to better ideas for both passing an exception from one thread to another
// and ability to use Thread.join or something equivalent
public class DefaultThreadExceptionHandler implements UncaughtExceptionHandler
{
	private Throwable exception;

	@Override
	public void uncaughtException(Thread t, Throwable e)
	{
		//log last exception
		exception = e;
	}
	
	public void checkException() throws Throwable
	{
		if(exception != null)
		{
			throw new Throwable(exception);
		}
	}
	
	public void checkRuntimeException()
	{
		if(exception != null)
		{
			throw new RuntimeException(exception);
		}
	}

}

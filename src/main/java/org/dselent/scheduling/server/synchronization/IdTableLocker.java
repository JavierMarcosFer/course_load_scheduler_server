package org.dselent.scheduling.server.synchronization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.dselent.scheduling.server.miscellaneous.Pair;

/**
 * Used to programmatically lock on (primary key id and table) OR just the table
 * 
 * Example:
 * Reading from a table to see if an entry exists and then adding it if it does not
 * Need to programmatically lock to prevent two threads from reading null and both
 * threads attempting to add the entry, resulting in a duplicate key exception for
 * one of the threads.  This class provides a nice mechanism for programmatically preventing
 * that problem.  I do not believe there is another way.
 * 
 * @author dselent
 *
 */
public class IdTableLocker
{
	public enum LockTypes
	{
		READ,
		WRITE;
	}
	
	private static final Object lock1 = new Object();
	private static final Object lock2 = new Object();
	
	//lock for specific id/table name combination
	private static Map<Pair<Integer, String>, ReentrantLock> userTableLock = new HashMap<Pair<Integer, String>, ReentrantLock>();
	
	//lock on a table name
	private static Map<String, ReentrantReadWriteLock> tableLock = new HashMap<String, ReentrantReadWriteLock>();

	
	private IdTableLocker()
	{
		
	}
	
	public static void lock(String tableName, LockTypes lockType)
	{
		synchronized(lock1)
		{
			ReentrantReadWriteLock rwLock = tableLock.get(tableName);
				
			if(rwLock == null)
			{
				rwLock = new ReentrantReadWriteLock(true);
				tableLock.put(tableName, rwLock);			
			}
	
			if(lockType == LockTypes.WRITE)
			{
				rwLock.writeLock().lock();
			}
			else
			{
				rwLock.readLock().lock();
			}
		}
	}
	
	public static void unlock(String tableName, LockTypes lockType)
	{
		synchronized(lock2)
		{
			ReentrantReadWriteLock rwLock = tableLock.get(tableName);
	
			if(rwLock != null)
			{
				if(lockType == LockTypes.WRITE)
				{
					rwLock.writeLock().unlock();
				}
				else
				{
					rwLock.readLock().unlock();
				}
			}
		}
	}
	
	public static void lock(List<Integer> userIdList, String tableName)
	{
		synchronized(lock1)
		{
			lock(tableName, LockTypes.READ);
			
			//
			
			for(Integer userId : userIdList)
			{
				Pair<Integer, String> entry = new Pair<Integer, String>(userId, tableName);
				ReentrantLock lock = userTableLock.get(entry);
				
				if(lock == null)
				{
					lock = new ReentrantLock();
					userTableLock.put(entry, lock);			
				}
				
				lock.lock();
			}
		}
	}
		
	public static void unlock(List<Integer> userIdList, String tableName)
	{
		synchronized(lock2)
		{
			unlock(tableName, LockTypes.READ);
			
			//
			
			ReentrantReadWriteLock rwLock = tableLock.get(tableName);
			
			if(rwLock != null)
			{
				tableLock.remove(tableName);
				rwLock.readLock().unlock();		
			}
			
			//
			
			for(Integer userId : userIdList)
			{
				Pair<Integer, String> entry = new Pair<Integer, String>(userId, tableName);
				ReentrantLock lock = userTableLock.get(entry);
				
				if(lock != null)
				{
					userTableLock.remove(entry);
					lock.unlock();		
				
				}
			}
		}
	}

}

package com.javamain.db.bigqueue01;

import java.io.Closeable;
import java.io.IOException;

/**
 * Append Only Big Array ADT
 * 
 * @author bulldog
 *
 */
public interface IBigArray extends Closeable {
	
	public static final long NOT_FOUND = -1;
	
	/**
	 * Append the data into the head of the array
	 * 
	 * @param data binary data to append
	 * @return appended index
	 * @throws IOException if there is any IO error
	 */
	long append(byte[] data) throws IOException;
	
	/**
	 * Get the data at specific index
	 * 
	 * @param index valid data index
	 * @return binary data if the index is valid
	 * @throws IOException if there is any IO error
	 */
	byte[] get(long index) throws IOException;
	
	/**
	 * Get the timestamp of data at specific index,
	 * 
	 * this is the timestamp when the data was appended.
	 * 
	 * @param index valid data index
	 * @return timestamp when the data was appended
	 * @throws IOException if there is any IO error
	 */
	long getTimestamp(long index) throws IOException;

	/**
	 * The total number of items has been appended into the array
	 * 
	 * @return total number
	 */
	long size();
	
	/**
	 * Get the back data file size per page.
	 * 
	 * @return size per page
	 */
	int getDataPageSize();
	
	/**
	 * The head of the array.
	 * 
	 * This is the next to append index, the index of the last appended data 
	 * is [headIndex - 1] if the array is not empty.
	 * 
	 * @return an index
	 */
	long getHeadIndex();
	
	/**
	 * The tail of the array.
	 * 
	 * The is the index of the first appended data
	 * 
	 * @return an index
	 */
	long getTailIndex();
	
	/**
	 * Check if the array is empty or not
	 * 
	 * @return true if empty false otherwise
	 */
	boolean isEmpty();
	
	/**
	 * Check if the ring space of java long type has all been used up.
	 * 
	 * can always assume false, if true, the world is end:)
	 * 
	 * @return array full or not
	 */
	boolean isFull();
	
	/**
	 * Force to persist newly appended data,
	 * 
	 * normally, you don't need to flush explicitly since:
	 * 1.) BigArray will automatically flush a cached page when it is replaced out,
	 * 2.) BigArray uses memory mapped file technology internally, and the OS will flush the changes even your process crashes,
	 * 
	 * call this periodically only if you need transactional reliability and you are aware of the cost to performance.
	 */
	void flush();

}

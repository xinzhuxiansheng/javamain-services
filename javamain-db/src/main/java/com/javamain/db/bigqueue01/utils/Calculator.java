package com.javamain.db.bigqueue01.utils;

public class Calculator {
	
	
	/**
	 * mod by shift
	 * 取余，val % (2^bits)
	 * @param val
	 * @param bits
	 * @return
	 */
	public static long mod(long val, int bits) {
		return val - ((val >> bits) << bits);
	}
	
	/**
	 * multiply by shift
	 *
	 * 乘法，val * 2^bits
	 *
	 * @param val
	 * @param bits
	 * @return
	 */
	public static long mul(long val, int bits) {
		return val << bits;
	}
	
	/**
	 * divide by shift
	 * 除法，val / 2^bits
	 * @param val 
	 * @param bits
	 * @return
	 */
	public static long div(long val, int bits) {
		return val >> bits;
	}

}

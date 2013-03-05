package com.intel.fibnative;

public class FibLib {

	/** Java Fibonacci */
	public static long fibJ(long n) {
		if(n==0) return 0;
		if(n==1) return 1;
		return fibJ(n-1)+fibJ(n-2);
	}
	
	public static native long fibN(long n);
}

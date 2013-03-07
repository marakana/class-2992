package com.intel.logservice;

import android.util.Log;

public class LogLib {
	public static void logJ(int priority, String tag, String msg) {
		if(priority < 3 || priority > 7) {
			throw new IllegalArgumentException("Unsupported priority");
		}
		
		if( tag==null || msg==null) {
			throw new NullPointerException("tag or message is NULL");
		}
		
		Log.println(priority, tag, msg);
	}

	static {
		System.loadLibrary("LogNative");
	}

	public static native void logN(int priority, String tag, String msg);
}

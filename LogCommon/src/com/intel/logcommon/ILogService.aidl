package com.intel.logcommon;

interface ILogService {
	void logJ(int priority, String tag, String message);
	void logN(int priority, String tag, String message);
}
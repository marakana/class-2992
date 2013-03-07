package com.intel.logcommon;

import com.intel.logcommon.LogMessage;

interface ILogService {
	void logJ(int priority, String tag, String message);
	void logN(int priority, String tag, String message);
	void log(in LogMessage msg);
}
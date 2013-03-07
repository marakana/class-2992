package com.intel.logcommon;

import com.intel.logcommon.LogMessage;
import com.intel.logcommon.LogListener;

interface ILogService {
	void logJ(int priority, String tag, String message);
	void logN(int priority, String tag, String message);
	void log(in LogMessage msg);
	oneway void asyncLog( in LogMessage msg, in LogListener listener );
}
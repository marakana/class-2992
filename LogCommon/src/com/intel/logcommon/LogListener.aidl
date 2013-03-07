package com.intel.logcommon;

import com.intel.logcommon.LogMessage;

oneway interface LogListener {
	void onResponse( in LogMessage msg );
}
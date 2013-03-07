package com.intel.fibcommon;

oneway interface FibListener {
	void onResponse( long result, long time );
}
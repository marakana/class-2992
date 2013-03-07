package com.intel.fibcommon;

import com.intel.fibcommon.FibRequest;

interface IFibService {
	long fibJ(long n);
	long fibN(long n);
	long fib( in FibRequest request);
}
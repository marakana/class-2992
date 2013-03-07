package com.intel.fibservice;

import android.os.RemoteException;

import com.intel.fibcommon.FibRequest;
import com.intel.fibcommon.IFibService;

public class IFibServiceImpl extends IFibService.Stub {

	@Override
	public long fibJ(long n) throws RemoteException {
		return FibLib.fibJ(n);
	}

	@Override
	public long fibN(long n) throws RemoteException {
		return FibLib.fibN(n);
	}

	@Override
	public long fib(FibRequest request) throws RemoteException {
		switch (request.getAlgorithm()) {
		case FibRequest.ALGORITHM_JAVA:
			return fibJ(request.getN());
		case FibRequest.ALGORITHM_NATIVE:
			return fibN(request.getN());
		default:
			throw new IllegalArgumentException("Unsupported algorithm");
		}
	}

}

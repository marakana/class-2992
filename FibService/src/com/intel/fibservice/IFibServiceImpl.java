package com.intel.fibservice;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.RemoteException;

import com.intel.fibcommon.FibListener;
import com.intel.fibcommon.FibRequest;
import com.intel.fibcommon.IFibService;

public class IFibServiceImpl extends IFibService.Stub {
	private Context context;

	public IFibServiceImpl(Context context) {
		super();
		this.context = context;
	}

	@Override
	public long fibJ(long n) throws RemoteException {
		// Check permission
		if (context
				.checkCallingOrSelfPermission("com.intel.permission.FIB_SERVICE_SLOW") != PackageManager.PERMISSION_GRANTED) {
			throw new SecurityException("Have you declared com.intel.permission.FIB_SERVICE_SLOW?");
		}
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

	@Override
	public void asyncFib(FibRequest request, FibListener listener)
			throws RemoteException {
		long time = System.currentTimeMillis();
		long result = fib(request);
		time = System.currentTimeMillis() - time;
		listener.onResponse(result, time);
	}

}

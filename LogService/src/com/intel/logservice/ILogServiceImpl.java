package com.intel.logservice;

import android.os.RemoteException;

import com.intel.logcommon.ILogService;

public class ILogServiceImpl extends ILogService.Stub {

	@Override
	public void logN(int priority, String tag, String msg)
			throws RemoteException {
		LogLib.logN(priority, tag, msg);
	}

	@Override
	public void logJ(int priority, String tag, String msg)
			throws RemoteException {
		LogLib.logJ(priority, tag, msg);
	}

}

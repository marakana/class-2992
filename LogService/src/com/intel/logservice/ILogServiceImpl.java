package com.intel.logservice;

import android.os.RemoteException;

import com.intel.logcommon.ILogService;
import com.intel.logcommon.LogMessage;

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

	@Override
	public void log(LogMessage logMessage) throws RemoteException {
		logN(logMessage.getPriority(), logMessage.getTag(),
				logMessage.getMessage());
	}
}

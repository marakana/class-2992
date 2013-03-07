package com.intel.logcommon;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

public class LogManager {
	private Context context;
	private ILogService logService;

	public LogManager(Context context) {
		this.context = context;
		context.bindService(LOG_SERVICE, CONN, Context.BIND_AUTO_CREATE);
	}

	@Override
	public void finalize() {
		context.unbindService(CONN);
	}

	private static final Intent LOG_SERVICE = new Intent(
			"com.intel.logcommon.ILogService");

	private ServiceConnection CONN = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			logService = ILogService.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			logService = null;
		}
	};

	// --- Proxy Calls ---

	public void logJ(int priority, String tag, String message) {
		if (logService != null) {
			try {
				logService.logJ(priority, tag, message);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	public void logN(int priority, String tag, String message) {
		if (logService != null) {
			try {
				logService.logN(priority, tag, message);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public void log(LogMessage logMessage) {
		if (logService != null) {
			try {
				logService.log(logMessage);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public void asyncLog(LogMessage logMessage, LogListener listener) {
		if (logService != null) {
			try {
				logService.asyncLog(logMessage, listener);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

}

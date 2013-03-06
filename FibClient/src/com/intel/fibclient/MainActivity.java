package com.intel.fibclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.intel.fibcommon.IFibService;

public class MainActivity extends Activity implements OnClickListener {
	private EditText input;
	private Button buttonGo;
	private TextView output;
	private IFibService fibService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		input = (EditText) findViewById(R.id.input);
		buttonGo = (Button) findViewById(R.id.button_go);
		output = (TextView) findViewById(R.id.output);

		buttonGo.setOnClickListener(this);
	}

	private static final Intent FIB_SERVICE = new Intent(
			"com.intel.fibcommon.IFibService");

	private ServiceConnection CONN = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			fibService = IFibService.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			fibService = null;
		}
	};
	
	@Override
	protected void onStart() {
		super.onStart();
		this.bindService(FIB_SERVICE, CONN, BIND_AUTO_CREATE);
	}

	@Override
	protected void onStop() {
		super.onStop();
		this.unbindService(CONN);
	}

	@Override
	public void onClick(View v) {
		long n = Long.parseLong(input.getText().toString());

		// Java

		try {
			long start;
			start = System.currentTimeMillis();
			long resultJ = fibService.fibJ(n);
			long timeJ = System.currentTimeMillis() - start;
			output.append(String.format("\n fibJ(%d)=%d (%d ms)", n, resultJ,
					timeJ));

			// Native
			start = System.currentTimeMillis();
			long resultN = fibService.fibN(n);
			long timeN = System.currentTimeMillis() - start;
			output.append(String.format("\n fibN(%d)=%d (%d ms)", n, resultN,
					timeN));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

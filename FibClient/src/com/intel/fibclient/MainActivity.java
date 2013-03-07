package com.intel.fibclient;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.intel.fibcommon.FibListener;
import com.intel.fibcommon.FibManager;
import com.intel.fibcommon.FibRequest;

public class MainActivity extends Activity implements OnClickListener {
	private EditText input;
	private Button buttonGo;
	private static TextView output;
	private FibManager fibManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		input = (EditText) findViewById(R.id.input);
		buttonGo = (Button) findViewById(R.id.button_go);
		output = (TextView) findViewById(R.id.output);

		buttonGo.setOnClickListener(this);

		fibManager = new FibManager(this);
	}

	private static final int WHAT_ID = 42;
	private static final Handler HANDLER = new Handler() {
		// Executes on the UI thread
		@Override
		public void handleMessage(Message msg) {
			if (msg.what != WHAT_ID)
				return;
			output.append(String.format("\n asyncFib()=%d (%d ms)", msg.arg1,
					msg.arg2));
		}
	};

	private static final FibListener LISTENER = new FibListener.Stub() {
		// Executes on the binder thread
		@Override
		public void onResponse(long result, long time) throws RemoteException {
			Message msg = HANDLER.obtainMessage(WHAT_ID, (int) result,
					(int) time);
			HANDLER.sendMessage(msg);
		}
	};

	@Override
	public void onClick(View v) {
		long n = Long.parseLong(input.getText().toString());

		// Java
		long start;
		fibManager.asyncFib(new FibRequest(FibRequest.ALGORITHM_JAVA, n),
				LISTENER);

		// Native
		start = System.currentTimeMillis();
		long resultN = fibManager.fib(new FibRequest(
				FibRequest.ALGORITHM_NATIVE, n));
		long timeN = System.currentTimeMillis() - start;
		output.append(String
				.format("\n fibN(%d)=%d (%d ms)", n, resultN, timeN));
	}
}

package com.intel.logclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.intel.logcommon.LogListener;
import com.intel.logcommon.LogManager;
import com.intel.logcommon.LogMessage;

public class LogActivity extends Activity implements OnClickListener {
	private static final String TAG = "LogActivity";

	private static final int[] LOG_LEVEL = { Log.VERBOSE, Log.DEBUG, Log.DEBUG,
			Log.WARN, Log.ERROR };

	private Spinner priority;

	private EditText tag;

	private EditText msg;

	private Button button;

	private RadioGroup type;

	private LogManager logManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.main);
		this.priority = (Spinner) super.findViewById(R.id.log_priority);
		this.tag = (EditText) super.findViewById(R.id.log_tag);
		this.msg = (EditText) super.findViewById(R.id.log_msg);
		this.type = (RadioGroup) super.findViewById(R.id.type);
		this.type.check(R.id.type_log_j);
		this.button = (Button) super.findViewById(R.id.log_button);
		this.button.setOnClickListener(this);

		logManager = new LogManager(this);

	}

	public void onClick(View v) {
		int priorityPosition = this.priority.getSelectedItemPosition();
		if (priorityPosition != AdapterView.INVALID_POSITION) {
			final int priority = LOG_LEVEL[priorityPosition];
			final String tag = this.tag.getText().toString();
			final String msg = this.msg.getText().toString();
			if (TextUtils.isEmpty(tag) || TextUtils.isEmpty(msg)) {
				new AlertDialog.Builder(this)
						.setMessage(R.string.log_tag_errors)
						.setPositiveButton(android.R.string.yes,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										LogActivity.this
												.log(priority, tag, msg);
									}
								}).setNegativeButton(android.R.string.no, null)
						.create().show();
			} else {
				log(priority, tag, msg);
			}
		}
	}
	
	private final Handler HANDLER = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if(msg.what!=42) return;
			Toast.makeText(LogActivity.this, R.string.log_success,
					Toast.LENGTH_SHORT).show();
		}
	};

	private final LogListener LISTENER = new LogListener.Stub() {
		@Override
		public void onResponse(LogMessage msg) throws RemoteException {
			HANDLER.sendMessage( HANDLER.obtainMessage(42));
		}
	};

	private void log(int priority, String tag, String msg) {
		try {
			LogMessage message = new LogMessage(priority, tag, msg);
			switch (this.type.getCheckedRadioButtonId()) {
			case R.id.type_log_j:
				logManager.log(message);
				Toast.makeText(this, R.string.log_success, Toast.LENGTH_SHORT)
						.show();
				break;
			case R.id.type_log_n:
				logManager.asyncLog(message, LISTENER);
				break;
			default:
				return;
			}
			// this.tag.getText().clear();
			// this.msg.getText().clear();
		} catch (RuntimeException e) {
			Toast.makeText(this, R.string.log_error, Toast.LENGTH_SHORT).show();
			Log.wtf(TAG, "Failed to log the message", e);
		}
	}
}

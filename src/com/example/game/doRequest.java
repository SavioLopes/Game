package com.example.game;

import java.util.Map;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.TextView;

public class doRequest extends AsyncTask<Void, JSONObject, JSONObject> {
	
	private JSONObject dataResponse = new JSONObject();
	private boolean requested = false;
	
	public boolean isRequested() {
		return this.requested;
	}

	public JSONObject getDataResponse() {
		return this.dataResponse;
	}

	AndrestClient rest = new AndrestClient();
	// Store context for dialogs
	private Context context = null;
	// Store error message
	private Exception e = null;
	// Passed in data object
	private Map<String, Object> data = null;
	// Passed in method
	private String method = "";
	// Passed in url
	private String url = "";

	public doRequest(Context context, Map<String, Object> data, String method,
			String url) {
		this.context = context;
		this.data = data;
		this.method = method;
		this.url = url;
	}

	@Override
	public JSONObject doInBackground(Void... arg0) {
		try {

			this.dataResponse = rest.request(url, method, data); // Do request;
			this.requested = true;
			return this.dataResponse;
			
		} catch (Exception e) {
			requested = true;
			this.e = e; // Store error
		}
		return null;
	}

	@Override
	public void onPostExecute(JSONObject data) {
		super.onPostExecute(data);
		// Display based on error existence
		if (e != null) {
			requested = true;
			new ResponseDialog(context, "We found an error!", e.getMessage())
					.showDialog();
		} else {
			dataResponse = data;
			requested = true;
			
			//new ResponseDialog(context, "Success!", data.toString()).showDialog();
		}
	}
}

/**
 * Extremely simple dialog builder, just so I don't have to mess about when
 * creating dialogs for the user.
 * 
 * @author Isaac Whitfield
 * @version 09/03/2014
 */
class ResponseDialog extends Builder {

	// Store the passed context
	private Context context;

	// Can be used as a regular builder
	public ResponseDialog(Context context) {
		super(context);
	}

	// Or as a custom builder, which we want
	public ResponseDialog(Context context, String title, String message) {
		super(context);
		// Store context
		this.context = context;
		// Set up everything
		setMessage(message);
		setTitle(title);
		setCancelable(false);
		setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss(); // It's just for info so we don't really
									// care what this does
			}
		});
	}

	public void showDialog() {
		// Create and show
		AlertDialog alert = create();
		alert.show();
		// Center align everything
		((TextView) alert.findViewById(android.R.id.message))
				.setGravity(Gravity.CENTER);
		((TextView) alert.findViewById((context.getResources().getIdentifier(
				"alertTitle", "id", "android")))).setGravity(Gravity.CENTER);
	}
}

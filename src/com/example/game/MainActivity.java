package com.example.game;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


@SuppressWarnings("deprecation")
@SuppressLint("NewApi") public class MainActivity extends ActionBarActivity {
	
	AndrestClient rest = new AndrestClient(); 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView text = (TextView) findViewById(R.id.textView1);
                text.setText("Sávio");
                
                String url = "http://rest-service.guides.spring.io/greeting"; 
				//JSONObject obj = rest.get("http://rest-service.guides.spring.io/greeting");
				//Toast.makeText(getApplicationContext(), obj.getString("id"), Toast.LENGTH_SHORT).show();
				new doRequest(MainActivity.this, null, "GET", url).execute();
				//JSONObject obj = rest.request(url, "GET", null);
				//text.setText(obj.getInt("id"));
				//Toast.makeText(getApplicationContext(), obj.getString("id"), Toast.LENGTH_SHORT).show();
                
                
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
private class doRequest extends AsyncTask<Void, JSONObject, JSONObject>{
		
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
		
		public doRequest(Context context, Map<String, Object> data, String method, String url){
			this.context = context;
			this.data = data;
			this.method = method;
			this.url = url;
		}
		
		@Override
		protected JSONObject doInBackground(Void... arg0) {
			try {
				
				return rest.request(url, method, data); // Do request
			} catch (Exception e) {
				this.e = e;	// Store error
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(JSONObject data){
			super.onPostExecute(data);
			// Display based on error existence
			if(e != null){
				new ResponseDialog(context, "We found an error!", e.getMessage()).showDialog();
			} else {
				new ResponseDialog(context, "Success!", data.toString()).showDialog();
			}
		}
	}
	
	/**
	 * Extremely simple dialog builder, just so I don't have to mess about when creating
	 * dialogs for the user.
	 * 
	 * @author 	Isaac Whitfield
	 * @version 09/03/2014
	 */
	private class ResponseDialog extends Builder {

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
					dialog.dismiss(); // It's just for info so we don't really care what this does
				}
			});
		}

		public void showDialog(){
			// Create and show
			AlertDialog alert = create();
			alert.show();
			// Center align everything
			((TextView)alert.findViewById(android.R.id.message)).setGravity(Gravity.CENTER);
			((TextView)alert.findViewById((context.getResources().getIdentifier("alertTitle", "id", "android")))).setGravity(Gravity.CENTER);
		}
	}
    
    
    
}

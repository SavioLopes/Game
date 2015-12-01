package com.example.game;

import org.json.JSONObject;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		Button button = (Button) findViewById(R.id.button1);
		// Se botao apertar...
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				EditText usuario = (EditText) findViewById(R.id.editText1);
				EditText senha = (EditText) findViewById(R.id.editText2);
				String user = usuario.getText().toString();
				String pass = senha.getText().toString();

				String url = "http://gameserver-saviolopes.rhcloud.com/login/" + user + "/" + pass;
				// Get
				doRequest request = new doRequest(LoginActivity.this, null, "GET", url);
				request.execute();

				while (!request.isRequested()) {}

				JSONObject response = request.getDataResponse();

				if (response != null) {
					Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}

package com.example.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendMessageActivity extends Activity {

	private Message message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_message);

		Button sendMessage = (Button) findViewById(R.id.buttonSendMessage);
		Button getMessages = (Button) findViewById(R.id.buttonGetMessage);
		
		getMessages.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditText userId = (EditText) findViewById(R.id.editTextUserId);
				String userIdValue = userId.getText().toString();
				String url = "http://gameserver-saviolopes.rhcloud.com/messages/"+userIdValue;
				
				new doRequest(SendMessageActivity.this, null, "GET", url).execute();
			}
		});
		
		sendMessage.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditText editText = (EditText) findViewById(R.id.editTextMessage);
				EditText userId = (EditText) findViewById(R.id.editTextUserId);
				String userIdValue = userId.getText().toString();
				String messageText = editText.getText().toString();
				String url = "http://gameserver-saviolopes.rhcloud.com/sendMessage";
				
				message = new Message();
				message.setMessage(messageText);
				message.setUserId(Integer.parseInt(userIdValue));
				
				new HttpAsyncTask().execute(url);
			}
		});

	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			// message = new Message();
			// message.setMessage(etName.getText().toString());
			// message.setCountry(etCountry.getText().toString());
			// person.setTwitter(etTwitter.getText().toString());
			String response = POST(urls[0], message);
			// Toast.makeText(SendMessageActivity.this, response,
			// Toast.LENGTH_SHORT).show();
			return response;
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG)
					.show();
		}
	}

	public static String POST(String url, Message message) {
		InputStream inputStream = null;
		String result = "";
		try {

			// 1. create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// 2. make POST request to the given URL
			HttpPost httpPost = new HttpPost(url);

			String json = "";

			// 3. build jsonObject
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("message", message.getMessage());
			jsonObject.accumulate("userId", message.getUserId());

			// 4. convert JSONObject to JSON to String
			json = jsonObject.toString();

			// ** Alternative way to convert Person object to JSON string usin
			// Jackson Lib
			// ObjectMapper mapper = new ObjectMapper();
			// json = mapper.writeValueAsString(person);

			// 5. set json to StringEntity
			StringEntity se = new StringEntity(json);

			// 6. set httpPost Entity
			httpPost.setEntity(se);

			// 7. Set some headers to inform server about the type of the
			// content
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			// 8. Execute POST request to the given URL
			HttpResponse httpResponse = httpclient.execute(httpPost);

			// 9. receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// 10. convert inputstream to string
			if (inputStream != null) {

				result = convertInputStreamToString(inputStream);
				Log.d("Log_Savio", result);
			} else {
				result = "Did not work!";
			}
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

		// 11. return result
		return result;
	}

	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}
}

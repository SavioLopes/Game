package com.example.game;

import java.util.Map;
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
@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				TextView text = (TextView) findViewById(R.id.textView1);
				text.setText("É tetra [2] !!!");
				String url = "http://gameserver-saviolopes.rhcloud.com/login";// "http://rest-service.guides.spring.io/greeting";
				exemplo_simples(url);
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

	private void exemplo_simples(final String url) {
		AlertDialog alerta;
		// Cria o gerador do AlertDialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// define o titulo
		builder.setTitle("Titulo");
		// define a mensagem
		builder.setMessage("Qualifique este software");
		// define um botão como positivo
		builder.setPositiveButton("Positivo",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						// Toast.makeText(MainActivity.this, "positivo=" + arg1,
						// Toast.LENGTH_SHORT).show();
						new doRequest(MainActivity.this, null, "GET", url)
								.execute();
					}
				});
		// define um botão como negativo.
		builder.setNegativeButton("Negativo",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						Toast.makeText(MainActivity.this, "negativo=" + arg1,
								Toast.LENGTH_SHORT).show();
					}
				});

		// cria o AlertDialog
		alerta = builder.create();
		// Exibe
		alerta.show();
	}
}

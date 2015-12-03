package com.example.game;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

@SuppressLint("NewApi") public class Cadastro extends ActionBarActivity {
		
	
			//private Matcher matcher;
		    
			@Override
		    protected void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.activity_cadastro);
		        
		        final EditText email1 = (EditText) findViewById(R.id.email1);
		        final EditText email2 = (EditText) findViewById(R.id.email2);
		        final EditText password1 = (EditText)findViewById(R.id.password1);
		        final EditText password2 = (EditText)findViewById(R.id.password2);
		        EditText username = (EditText)findViewById(R.id.username);
		        Button validar = (Button)findViewById(R.id.validate);
		        
		        validar.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if (!validateEmail(email1.getText().toString())) {
							email1.setError("Email Inválido");
							email1.requestFocus();
						}
						else if (!validateEmail(email2.getText().toString())) {
							password1.setError("Password Inválida");
							email1.requestFocus();
						}
						else if (!validatePassword(password1.getText().toString())) {
							password1.setError("Password Inválida");
							email1.requestFocus();
						} 
						else if (!validatePassword(password2.getText().toString())) {
							password1.setError("Password Inválida");
							email1.requestFocus();
						} 
						else {
							Toast.makeText(Cadastro.this, "Validação com sucesso", Toast.LENGTH_LONG).show();
						}
						
					}
				});
		        
		    }
		    // Retorna true se a senha é válida e false se a senha é inválida
			protected boolean validatePassword(String password) {
				if (password!=null && password.length()>=6) {
					return true;
				} else {
					return false;	
				}
				
			}

			
			// Retorna true se o email1 é válido e false se o email1 é inválido
			protected boolean validateEmail(String email) {
				String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
				
				Pattern pattern = Pattern.compile(emailPattern);
				Matcher matcher = pattern.matcher(email);
				return matcher.matches();
			}
			

}
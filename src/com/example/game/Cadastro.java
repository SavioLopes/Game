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
						if (!validateEmail1(email1.getText().toString())) {
							email1.setError("Email Inv�lido");
							email1.requestFocus();
						} else if (!validateEmail2(email2.getText().toString())) {
							email2.setError("Email Inv�lido");
							email2.requestFocus();
						} 
						else if (!validatePassword1(password1.getText().toString())) {
							password1.setError("Password Inv�lida");
							email1.requestFocus();
						} 
						else if (!validatePassword2(password2.getText().toString())) {
							password2.setError("Password Inv�lida");
							email2.requestFocus();
						
						}else {
							Toast.makeText(Cadastro.this, "Valida��o com sucesso", Toast.LENGTH_LONG).show();
						}
						
					}
				});
		        
		    }
		    // Retorna true se a senha � v�lida e false se a senha � inv�lida
			protected boolean validatePassword1(String password1) {
				if (password1!=null && password1.length()>=6) {
					return true;
				} else {
					return false;	
				}
				
			}
			
			protected boolean validatePassword2(String password2) {
				if (password2!=null && password2.length()>=6) {
					return true;
				} else {
					return false;	
				}
				
			}
			
			// Retorna true se o email1 � v�lido e false se o email1 � inv�lido
			protected boolean validateEmail1(String email1) {
				String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
				
				Pattern pattern = Pattern.compile(emailPattern);
				Matcher matcher = pattern.matcher(email1);
				return matcher.matches();
			}
			
			// Retorna true se o email2 � v�lido e false se o email2 � inv�lido
			protected boolean validateEmail2(String email2) {
				String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
				
				Pattern pattern = Pattern.compile(emailPattern);
				Matcher matcher = pattern.matcher(email2);
				return matcher.matches();
			}
}
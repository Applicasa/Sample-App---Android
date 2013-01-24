package com.example.appvilleegg.sampleApp;
 
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiLogger;
import applicasa.LiCore.communication.LiCallback.LiCallbackUser;
import applicasa.LiCore.communication.LiFilters;
import applicasa.LiCore.communication.LiFilters.Operation;
import applicasa.LiCore.communication.LiQuery;
import applicasa.LiCore.communication.LiRequestConst.LiObjResponse;
import applicasa.LiCore.communication.LiRequestConst.QueryKind;
import applicasa.LiCore.communication.LiRequestConst.RequestAction;
import applicasa.kit.facebook.LiFBmanager;
import applicasa.kit.facebook.LiFacebookResponse.LiFacebookResponseLogin;
import applicasa.kit.facebook.LiObjFacebookFriends;

import com.applicasa.ApplicasaManager.LiSession;
import com.applicasa.User.User;
import com.applicasa.User.UserData.LiFieldUser;
import com.appvilleegg.R;
 
public class LoginActivity extends Activity  {
 
	ImageButton btnLoginLater;
	ImageButton btnLogin;
	ImageButton btnLoginFB;
	ImageButton btnRegister;
	
	EditText email;
	EditText password;
	private LoginActivity mActivity;
	private TextView textForgot;
	private ProgressBar progressBar;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		mActivity = this;
		 
		btnLoginLater = (ImageButton)findViewById(R.id.btn_log_in_later);
		btnLogin = (ImageButton)findViewById(R.id.btn_log_in);
		btnLoginFB = (ImageButton)findViewById(R.id.btn_log_in_fb);
		btnRegister = (ImageButton)findViewById(R.id.btn_register);
		textForgot = (TextView)findViewById(R.id.txt_forgotPassword);
		email = (EditText)findViewById(R.id.txt_input_userName);
		password = (EditText)findViewById(R.id.txt_input_password);
		progressBar = (ProgressBar)findViewById(R.id.progressBar);
		textForgot.setClickable(true);
		
		Log.w("Session", "start Login Activity");
		LiSession.sessionStart(mActivity,null);
	}

 

	public void clickHandler(View v)
	{
		String userName;
		String pass;
		switch (v.getId())
		{
			case R.id.btn_log_in_later:
				
			break;
			
			case R.id.btn_log_in:
				btnLogin.setClickable(false);
				progressBar.setVisibility(View.VISIBLE);
				userName = email.getText().toString();
				pass = password.getText().toString();
				
				 User.loginUser(userName, pass, new LiCallbackUser () {
					
					public void onSuccessfull(RequestAction action) {
						progressBar.setVisibility(View.INVISIBLE);
						btnLoginFB.setClickable(true);
						finish();
					}
					
					public void onFailure(RequestAction action, LiErrorHandler error) {
						progressBar.setVisibility(View.INVISIBLE);
						Toast.makeText(mActivity, "Can't Login User", Toast.LENGTH_LONG).show();
						
						btnLogin.setClickable(true);
					}
				});
				
			break;
			
			case R.id.btn_log_in_fb:
				btnLoginFB.setClickable(false);
				LiLogger.logInfo("Login ", "user with fb");
				User.loginWithFacebookUserFromActivity(LoginActivity.this, new LiFacebookResponseLogin() {
					
					public void onFBLoginResponse(User currentUser) {
						// TODO Auto-generated method stub
						btnLoginFB.setClickable(true);
						finish();
					}
					
					public void onFBError(LiErrorHandler error) {
						// TODO Auto-generated method stub
						btnLoginFB.setClickable(true);
						LiLogger.logError("LoginActivity", error.getMessage());
					}
				});
			break;
			
			case R.id.btn_register:
				// Go to Register user Activity
				Intent i = new Intent(this, RegisterActivity.class);
				startActivity(i);
				finish();
			break;
			
			case R.id.txt_forgotPassword:
				textForgot.setClickable(false);
				createDialog();
		}
	}
	
	

		@Override
		/**
		 * Handles result from fb login
		 */
		protected void onActivityResult(int requestCode, int resultCode, Intent data){
			User.onActivityResult(this,requestCode, resultCode, data);
		}
		
		
		/**
		 * Create alert dialog to receive username to retreive the user's email address
		 */
		private void createDialog() {
			// TODO Auto-generated method stub
				  final AlertDialog.Builder alert = new AlertDialog.Builder(this);
				    final EditText input = new EditText(this);
				    input.setHint("Username");
				    alert.setTitle("Forgot Password");
				    
				    alert.setView(input);
				    alert.setPositiveButton("Send", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton) {
				            String username = input.getText().toString().trim();
				            forgotPassword(username);
				        }
				    });

				    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int whichButton) {
				            dialog.cancel();
				        }
				    });
				    alert.show();  

		}
	
		private void forgotPassword(String userName)
		{
			progressBar.setVisibility(View.VISIBLE);
			User.forgotPassword(userName,new LiCallbackUser() {
				
				public void onSuccessfull(RequestAction arg0) {
					// TODO Auto-generated method stub
					progressBar.setVisibility(View.INVISIBLE);
					 Toast.makeText(getApplicationContext(), "An email with password was sent", Toast.LENGTH_SHORT).show();
					 textForgot.setClickable(true);
				}
				
				public void onFailure(RequestAction arg0, LiErrorHandler ex) {
					// TODO Auto-generated method stub
					progressBar.setVisibility(View.INVISIBLE);
					textForgot.setClickable(true);
					 Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
				}
			});
		}
		
		protected void onPause() {
			// TODO Auto-generated method stub
			Log.w("Session", "End Login Activity");
			LiSession.sessionEnd(mActivity);
			super.onPause();
		}
		protected void onResume() {
			// TODO Auto-generated method stub
			Log.w("Session", "Resume Login Activity");
			LiSession.sessionResume(mActivity);
			super.onResume();
		}
}

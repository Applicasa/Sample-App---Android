package com.example.appvilleegg.sampleApp;
 
import java.util.List;


import com.applicasa.ApplicasaManager.LiManager;
import com.applicasa.ApplicasaManager.LiManager.LiObject;
import com.applicasa.User.User;
import com.facebook.android.LiFacebook;

import com.appvilleegg.R;
import com.example.appvilleegg.main.MainActivity;

import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;
import applicasa.LiCore.Applicasa;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiLogger;
import applicasa.LiCore.LiSharedPrefrences;
import applicasa.LiCore.LiErrorHandler.ApplicasaResponse;
import applicasa.LiCore.communication.LiFilters;
import applicasa.LiCore.communication.LiQuery;
import applicasa.LiCore.communication.LiCallback.LiCallbackUser;
import applicasa.LiCore.communication.LiCallback.LiCallbackAction;
import applicasa.LiCore.communication.LiFilters.Condition;
import applicasa.LiCore.communication.LiFilters.Operation;
import applicasa.LiCore.communication.LiObjRequest.LiCallbackInitialize;
import applicasa.LiCore.communication.LiRequestConst.LiObjResponse;
import applicasa.LiCore.communication.LiRequestConst.QueryKind;
import applicasa.LiCore.communication.LiRequestConst.RequestAction;
import applicasa.kit.FaceBook.LiFBmanager;
import applicasa.kit.FaceBook.LiFacebookResponse;
import applicasa.kit.FaceBook.LiObjFacebookFriends;
import applicasa.kit.IAP.IAP;
 
public class LoginActivity extends Activity  {
 
	ImageButton btnLoginLater;
	ImageButton btnLogin;
	ImageButton btnLoginFB;
	ImageButton btnRegister;
	
	EditText email;
	EditText password;
	private LoginActivity mActivity;
	private TextView textForgot;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		mActivity = this;
		 
		btnLoginLater = (ImageButton)findViewById(R.id.btn_log_in_later);
		btnLogin = (ImageButton)findViewById(R.id.btn_log_in);
		btnLoginFB = (ImageButton)findViewById(R.id.btn_log_in_fb);
		btnRegister = (ImageButton)findViewById(R.id.btn_register);
		textForgot = (TextView)findViewById(R.id.txt_forgotPassword);
		email = (EditText)findViewById(R.id.txt_input_email);
		password = (EditText)findViewById(R.id.txt_input_password);
		
		
		textForgot.setClickable(true);
		
	}

 

	public void clickHandler(View v)
	{
		String userName;
		String pass;
		switch (v.getId())
		{
			case R.id.btn_log_in_later:
				finish();
			break;
			
			case R.id.btn_log_in:
				btnLogin.setClickable(false);
				userName = email.getText().toString();
				pass = password.getText().toString();
				 User.logInUserWithUserName(userName, pass, new LiCallbackUser () {
					
					public void onSuccessfull(RequestAction action) {
						finish();
					}
					
					public void onFailure(RequestAction action, LiErrorHandler error) {
						Toast.makeText(mActivity, "Can't Login User", Toast.LENGTH_LONG).show();
						btnLogin.setClickable(true);
					}
				});
				
			break;
			
			case R.id.btn_log_in_fb:
				btnLoginFB.setClickable(false);
				User.LoginWithFacebookUser(this, new String[]{"publish_stream"}, new LiFacebookResponse() {
						
						public void onGetFriendsResponse(LiObjResponse requestResponse,
								List<LiObjFacebookFriends> friendsList) {
							// TODO Auto-generated method stub
						}
						
						public void onFBLoginResponse(User currentUser) {
							// TODO Auto-generated method stub
							finish();
						}
						
						public void onFBError(LiErrorHandler error) {
							// TODO Auto-generated method stub
							btnLoginFB.setClickable(true);
							LiLogger.LogError("LoginActivity", error.getMessage());
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
			
			LiFBmanager.onActivityResult(requestCode, resultCode, data);
			finish();
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
			User.forgotPassword(userName,new LiCallbackUser() {
				
				public void onSuccessfull(RequestAction arg0) {
					// TODO Auto-generated method stub
					 Toast.makeText(getApplicationContext(), "An email with password was sent", Toast.LENGTH_SHORT).show();
					 textForgot.setClickable(true);
				}
				
				public void onFailure(RequestAction arg0, LiErrorHandler ex) {
					// TODO Auto-generated method stub
					textForgot.setClickable(true);
					 Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
				}
			});
		}
}

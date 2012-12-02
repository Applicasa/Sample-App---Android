package com.example.appvilleegg.sampleApp;
 
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import com.appvilleegg.R;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import applicasa.LiCore.Applicasa;
import applicasa.LiCore.LiFileCacher;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiErrorHandler.ApplicasaResponse;
import applicasa.LiCore.LiLogger;
import applicasa.LiCore.communication.LiCallback.LiCallbackAction;
import applicasa.LiCore.communication.LiCallback.LiCallbackGetCachedFile;
import applicasa.LiCore.communication.LiCallback.LiCallbackUser;
import applicasa.LiCore.communication.LiObjRequest;
import applicasa.LiCore.communication.LiObjRequest.LiCallbackInitialize;
import applicasa.LiCore.communication.LiRequestConst.RequestAction;
import applicasa.kit.IAP.IAP;
import applicasa.kit.IAP.IAP.LiCurrency;

import com.applicasa.ApplicasaManager.LiManager.LiObject;
import com.applicasa.User.User;
import com.applicasa.User.UserData.LiFieldUser;
 
public class RegisterActivity extends Activity  {
 
	
	private static final int PIC = 0;
	ImageButton btnRegister;
	EditText email;
	EditText password;
	EditText firstName;
	EditText lastName;
	EditText phoneNumber;
	ImageView view;
	Bitmap image;
	ImageView title_register;
	ProgressBar bar;
	
	private boolean imageChanged = false;
	boolean viewProfile = false;
	private String filePath;
	private RegisterActivity mActivity;
	private EditText userName;
//	private ProgressBar progressBar;
	private ProgressBar progressBarBig;
	
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		mActivity = this;
		Bundle extras = getIntent().getExtras();
		
		btnRegister = (ImageButton)findViewById(R.id.btn_register);
		
		userName = (EditText)findViewById(R.id.txt_input_userName);
		email = (EditText)findViewById(R.id.txt_input_email);
		password = (EditText)findViewById(R.id.txt_input_password);
		firstName = (EditText)findViewById(R.id.txt_firstName);
		lastName = (EditText)findViewById(R.id.txt_lastName);
		phoneNumber = (EditText)findViewById(R.id.txt_phone);
		title_register = (ImageView)findViewById(R.id.title_register);
		progressBarBig = (ProgressBar)findViewById(R.id.progressBarBig);
		
		view = (ImageView)findViewById(R.id.img_picture);
		
		if (  extras != null && (viewProfile=extras.getBoolean("MyProfile", false)))
		{
			User currnetUser = Applicasa.getCurrentUser();
			
			
			 getUserImage(currnetUser);
			 userName.setText(currnetUser.UserName);
			 userName.setClickable(false);
			 userName.setEnabled(false);
			 password.setEnabled(false);
			 userName.setFocusable(false);
			 password.setFocusable(false);
			 
			 email.setText(currnetUser.UserEmail);
			 firstName.setText(currnetUser.UserFirstName);
			 lastName.setText(currnetUser.UserLastName);
			 phoneNumber.setText(currnetUser.UserPhone);
			
			 // change button
			 btnRegister.setImageResource(R.drawable.btn_save);
			 title_register.setImageResource(R.drawable.title_my_profile);
			
		}

		view.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, PIC);
				imageChanged = true;
			}
		
		});
	}

	private void getUserImage(User currnetUser) {
		// TODO Auto-generated method stub
		if (!currnetUser.UserImage.equals(""))
		{
			LiFileCacher.GetBitmapFromCache(currnetUser.UserImage, new LiCallbackGetCachedFile() {
			
			public void onSuccessfull(InputStream is) {
				// TODO Auto-generated method stub
			}
			
			public void onFailure(LiErrorHandler error) {
				// TODO Auto-generated method stub
				LiLogger.LogWarning("Register", "getFile");
			}

			public void onSuccessfullBitmap(Bitmap bm) {
				// TODO Auto-generated method stub
				// decode the data, subsampling along the way
//				image = BitmapFactory.decodeStream(is);
				image = bm;
				
				view.setImageBitmap(image);
				DisplayMetrics metrics = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(metrics);

				int h = metrics.heightPixels;
				int w = metrics.widthPixels;
				
				view.setMaxHeight( h/4);
				view.setMaxWidth(w/3);
				view.setMinimumHeight( h/4);
				view.setMinimumWidth(w/3);
				view.invalidate();
				bar.setVisibility(View.INVISIBLE);
		        bar.invalidate();
			}
		});
		}
	}

	public void clickHandler(View v)
	{
		String userName;
		String pass;
		switch (v.getId())
		{
			
			case R.id.btn_register:
				
				btnRegister.setClickable(false);
				userName = this.userName.getText().toString();
				pass = password.getText().toString();
					final User newUser = new User();
					newUser.UserEmail = userName;
					
					if (firstName.getText() != null)
						newUser.UserFirstName = firstName.getText().toString();
					
					if (email.getText() != null)
						newUser.UserEmail = email.getText().toString();
					
					if (lastName.getText() != null)
						newUser.UserLastName = lastName.getText().toString();
					if (phoneNumber.getText() != null)
						newUser.UserPhone = phoneNumber.getText().toString();
					
					
					if (viewProfile)
					{
						progressBarBig.setVisibility(View.VISIBLE);
						User currentUser = User.getCurrentUser();
						currentUser.UserEmail = userName;
						currentUser.UserFirstName = firstName.getText().toString();
						currentUser.UserEmail = email.getText().toString();
						currentUser.UserLastName = lastName.getText().toString();
						currentUser.UserPhone = phoneNumber.getText().toString();
						currentUser.save(new LiCallbackAction() {
							
							public void onFailure(LiErrorHandler error) {
								// TODO Auto-generated method stub
								Toast.makeText(mActivity, "error occured "+error.getMessage(), Toast.LENGTH_LONG).show();
								progressBarBig.setVisibility(View.INVISIBLE);
							}
							
							public void onComplete(ApplicasaResponse response, String msg,
									RequestAction action, String itemID, LiObject liobject) {
								// TODO Auto-generated method stub
								Toast.makeText(mActivity, "Saved successfully", Toast.LENGTH_LONG).show();
								btnRegister.setClickable(true);
								progressBarBig.setVisibility(View.INVISIBLE);
							}
						});
						
						if (imageChanged)
						{
							progressBarBig.setVisibility(View.VISIBLE);
							currentUser.updloadFile(LiFieldUser.UserImage, filePath,new LiCallbackAction() {
								
								public void onFailure(LiErrorHandler error) {
									// TODO Auto-generated method stub
									LiFileCacher.GetFileFromCache(Applicasa.getCurrentUser().UserImage, new LiCallbackGetCachedFile() {
										
										public void onSuccessfull(InputStream in) {
											// TODO Auto-generated method stub
											Log.w("TAG", "Success");
											btnRegister.setClickable(true);
										}
										
										public void onFailure(LiErrorHandler error) {
											// TODO Auto-generated method stub
											Log.w("TAG", error.ErrorMessage);
										}

										public void onSuccessfullBitmap(
												Bitmap bitmap) {
											// TODO Auto-generated method stub
											
										}
									}); 
									
								}
								
								public void onComplete(ApplicasaResponse response, String msg,
										RequestAction action, String itemID, LiObject liobject) {
									// TODO Auto-generated method stub
									progressBarBig.setVisibility(View.INVISIBLE);
									LiLogger.LogInfo("UserImage", Applicasa.getCurrentUser().UserImage);
									Toast.makeText(mActivity, "Image loaded successfully", Toast.LENGTH_LONG).show();
								}
							}); 
						}
					}
					else // if not viewing, then registering user
					{
						progressBarBig.setVisibility(View.VISIBLE);
						 newUser.registerUser(userName, pass,new LiCallbackUser () {
							
							public void onSuccessfull(RequestAction action) {
								progressBarBig.setVisibility(View.INVISIBLE);
								if (filePath != null && !filePath.equals(""))
								{
									newUser.updloadFile(LiFieldUser.UserImage, filePath, null);
								}
								
								btnRegister.setClickable(true);
								refreshInventory();
								finish();
								
							}
							
							public void onFailure(RequestAction action, LiErrorHandler error) {
								btnRegister.setClickable(true);
								progressBarBig.setVisibility(View.INVISIBLE);
								Toast.makeText(mActivity, "Can't Register User", Toast.LENGTH_SHORT).show();
							}
						});
						
					}
			break;
		}
	}
	
	
	private void refreshInventory()
	{
		try {	IAP.refreshInventory();}
		catch (LiErrorHandler e) {	e.printStackTrace();	}

	}
	
	
	@Override
	/**
	 * Handles result after selecting an Image
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		
			
		switch(requestCode) { 
	case PIC:
	    if(resultCode == RESULT_OK){  
	        Uri selectedImage = data.getData();
	        
	     // sub-sampling options
    		BitmapFactory.Options opts = new BitmapFactory.Options();
    		opts.inSampleSize = 1;
           
	        filePath = getRealPathFromURI(selectedImage);
	
	        image = BitmapFactory.decodeFile(filePath,opts);
	        imageChanged = true;
	        
	        
	        view.setImageBitmap(image);

	        DisplayMetrics metrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(metrics);
			int h = metrics.heightPixels;
			int w = metrics.widthPixels;
			view.setMaxHeight( h/4);
			view.setMaxWidth(w/3);
			view.setMinimumHeight( h/4);
			view.setMinimumWidth(w/3);
	        view.invalidate();
	    }
	}
	}
	
	
	LiCallbackAction callback = new LiCallbackAction() {
		
		public void onFailure(LiErrorHandler error) {
			// TODO Auto-generated method stub
			
		}
		
		public void onComplete(ApplicasaResponse response, String msg,
				RequestAction action, String itemID, LiObject liobject) {
			// TODO Auto-generated method stub
			
			switch (action)
			{
				case UPLOAD_FILE:
					Log.w("error", "failed");
				default:
					 	//To open up a gallery browser
						Intent intent = new Intent();
						intent.setType("image/*");
						intent.setAction(Intent.ACTION_GET_CONTENT);
						startActivityForResult(Intent.createChooser(intent, "Select Picture"),20);
						
			}
			
		}
	};
	
	// And to convert the image URI to the direct file system path of the image file
	public String getRealPathFromURI(Uri contentUri) {

	        // can post image
	        String [] proj={MediaStore.Images.Media.DATA};
	        Cursor cursor = managedQuery( contentUri,
	                        proj, // Which columns to return
	                        null,       // WHERE clause; which rows to return (all rows)
	                        null,       // WHERE clause selection arguments (none)
	                        null); // Order-by clause (ascending by name)
	        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        cursor.moveToFirst();

	        return cursor.getString(column_index);
	}
	
}

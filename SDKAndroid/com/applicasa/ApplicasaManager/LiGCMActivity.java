package com.applicasa.ApplicasaManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import applicasa.LiCore.Applicasa;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.Push.LiCallbackPush;
import applicasa.LiCore.communication.LiObjRequest.LiCallbackInitialize;
import applicasa.LiJson.LiJSONException;
import applicasa.LiJson.LiJSONObject;

import com.applicasa.User.User;
import com.example.appvilleegg.main.MainActivity;

public class LiGCMActivity extends Activity implements LiCallbackInitialize{
	
	Activity mActivity;
	Bundle extras;
	final String TAG = LiGCMActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		/**
		 *  Set Push message content View
		 */
		//setContentView(R.layout.main);
		super.onCreate(savedInstanceState);
		
		extras = getIntent().getExtras();
		
		mActivity = this;
		
		
		if (!Applicasa.isInitialized())
		{
			try {
				LiManager.initialize(this, this);
					
			} catch (LiErrorHandler e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
			parsePush();
		
		
	}
	
	private void parsePush() {
		// TODO Auto-generated method stub
		if (extras != null) {
			String message = extras.getString("alert");
			String tag = extras.getString("tag");
			Log.i(TAG,"tag "+ tag);
			
			if (tag != null && tag != "")
			{
				try {
					String id = "";
					LiJSONObject jsonTag  = new LiJSONObject(tag);
					if (jsonTag.has("id"))
					{
						id = jsonTag.getString("id");
						User user  = new User(id);
						createDialog(user,message );
					}
					else
					{
						goToMain();
						return;
					}
				} catch (LiJSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Create alert dialog to update \ add dynamic item
	 */
	private void createDialog(final User user, String msg) {
		// TODO Auto-generated method stub
			  final AlertDialog.Builder alert = new AlertDialog.Builder(mActivity);
			  	LinearLayout lila1= new LinearLayout(mActivity);
			  	lila1.setOrientation(1); //1 is for vertical orientation
			  	final TextView message = new TextView(mActivity);
			  	message.setTextSize(25);
			  	final EditText text = new EditText(mActivity);
			  	lila1.addView(message);
			  	lila1.addView(text);
			  	
			    if (user == null)
			    {
			    	return;
			    }
			    else
			    {
			    	alert.setTitle("Send message to: "+user.UserName);
			    	text.setHint("Enter message");
			    	message.setText(msg);
			    }
			    
			    alert.setView(lila1);
			    alert.setPositiveButton("Send", new DialogInterface.OnClickListener() {
			        public void onClick(final DialogInterface dialog, int whichButton) {
			        	sendPushToUser(user, text.getText().toString());
			        }
			    });

			    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			            dialog.cancel();
			            goToMain();
			        }
			    });
			    
			    alert.show();  
	}
	
	private void goToMain() {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		finish();
	}

	private void sendPushToUser(User user, String text)
	{
		LiGCMPushMessage message = new LiGCMPushMessage();
		
		message.setMessage(text);
		message.setBadge(1);
		message.addReceipient(user);
		message.sendPush(new LiCallbackPush() {
			
			public void onFailure(LiErrorHandler arg0) {
				// TODO Auto-generated method stub
				goToMain();
			}
			
			public void onComplete() {
				// TODO Auto-generated method stub
				Toast.makeText(mActivity, "Message sent", Toast.LENGTH_SHORT).show();
				goToMain();
			}
		});
	}

	public void onCompleteInitialize() {
		// TODO Auto-generated method stub
		parsePush();
	}

	public void onFailure(LiErrorHandler arg0) {
		// TODO Auto-generated method stub
		goToMain();
	}
}
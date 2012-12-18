package com.example.appvilleegg.Global;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.Push.LiCallbackPush;
import applicasa.LiJson.LiJSONException;

import com.applicasa.ApplicasaManager.LiGCMActivity;
import com.applicasa.ApplicasaManager.LiGCMPushMessage;
import com.applicasa.User.User;

public class Global {

	
	public static void SendPushToUser(final Activity mActivity, User user, String text)
	{
		LiGCMPushMessage message = new LiGCMPushMessage();
		
		message.setMessage(text);
		try {
			// add the sender ID, to reply to
			message.addTag("id", User.getCurrentUser().UserID);
		} catch (LiJSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		message.setBadge(1);
		message.setSound("egg");
		message.addReceipient(user);
		message.sendPush(new LiCallbackPush() {
			
			public void onFailure(LiErrorHandler arg0) {
				// TODO Auto-generated method stub
			}
			
			public void onComplete() {
				// TODO Auto-generated method stub
				Toast.makeText(mActivity, "Message sent", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	/**
	 * Create alert dialog to update \ add dynamic item
	 */
	public static void createDialog(final User user, final String msg, final Activity mActivity) {
		// TODO Auto-generated method stub
			  final AlertDialog.Builder alert = new AlertDialog.Builder(mActivity);
			  	LinearLayout lila1= new LinearLayout(mActivity);
			  	lila1.setOrientation(1); //1 is for vertical orientation
			  	
			  	final EditText text = new EditText(mActivity);
			  	
			  	if (msg != null)
			  	{
			  		
			  		final TextView message = new TextView(mActivity);
			  		message.setTextSize(25);
			  		lila1.addView(message);
			  		message.setText(msg);
			  	}
			  	lila1.addView(text);
			  	
			    if (user == null)
			    {
			    	return;
			    }
			    else
			    {
			    	alert.setTitle("Send message to: " +((user.UserName!=null && !user.UserName.equals(""))?user.UserName:user.UserFirstName +" "+  user.UserLastName));
			    	text.setHint("Enter message");
			    }
			    
			    alert.setView(lila1);
			    alert.setPositiveButton("Send", new DialogInterface.OnClickListener() {
			        public void onClick(final DialogInterface dialog, int whichButton) {
			        	Global.SendPushToUser(mActivity,user, text.getText().toString());
			        	if (msg != null)
			        		LiGCMActivity.goToMain();
			        	
			        }
			    });

			    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			            dialog.cancel();
			            if (msg != null)
			        		LiGCMActivity.goToMain();
			          
			        }
			    });
			    
			    alert.show();  
	}
}

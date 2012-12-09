package com.example.appvilleegg.sampleApp;

import java.util.Currency;
import java.util.List;

import com.appvilleegg.R;
import com.example.appvilleegg.adapters.FriendsArrayAdapter;
import com.example.appvilleegg.adapters.UserRadiusArrayAdapter;

import com.applicasa.ApplicasaManager.LiCallbackQuery.LiUserGetArrayCallback;
import com.applicasa.ApplicasaManager.LiManager.LiObject;
import com.applicasa.ApplicasaManager.LiGCMPushMessage;
import com.applicasa.ApplicasaManager.LiSession;
import com.applicasa.ApplicasaManager.LiUserLocation;
import com.applicasa.Dynamic.Dynamic;
import com.applicasa.User.User;
import com.applicasa.User.UserData.LiFieldUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import applicasa.LiCore.Applicasa;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiLocation;
import applicasa.LiCore.LiLocationCallback;
import applicasa.LiCore.LiLogger;
import applicasa.LiCore.LiErrorHandler.ApplicasaResponse;
import applicasa.LiCore.Push.LiCallbackPush;
import applicasa.LiCore.Push.LiObjPushMessage;
import applicasa.LiCore.communication.LiQuery;
import applicasa.LiCore.communication.LiCallback.LiCallbackAction;
import applicasa.LiCore.communication.LiRequestConst.LiObjResponse;
import applicasa.LiCore.communication.LiRequestConst.QueryKind;
import applicasa.LiCore.communication.LiRequestConst.RequestAction;
import applicasa.LiJson.LiJSONException;
import applicasa.kit.FaceBook.LiFacebookResponse;
import applicasa.kit.FaceBook.LiObjFacebookFriends;

public class UsersRadiusListActivity extends ListActivity implements LiLocationCallback{

	static UserRadiusArrayAdapter adapter;
	static Activity mActivity;
	 ProgressBar bar;
     List<User> users;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		mActivity = this;
		bar = (ProgressBar)findViewById(R.id.progressBar);
		bar.setVisibility(View.VISIBLE);
		
		LiUserLocation.EnableGps();
		LiUserLocation.EnableNetwork();
		
		LiUserLocation.getLocation(this);
		
		LiSession.SessionStart(mActivity,null);
		
	}
	

	public void getLastBestUpdatedLocation(LiLocation location,
			boolean updatedSuccesful) {
		// TODO Auto-generated method stub
		LiLogger.LogInfo("Location", "getLastBestUpdatedLocation");
	}

	public void getLastBestLocation(LiLocation location) {
		// TODO Auto-generated method stub
		LiLogger.LogInfo("Location", "getLastBestLocation");
		
		LiQuery query = new LiQuery();
		
		query.setGeoFilter(LiFieldUser.UserLocation, location, 10000000);
		 
		User.getWithQuery(query, QueryKind.LITE, new LiUserGetArrayCallback() {
			
			public void onGetUserFailure(LiErrorHandler error) {
				// TODO Auto-generated method stub
				
			}
			
			public void onGetUserComplete(List<User> items) {
				// TODO Auto-generated method stub
				users = items;
				adapter = new UserRadiusArrayAdapter(mActivity,items);
				setListAdapter(adapter);
				bar.setVisibility(View.INVISIBLE);
			}
		});
	}

	public void onFailure(LiErrorHandler Error) {
		// TODO Auto-generated method stub
		LiLogger.LogInfo("Location", Error.getMessage());
	}
	
	 @Override
     protected void onListItemClick(ListView l, View v, int position, long id) {
		 LiLogger.LogInfo("position", String.valueOf(position));
		 User user = users.get(position);
//		 sendPushToUser(user,"I'm only " +String.valueOf((int)(user.DistanceFromCurrent*1000)) +"m From U");
		 createDialog(users.get(position));
	    }
	
	 

		/**
		 * Create alert dialog to update \ add dynamic item
		 */
		private void createDialog(final User user) {
			// TODO Auto-generated method stub
				  final AlertDialog.Builder alert = new AlertDialog.Builder(mActivity);
				  	LinearLayout lila1= new LinearLayout(mActivity);
				  	lila1.setOrientation(1); //1 is for vertical orientation
				    final EditText text = new EditText(mActivity);
				  	lila1.addView(text);
				  	
				    if (user == null)
				    {
				    	return;
				    }
				    else
				    {
				    	alert.setTitle("Send message to: "+user.UserName);
				    	text.setHint("Enter message");
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
				        }
				    });
				    
				    alert.show();  
		}
		
		
	private void sendPushToUser(User user, String text)
	{
		LiGCMPushMessage message = new LiGCMPushMessage();
		
//		message.setMessage("I'm only " +String.valueOf((int)(user.DistanceFromCurrent*1000)) +"m From U");
//		message.setMessage("2:1 to manchester united");
		message.setMessage(text);
		try {
			message.addTag("id", user.UserID);
		} catch (LiJSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		message.setBadge(1);
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
	protected void onPause() {
		// TODO Auto-generated method stub
		LiSession.SessionEnd(mActivity);
		super.onPause();
	}
	protected void onResume() {
		// TODO Auto-generated method stub
		LiSession.SessionResume(mActivity);
		super.onResume();
	}
}

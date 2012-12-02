package com.example.appvilleegg.sampleApp;

import java.util.Currency;
import java.util.List;

import com.appvilleegg.R;
import com.example.appvilleegg.adapters.FriendsArrayAdapter;
import com.example.appvilleegg.adapters.UserRadiusArrayAdapter;

import com.applicasa.ApplicasaManager.LiCallbackQuery.LiUserGetArrayCallback;
import com.applicasa.ApplicasaManager.LiUserLocation;
import com.applicasa.User.User;
import com.applicasa.User.UserData.LiFieldUser;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import applicasa.LiCore.Applicasa;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiLocation;
import applicasa.LiCore.LiLocationCallback;
import applicasa.LiCore.LiLogger;
import applicasa.LiCore.Push.LiCallbackPush;
import applicasa.LiCore.Push.LiObjPushMessage;
import applicasa.LiCore.communication.LiQuery;
import applicasa.LiCore.communication.LiRequestConst.LiObjResponse;
import applicasa.LiCore.communication.LiRequestConst.QueryKind;
import applicasa.LiJson.LiJSONException;
import applicasa.kit.FaceBook.LiFacebookResponse;
import applicasa.kit.FaceBook.LiObjFacebookFriends;

public class UsersRadiusListActivity extends ListActivity implements LiLocationCallback{

	static UserRadiusArrayAdapter adapter;
	static Activity act;
	 ProgressBar bar;
     List<User> users;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		act = this;
		bar = (ProgressBar)findViewById(R.id.progressBar);
		bar.setVisibility(View.VISIBLE);
		
		LiUserLocation.EnableGps();
		LiUserLocation.EnableNetwork();
		
		LiUserLocation.getLocation(this);
		
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
		query.setGeoFilter(LiFieldUser.UserLocation, location, 1000000);
		 
		User.getWithQuery(query, QueryKind.LITE, new LiUserGetArrayCallback() {
			
			public void onGetUserFailure(LiErrorHandler error) {
				// TODO Auto-generated method stub
				
			}
			
			public void onGetUserComplete(List<User> items) {
				// TODO Auto-generated method stub
				users = items;
				adapter = new UserRadiusArrayAdapter(act,items);
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
		 sendPushToUser(users.get(position));
	    }
	
	private void sendPushToUser(User user)
	{
		LiObjPushMessage push = new LiObjPushMessage();
		push.mMSG="I'm only " +String.valueOf((int)(user.DistanceFromCurrent*1000)) +"m From U";
		push.mBadge = 1;
	
		push.addReceipientUserIDList(user.UserID);
		push.sendAsync(new LiCallbackPush() {
			
			public void onFailure(LiErrorHandler arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void onComplete() {
				// TODO Auto-generated method stub
				Toast.makeText(act, "Message sent", Toast.LENGTH_SHORT).show();
			}
		});
	}
}

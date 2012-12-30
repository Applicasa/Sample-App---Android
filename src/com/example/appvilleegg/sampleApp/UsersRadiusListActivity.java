package com.example.appvilleegg.sampleApp;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import com.appvilleegg.R;
import com.example.appvilleegg.Global.Global;
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
import applicasa.LiCore.communication.LiFilters;
import applicasa.LiCore.communication.LiQuery;
import applicasa.LiCore.communication.LiCallback.LiCallbackAction;
import applicasa.LiCore.communication.LiFilters.Condition;
import applicasa.LiCore.communication.LiFilters.Operation;
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
		
		LiUserLocation.enableGps();
		LiUserLocation.enableNetwork();
		
		LiUserLocation.getLocation(this);
		
		LiSession.sessionStart(mActivity,null);
		
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
		// Add Filter to receive only users with username or first name or last name
		
		query.setGeoFilter(LiFieldUser.UserLocation, location, 10000000);
		 
		User.getArrayWithQuery(query, QueryKind.LIGHT, new LiUserGetArrayCallback() {
			
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
		 createDialog(users.get(position));
	    }
	
	 

		/**
		 * Create alert dialog to update \ add dynamic item
		 */
		private void createDialog(final User user) {
			// TODO Auto-generated method stub
				 Global.createDialog(user, null, mActivity);
		}
		
	protected void onPause() {
		// TODO Auto-generated method stub
		LiSession.sessionEnd(mActivity);
		LiUserLocation.disableGps();
		LiUserLocation.disableNetwork();
		super.onPause();
	}
	protected void onResume() {
		// TODO Auto-generated method stub
		LiSession.sessionResume(mActivity);
		super.onResume();
	}
}

package com.example.appvilleegg.sampleApp;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiLocation;
import applicasa.LiCore.LiLocationCallback;
import applicasa.LiCore.LiLogger;
import applicasa.LiCore.communication.LiFilters;
import applicasa.LiCore.communication.LiFilters.Condition;
import applicasa.LiCore.communication.LiFilters.Operation;
import applicasa.LiCore.communication.LiQuery;
import applicasa.LiCore.communication.LiRequestConst.QueryKind;

import com.applicasa.ApplicasaManager.LiCallbackQuery.LiUserGetArrayCallback;
import com.applicasa.ApplicasaManager.LiSession;
import com.applicasa.ApplicasaManager.LiUserLocation;
import com.applicasa.User.User;
import com.applicasa.User.UserData.LiFieldUser;
import com.appvilleegg.R;
import com.example.appvilleegg.adapters.UserRadiusArrayAdapter;

public class UsersRadiusListActivity extends ListActivity implements LiLocationCallback{

	static UserRadiusArrayAdapter adapter;
	 ProgressBar bar;
     List<User> users;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		bar = (ProgressBar)findViewById(R.id.progressBar);
		bar.setVisibility(View.VISIBLE);
		
		LiUserLocation.enableGps();
		LiUserLocation.enableNetwork();
		LiUserLocation.getLocation(this);
		
		LiSession.sessionStart(this);
		
	}
	

	public void getLastBestUpdatedLocation(LiLocation location,
			boolean updatedSuccesful) {
		// TODO Auto-generated method stub
		LiLogger.logInfo("Location", "getLastBestUpdatedLocation");
	}

	public void getLastBestLocation(LiLocation location) {
		// TODO Auto-generated method stub
		LiLogger.logInfo("Location", "getLastBestLocation");
		
		LiQuery query = new LiQuery();
		// Add Filter to receive only users with username or first name or last name
		List<LiFilters> filtersList = new ArrayList<LiFilters>();
		filtersList.add(new LiFilters(LiFieldUser.UserFirstName,Operation.NOT_EQUAL,""));
		filtersList.add(new LiFilters(LiFieldUser.UserLastName,Operation.NOT_EQUAL,""));
		filtersList.add(new LiFilters(LiFieldUser.UserName,Operation.NOT_EQUAL,""));
		
		query.Lifilters = new  LiFilters(filtersList, Condition.OR);
		query.setGeoFilter(LiFieldUser.UserLocation, location, 10000000);
		 
		User.getArrayWithQuery(query, QueryKind.LIGHT, new LiUserGetArrayCallback() {
			
			public void onGetUserFailure(LiErrorHandler error) {
				// TODO Auto-generated method stub
				
			}
			
			public void onGetUserComplete(List<User> items) {
				// TODO Auto-generated method stub
				users = items;
				adapter = new UserRadiusArrayAdapter(UsersRadiusListActivity.this,items);
				setListAdapter(adapter);
				bar.setVisibility(View.INVISIBLE);
			}
		});
	}

	public void onFailure(LiErrorHandler Error) {
		// TODO Auto-generated method stub
		LiLogger.logInfo("Location", Error.getMessage());
	}
	
	 @Override
     protected void onListItemClick(ListView l, View v, int position, long id) {
		 LiLogger.logInfo("position", String.valueOf(position));
		
		 Intent i = new Intent(this, ChatActivity.class);
		 i.putExtra("id",users.get(position).UserID);
		 startActivity(i); 
	    }
	
	 
		
	protected void onPause() {
		// TODO Auto-generated method stub
		LiSession.sessionEnd(this);
		LiUserLocation.disableGps();
		LiUserLocation.disableNetwork();
		super.onPause();
	}
	protected void onResume() {
		// TODO Auto-generated method stub
		LiSession.sessionResume(this);
		super.onResume();
	}
}

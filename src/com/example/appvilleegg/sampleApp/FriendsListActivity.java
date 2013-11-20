package com.example.appvilleegg.sampleApp;

import java.util.Arrays;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.communication.LiRequestConst.LiObjResponse;
import applicasa.kit.facebook.LiFBmanager;
import applicasa.kit.facebook.LiFacebookResponse.LiFacebookResponseGetFriends;
import applicasa.kit.facebook.LiFacebookResponse.LiFacebookResponseLogin;
import applicasa.kit.facebook.LiObjFacebookFriends;

import com.applicasa.ApplicasaManager.LiSession;
import com.applicasa.User.User;
import com.appvilleegg.R;
import com.example.appvilleegg.adapters.FriendsArrayAdapter;
import com.facebook.Session;
import com.facebook.Session.NewPermissionsRequest;

public class FriendsListActivity extends ListActivity {

	static FriendsArrayAdapter adpater;
	 ProgressBar bar;
     
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		bar = (ProgressBar)findViewById(R.id.progressBar);
		bar.setVisibility(View.VISIBLE);
		
		LiSession.sessionStart(this);
		
		login();
	}
	
	protected void onPause() {
		// TODO Auto-generated method stub
		LiSession.sessionEnd(this);
		super.onPause();
	}
	protected void onResume() {
		// TODO Auto-generated method stub
		LiSession.sessionResume(this);
		super.onResume();
	}
	
	
	private void requestFriends()
	{
		User.getFacebookFriendsWithUser(this,new LiFacebookResponseGetFriends() {
			
			public void onGetFriendsResponse(LiObjResponse requestResponse,
					List<LiObjFacebookFriends> friendsList) {
				// TODO Auto-generated method stub
				adpater = new FriendsArrayAdapter(FriendsListActivity.this, friendsList);
				setListAdapter(adpater);
				bar.setVisibility(View.INVISIBLE);
			}
			
			public void onFBError(LiErrorHandler error) {
				// TODO Auto-generated method stub
				Toast.makeText(FriendsListActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
				finish();
			}
		});
	}
	private void login()
	{
		User.loginWithFacebookUserFromActivity(FriendsListActivity.this, new LiFacebookResponseLogin() {
		
		public void onFBLoginResponse(User currentUser) {
			// TODO Auto-generated method stub
			
			if (!canPost())
				 requestPermission(Arrays.asList(new String[]{"publish_stream"}));
				
			requestFriends();
		}
		
		public void onFBError(LiErrorHandler error) {
			// TODO Auto-generated method stub
			
		}
	});
	}
	
	public static boolean canPost()
	{
		for (String permission : Session.getActiveSession().getPermissions())
		{
			if (permission.equalsIgnoreCase("publish_stream"))
				return true;
		}
		return false;
	}
	
	private void requestPermission(List<String>permissions)
	{
		NewPermissionsRequest publish = new NewPermissionsRequest(this, permissions);
		Session session = Session.getActiveSession();
		
		session.requestNewPublishPermissions(publish);

	}
	
	/**
	 * Handles result from fb login
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (LiFBmanager.FACEBOOK == requestCode)
			User.onActivityResult(this,requestCode, resultCode, data);
	}
}

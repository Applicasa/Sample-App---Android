package com.example.appvilleegg.sampleApp;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import applicasa.LiCore.Applicasa;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.communication.LiRequestConst.LiObjResponse;
import applicasa.kit.facebook.LiFacebookResponse;
import applicasa.kit.facebook.LiFacebookResponse.LiFacebookResponseGetFriends;
import applicasa.kit.facebook.LiObjFacebookFriends;

import com.applicasa.ApplicasaManager.LiSession;
import com.applicasa.User.User;
import com.appvilleegg.R;
import com.example.appvilleegg.adapters.FriendsArrayAdapter;

public class FriendsListActivity extends ListActivity {

	static FriendsArrayAdapter adpater;
	static Activity mActivity;
	 ProgressBar bar;
     
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		mActivity = this;
		bar = (ProgressBar)findViewById(R.id.progressBar);
		bar.setVisibility(View.VISIBLE);
		
		LiSession.sessionStart(mActivity);
		
		
		/**
		 * see if the user is register to facebook, if so retrieves his friends. If not, show an error Via toast message
		 */
		if (User.getCurrentUser().UserIsRegisteredFacebook)
		 {
			 User.getFacebookFriendsWithUser(this,new LiFacebookResponseGetFriends() {
				
				public void onGetFriendsResponse(LiObjResponse requestResponse,
						List<LiObjFacebookFriends> friendsList) {
					// TODO Auto-generated method stub
					adpater = new FriendsArrayAdapter(mActivity, friendsList);
					setListAdapter(adpater);
					bar.setVisibility(View.INVISIBLE);
				}
				
				public void onFBError(LiErrorHandler error) {
					// TODO Auto-generated method stub
					Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_LONG).show();
					finish();
				}
			});
		 }
		else
		{
			bar.setVisibility(View.INVISIBLE);
			Toast.makeText(mActivity, "please login to facebook", Toast.LENGTH_LONG).show();
			finish();
		}
	}
	
	protected void onPause() {
		// TODO Auto-generated method stub
		LiSession.sessionEnd(mActivity);
		super.onPause();
	}
	protected void onResume() {
		// TODO Auto-generated method stub
		LiSession.sessionResume(mActivity);
		super.onResume();
	}
}

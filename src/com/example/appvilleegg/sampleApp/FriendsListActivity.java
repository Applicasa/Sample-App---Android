package com.example.appvilleegg.sampleApp;

import java.util.List;

import com.applicasa.User.User;
import com.appvilleegg.R;
import com.example.appvilleegg.adapters.FriendsArrayAdapter;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import applicasa.LiCore.Applicasa;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.communication.LiRequestConst.LiObjResponse;
import applicasa.kit.FaceBook.LiFacebookResponse;
import applicasa.kit.FaceBook.LiObjFacebookFriends;

public class FriendsListActivity extends ListActivity {

	static FriendsArrayAdapter adpater;
	static Activity act;
	 ProgressBar bar;
     
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		act = this;
		bar = (ProgressBar)findViewById(R.id.progressBar);
		bar.setVisibility(View.VISIBLE);
		/**
		 * see if the user is register to facebook, if so retreives his friends. If not, show an error Via toast message
		 */
		if (Applicasa.getCurrentUser().UserIsRegisteredFacebook)
		 {
			 User.GetFacebookFriendsWithUser(this, new LiFacebookResponse() {
				
				public void onGetFriendsResponse(LiObjResponse requestResponse,
						List<LiObjFacebookFriends> friendsList) {
					// TODO Auto-generated method stub
					adpater = new FriendsArrayAdapter(act, friendsList);
					setListAdapter(adpater);
					bar.setVisibility(View.INVISIBLE);
					
				}
				
				public void onFBLoginResponse(User currentUser) {
					// TODO Auto-generated method stub
					
				}
				
				public void onFBError(LiErrorHandler error) {
					// TODO Auto-generated method stub
					Toast.makeText(act, error.getMessage(), Toast.LENGTH_LONG).show();
					finish();
				}
			});
		 }
		else
		{
			bar.setVisibility(View.INVISIBLE);
			Toast.makeText(act, "please login to facebook", Toast.LENGTH_LONG).show();
			finish();
		}
	}
}

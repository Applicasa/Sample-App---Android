package com.applicasa.ApplicasaManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import applicasa.LiCore.Applicasa;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.communication.LiObjRequest.LiCallbackInitialize;
import applicasa.LiCore.communication.LiRequestConst.QueryKind;
import applicasa.LiJson.LiJSONException;
import applicasa.LiJson.LiJSONObject;

import com.applicasa.ApplicasaManager.LiCallbackQuery.LiUserGetByIDCallback;
import com.applicasa.User.User;
import com.appvilleegg.R;
import com.example.appvilleegg.Global.Global;
import com.example.appvilleegg.main.MainActivity;

public class LiGCMActivity extends Activity implements LiCallbackInitialize{
	
	static Activity mActivity;
	Bundle extras;
	final String TAG = LiGCMActivity.class.getSimpleName();
	ProgressBar progressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		/**
		 *  Set Push message content View
		 */
		setContentView(R.layout.msg);
		super.onCreate(savedInstanceState);
		
		extras = getIntent().getExtras();
		
		mActivity = this;
		
		 progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
		 RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		 
		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		progressBar.setLayoutParams(params);

		addContentView(progressBar, params);
		
		if (!Applicasa.isInitialized())
		{
			LiManager.initialize(this, this);
		}
		else
			parsePush();
		
		
	}
	
	private void parsePush() {
		// TODO Auto-generated method stub
		if (extras != null) {
			final String message = extras.getString("alert");
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
						User.getByID(id, QueryKind.LITE, new LiUserGetByIDCallback() {
							
							public void onGetUserFailure(LiErrorHandler error) {
								// TODO Auto-generated method stub
																
							}
							
							public void onGetUserComplete(User user) {
								// TODO Auto-generated method stub
								progressBar.setVisibility(View.INVISIBLE);
								Global.createDialog(user, message, mActivity);
							}
						});

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


	public static  void goToMain() {
		// TODO Auto-generated method stub
		Intent i = new Intent(mActivity, MainActivity.class);
		mActivity.startActivity(i);
		mActivity.finish();
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
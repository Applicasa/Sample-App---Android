package com.applicasa.ApplicasaManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import applicasa.LiCore.Applicasa;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.communication.LiObjRequest.LiCallbackInitialize;
import applicasa.LiJson.LiJSONException;
import applicasa.LiJson.LiJSONObject;

import com.appvilleegg.R;
import com.example.appvilleegg.main.MainActivity;
import com.example.appvilleegg.sampleApp.ChatActivity;

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
		
		 progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleSmall);
		 RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		 
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
						 Intent i = new Intent(this, ChatActivity.class);
						 i.putExtra("id",id);
						 startActivity(i); 
						 finish();
					}
				} catch (LiJSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}


	public void onCompleteInitialize() {
		// TODO Auto-generated method stub
		parsePush();
	}

	public void onFailure(LiErrorHandler arg0) {
		// TODO Auto-generated method stub
		 Intent i = new Intent(this, MainActivity.class);
		 startActivity(i); 
		 finish();
	}
}
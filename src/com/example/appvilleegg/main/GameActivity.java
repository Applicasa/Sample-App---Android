package com.example.appvilleegg.main;

import com.applicasa.ApplicasaManager.LiSession;

import com.appvilleegg.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.promotion.sessions.LiSessionManager.LiGameResult;

public class GameActivity extends Activity {

	Button mStart,mStop,mResume,mPause;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity);
		mPause = (Button)findViewById(R.id.btn_pause);
		mStart = (Button)findViewById(R.id.btn_start);
		mResume = (Button)findViewById(R.id.btn_Resume);
		mStop = (Button)findViewById(R.id.btn_stop);
	}
	
	protected void onResume(){
		try {
			LiSession.GameResume();
		} catch (LiErrorHandler e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		try {
			LiSession.GamePause();
		} catch (LiErrorHandler e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	public void onClickHandler(View v)
	{
		int id = v.getId();
		try {
			switch (id)
			{
				case R.id.btn_start:
					LiSession.GameStart("new level",null);
					break;
				case R.id.btn_stop:
			
					LiSession.GameFinished(LiGameResult.LOSE, 5, 4, 3, 2, null);
				
					break;
				case R.id.btn_Resume:
					LiSession.GameResume();
					break;
				case R.id.btn_pause:
					LiSession.GamePause();
					break;
			}
		} catch (LiErrorHandler e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

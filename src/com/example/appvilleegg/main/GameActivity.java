package com.example.appvilleegg.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.promotion.sessions.LiSessionManager.LiGameResult;

import com.applicasa.ApplicasaManager.LiSession;
import com.appvilleegg.R;

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
			LiSession.gameResume();
		} catch (LiErrorHandler e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		try {
			LiSession.gamePause();
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
					LiSession.gameStart("new level",null);
					Toast.makeText(this, "Game Started", Toast.LENGTH_SHORT).show();
					break;
				case R.id.btn_stop:
			
					createDialog();
				
					break;
				case R.id.btn_Resume:
					LiSession.gameResume();
					Toast.makeText(this, "Game Resumed", Toast.LENGTH_SHORT).show();
					break;
				case R.id.btn_pause:
					LiSession.gamePause();
					Toast.makeText(this, "Game Paused", Toast.LENGTH_SHORT).show();
					break;
			}
		} catch (LiErrorHandler e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Create alert dialog to update \ add dynamic item
	 */
	public  void createDialog() {
		// TODO Auto-generated method stub
			  final AlertDialog.Builder alert = new AlertDialog.Builder(this);
			  alert.setTitle("Game End ");
			  	LinearLayout lila1= new LinearLayout(this);
			  	lila1.setOrientation(1); //1 is for vertical orientation
			  	final EditText mainCurrency = new EditText(this);
			  	mainCurrency.setInputType(InputType.TYPE_CLASS_NUMBER);
			  	mainCurrency.setHint("Main Currency");
			  	lila1.addView(mainCurrency);
			  	
			  	final EditText secondaryCurrency = new EditText(this);
			  	secondaryCurrency.setInputType(InputType.TYPE_CLASS_NUMBER);
			  	secondaryCurrency.setHint("Secondary Currency");
			  	lila1.addView(secondaryCurrency);
			  	
			  	final EditText score = new EditText(this);
			  	score.setInputType(InputType.TYPE_CLASS_NUMBER);
			  	score.setHint("Score");
			  	lila1.addView(score);
			  	
				final EditText bonus = new EditText(this);
				bonus.setInputType(InputType.TYPE_CLASS_NUMBER);
				bonus.setHint("Bonus");
			  	lila1.addView(bonus);
			  	
			    	
			    
			    alert.setView(lila1);
			    alert.setPositiveButton("WIN", new DialogInterface.OnClickListener() {
			        public void onClick(final DialogInterface dialog, int whichButton) {
			        	try {
							LiSession.gameFinished(LiGameResult.WIN, 
									Integer.valueOf((mainCurrency.getText().toString()!= "")?mainCurrency.getText().toString() :"0"), 
									Integer.valueOf((secondaryCurrency.getText().toString()!= "") ?secondaryCurrency.getText().toString():"0" ),
									Integer.valueOf((score.getText().toString()!= "")?score.getText().toString():"0"),
									Integer.valueOf((bonus.getText().toString() !="")?bonus.getText().toString():"0"),
									null);
						} catch (LiErrorHandler e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
			    });

			    alert.setNegativeButton("LOSE", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			        	try {
							LiSession.gameFinished(LiGameResult.LOSE,
									Integer.valueOf((mainCurrency.getText().toString()!= "")?mainCurrency.getText().toString() :"0"), 
									Integer.valueOf((secondaryCurrency.getText().toString()!= "") ?secondaryCurrency.getText().toString():"0" ),
									Integer.valueOf((score.getText().toString()!= "")?score.getText().toString():"0"),
									Integer.valueOf((bonus.getText().toString() !="")?bonus.getText().toString():"0"),
									null);
						} catch (LiErrorHandler e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
			    });
			    alert.setNeutralButton("EXIT", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			        	try {
							LiSession.gameFinished(LiGameResult.EXIT,
									Integer.valueOf((mainCurrency.getText().toString()!= "")?mainCurrency.getText().toString() :"0"), 
									Integer.valueOf((secondaryCurrency.getText().toString()!= "") ?secondaryCurrency.getText().toString():"0" ),
									Integer.valueOf((score.getText().toString()!= "")?score.getText().toString():"0"),
									Integer.valueOf((bonus.getText().toString() !="")?bonus.getText().toString():"0"),
									null);
						} catch (LiErrorHandler e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
			    });
			    
			    alert.show();  
	}
}

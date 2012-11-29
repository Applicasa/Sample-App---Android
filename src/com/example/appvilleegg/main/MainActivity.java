package com.example.appvilleegg.main;

import java.util.Iterator;
import java.util.List;

import com.applicasa.ApplicasaManager.LiCallbackQuery.LiDynamicGetArrayCallback;
import com.applicasa.ApplicasaManager.LiCallbackQuery.LiDynamicGetByIDCallback;
import com.applicasa.ApplicasaManager.LiManager;
import com.applicasa.ApplicasaManager.LiPromo;
import com.applicasa.ApplicasaManager.LiSession;
import com.applicasa.ApplicasaManager.LiUserLocation;
import com.applicasa.Dynamic.Dynamic;
import com.applicasa.Promotion.Promotion;
import com.applicasa.User.User;
import com.appvilleegg.R;
import com.example.appvilleegg.sampleApp.FriendsListActivity;
import com.example.appvilleegg.sampleApp.LoginActivity;
import com.example.appvilleegg.sampleApp.RegisterActivity;
import com.example.appvilleegg.sampleApp.TabsFragmentActivity;
import com.example.appvilleegg.sampleApp.UsersRadiusListActivity;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import applicasa.LiCore.Applicasa;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiLocation;
import applicasa.LiCore.LiLogger;
import applicasa.LiCore.Push.LiObjPushMessage;
import applicasa.LiCore.communication.LiCallback.LiCallbackUser;
import applicasa.LiCore.communication.LiObjRequest.LiCallbackInitialize;
import applicasa.LiCore.communication.LiRequestConst.QueryKind;
import applicasa.LiCore.communication.LiRequestConst.RequestAction;
import applicasa.LiCore.promotion.sessions.LiPromotionCallback;
import applicasa.LiCore.promotion.sessions.LiSessionManager;
import applicasa.LiCore.promotion.sessions.LiPromotionCallback.LiPromotionAction;
import applicasa.LiCore.promotion.sessions.LiPromotionCallback.LiPromotionResult;
import applicasa.LiCore.promotion.sessions.LiSessionManager.LiGameResult;
import applicasa.LiJson.LiJSONException;
import applicasa.kit.IAP.IAP;

public class MainActivity extends Activity implements LiCallbackInitialize {
	
	Context cont;
	List<Dynamic> arrDynamic;
	ListView lvMain;
	Context context;
	Activity mActivity = this;
	ImageButton btn_login;
	ImageButton btn_play;
	ImageButton btn_Store;
	ImageButton btn_Radius_Friends;
	ImageButton btn_myProfile;
	ImageButton btn_fb_Friends;;
	ProgressBar bar;
	private static ImageView spProfile;
	private static ImageView usProfile;

		
	/**
	 * Promotion Callback listener
	 */
	LiPromotionCallback promoCallback = new LiPromotionCallback() {
		
		@Override
		public void onHasPromotionToDisplay(List<Promotion> promotions) {
			// TODO Auto-generated method stub
			promotions.get(0).show(mActivity, new LiPromotionResultCallback() {
				
				public void onPromotionResultCallback(LiPromotionAction arg0,
						LiPromotionResult arg1, Object arg2) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	};
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_egg);
        cont = this;
        mActivity = this;
        btn_login = (ImageButton)findViewById(R.id.btn_login_main);
        btn_play = (ImageButton)findViewById(R.id.btn_play);
        btn_Store = (ImageButton)findViewById(R.id.btn_store);
        btn_myProfile = (ImageButton)findViewById(R.id.btn_my_profile);
        
        bar = (ProgressBar)findViewById(R.id.progressBar);
        btn_Radius_Friends = (ImageButton)findViewById(R.id.btn_findFriends);
        btn_fb_Friends = (ImageButton)findViewById(R.id.btn_fbFeatures);
			
    	spProfile = (ImageView)findViewById(R.id.img_sp_profile);
    	usProfile = (ImageView)findViewById(R.id.img_profile);
    	
        btn_login.setClickable(false);
        btn_play.setClickable(false);
        btn_Store.setClickable(false);
        btn_Radius_Friends.setClickable(false);
        btn_fb_Friends.setClickable(false);
        btn_myProfile.setClickable(false);
        
        context = this;
      	LiPromo.setPromoCallback(promoCallback);
      	
		try {
			LiManager.initialize(this, this);
		
		} catch (LiErrorHandler e) {
			// TODO Auto-generated catch block
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
        
    }
    
    
    public void initView()
    {
    	  btn_login.setClickable(true);
	      btn_play.setClickable(true);
	      btn_Store.setClickable(true);
	      btn_Radius_Friends.setClickable(true);
	      btn_myProfile.setClickable(true);
	      btn_fb_Friends.setClickable(true);
	      bar.setVisibility(View.INVISIBLE);
	        
	       if(Applicasa.isCurrentUserRegistered())
	        {
	        	btn_login.setImageResource(R.drawable.btn_logout);
	        }
	       refreshProfileImages();
    }

	public void onCompleteInitialize() {

		initView();
		
		//Start session
		LiSession.SessionStart(this,promoCallback);
		
		// Updates User Location
		LiUserLocation.updateLocation();
		
		// Register IAP Observer
		IAP.RegisterLiInAppObserver(new IapObserver(this));
		
	 }
		
	
	public static void refreshProfileImages()
	{
		if ( spProfile != null && usProfile != null)
		{
			
		
			switch(Applicasa.getUserSpendProfile())
			{
				case None:
					break;
				case Rockefeller:
					spProfile.setImageResource(R.drawable.rockfelle);
					break;
				case TaxPayer:
					spProfile.setImageResource(R.drawable.taxpayer);
					break;
				case Tourist:
					spProfile.setImageResource(R.drawable.turist);
					break;
				case Zombie:
					spProfile.setImageResource(R.drawable.ombie);
					break;
			}
			switch(Applicasa.getUserUsageProfile())
			{
				case None:
					break;
				case General:
					usProfile.setImageResource(R.drawable.general);
					break;
				case Hippie:
					usProfile.setImageResource(R.drawable.hippie);
					break;
				case Private:
					usProfile.setImageResource(R.drawable.us_private);
					break;
				case Serganet:
					usProfile.setImageResource(R.drawable.sergeantx);
					break;
			}
		}
	}

	public void onClickHandler(View v) throws LiErrorHandler
	{
		int id = v.getId();
		
		Intent i;
		switch (id)
		{
		case R.id.btn_login_main: // or logout
			if (Applicasa.isCurrentUserRegistered())
			{
			    btn_login.setClickable(false);
				User.logoutUser(new LiCallbackUser() {
					
					public void onSuccessfull(RequestAction action) {
						// TODO Auto-generated method stub
						bar.setVisibility(View.INVISIBLE);
						 btn_login.setClickable(true);
						 btn_login.setImageResource(R.drawable.btn_nav_login);
						 btn_login.invalidate();

					}
					
					public void onFailure(RequestAction action, LiErrorHandler error) {
						// TODO Auto-generated method stub
						bar.setVisibility(View.INVISIBLE);
						Toast.makeText(context, "logout failed", Toast.LENGTH_LONG).show();
					}
				});
			}
			else
			{
				i = new Intent(this, LoginActivity.class);
				startActivity(i);
			}
			break;
			
		case R.id.btn_play:
			 btn_play.setClickable(false);
			i = new Intent(this, GameActivity.class);
			startActivity(i);
			
			break;
			
		case R.id.btn_store:
			   btn_Store.setClickable(false);
			 i = new Intent(this, TabsFragmentActivity.class);
			startActivity(i);
			break;
			
		case R.id.btn_findFriends:
			btn_Radius_Friends.setClickable(false);
			 i = new Intent(this, UsersRadiusListActivity.class);
				startActivity(i); 
			break;
			 
		case R.id.btn_my_profile:
			btn_myProfile.setClickable(false);
			 i = new Intent(this, RegisterActivity.class);
			i.putExtra("MyProfile", true);
			startActivity(i); 
			break;
		case R.id.btn_fbFeatures:
			  btn_fb_Friends.setClickable(false);
			 i = new Intent(this, FriendsListActivity.class);
				startActivity(i); 
			
			break;
		}
	}
	public void onFailure(LiErrorHandler arg0) {
		Log.w("Applicasa", "failed init");
	}
	
	protected void onPause()
	{
		super.onPause();
	}
	
	protected void onStop() {
		LiLogger.LogInfo("Session", "SessionEnd");
		LiSession.SessionEnd(this);
		IAP.unBindBillingService();
		super.onStop();
	}
	
	protected void onResume() {
		super.onResume();
	}
	protected void onRestart() {
		super.onRestart();
		initView();
	}
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}

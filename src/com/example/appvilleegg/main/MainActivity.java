package com.example.appvilleegg.main;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import applicasa.LiCore.Applicasa;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiFileCacher;
import applicasa.LiCore.LiLogger;
import applicasa.LiCore.communication.LiCallback.LiCallbackUser;
import applicasa.LiCore.communication.LiObjRequest.LiCallbackInitialize;
import applicasa.LiCore.communication.LiRequestConst.RequestAction;
import applicasa.LiCore.promotion.sessions.LiPromotionCallback;
import applicasa.kit.IAP.billing.Utils.LiIabHelper;
import applicasa.kit.IAP.billing.Utils.LiIabResult;
import applicasa.kit.IAP.billing.Utils.LiInventory;
import applicasa.kit.IAP.billing.Utils.LiPurchase;

import com.applicasa.ApplicasaManager.LiManager;
import com.applicasa.ApplicasaManager.LiPromo;
import com.applicasa.ApplicasaManager.LiSession;
import com.applicasa.ApplicasaManager.LiStore;
import com.applicasa.ApplicasaManager.LiUserLocation;
import com.applicasa.Dynamic.Dynamic;
import com.applicasa.Promotion.Promotion;
import com.applicasa.User.User;
import com.appvilleegg.R;
import com.example.appvilleegg.sampleApp.DynamicListActivity;
import com.example.appvilleegg.sampleApp.FriendsListActivity;
import com.example.appvilleegg.sampleApp.LoginActivity;
import com.example.appvilleegg.sampleApp.RegisterActivity;
import com.example.appvilleegg.sampleApp.TabsFragmentActivity;
import com.example.appvilleegg.sampleApp.UsersRadiusListActivity;

public class MainActivity extends Activity implements LiCallbackInitialize {
	
	
	List<Dynamic> arrDynamic;
	ListView lvMain;
	Context context;
	Activity mActivity = this;
	ImageButton btn_login;
	ImageButton btn_play;
	ImageButton btn_Store;
	ImageButton btn_Radius_Friends;
	ImageButton btn_myProfile;
	ImageButton btn_fb_Friends;
	ImageButton btn_dynamicContent;
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
        mActivity = this;
        btn_login = (ImageButton)findViewById(R.id.btn_login_main);
        btn_play = (ImageButton)findViewById(R.id.btn_play);
        btn_Store = (ImageButton)findViewById(R.id.btn_store);
        btn_myProfile = (ImageButton)findViewById(R.id.btn_my_profile);
        
        bar = (ProgressBar)findViewById(R.id.progressBar);
        btn_Radius_Friends = (ImageButton)findViewById(R.id.btn_findFriends);
        btn_dynamicContent = (ImageButton)findViewById(R.id.btn_DynamicContent);
        btn_fb_Friends = (ImageButton)findViewById(R.id.btn_fbFeatures);
			
    	spProfile = (ImageView)findViewById(R.id.img_sp_profile);
    	usProfile = (ImageView)findViewById(R.id.img_profile);
    	
    	// Disable all buttons before init is completed
        btn_login.setClickable(false);
        btn_play.setClickable(false);
        btn_Store.setClickable(false);
        btn_Radius_Friends.setClickable(false);
        btn_fb_Friends.setClickable(false);
        btn_myProfile.setClickable(false);
        btn_dynamicContent.setClickable(false);
        
        context = this;
        addShortcut(context);
      	LiPromo.setPromoCallback(promoCallback);
      	
		LiManager.initialize(this, this);
		
		
		
    }
    
    public void initView()
    {
    	// enable all buttons before init is completed
    	  btn_login.setClickable(true);
	      btn_play.setClickable(true);
	      btn_Store.setClickable(true);
	      btn_Radius_Friends.setClickable(true);
	      btn_myProfile.setClickable(true);
	      btn_fb_Friends.setClickable(true);
	      btn_dynamicContent.setClickable(true);
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
		
		
		List<Promotion> list = LiPromo.GetAvailablePromotions();
		if (list.size()>0)
			list.get(0).show(mActivity, null);
		
	 }
		
	
	public static void refreshProfileImages()
	{
		if ( spProfile != null && usProfile != null)
		{
			switch(User.getCurrentUserSpendProfile())
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
					spProfile.setImageResource(R.drawable.zombie);
					break;
			}
			switch(User.getCurrentUserUsageProfile())
			{
				case None:
					break;
				case General:
					usProfile.setImageResource(R.drawable.general);
					break;
				case Civilan:
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
				
		case R.id.btn_DynamicContent:
			btn_dynamicContent.setClickable(false);
			 i = new Intent(this, DynamicListActivity.class);
				startActivity(i); 
			
			break;
		}
	}
	public void onFailure(LiErrorHandler arg0) {
		Log.w("Applicasa", "failed init");
	}
	
	
	
	// Add Shotcut
	public void addShortcut(Context context) {
		SharedPreferences settings = context.getSharedPreferences("AppVille", 0);
		SharedPreferences.Editor editor = settings.edit();
		
		if (!settings.getBoolean("ShortcutAdded", false))
		{
			Intent shortcutIntent = new Intent();
			shortcutIntent.setClassName(getPackageName(),this.getLocalClassName());
			shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			shortcutIntent.setAction(Intent.ACTION_MAIN);
			
			Intent intent = new Intent();
			intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
			intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "AppVill 2.0");
			intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, R.drawable.ic_launcher));
			intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
			context.sendBroadcast(intent);
			
			editor.putBoolean("ShortcutAdded", true);
			editor.commit();
		}
    }
	
		
	protected void onPause()
	{
		super.onPause();
	}
	
	protected void onStop() {
		LiLogger.LogInfo("Session", "SessionEnd");
		LiSession.SessionEnd(this);
		super.onStop();
	}
	protected void onResume() {
		LiSession.SessionResume(context);
		super.onResume();
	}
	
	protected void onRestart() {
		super.onRestart();
		initView();
	}
	protected void onDestroy() {
		// dispose the IAB services 
		LiStore.dispose();
		LiFileCacher.ClearMemory();
		super.onDestroy();
	}
	
}

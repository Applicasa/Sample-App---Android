package com.example.appvilleegg.sampleApp;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.applicasa.ApplicasaManager.LiCallbackQuery.LiDynamicGetArrayCallback;
import com.applicasa.ApplicasaManager.LiManager;
import com.applicasa.ApplicasaManager.LiPromo;
import com.applicasa.ApplicasaManager.LiSession;
import com.applicasa.ApplicasaManager.LiStore;
import com.applicasa.ApplicasaManager.LiManager.LiObject;
import com.applicasa.Dynamic.Dynamic;
import com.applicasa.Dynamic.DynamicData.LiFieldDynamic;
import com.applicasa.Promotion.Promotion;
import com.applicasa.User.User;
import com.applicasa.VirtualCurrency.VirtualCurrency;
import com.applicasa.VirtualGood.VirtualGood;

import com.appvilleegg.R;
import com.example.appvilleegg.adapters.ShareDialog;
import com.example.appvilleegg.adapters.VirtualCurrencyAdapter;
import com.example.appvilleegg.fragments.InventoryFragment;
import com.example.appvilleegg.fragments.VirtualCurrencyFragment;
import com.example.appvilleegg.fragments.VirtualGoodFragment;
import com.example.appvilleegg.main.IapObserver;
import com.example.appvilleegg.main.MainActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import applicasa.LiCore.Applicasa;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiSharedPrefrences;
import applicasa.LiCore.LiErrorHandler.ApplicasaResponse;
import applicasa.LiCore.LiLogger;
import applicasa.LiCore.communication.LiCallback.LiCallbackAction;
import applicasa.LiCore.communication.LiCallback.LiCallbackUser;
import applicasa.LiCore.communication.LiObjRequest.LiCallbackInitialize;
import applicasa.LiCore.communication.LiRequestConst.LiObjResponse;
import applicasa.LiCore.communication.LiRequestConst.QueryKind;
import applicasa.LiCore.communication.LiRequestConst.RequestAction;
import applicasa.LiCore.promotion.sessions.LiPromotionCallback;
import applicasa.LiCore.promotion.sessions.LiPromotionCallback.LiPromotionAction;
import applicasa.LiCore.promotion.sessions.LiPromotionCallback.LiPromotionResult;
import applicasa.LiCore.promotion.sessions.LiPromotionCallback.LiPromotionResultCallback;
import applicasa.kit.IAP.IAP;
import applicasa.kit.IAP.IAP.LiCurrency;


/**
 * @author mwho
 *
 */
public class TabsFragmentActivity extends FragmentActivity implements TabHost.OnTabChangeListener, LiPromotionResultCallback{

	   private static final String TAG = TabsFragmentActivity.class.getCanonicalName();
	   private  TextView 				mBalanceMain;
	   private  TextView 				mBalanceSecondary;
	   private  ProgressBar			mProgressBar;
	   private  ImageView 			mImageView;
	   
	   public static boolean clickEnabled = true; 
	   Bundle mBundle;
	   private ImageButton logout;
	   
	   LiPromotionCallback mLiPromotionCallback;
	   
	 
	
	private TabHost mTabHost;
	private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabInfo>();
	private TabInfo mLastTab = null;
	private Bundle args;
	
	private static TabsFragmentActivity mActivity;
	

	private class TabInfo {
		 private String tag;
         private Class clss;
         private Bundle args;
         private TabSpec tabSpec;
         private Fragment fragment;
         TabInfo(String tag, Class clazz,TabSpec tabSpec, Bundle args) {
        	 this.tag = tag;
        	 this.tabSpec = tabSpec;
        	 this.clss = clazz;
        	 this.args = args;
         }

	}

	class TabFactory implements TabContentFactory {

		private final Context mContext;

	    /**
	     * @param context
	     */
	    public TabFactory(Context context) {
	        mContext = context;
	    }

	    /** (non-Javadoc)
	     * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
	     */
	    public View createTabContent(String tag) {
	        View v = new View(mContext);
	        v.setMinimumWidth(20);
	        v.setMinimumHeight(20);
	        return v;
	    }

	}
	/** (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		args = savedInstanceState;
		// Step 1: Inflate layout
		setContentView(R.layout.tab_host);
		mActivity = this;
		
		
//		IAP.RegisterLiInAppObserver(new IapObserver(mActivity));
		
		LiSession.SessionStart(mActivity,null);
		
		initialiseTabHost();
		
		if (args != null) {
            mTabHost.setCurrentTabByTag(args.getString("tab")); //set the tab as per the saved state
        }
		
		mBalanceMain = (TextView)findViewById(R.id.txt_gridBalanceMain);
		mBalanceSecondary = (TextView)findViewById(R.id.txt_gridBalanceSecondary);
		mBalanceMain.setTypeface(Typeface.SANS_SERIF);
		mBalanceSecondary.setTypeface(Typeface.SANS_SERIF);
		
		mImageView = (ImageView)findViewById(R.id.img_currentBalance);
		mImageView.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new ShareDialog(mActivity).show();
			}
		});
		
	
		logout 	=  (ImageButton) findViewById(R.id.btn_Logout);
		if (Applicasa.isCurrentUserRegistered())
			logout.setVisibility(View.VISIBLE);
		else
			logout.setVisibility(View.INVISIBLE);
		
		
		refreshUI();
		
		// ReRegister for promotion callback 
	    LiPromo.setPromoCallback(new LiPromotionCallback() {
			
				@Override
				public void onHasPromotionToDisplay(List<Promotion> promotions) {
					// TODO Auto-generated method stub
					promotions.get(0).show(mActivity,mActivity);
				}
		});
		
	}

	/** (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle)
     */
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", mTabHost.getCurrentTabTag()); //save the tab selected
        super.onSaveInstanceState(outState);
    }

	/**
	 * Step 2: Setup TabHost
	 */
	private void initialiseTabHost() {
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
        Resources ressources = getResources(); 
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        
        // Inventory tab
 		TabSpec tabSpecInventory = mTabHost.newTabSpec("inventory").setIndicator("", ressources.getDrawable(R.drawable.inventory_tab_selector));
 		TabInfo tabInfoInventory = new TabInfo("inventory", InventoryFragment.class,tabSpecInventory, args);
     		
 		// Product tab
 		TabSpec tabSpecProduct = mTabHost.newTabSpec("virtualGood").setIndicator("", ressources.getDrawable(R.drawable.product_tab_selector));
 		TabInfo tabInfoProduct = new TabInfo("virtualGood", VirtualGoodFragment.class, tabSpecProduct, args );
      
 		// Store Tab
 		TabSpec tabSpecStore = mTabHost.newTabSpec("virtualCurrency").setIndicator("", ressources.getDrawable(R.drawable.store_tab_selector));
 		TabInfo tabInfoStore = new TabInfo("virtualCurrency", VirtualCurrencyFragment.class, tabSpecStore, args);
      
     		
        
        TabsFragmentActivity.addTab(this, this.mTabHost, tabInfoProduct);
        this.mapTabInfo.put(tabInfoProduct.tag, tabInfoProduct);
        TabsFragmentActivity.addTab(this, this.mTabHost, tabInfoInventory);
        this.mapTabInfo.put(tabInfoInventory.tag, tabInfoInventory);
        TabsFragmentActivity.addTab(this, this.mTabHost, tabInfoStore);
        this.mapTabInfo.put(tabInfoStore.tag, tabInfoStore);
        // Default to first tab
        
        this.onTabChanged("virtualGood");
        mTabHost.setCurrentTabByTag("virtualGood");
        //
        for(int i = 0; i < mTabHost.getTabWidget().getTabCount(); i++)
		{
			mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
		}
        mTabHost.setOnTabChangedListener(this);
	}

	/**
	 * @param activity
	 * @param tabHost
	 * @param tabSpec
	 * @param clss
	 * @param args
	 */
	private static void addTab(TabsFragmentActivity activity, TabHost tabHost, TabInfo tabInfo) {
		// Attach a Tab view factory to the spec
		tabInfo.tabSpec.setContent(activity.new TabFactory(activity));
        String tag = tabInfo.tabSpec.getTag();

        // Check to see if we already have a fragment for this tab, probably
        // from a previously saved state.  If so, deactivate it, because our
        // initial state is that a tab isn't shown.
        tabInfo.fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
        if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.detach(tabInfo.fragment);
            ft.commit();
            activity.getSupportFragmentManager().executePendingTransactions();
        }

        tabHost.addTab(tabInfo.tabSpec);
	}

	/** (non-Javadoc)
	 * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.String)
	 */
	public void onTabChanged(String tag) {
		TabInfo newTab = (TabInfo) this.mapTabInfo.get(tag);
		if (mLastTab != newTab) {
			FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            if (mLastTab != null) {
                if (mLastTab.fragment != null) {
                	ft.detach(mLastTab.fragment);
                }
            }
            if (newTab != null) {
                if (newTab.fragment == null) {
                    newTab.fragment = Fragment.instantiate(this,
                            newTab.clss.getName(), newTab.args);
                    ft.add(R.id.realtabcontent, newTab.fragment, newTab.tag);
                } else {
                    ft.attach(newTab.fragment);
                }
            }

            mLastTab = newTab;
            ft.commit();
            this.getSupportFragmentManager().executePendingTransactions();
		}
    }

	/**
	 * Handles Click event
	 * @param v
	 */
	public void clickHandler(View v)
	{
		if (v.getId() == R.id.btn_Logout) {
			logout.setClickable(false);
			mProgressBar.setVisibility(View.VISIBLE);
			User.logoutUser(new LiCallbackUser() {
				
				public void onSuccessfull(RequestAction action) {
					// TODO Auto-generated method stub
					LiSession.SessionStart(mActivity,null);
					Intent i = new Intent(mActivity, MainActivity.class);
					startActivity(i);
					mProgressBar.setVisibility(View.INVISIBLE);
					logout.setClickable(true);
					finish();
				}
				
				public void onFailure(RequestAction action, LiErrorHandler error) {
					// TODO Auto-generated method stub
					mProgressBar.setVisibility(View.INVISIBLE);
					Toast.makeText(mActivity, "failed logout user", Toast.LENGTH_SHORT).show();
					logout.setClickable(true);
				}
			});
		}
	}
	
	public static void refreshUI()
	{
		if (mActivity != null)
		{
			mActivity.mBalanceMain.setText(String.valueOf(IAP.getUserCurrencyBalance(LiCurrency.MainCurrency)));
			mActivity.mBalanceSecondary.setText(String.valueOf(IAP.getUserCurrencyBalance(LiCurrency.SencondaryCurrency)));
		}
	}
	
	protected void onPause() {
		// TODO Auto-generated method stub
		LiSession.SessionEnd(mActivity);
		super.onPause();
	}
	protected void onResume() {
		// TODO Auto-generated method stub
		LiSession.SessionStart(mActivity,null);
		super.onResume();
	}

	public void onPromotionResultCallback(LiPromotionAction action,
			LiPromotionResult result, Object info) {
		// TODO Auto-generated method stub
		if (action.equals(LiPromotionAction.Cancelled))
		{
			Log.i("Promotion", "action cancelled");
			return;
		}
		if (action.equals(LiPromotionAction.Failed))
		{
			Log.i("Promotion", "action "+ result.toString()+" Failed ");
			return;
		}
		switch (result)
		{
		case PromotionResultDealMainVirtualCurrency:
			Log.i("Promotion", "Deal of "+((VirtualCurrency)info).VirtualCurrencyCredit +"received" );
			refreshUI();
			break;
		case PromotionResultDealSeconedaryVirtualCurrency:
			Log.i("Promotion", "Deal of "+((VirtualCurrency)info).VirtualCurrencyCredit +"received" );
			refreshUI();
			break;
		case PromotionResultDealVirtualGood:
			Log.i("Promotion", ((VirtualGood)info).VirtualGoodTitle +" Deal was received");
			refreshUI();
			LiStore.reloadVirtualGoodInventory();
			break;
		case PromotionResultGiveMainCurrencyVirtualCurrency:
			Log.i("Promotion", String.valueOf((Integer)info)+" Main Currency received");
			refreshUI();
			break;
		case PromotionResultGiveSeconedaryCurrencyVirtualCurrency:
			Log.i("Promotion", String.valueOf((Integer)info)+" Secondary Currency received");
			refreshUI();
			break;
		case PromotionResultGiveVirtualGood:
			Log.i("Promotion", ((VirtualGood)info).VirtualGoodTitle +" was received");
			LiStore.reloadVirtualGoodInventory();
			break;
		case PromotionResultLinkOpened:
			Log.i("Promotion", "Link "+(String)info+" Opened");
			break;
		case PromotionResultNothing:
			break;
		case PromotionResultStringInfo:
			break;
			
		}
	}
}



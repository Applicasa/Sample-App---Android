package com.example.appvilleegg.fragments;

import java.util.List;

import com.applicasa.ApplicasaManager.LiPromo;
import com.applicasa.ApplicasaManager.LiStore;
import com.applicasa.Promotion.Promotion;
import com.applicasa.VirtualCurrency.VirtualCurrency;

import com.appvilleegg.R;
import com.example.appvilleegg.adapters.VirtualCurrencyAdapter;
import com.example.appvilleegg.sampleApp.TabsFragmentActivity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiLogger;
import applicasa.LiCore.promotion.sessions.LiPromotionCallback;
import applicasa.kit.IAP.IAP;
import applicasa.kit.IAP.IAP.LiCurrency;


public class VirtualCurrencyFragment extends Fragment implements GridView.OnItemClickListener{
	/** (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	String Tag = VirtualCurrencyFragment.class.getSimpleName();
	
    private GridView                mGridView;
    private VirtualCurrencyAdapter  			mStoreAdapter;
    Activity activity;
	   
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		final View v = inflater.inflate(R.layout.grid_frag, container, false);
		mGridView = (GridView) v.findViewById(R.id.grid_view);
		mGridView.setVisibility(View.VISIBLE);	
	    return v;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		activity = getActivity();
		
		 if (activity != null) {
	            // Create an instance of the custom adapter for the GridView. A static array of location data
	            // is stored in the Application sub-class for this app. This data would normally come
	            // from a database or a web service.
			 	mStoreAdapter = VirtualCurrencyAdapter.getInstance(activity, LiStore.GetAllVirtualCurrency());
	            
	            if (mGridView != null) {
	                mGridView.setAdapter(mStoreAdapter);
	            }
	            mGridView.setOnItemClickListener(this);
	 	
		 }
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	}
	
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		LiLogger.LogWarning(Tag, "Clicked");
		if (TabsFragmentActivity.clickEnabled)	
		{
			VirtualCurrency vc = LiStore.GetAllVirtualCurrency().get(position);
			LiStore.BuyVirtualCurrency(vc);
		}
	}
	
	


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LiLogger.LogInfo("TABS Virtual" , "on resume");
		activity = getActivity();
	}
	
	
	
	
}

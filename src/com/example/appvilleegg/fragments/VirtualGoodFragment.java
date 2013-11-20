package com.example.appvilleegg.fragments;


import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiLogger;
import applicasa.kit.IAP.IAP.GetVirtualGoodKind;
import applicasa.kit.IAP.IAP.LiCurrency;
import applicasa.kit.IAP.IAP.LiIapAction;
import applicasa.kit.IAP.Callbacks.LiCallbackVirtualGoodRequest;

import com.applicasa.ApplicasaManager.LiStore;
import com.applicasa.VirtualGood.VirtualGood;
import com.appvilleegg.R;
import com.example.appvilleegg.adapters.VirtualGoodAdapter;
import com.example.appvilleegg.sampleApp.TabsFragmentActivity;


public class VirtualGoodFragment extends Fragment implements GridView.OnItemClickListener{
	/** (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	String Tag = VirtualGoodFragment.class.getSimpleName();
	
    private GridView                mGridView;
    private static VirtualGoodAdapter  	mProductAdapter;
    List<VirtualGood> vgList;
	   
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
		
		 if (getActivity() != null) {
	            // gets an instance of the custom adapter for the Virtual Product.
			  vgList = LiStore.getAllVirtualGoods(GetVirtualGoodKind.ALL);
			 
			 mProductAdapter = VirtualGoodAdapter.getInstance(getActivity(), vgList);
	            
	            if (mGridView != null) {
	                mGridView.setAdapter(mProductAdapter);
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
		LiLogger.logWarning(Tag, "Clicked");
		if (TabsFragmentActivity.clickEnabled)	
		{
			VirtualGood item = vgList.get(position);
			if (item.VirtualGoodMainCurrency == 0)
			{
				item.buyVirtualGoods(getActivity(), TabsFragmentActivity.purchaseCallback);
			}
			else
			{
				
			item.buyVirtualGoods(1,LiCurrency.MainCurrency, new LiCallbackVirtualGoodRequest() {
					
					@Override
					public void onActionFinisedSuccessfully(LiIapAction liIapAction,
							VirtualGood item) {
						// TODO Auto-generated method stub
						TabsFragmentActivity.refreshUI();
					}
					
					@Override
					public void onActionFailed(LiIapAction liIapAction, VirtualGood item,
							LiErrorHandler errors) {
					}
				});
			}
		}
	}


	public static void OnInitCompleted() {
		mProductAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void onPause(){
		super.onPause();
		LiLogger.logInfo("TABS Virtual" , "on pause");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		LiLogger.logInfo("TABS Virtual" , "on resume");
	}
}

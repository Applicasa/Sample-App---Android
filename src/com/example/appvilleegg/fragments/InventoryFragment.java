package com.example.appvilleegg.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import applicasa.LiCore.LiErrorHandler;
import applicasa.kit.IAP.IAP.GetVirtualGoodKind;
import applicasa.kit.IAP.IAP.LiIapAction;
import applicasa.kit.IAP.Callbacks.LiCallbackVirtualGoodRequest;

import com.applicasa.ApplicasaManager.LiStore;
import com.applicasa.VirtualGood.VirtualGood;
import com.appvilleegg.R;
import com.example.appvilleegg.adapters.InventoryArrayAdapter;
import com.example.appvilleegg.sampleApp.TabsFragmentActivity;

public class InventoryFragment extends ListFragment implements OnItemClickListener{
	   private InventoryArrayAdapter mInventoryAdapter;
	   private List<VirtualGood> list;
	   private ListFragment mListFragment;
	   
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		 	View view = inflater.inflate(R.layout.inventory_list_holder, container, false);
		 	
		 	// Get all Virtual good with inventory
		 	list = LiStore.getAllVirtualGoods(GetVirtualGoodKind.HasInventory);
		 	mInventoryAdapter = InventoryArrayAdapter.getInstance(this.getActivity(),list );
		 	mListFragment = this;
		 	setListAdapter(mInventoryAdapter);
		 	
		if (container == null) {
            return null;
        }
		
		return view;
	}
	
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		getListView().setOnItemClickListener(this);
	}


	public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {
		// TODO Auto-generated method stub
		final VirtualGood vg = list.get(position);
		LiStore.useVirtualGoods(vg, 1, new LiCallbackVirtualGoodRequest() {
			
			@Override
			public void onActionFinisedSuccessfully(LiIapAction liIapAction,
					VirtualGood item) {
				// TODO Auto-generated method stub
				if (vg.VirtualGoodUserInventory == 0 && TabsFragmentActivity.clickEnabled)
				{
					list.remove(position);
					mInventoryAdapter = InventoryArrayAdapter.getInstance(InventoryFragment.this.getActivity(),list );
				 	setListAdapter(mInventoryAdapter);
				}
				mInventoryAdapter.notifyDataSetChanged();
			}
			
			@Override
			public void onActionFailed(LiIapAction liIapAction, VirtualGood item,
					LiErrorHandler errors) {
				// TODO Auto-generated method stub
				Log.w(InventoryFragment.class.getSimpleName(), "Failed Using Inventory");
			}
		});
	}
}
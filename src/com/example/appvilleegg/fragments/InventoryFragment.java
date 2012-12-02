package com.example.appvilleegg.fragments;

import java.util.List;


import com.applicasa.ApplicasaManager.LiStore;
import com.applicasa.VirtualGood.VirtualGood;
import com.appvilleegg.R;
import com.example.appvilleegg.adapters.InventoryArrayAdapter;
import com.example.appvilleegg.adapters.VirtualCurrencyAdapter;
import com.example.appvilleegg.sampleApp.TabsFragmentActivity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import applicasa.LiCore.LiLogger;
import applicasa.kit.IAP.IAP;
import applicasa.kit.IAP.IAP.GetVirtualGoodKind;

public class InventoryFragment extends ListFragment implements OnItemClickListener{
	/** (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	
	   private InventoryArrayAdapter mInventoryAdapter;
	   private List<VirtualGood> list;
	   
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		 	View view = inflater.inflate(R.layout.inventory_list_holder, container, false);
		 	
		 	// Get all Virtual good with inventory
		 	list = LiStore.GetAllVirtualGoods(GetVirtualGoodKind.HasInventory);
		 	mInventoryAdapter = new InventoryArrayAdapter(this.getActivity(),list );
		 	setListAdapter(mInventoryAdapter);
		 	
	        // Instance of ImageAdapter Class
	      
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


	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		VirtualGood vg = list.get(position);
		IAP.UseVirtualGoods(vg, 1);
		if (vg.VirtualGoodUserInventory == 0 && TabsFragmentActivity.clickEnabled)
		{
			list.remove(position);
			mInventoryAdapter = new InventoryArrayAdapter(this.getActivity(),list );
		 	setListAdapter(mInventoryAdapter);
		}
		mInventoryAdapter.notifyDataSetChanged();
		
	}
	
}
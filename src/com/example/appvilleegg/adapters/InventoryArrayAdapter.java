package com.example.appvilleegg.adapters;

import java.io.InputStream;
import java.util.List;


import com.applicasa.VirtualGood.VirtualGood;
import com.appvilleegg.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiFileCacher;
import applicasa.LiCore.communication.LiCallback.LiCallbackGetCachedFile;

public class InventoryArrayAdapter extends ArrayAdapter<VirtualGood> {
	private Activity activity;
	private List<VirtualGood> inAppObjectList = null;
	InventoryArrayAdapter mAdapter;
	private String TAG = "Matket Array Adapter";
	static class ViewHolder {
		public TextView itemName;
		public TextView quantity;
		public ImageView img;
		//public ImageView image; //Maybe In the Future we'll add Image to our list view
	}
	/**
	 * Constructor
	 * @param activity
	 * @param catalogList
	 */
	public InventoryArrayAdapter(Activity activity, List<VirtualGood> inAppObjectList) {
		super(activity, R.layout.inventory_list, inAppObjectList);
		
		this.mAdapter = this;
		this.activity = activity;
		this.inAppObjectList = inAppObjectList;
	}
	
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		View rowView = convertView;
		
		if (rowView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.inventory_list, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.itemName = (TextView) rowView.findViewById(R.id.txt_itemName);
			viewHolder.quantity = (TextView) rowView.findViewById(R.id.txt_quantity);
			viewHolder.img = (ImageView)rowView.findViewById(R.id.img_product);
			rowView.setTag(viewHolder);
		}

		final ViewHolder holder = (ViewHolder) rowView.getTag();
		
		if (inAppObjectList != null && inAppObjectList.size() != 0 && inAppObjectList.size() > position )
		{
			// Get Branch item name and price and sets it in the list holder
			String name = ((VirtualGood)inAppObjectList.get(position)).VirtualGoodTitle;
			holder.itemName.setText(name);
			holder.quantity.setText(String.valueOf(((VirtualGood)inAppObjectList.get(position)).VirtualGoodUserInventory));
			VirtualGood item = inAppObjectList.get(position);
			LiFileCacher.GetBitmapFromCache(item.VirtualGoodImageA, new LiCallbackGetCachedFile() {
				
				public void onSuccessfull(InputStream is) {
					// TODO Auto-generated method stub
				}
				
				public void onFailure(LiErrorHandler error) {
					// TODO Auto-generated method stub
					
				}

				public void onSuccessfullBitmap(Bitmap bitmap) {
					// TODO Auto-generated method stub
					holder.img.setImageDrawable(new BitmapDrawable(bitmap));
				}
			});
			
			
			
			
//			if (inAppObjectList[position].used)
//				holder.buy.setEnabled(false);
		}
		
		return rowView;
	}
	
	
	
	public int getCount() {
		return inAppObjectList.size();
	}

	public VirtualGood getItem(int position) {
		return inAppObjectList.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}
}

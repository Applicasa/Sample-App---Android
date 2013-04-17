package com.example.appvilleegg.adapters;

import java.io.InputStream;
import java.util.List;
import java.util.WeakHashMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiFileCacher;
import applicasa.LiCore.communication.LiCallback.LiCallbackGetCachedFile;

import com.applicasa.VirtualGood.VirtualGood;
import com.appvilleegg.R;

public class InventoryArrayAdapter extends ArrayAdapter<VirtualGood> {
	
	/**
	 * Inventory Adapter
	 */
	private static InventoryArrayAdapter adapter;
	private static Activity mActivity;
	private List<VirtualGood> mInventoryList;
    
    private static WeakHashMap<String, Bitmap> imageMap= new WeakHashMap<String, Bitmap>() ;
    
    static class ViewHolder {
		public TextView itemName;
		public TextView quantity;
		public ImageView img;
	}
    
	// Constructor
    private InventoryArrayAdapter(Activity activity, List<VirtualGood> list){
    	super(activity, R.layout.inventory_list, list);
    	mActivity = activity;
        mInventoryList = list;
    }
	
   

    
	
	 public static InventoryArrayAdapter getInstance(Activity activity, List<VirtualGood> list)
	 {
		 adapter = new InventoryArrayAdapter(activity,list);
		 return adapter;
	 }
	 
	 private void downloadMaterial(String url)
	 {
			{
				if (!imageMap.containsKey(url))
				{
					new AsyncTask<String, Void, Boolean>() {
					    @Override
					    protected Boolean doInBackground(String ... params) {
					    	final String url = params[0];
					    	LiFileCacher.getBitmapFromCache(url, new LiCallbackGetCachedFile() {
								
								public void onSuccessfull(InputStream is) {
									// TODO Auto-generated method stub
									
								}
								
								public void onFailure(LiErrorHandler error) {
									// TODO Auto-generated method stub
									
								}

								public void onSuccessfullBitmap(Bitmap bitmap) {
									// TODO Auto-generated method stub
									imageMap.put(url, bitmap);
									publishProgress();
								}
							});
					    	return true;
					    }
					    /**
					     * Updates the UI after receiving the Image
					     */
					    protected void onProgressUpdate(Void... progress) {
					    	cacheUpdated();
					     }
			
					}.execute(url);
				}
			}
	 }
	 

        
    
	/**
	 * Getter: return generated data
	 * @return array of Image
	 */
	public Object getData() {

		// return generated thumbs
		return mInventoryList;
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;
        final ViewHolder viewHolder;
        Typeface face=Typeface.createFromAsset(mActivity.getAssets(), "font.ttf"); 
		if (rowView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.inventory_list, null);
			ViewHolder holder = new ViewHolder();
			holder.itemName = (TextView) rowView.findViewById(R.id.txt_itemName);
			holder.quantity = (TextView) rowView.findViewById(R.id.txt_quantity);
			holder.img = (ImageView)rowView.findViewById(R.id.img_product);
			rowView.setTag(holder);
		}

         viewHolder = (ViewHolder) rowView.getTag();
			
		if (mInventoryList != null && mInventoryList.size() > position )
		{
			// Get Branch item name and price and sets it in the list holder
			String name = ((VirtualGood)mInventoryList.get(position)).VirtualGoodTitle;
			viewHolder.itemName.setText(name);
			viewHolder.quantity.setText(String.valueOf(((VirtualGood)mInventoryList.get(position)).VirtualGoodUserInventory));
			VirtualGood item = mInventoryList.get(position);
			if (imageMap.containsKey(item.VirtualGoodImageA))
			{
				viewHolder.img.setImageDrawable(new BitmapDrawable(imageMap.get(item.VirtualGoodImageA)));
				viewHolder.img.setScaleType(ImageView.ScaleType.FIT_CENTER);
			}
			else
			{
				downloadMaterial(item.VirtualGoodImageA);
			}
		}
		return rowView;
	}
	
	/**
	 * Notify the adapter that our data has changed so it can
	 * refresh the views & display any newly-generated thumbs
	 */
	private static void cacheUpdated() {
		adapter.notifyDataSetChanged();
	}
}
    
    
    
    
    
    
//	private Activity activity;
//	private List<VirtualGood> inAppObjectList = null;
//	InventoryArrayAdapter mAdapter;
//	private String TAG = "Matket Array Adapter";
//	static class ViewHolder {
//		public TextView itemName;
//		public TextView quantity;
//		public ImageView img;
//		//public ImageView image; //Maybe In the Future we'll add Image to our list view
//	}
//	/**
//	 * Constructor
//	 * @param activity
//	 * @param catalogList
//	 */
//	public InventoryArrayAdapter(Activity activity, List<VirtualGood> inAppObjectList) {
//		super(activity, R.layout.inventory_list, inAppObjectList);
//		
//		this.mAdapter = this;
//		this.activity = activity;
//		this.inAppObjectList = inAppObjectList;
//	}
//	
//	@Override
//	public View getView(final int position, View convertView, ViewGroup parent) {
//		
//		View rowView = convertView;
//		
//		if (rowView == null) {
//			LayoutInflater inflater = activity.getLayoutInflater();
//			rowView = inflater.inflate(R.layout.inventory_list, null);
//			ViewHolder viewHolder = new ViewHolder();
//			viewHolder.itemName = (TextView) rowView.findViewById(R.id.txt_itemName);
//			viewHolder.quantity = (TextView) rowView.findViewById(R.id.txt_quantity);
//			viewHolder.img = (ImageView)rowView.findViewById(R.id.img_product);
//			rowView.setTag(viewHolder);
//		}
//
//		final ViewHolder holder = (ViewHolder) rowView.getTag();
//		
//		if (inAppObjectList != null && inAppObjectList.size() != 0 && inAppObjectList.size() > position )
//		{
//			// Get Branch item name and price and sets it in the list holder
//			String name = ((VirtualGood)inAppObjectList.get(position)).VirtualGoodTitle;
//			holder.itemName.setText(name);
//			holder.quantity.setText(String.valueOf(((VirtualGood)inAppObjectList.get(position)).VirtualGoodUserInventory));
//			VirtualGood item = inAppObjectList.get(position);
//			LiFileCacher.getBitmapFromCache(item.VirtualGoodImageA, new LiCallbackGetCachedFile() {
//				
//				public void onSuccessfull(InputStream is) {
//					// TODO Auto-generated method stub
//				}
//				
//				public void onFailure(LiErrorHandler error) {
//					// TODO Auto-generated method stub
//					
//				}
//
//				public void onSuccessfullBitmap(Bitmap bitmap) {
//					// TODO Auto-generated method stub
//					holder.img.setImageBitmap(bitmap);
//				}
//			});
//			
//		}
//		
//		return rowView;
//	}
	
	
	
//	public int getCount() {
//		return inAppObjectList.size();
//	}
//
//	public VirtualGood getItem(int position) {
//		return inAppObjectList.get(position);
//	}
//
//	public long getItemId(int position) {
//		return 0;
//	}

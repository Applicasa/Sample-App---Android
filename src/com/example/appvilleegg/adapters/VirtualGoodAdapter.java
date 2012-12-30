package com.example.appvilleegg.adapters;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import com.applicasa.VirtualGood.VirtualGood;

import com.appvilleegg.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiFileCacher;
import applicasa.LiCore.communication.LiCallback.LiCallbackGetCachedFile;

public class VirtualGoodAdapter extends BaseAdapter {

	private Context mContext;
	private List<VirtualGood> mVirtualGoods;
	private LayoutInflater  mInflater;
	private static VirtualGoodAdapter adapter;
	private HashMap<String, Bitmap> imageMap;
    
	static class ViewHolder {
		public TextView itemName;
		public ImageView img;
		//public ImageView image; //Maybe In the Future we'll add Image to our list view
		public TextView price;
	}
	
	 public static VirtualGoodAdapter getInstance(Context context, List<VirtualGood> list)
	 {
		 if (adapter == null)
			 adapter = new VirtualGoodAdapter(context,list);
		 else
		 {
			 adapter.mVirtualGoods = list;
		 }
		 adapter.downloadMaterial();
		 return adapter;
	 }
	 
	 private void downloadMaterial()
	 {
		 Iterator<VirtualGood> iter = mVirtualGoods.iterator();
			while(iter.hasNext())
			{
				String url = iter.next().VirtualGoodImageA;
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
									imageMap.put(url,bitmap);
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
	 
	// Constructor
    private VirtualGoodAdapter(Context context, List<VirtualGood> list){
    	mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        mVirtualGoods = list;
        imageMap = new HashMap<String, Bitmap>();
        
    }
    
	
	public View getView(int position, View convertView, ViewGroup parent) {
		 View  view = convertView;
        final ViewHolder viewHolder;
		if (view == null) {
			view = mInflater.inflate(R.layout.grid_product_item, null);
			viewHolder = new ViewHolder();
			viewHolder.img = (ImageView) view.findViewById(R.id.img_gridProductItem);
			viewHolder.price = (TextView) view.findViewById(R.id.txt_gridItemTextPrice);
			view.setTag(viewHolder);
		
		}
	 else {
            viewHolder = (ViewHolder) view.getTag();
        }
		
		if (mVirtualGoods != null && mVirtualGoods.size() > position )
		{
			// Get Branch item name and price and sets it in the list holder
			VirtualGood item = ((VirtualGood)mVirtualGoods.get(position));
			viewHolder.price.setText(String.valueOf(item.VirtualGoodMainCurrency)); 
			if (imageMap.containsKey(item.VirtualGoodImageA))
			{
				viewHolder.img.setImageDrawable(new BitmapDrawable(imageMap.get(item.VirtualGoodImageA)));
			}
			
		}
		
		return view;
	}
	
	public int getCount() {
		return mVirtualGoods.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}
	
	
	/**
	 * Notify the adapter that our data has changed so it can
	 * refresh the views & display any newly-generated thumbs
	 */
	private static void cacheUpdated() {
		adapter.notifyDataSetChanged();
	}
}

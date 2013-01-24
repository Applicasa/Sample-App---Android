package com.example.appvilleegg.adapters;

import java.io.InputStream;
import java.util.List;
import java.util.WeakHashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
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

import com.applicasa.VirtualCurrency.VirtualCurrency;
import com.appvilleegg.R;

public class VirtualCurrencyAdapter extends BaseAdapter {

	private static VirtualCurrencyAdapter adapter;
	private Context mContext;
	private List<VirtualCurrency> mVirtualCurrencyList;
    private LayoutInflater  mInflater;
	
    private static WeakHashMap<String, Bitmap> imageMap= new WeakHashMap<String, Bitmap>() ;

	static class ViewHolder {
		public TextView itemName;
		public ImageView img;
		public TextView title; 
	}
	
	 public static VirtualCurrencyAdapter getInstance(Context context, List<VirtualCurrency> list)
	 {
		 if (adapter == null)
			 adapter = new VirtualCurrencyAdapter(context,list);
		 else
		 {
			 adapter.mVirtualCurrencyList = list;
		 }
//		 adapter.downloadMaterial();
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
	 
	// Constructor
    private VirtualCurrencyAdapter(Context context, List<VirtualCurrency> list){
    	mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        mVirtualCurrencyList = list;
    }
        
    
	/**
	 * Getter: return generated data
	 * @return array of Image
	 */
	public Object getData() {

		// return generated thumbs
		return mVirtualCurrencyList;
	}
	
	
	public View getView(int position, View convertView, ViewGroup parent) {

        View  view = convertView;
        final ViewHolder viewHolder;
        Typeface face=Typeface.createFromAsset(mContext.getAssets(), "font.ttf"); 
		if (view == null) {
			 // pull the cached data for the image assigned to this position
			view = mInflater.inflate(R.layout.grid_store_item, null);
			
			viewHolder = new ViewHolder();
			viewHolder.itemName = (TextView) view.findViewById(R.id.txt_gridItemText);
			viewHolder.img = (ImageView) view.findViewById(R.id.img_gridItem);
			viewHolder.title = (TextView)view.findViewById(R.id.txt_amount);
			view.setTag(viewHolder);
		}

		 else 
		 {
	         viewHolder = (ViewHolder) view.getTag();
		 }
			
			if (mVirtualCurrencyList != null && mVirtualCurrencyList.size() > position )
			{
				// Get Branch item name and price and sets it in the list holder
				VirtualCurrency item = ((VirtualCurrency)mVirtualCurrencyList.get(position));
				int credit = item.VirtualCurrencyCredit;
				String price = String.valueOf(item.VirtualCurrencyPrice);
				viewHolder.itemName.setText("$"+price);
				viewHolder.title.setText(String.valueOf(credit));
				viewHolder.title.setTypeface(face);
				if (imageMap.containsKey(item.VirtualCurrencyImageA))
				{
					viewHolder.img.setImageDrawable(new BitmapDrawable(imageMap.get(item.VirtualCurrencyImageA)));
					viewHolder.img.setScaleType(ImageView.ScaleType.FIT_CENTER);
				}
				else
				{
					downloadMaterial(item.VirtualCurrencyImageA);
				}
			}
		return view;
	}
    
	

	
	public int getCount() {
		return mVirtualCurrencyList.size();
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

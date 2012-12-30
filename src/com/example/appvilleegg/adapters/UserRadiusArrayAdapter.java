package com.example.appvilleegg.adapters;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiFileCacher;
import applicasa.LiCore.communication.LiCallback.LiCallbackGetCachedFile;

import com.applicasa.User.User;
import com.appvilleegg.R;

public class UserRadiusArrayAdapter extends ArrayAdapter<User> {
	private static UserRadiusArrayAdapter adapter;
	private Activity activity;
	private List<User> friends = null;
	
	private WeakHashMap<String, Bitmap> imageMap= new WeakHashMap<String, Bitmap>();

	private String TAG = "Matket Array Adapter";
	static class ViewHolder {
		public TextView itemName;
		public ImageView pic;
		public TextView distance;
		public ProgressBar bar;

	}
		
	/**
	 * Constructor
	 * @param activity
	 * @param catalogList
	 */
	public UserRadiusArrayAdapter(Activity activity, List<User> friends) {
		super(activity, R.layout.radius_list, friends);
		
		this.activity = activity;
		this.friends = friends;
		adapter = this;
	}
	
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.radius_list, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.itemName = (TextView) rowView.findViewById(R.id.friend_textName);
			viewHolder.distance = (TextView) rowView.findViewById(R.id.txt_distance);
			viewHolder.pic = (ImageView) rowView.findViewById(R.id.friend_img_contact);
			viewHolder.bar = (ProgressBar)rowView.findViewById(R.id.progressBar);
			
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		
		if (friends != null && friends.size() > position )
		{
			// Get Branch item name and price and sets it in the list holder
			holder.itemName.setText(friends.get(position).UserFirstName +" "+ friends.get(position).UserLastName);
			
			holder.distance.setText(onlyTwoDecimalPlaces(friends.get(position).DistanceFromCurrent )+ "km");
			
			String imageUrl = friends.get(position).UserImage;
			if (imageMap.containsKey(imageUrl))
			{
				holder.pic.setImageBitmap(imageMap.get(imageUrl));
				holder.pic.setMaxHeight( 30);
				holder.pic.setMaxWidth(30);
				holder.pic.setMinimumHeight( 30);
				holder.pic.setMinimumWidth(30);
				holder.bar.setVisibility(View.INVISIBLE);
			}
			else if (imageUrl.isEmpty() || imageUrl.equals("")) 
			{
				holder.bar.setVisibility(View.INVISIBLE);
				holder.pic.setImageResource(R.drawable.profile_picture);
			}
			else
			{
				downloadThumbnail(imageUrl);
			}
		}
		
		return rowView;
	}
	
	/**
	 * Getter: return generated data
	 * @return array of Image
	 */
	public Object getData() {
		// stop the task if it isn't finished

		// return generated thumbs
		return friends;
	}
	
	/**
	 * Notify the adapter that our data has changed so it can
	 * refresh the views & display any newly-generated thumbs
	 */
	private static void cacheUpdated() {
		adapter.notifyDataSetChanged();
	}
	
	private synchronized void downloadThumbnail(final String url)
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

	private  String onlyTwoDecimalPlaces(double number) {
	    StringBuilder sbFloat = new StringBuilder(String.valueOf(number));
	    int start = sbFloat.indexOf(".");
	    if (start < 0) {
	        return sbFloat.toString();
	    }
	    int end = start+3;
	    if((end)>(sbFloat.length()-1)) end = sbFloat.length();

	    String twoPlaces = sbFloat.substring(start, end);
	    sbFloat.replace(start, sbFloat.length(), twoPlaces);
	    return  sbFloat.toString();
	}
}

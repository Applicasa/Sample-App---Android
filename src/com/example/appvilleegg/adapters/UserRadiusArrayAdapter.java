package com.example.appvilleegg.adapters;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



import com.applicasa.User.User;

import com.appvilleegg.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

public class UserRadiusArrayAdapter extends ArrayAdapter<User> {
	private static UserRadiusArrayAdapter adapter;
	private Activity activity;
	private List<User> friends = null;

	private String TAG = "Matket Array Adapter";
	static class ViewHolder {
		public TextView itemName;
		public ImageView pic;
		public TextView distance;
		public ProgressBar bar;

	}
	
	private Map<String, Bitmap> imageMap = new HashMap<String, Bitmap>();
		
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
		// get our thumbnail generation task ready to execute
        
				
		Iterator<User> iter = friends.iterator();
		while(iter.hasNext())
		{
			
			new AsyncTask<String, Void, Boolean>() {
	
			    @Override
			    protected Boolean doInBackground(String ... params) {
			    	final String url = params[0];
			    	LiFileCacher.GetBitmapFromCache(url, new LiCallbackGetCachedFile() {
						
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
	
			}.execute(iter.next().UserImage);
		}
     		
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
			
			holder.distance.setText(String.valueOf((int)(friends.get(position).DistanceFromCurrent * 1000))+ "m");
			
			if (imageMap.containsKey(friends.get(position).UserImage))
			{
				holder.pic.setImageBitmap(imageMap.get(friends.get(position).UserImage));
				holder.pic.setMaxHeight( 30);
				holder.pic.setMaxWidth(30);
				holder.pic.setMinimumHeight( 30);
				holder.pic.setMinimumWidth(30);
				holder.bar.setVisibility(View.INVISIBLE);
			}
			else if (friends.get(position).UserImage.isEmpty()) 
			{
				holder.bar.setVisibility(View.INVISIBLE);
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

}

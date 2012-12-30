package com.example.appvilleegg.adapters;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;



import com.applicasa.User.User;
import com.facebook.android.LiDialogError;
import com.facebook.android.LiFacebook.LiDialogListener;
import com.facebook.android.LiFacebookError;

import com.appvilleegg.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiFileCacher;
import applicasa.LiCore.communication.LiCallback.LiCallbackGetCachedFile;
import applicasa.kit.FaceBook.LiObjFacebookFriends;

public class FriendsArrayAdapter extends ArrayAdapter<LiObjFacebookFriends> {
	private static FriendsArrayAdapter adapter;
	private Activity activity;
	private List<LiObjFacebookFriends> friends = null;
	private HashMap<String, Bitmap> imageMap;
	
	private String TAG = "Matket Array Adapter";
	static class ViewHolder {
		public TextView itemName;
		public ImageView pic = null;
		public ImageButton btnFriend;
		public ProgressBar bar;
		//public ImageView image; //Maybe In the Future we'll add Image to our list view
	}
	
	/**
	 * Constructor
	 * @param activity
	 * @param catalogList
	 */
	public FriendsArrayAdapter(Activity activity, final List<LiObjFacebookFriends> friends) {
		super(activity, R.layout.friends_list, friends);
		
		this.activity = activity;
		this.friends = friends;
		adapter = this;
		
		
		imageMap = new HashMap<String, Bitmap>();
		// get our thumbnail generation task ready to execute
		
		// Using an AsyncTask to load the slow images in a background thread
		
		Iterator<LiObjFacebookFriends> iter = friends.iterator();
		while(iter.hasNext())
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
	
			}.execute(iter.next().mFacebookImage);
		}
	}
	
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.friends_list, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.itemName = (TextView) rowView.findViewById(R.id.friend_textName);
			viewHolder.btnFriend = (ImageButton) rowView.findViewById(R.id.friend_add);
			viewHolder.pic = (ImageView) rowView.findViewById(R.id.friend_img_contact);
			viewHolder.bar = (ProgressBar)rowView.findViewById(R.id.progressBar);
			rowView.setTag(viewHolder);
		}

		final ViewHolder holder = (ViewHolder) rowView.getTag();
		
		if (friends != null && friends.size() > position )
		{
			// Get Branch item name and price and sets it in the list holder
			holder.itemName.setText(friends.get(position).mFacebookName);
			if (friends.get(position).user == null || friends.get(position).user.UserID == "0")
			{
				holder.btnFriend.setClickable(true);
				holder.btnFriend.setImageResource(R.drawable.added);
				holder.bar.setVisibility(View.INVISIBLE);
				holder.btnFriend.setOnClickListener(new OnClickListener() {
					
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						     Bundle params = new Bundle();
						     params.putString("message", "Download AppVille");
						     params.putString("name", "AppVille");
						     params.putString("link", "https://play.google.com/store/apps/details?id=com.appvilleegg");
						     params.putString("description", "AppVille invitation");
						     params.putString("picture", "https://lh6.ggpht.com/C_at3-AXnhBCMTVIvdn7aZghbE_cO3Rkwv9DwxRuk85mOHIlx-4nIF2LrByGL6pmO3RK=w124");
							 User.postOnFriendsWall(activity,friends.get(position).mFacebookID ,params, new LiDialogListener() {
								
								public void onFacebookError(LiFacebookError e) {
									// TODO Auto-generated method stub
									Log.e("fb", e.getMessage());
								}
								
								public void onError(LiDialogError e) {
									// TODO Auto-generated method stub
									Log.e("fb", e.getMessage());
								}
								
								public void onComplete(Bundle values) {
									// TODO Auto-generated method stub
									Log.e("completed", "succes");
									Toast.makeText(activity, "Invitation sent", Toast.LENGTH_SHORT).show();
								}
								
								public void onCancel() {
									// TODO Auto-generated method stub
									
								}
							});
					}
				});
			}
			else
			{
				holder.btnFriend.setClickable(false);
				holder.btnFriend.setImageResource(R.drawable.add);
			}
			
			if (imageMap.containsKey(friends.get(position).mFacebookImage))
			{
					holder.pic.setImageDrawable(new BitmapDrawable(imageMap.get(friends.get(position).mFacebookImage)));
					holder.pic.setMaxHeight( 10);
					holder.pic.setMaxWidth(10);
					holder.pic.setMinimumHeight( 10);
					holder.pic.setMinimumWidth(10);
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

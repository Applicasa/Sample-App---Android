package com.example.appvilleegg.adapters;

import java.util.List;

import com.applicasa.Dynamic.Dynamic;
import com.appvilleegg.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DynamicArrayAdapter extends ArrayAdapter<Dynamic> {
	private Activity activity;
	private List<Dynamic> mDyanmic = null;
	DynamicArrayAdapter mAdapter;
	private String TAG = "Matket Array Adapter";
	static class ViewHolder {
		public TextView itemName;
		public TextView number;
		//public ImageView image; //Maybe In the Future we'll add Image to our list view
	}
	/**
	 * Constructor
	 * @param activity
	 * @param catalogList
	 */
	public DynamicArrayAdapter(Activity activity, List<Dynamic> dyanmic) {
		super(activity, R.layout.dynamic_list, dyanmic);
		
		this.mAdapter = this;
		this.activity = activity;
		this.mDyanmic = dyanmic;
	}
	
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		View rowView = convertView;
		
		if (rowView == null) {
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.dynamic_list, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.itemName = (TextView) rowView.findViewById(R.id.txt_itemName);
			viewHolder.number = (TextView) rowView.findViewById(R.id.txt_number);
			rowView.setTag(viewHolder);
		}

		final ViewHolder holder = (ViewHolder) rowView.getTag();
		
		if (mDyanmic != null && mDyanmic.size() != 0 && mDyanmic.size() > position )
		{
			// Get Branch item name and price and sets it in the list holder
			String name = ((Dynamic)mDyanmic.get(position)).DynamicText;
			holder.itemName.setText(name);
			holder.number.setText(String.valueOf(((Dynamic)mDyanmic.get(position)).DynamicNumber));
		}
		
		return rowView;
	}
	
	
	
	public int getCount() {
		return mDyanmic.size();
	}

	public Dynamic getItem(int position) {
		return mDyanmic.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}
}

package com.example.appvilleegg.sampleApp;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiErrorHandler.ApplicasaResponse;
import applicasa.LiCore.communication.LiCallback.LiCallbackAction;
import applicasa.LiCore.communication.LiObjRequest;
import applicasa.LiCore.communication.LiRequestConst.QueryKind;
import applicasa.LiCore.communication.LiRequestConst.RequestAction;
import applicasa.LiJson.LiJSONException;

import com.applicasa.ApplicasaManager.LiCallbackQuery.LiDynamicGetArrayCallback;
import com.applicasa.ApplicasaManager.LiManager.LiObject;
import com.applicasa.ApplicasaManager.LiSession;
import com.applicasa.Dynamic.Dynamic;
import com.example.appvilleegg.R;
import com.example.appvilleegg.adapters.DynamicArrayAdapter;

public class DynamicListActivity extends ListActivity implements OnItemClickListener{

	static Activity mActivity;
	 ProgressBar bar;
	 
	  private DynamicArrayAdapter mDynamicArrayAdapter;
	  private List<Dynamic> list;
	   
	   
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dynamic_list_holder);
		mActivity = this;
		bar = (ProgressBar)findViewById(R.id.progressBar);
		
		LiSession.sessionStart(this);
		
		try {
			int count = LiObjRequest.deleteItemsAccordingToClassName("Dynamic");
			refreshView();
		} catch (LiErrorHandler e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LiJSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void refreshView() {
		// TODO Auto-generated method stub
		bar.setVisibility(View.VISIBLE);
		
	 	Dynamic.getArrayWithQuery(null, QueryKind.LIGHT, new LiDynamicGetArrayCallback() {
			
			public void onGetDynamicFailure(LiErrorHandler error) {
				// TODO Auto-generated method stub
				bar.setVisibility(View.INVISIBLE);
				Toast.makeText(mActivity, "Error occured "+ error.getMessage(), Toast.LENGTH_LONG).show();
			}
			
			public void onGetDynamicComplete(List<Dynamic> items) {
				// TODO Auto-generated method stub
				list = items;
				bar.setVisibility(View.INVISIBLE);
				mDynamicArrayAdapter = new DynamicArrayAdapter(mActivity,list );
			 	setListAdapter(mDynamicArrayAdapter);
			}
		});
	}


	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		getListView().setOnItemClickListener(this);
	}
	
	
	public void onClickHandler(View v)
	{
		createDialog(null);	
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		Dynamic item = list.get(position);
		createDialog(item);
		
	}
	
	/**
	 * Create alert dialog to update \ add dynamic item
	 */
	private void createDialog(final Dynamic item) {
		// TODO Auto-generated method stub
			  final AlertDialog.Builder alert = new AlertDialog.Builder(mActivity);
			  	LinearLayout lila1= new LinearLayout(mActivity);
			  	lila1.setOrientation(1); //1 is for vertical orientation
			    final EditText text = new EditText(mActivity);
			    final EditText number = new EditText(mActivity);
			    number.setInputType(InputType.TYPE_CLASS_NUMBER);
			  	lila1.addView(text);
			  	lila1.addView(number);
			  	
			    if (item == null)
			    {
			    	alert.setTitle("Save item");
			    	
			    	text.setHint("Dynamic Text");
			    	number.setHint("Dynamic number");
			    }
			    else
			    {
			    	alert.setTitle("Update item");
			    	
			    	text.setText(item.DynamicText);
			    	number.setText(String.valueOf(item.DynamicNumber));
			    }
			    
			    alert.setView(lila1);
			    alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
			    	Dynamic toSave = item;
			        public void onClick(final DialogInterface dialog, int whichButton) {
			        	if (toSave == null)
			        		toSave = new Dynamic();
			        	
			        	String temp;
			        	toSave.DynamicText = text.getText().toString().trim();
			        
			        	temp = number.getText().toString();
			        	if (!temp.equals(""))
			        		toSave.DynamicNumber = Integer.valueOf(temp);
			        	
			        	toSave.save(new LiCallbackAction() {
							
							public void onFailure(LiErrorHandler arg0) {
								// TODO Auto-generated method stub
								dialog.dismiss();
								Log.e("Failed Saving Item", arg0.getMessage());
								Toast.makeText(mActivity, "Failed Saving Item "+arg0.ErrorMessage, Toast.LENGTH_LONG).show();
								Log.e("Failed Saving Item", arg0.getMessage());
							}
							
							public void onComplete(ApplicasaResponse arg0, String arg1,
									RequestAction arg2, String arg3, LiObject arg4) {
								// TODO Auto-generated method stub
								Toast.makeText(mActivity, "Item Saved", Toast.LENGTH_LONG).show();
								
								Cursor cursor = LiObjRequest.GetRawSQL("Select * from tbl_Dynamic where DynamicID =?", new String[]{arg3});
								Toast.makeText(mActivity, String.valueOf(cursor.getCount()), Toast.LENGTH_LONG).show();
								
								refreshView();
								dialog.dismiss();
							}
						});
			        }
			    });

			    alert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int whichButton) {
			            dialog.cancel();
			        }
			    });
			    
			    // If item isn't null then we can delete it
			    if (item !=null)
	        	{
				    alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
				        public void onClick(final DialogInterface dialog, int whichButton) {
				        	
				        		item.delete(new LiCallbackAction() {
									
									public void onFailure(LiErrorHandler arg0) {
										// TODO Auto-generated method stub
										dialog.dismiss();
										Log.e("Failed deleteing Item", arg0.getMessage());
										Toast.makeText(mActivity, "Failed deleteing Item "+arg0.ErrorMessage, Toast.LENGTH_LONG).show();
										Log.e("Failed Saving Item", arg0.getMessage());
									}
									
									public void onComplete(ApplicasaResponse arg0, String arg1,
											RequestAction arg2, String arg3, LiObject arg4) {
										// TODO Auto-generated method stub
										Toast.makeText(mActivity, "Item Deleted", Toast.LENGTH_LONG).show();
										refreshView();
										dialog.dismiss();
									}
								});
				        }
				    });
	        	}
			    alert.show();  
	}
	
	protected void onPause() {
		// TODO Auto-generated method stub
		LiSession.sessionEnd(mActivity);
		super.onPause();
	}
	protected void onResume() {
		// TODO Auto-generated method stub
		LiSession.sessionResume(mActivity);
		super.onResume();
	}
}

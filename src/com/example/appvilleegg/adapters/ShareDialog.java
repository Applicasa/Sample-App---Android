package com.example.appvilleegg.adapters;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;
import applicasa.LiCore.LiLogger;
import applicasa.kit.IAP.IAP;
import applicasa.kit.IAP.IAP.LiCurrency;

import com.applicasa.ApplicasaManager.LiStore;
import com.appvilleegg.R;
import com.facebook.FacebookException;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;

public class ShareDialog extends Dialog   {
	
	Activity mActivity;
	
	ImageButton mFb;
	ImageButton mTwitter;
	ImageButton mEmail;

	protected String TAG = this.getClass().getSimpleName();
    public ShareDialog(final Activity activity) {
        super(activity, android.R.style.Theme_Translucent_NoTitleBar);
        mActivity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        setContentView(R.layout.share_dialog);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		mFb = (ImageButton)findViewById(R.id.btn_shareOnFb);
		mTwitter = (ImageButton)findViewById(R.id.btn_shareOnTwitter);
		mEmail = (ImageButton)findViewById(R.id.btn_shareOnEmail);
		
		mFb.setOnClickListener(onClickHandler);
		mTwitter.setOnClickListener(onClickHandler);
		mEmail.setOnClickListener(onClickHandler);
		
	
	}
    
    android.view.View.OnClickListener onClickHandler = new android.view.View.OnClickListener() {
    	public void onClick(View v) {
    		
    	switch (v.getId())
    	{
	    	case R.id.btn_shareOnFb:
				 Session session = Session.getActiveSession();
				 if (session == null)
				 {
					 LiLogger.logInfo(TAG , "Facebook session is null");
				 }
				 
				 Bundle parameters = new Bundle();
	    		 parameters.putString("message", "I have "+String.valueOf(LiStore.getUserCurrencyBalance(LiCurrency.MainCurrency)) +" Gold Coins ");
	    		 parameters.putString("name", "AppVille");
	    		 parameters.putString("link", "www.applicasa.com");
	    		 parameters.putString("description", "Richi rich");
	    		 parameters.putString("picture", "https://s3.amazonaws.com/appsmaterials/705E89/1352354269-238956b2-9f72-43fb-9867-8173d5c8b09a.png");
	    		 
	    		 
			     Request.Callback callback= new Request.Callback() {
			            public void onCompleted(Response response) {
			                JSONObject graphResponse = response
			                                           .getGraphObject()
			                                           .getInnerJSONObject();
			                String postId = null;
			                try {
			                    postId = graphResponse.getString("id");
			                } catch (JSONException e) {
			                    Log.i(TAG,
			                        "JSON error "+ e.getMessage());
			                }
			                FacebookException error = response.getError();
			                if (error != null) {
			                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
			                    } else {
			                        Toast.makeText(getContext(), 
			                             postId,
			                             Toast.LENGTH_LONG).show();
			                }
			            }
			        };

			        Request request = new Request(session, "me/feed", parameters, 
			                              HttpMethod.POST, callback);

			        RequestAsyncTask task = new RequestAsyncTask(request);
			        task.execute();
			        break;
	    		 
	    		case R.id.btn_shareOnEmail:
	    			
    		    String html = "<!DOCTYPE html><html><body><a href=\"https://play.google.com/store/apps/details?id=com.appvilleegg&feature=search_result#?t=W251bGwsMSwyLDEsImNvbS5hcHB2aWxsZWVnZyJd\" target=\"_blank\">Download AppVille and join Me!</a>" + "</body></html>";
    			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
    	        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,  "I have "+String.valueOf(IAP.getUserCurrencyBalance(LiCurrency.MainCurrency)) +" Gold Coins ");
    	        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,Html.fromHtml(html));
    	        emailIntent.setType("plain/text");
    	        mActivity.startActivity(Intent.createChooser(emailIntent, "Share My balance"));
    		break;
    	case R.id.btn_shareOnTwitter:
    		// Soon
    		break;
    	}
    	dismiss();
	}
    };
    
    
}

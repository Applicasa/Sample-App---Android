package com.example.appvilleegg.adapters;

import java.io.IOException;
import java.io.InputStream;

import com.applicasa.ApplicasaManager.LiStore;
import com.applicasa.Promotion.Promotion;
import com.applicasa.User.User;
import com.applicasa.VirtualGood.VirtualGood;
import com.appvilleegg.R;
import com.facebook.android.LiDialogError;
import com.facebook.android.LiFacebook.LiDialogListener;
import com.facebook.android.LiFacebookError;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.Toast;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiFileCacher;
import applicasa.LiCore.LiLogger;
import applicasa.LiCore.communication.LiCallback.LiCallbackGetCachedFile;
import applicasa.LiJson.LiJSONException;
import applicasa.kit.IAP.IAP;
import applicasa.kit.IAP.IAP.LiCurrency;
import 	android.text.Html
;

public class ShareDialog extends Dialog   {
	Context mContext;

	ImageButton mFb;
	ImageButton mTwitter;
	ImageButton mEmail;
    public ShareDialog(final Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        mContext = context;
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
	    		 Bundle parameters = new Bundle();
	    		 parameters.putString("message", "I have "+String.valueOf(LiStore.getUserCurrencyBalance(LiCurrency.MainCurrency)) +" Gold Coins ");
	    		 parameters.putString("name", "AppVille");
	    		 parameters.putString("link", "www.applicasa.com");
	    		 parameters.putString("description", "Richi rich");
	    		 parameters.putString("picture", "https://s3.amazonaws.com/appsmaterials/705E89/1352354269-238956b2-9f72-43fb-9867-8173d5c8b09a.png");
	    		 User.postOnUserWall(getOwnerActivity(), parameters, new LiDialogListener() {
	 				
	 				public void onFacebookError(LiFacebookError arg0) {
	 					// TODO Auto-generated method stub
	 					Toast.makeText(mContext, arg0.getMessage(), Toast.LENGTH_SHORT).show();
	 				}
	 				
	 				public void onError(LiDialogError arg0) {
	 					// TODO Auto-generated method stub
	 					Toast.makeText(mContext, arg0.getMessage(), Toast.LENGTH_SHORT).show();
	 				}
	 				
	 				public void onComplete(Bundle arg0) {
	 					// TODO Auto-generated method stub
	 					
	 				}
	 				
	 				public void onCancel() {
	 					// TODO Auto-generated method stub
	 					
	 				}
	 			});
	    		 
	    		break;
	    		case R.id.btn_shareOnEmail:
	    			
    		    String html = "<!DOCTYPE html><html><body><a href=\"https://play.google.com/store/apps/details?id=com.appvilleegg&feature=search_result#?t=W251bGwsMSwyLDEsImNvbS5hcHB2aWxsZWVnZyJd\" target=\"_blank\">Download AppVille and join Me!</a>" + "</body></html>";
    			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
    	        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,  "I have "+String.valueOf(IAP.getUserCurrencyBalance(LiCurrency.MainCurrency)) +" Gold Coins ");
    	        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,Html.fromHtml(html));
    	        emailIntent.setType("plain/text");
    	        mContext.startActivity(Intent.createChooser(emailIntent, "Share My balance"));
    		break;
    	case R.id.btn_shareOnTwitter:
    		// Soon
    		break;
    	}
    	dismiss();
	}
    };
    
    
}

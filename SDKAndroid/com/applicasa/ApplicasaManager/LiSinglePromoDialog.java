package com.applicasa.ApplicasaManager;

import java.io.IOException;
import java.io.InputStream;

import com.applicasa.Promotion.Promotion;
import com.applicasa.VirtualGood.VirtualGood;
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
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiFileCacher;
import applicasa.LiCore.LiLogger;
import applicasa.LiCore.communication.LiCallback.LiCallbackGetCachedFile;
import applicasa.LiJson.LiJSONException;
import applicasa.kit.IAP.IAP;
import applicasa.kit.IAP.IAP.LiCurrency;



public class LiSinglePromoDialog extends Dialog   {
	Activity mActivity;
	Context mContext;
	private ImageView mExitButton;
	
	private Promotion mSinglePromo;
	FrameLayout mFrameLayout;
	RelativeLayout mRelativeLayout; 
	ProgressDialog mSpinner;
	ImageButton mImageButton;
	RelativeLayout.LayoutParams rl;
	
	protected boolean isBackgroundAvailable = false;
	protected boolean isButtonAvailable = false;
	
    public LiSinglePromoDialog(final Activity activity, Promotion singlePromo) {
        super(activity, android.R.style.Theme_Translucent_NoTitleBar);
        mActivity = activity;
        mSinglePromo = singlePromo;
        
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		/**
		 * Call to create the layouts
		 */
		createPromoLayout();
		
		mSpinner = new ProgressDialog(getContext());
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner.setMessage("Loading Promotion...");
        mSpinner.show();
		
		/**
		 * Sets the Promos Button
		 */
		createActionButton();
		
		/**
		 * Create the Exit Image
		 */
		createExitImage();
		
		/**
		 * Loads the promotion materials
		 */
		LoadPromo();
	}
    
    
    
    
    
    
    /**
     * Generate the Promotion layouts
     */
    private void createPromoLayout() {
    	/**
		 * The base Layout 
		 */
		mFrameLayout = new FrameLayout(getContext());
		FrameLayout.LayoutParams fp = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);
		
		// Set's the padding of the Promo
		DisplayMetrics displaymetrics = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;
		
		mFrameLayout.setPadding(width/14, height/14, width/14, height/14);
		addContentView(mFrameLayout, fp);
		
		
		/**
		 * Sets the layout of the promo - this layout's bg is the promo image
		 */
		
		mRelativeLayout = new RelativeLayout(getContext());
		rl = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		mRelativeLayout.setBackgroundColor(000000);
	}

    /**
     * Generate the Action Button and adds to View
     */
	private void createActionButton() {
		// TODO Auto-generated method stub
    	mImageButton = new ImageButton(getContext());
		mImageButton.setAdjustViewBounds(true);
		RelativeLayout.LayoutParams btn_rl = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		btn_rl.addRule(RelativeLayout.CENTER_HORIZONTAL);
		btn_rl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mImageButton.setScaleType(ScaleType.FIT_CENTER);
		mImageButton.setPadding(0, 0, 0, pxFromDp(40));
		mImageButton.setAdjustViewBounds(true);
		mImageButton.setBackgroundColor(00000000);
		mImageButton.setOnClickListener(clickHandler);
		mRelativeLayout.addView(mImageButton,btn_rl);
	}

	/**
	 * Genereat the Exit Button and adds to View
	 */
	private void createExitImage() {
		mExitButton = new ImageView(getContext());
		try { 
			AssetManager mngr = mActivity.getAssets();
			// Create an input stream to read from the asset folder
            InputStream ins = mngr.open("x_btn.png");
            // Convert the input stream into a bitmap
            Bitmap bitmap= BitmapFactory.decodeStream(ins);
            mExitButton.setImageBitmap(bitmap);
            mExitButton.setClickable(true);
            
            
		} catch ( IOException e) {
			LiLogger.LogError(LiSinglePromoDialog.class.getSimpleName(), "Failed Creating x_btn.png " +e.getMessage());
		}
		
		 mRelativeLayout.addView(mExitButton);
		 
		 mExitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//        		// updates analytics that the Promo was only viewed
            	mSinglePromo.updateViewUseCount(1, 0);
                LiSinglePromoDialog.this.dismiss();
            }
        });
       
    }
	
	/**
	 * Loads material asynchronously
	 */
    private void LoadPromo() {
    	
		LiLogger.LogDebug("**** PromoAvailable ****", "Promo Type "+mSinglePromo.PromotionAppEvent.toString()+" "+mSinglePromo.PromotionAppEvent.getId());
		
		// Load Materials
		LiFileCacher.GetBitmapFromCache(mSinglePromo.PromotionImage, new LiCallbackGetCachedFile() {
			
			public void onSuccessfull(InputStream is) {
				// TODO Auto-generated method stub
			}
			
			public void onFailure(LiErrorHandler error) {
				// TODO Auto-generated method stub
				LiLogger.LogError("Promo Adapter", "Source not found");
			}

			public void onSuccessfullBitmap(Bitmap bitmap) {
				
				Drawable dr = new BitmapDrawable(bitmap);
				mRelativeLayout.setBackgroundDrawable(dr);
				
				// indicates bg is ready
				isBackgroundAvailable  = true;
				showPromo();
			}
		});
		LiFileCacher.GetBitmapFromCache(mSinglePromo.PromotionButton, new LiCallbackGetCachedFile() {
			
			public void onSuccessfull(InputStream is) {
				// TODO Auto-generated method stub
			}
			
			public void onFailure(LiErrorHandler error) {
				// TODO Auto-generated method stub
				LiLogger.LogError("Promo Adapter", "Source not found");
			}

			public void onSuccessfullBitmap(Bitmap bitmap) {
				// TODO Auto-generated method stub
				LiLogger.LogInfo("Promo Adapter", "Source found");
				mImageButton.setImageBitmap(bitmap);
				mImageButton.setVisibility(View.VISIBLE);
				mImageButton.setClickable(true);

				// indicate Button is ready
				isButtonAvailable = true;
				showPromo();
			}
		});
	}

    
    private int pxFromDp(float dp)
    {
        return (int) (dp * this.getContext().getResources().getDisplayMetrics().density);
    }
    
    /**
     * When material are available 
     * removes the spinner and add the promotion View
     */
	protected void showPromo() {
		if (isBackgroundAvailable && isButtonAvailable)
		{
			 mSpinner.dismiss();
			 mFrameLayout.addView(mRelativeLayout, rl);
		}
	}

	
	
	android.view.View.OnClickListener clickHandler = new android.view.View.OnClickListener() {
		
		public void onClick(View v) {
		try {
			   mSinglePromo.updateViewUseCount( 1, 1);
			  switch (mSinglePromo.PromotionActionKind)
			  {
				  case NULL:
					  break;
				  case NOTHING:
					  break;
				  case LINK:
					  if (mSinglePromo.PromotionActionData.has("link"))
					  {
						  String link = mSinglePromo.PromotionActionData.getString("link");
						  if (!link.startsWith("http://") && !link.startsWith("https://") )
							  link = "http://"+link; 
						  Intent webIntent  = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
						  
						  mActivity.startActivity(webIntent);
						 
						 dismiss();
					  }
					  break;
				  case STRING:
					  // The promotion text is retreived 
					  String text = mSinglePromo.PromotionActionData.getString("string");

					  break;
				  case GIVE_VC:
					  int amount = mSinglePromo.PromotionActionData.getInt("amount");
					  int vcKind = mSinglePromo.PromotionActionData.getInt("virtualCurrencyKind");
					  IAP.GiveVirtualCurrency(amount, LiCurrency.values()[vcKind]);
					  IAP.notifyObserver();
					  break;
				  case GIVE_VG:
					  String id= mSinglePromo.PromotionActionData.getString("_id");
					  IAP.GiveVirtualGoods(IAP.GetVirtualGoodById(id), 1);
					  break;
				  case DEAL_VC:
					   id= mSinglePromo.PromotionActionData.getString("_id");
					   IAP.BuyVirtualCurrency(IAP.GetVirtualCurrencyDealById(id));
					  break;
				  case DEAL_VG:
					   id= mSinglePromo.PromotionActionData.getString("_id");
					   VirtualGood vg = IAP.GetVirtualGoodDealById(id);
					   IAP.BuyVirtualGoods(vg, 1, (vg.VirtualGoodMainCurrency!=0) ? LiCurrency.MainCurrency:LiCurrency.SencondaryCurrency);
					  break;
				  }
			  dismiss();
			  
			} catch (LiJSONException e) {
				// TODO Auto-generated catch block
				LiLogger.LogError(LiSinglePromoDialog.class.getSimpleName(), "Failed generating Promotion action "+e.getMessage());
			}
		  
		}
	};
}

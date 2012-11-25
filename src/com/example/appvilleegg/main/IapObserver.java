package com.example.appvilleegg.main;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiLogger;
import applicasa.kit.IAP.IAP;
import applicasa.kit.IAP.LiInAppObserver;
import applicasa.kit.IAP.LiBillingService.RequestPurchase;
import applicasa.kit.IAP.LiBillingService.RestoreTransactions;
import applicasa.kit.IAP.LiIAPConsts.ResponseCode;

import com.applicasa.ApplicasaManager.LiPromo;
import com.applicasa.Promotion.Promotion;
import com.applicasa.VirtualCurrency.VirtualCurrency;
import com.example.appvilleegg.sampleApp.TabsFragmentActivity;

public class IapObserver extends LiInAppObserver{
	Context mContext;
	public IapObserver(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	@Override
	public void onBillingSupported(boolean supported, String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRequestResponse(RequestPurchase request,
			ResponseCode responseCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRestoreTransactionsResponse(RestoreTransactions request,
			ResponseCode responseCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPurchaseCanceled(VirtualCurrency item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPurchaseRefunded(VirtualCurrency item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPurchaseFinisedSuccessfully(VirtualCurrency item) {
		// TODO Auto-generated method stub
		TabsFragmentActivity.refreshUI();
	}

	@Override
	public void onPurchaseFailed(VirtualCurrency item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshUI() {
		// TODO Auto-generated method stub
		TabsFragmentActivity.refreshUI();
		LiLogger.LogWarning("TabsFragmentActivity","refreshUI");
	}

	@Override
	public void onFinishedInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void errorReceiver(String text, LiErrorHandler error) {
		// TODO Auto-generated method stub
//		Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG).show();
	}

}

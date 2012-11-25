package com.example.appvilleegg.sampleApp;

import com.applicasa.VirtualCurrency.VirtualCurrency;

import android.content.Context;
import applicasa.LiCore.LiErrorHandler;
import applicasa.kit.IAP.LiBillingService.RequestPurchase;
import applicasa.kit.IAP.LiBillingService.RestoreTransactions;
import applicasa.kit.IAP.LiIAPConsts.ResponseCode;
import applicasa.kit.IAP.LiInAppObserver;

public class IAPObserver extends LiInAppObserver{

	public IAPObserver (Context context){
		super(context);
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
		
	}

	@Override
	public void refreshUI() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinishedInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void errorReceiver(String text, LiErrorHandler error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPurchaseFailed(VirtualCurrency item) {
		// TODO Auto-generated method stub
		
	}
}

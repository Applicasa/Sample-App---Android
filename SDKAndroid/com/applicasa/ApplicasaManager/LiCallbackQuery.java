package com.applicasa.ApplicasaManager;

import java.util.List;

import applicasa.LiCore.LiErrorHandler;

import com.applicasa.Chat.Chat;
import com.applicasa.Dynamic.Dynamic;
import com.applicasa.Places.Places;
import com.applicasa.Tips.Tips;
import com.applicasa.User.User;
import com.applicasa.VirtualCurrency.VirtualCurrency;
import com.applicasa.VirtualGood.VirtualGood;
import com.applicasa.VirtualGoodCategory.VirtualGoodCategory;
public class LiCallbackQuery {
	// User Get By Id Callback
	public static interface LiUserGetByIDCallback {
	
		public void onGetUserComplete(User items);
		public void onGetUserFailure(LiErrorHandler error);
	}

	// User GetArray Callback
		public static interface LiUserGetArrayCallback {

		public void onGetUserComplete(List<User> items);
		public void onGetUserFailure(LiErrorHandler error);
	}
	// VirtualCurrency Get By Id Callback
	public static interface LiVirtualCurrencyGetByIDCallback {
	
		public void onGetVirtualCurrencyComplete(VirtualCurrency items);
		public void onGetVirtualCurrencyFailure(LiErrorHandler error);
	}

	// VirtualCurrency GetArray Callback
		public static interface LiVirtualCurrencyGetArrayCallback {

		public void onGetVirtualCurrencyComplete(List<VirtualCurrency> items);
		public void onGetVirtualCurrencyFailure(LiErrorHandler error);
	}
	// VirtualGood Get By Id Callback
	public static interface LiVirtualGoodGetByIDCallback {
	
		public void onGetVirtualGoodComplete(VirtualGood items);
		public void onGetVirtualGoodFailure(LiErrorHandler error);
	}

	// VirtualGood GetArray Callback
		public static interface LiVirtualGoodGetArrayCallback {

		public void onGetVirtualGoodComplete(List<VirtualGood> items);
		public void onGetVirtualGoodFailure(LiErrorHandler error);
	}
	// VirtualGoodCategory Get By Id Callback
	public static interface LiVirtualGoodCategoryGetByIDCallback {
	
		public void onGetVirtualGoodCategoryComplete(VirtualGoodCategory items);
		public void onGetVirtualGoodCategoryFailure(LiErrorHandler error);
	}

	// VirtualGoodCategory GetArray Callback
		public static interface LiVirtualGoodCategoryGetArrayCallback {

		public void onGetVirtualGoodCategoryComplete(List<VirtualGoodCategory> items);
		public void onGetVirtualGoodCategoryFailure(LiErrorHandler error);
	}
	// Dynamic Get By Id Callback
	public static interface LiDynamicGetByIDCallback {
	
		public void onGetDynamicComplete(Dynamic items);
		public void onGetDynamicFailure(LiErrorHandler error);
	}

	// Dynamic GetArray Callback
		public static interface LiDynamicGetArrayCallback {

		public void onGetDynamicComplete(List<Dynamic> items);
		public void onGetDynamicFailure(LiErrorHandler error);
	}
	// Places Get By Id Callback
	public static interface LiPlacesGetByIDCallback {
	
		public void onGetPlacesComplete(Places items);
		public void onGetPlacesFailure(LiErrorHandler error);
	}

	// Places GetArray Callback
		public static interface LiPlacesGetArrayCallback {

		public void onGetPlacesComplete(List<Places> items);
		public void onGetPlacesFailure(LiErrorHandler error);
	}
	// Tips Get By Id Callback
	public static interface LiTipsGetByIDCallback {
	
		public void onGetTipsComplete(Tips items);
		public void onGetTipsFailure(LiErrorHandler error);
	}

	// Tips GetArray Callback
		public static interface LiTipsGetArrayCallback {

		public void onGetTipsComplete(List<Tips> items);
		public void onGetTipsFailure(LiErrorHandler error);
	}
	// Chat Get By Id Callback
	public static interface LiChatGetByIDCallback {
	
		public void onGetChatComplete(Chat items);
		public void onGetChatFailure(LiErrorHandler error);
	}

	// Chat GetArray Callback
		public static interface LiChatGetArrayCallback {

		public void onGetChatComplete(List<Chat> items);
		public void onGetChatFailure(LiErrorHandler error);
	}
}

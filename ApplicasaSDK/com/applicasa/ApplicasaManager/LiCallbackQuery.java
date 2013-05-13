package com.applicasa.ApplicasaManager;

import java.util.List;
import applicasa.LiCore.LiErrorHandler;
import org.apache.http.NameValuePair;
import com.applicasa.User.User;
import com.applicasa.VirtualCurrency.VirtualCurrency;
import com.applicasa.VirtualGood.VirtualGood;
import com.applicasa.VirtualGoodCategory.VirtualGoodCategory;
import com.applicasa.Dynamic.Dynamic;
import com.applicasa.Chat.Chat;
import com.applicasa.Achievments.Achievments;
import com.applicasa.Foo.Foo;
import com.applicasa.GameV.GameV;
import com.applicasa.DataManager.DataManager;
import com.applicasa.DataManString.DataManString;
import com.applicasa.ScoreB.ScoreB;
import com.applicasa.Levels.Levels;
import com.applicasa.Colors.Colors;
import com.applicasa.Languages.Languages;
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
	// Achievments Get By Id Callback
	public static interface LiAchievmentsGetByIDCallback {
	
		public void onGetAchievmentsComplete(Achievments items);
		public void onGetAchievmentsFailure(LiErrorHandler error);
	}

	// Achievments GetArray Callback
		public static interface LiAchievmentsGetArrayCallback {

		public void onGetAchievmentsComplete(List<Achievments> items);
		public void onGetAchievmentsFailure(LiErrorHandler error);
	}
	// Foo Get By Id Callback
	public static interface LiFooGetByIDCallback {
	
		public void onGetFooComplete(Foo items);
		public void onGetFooFailure(LiErrorHandler error);
	}

	// Foo GetArray Callback
		public static interface LiFooGetArrayCallback {

		public void onGetFooComplete(List<Foo> items);
		public void onGetFooFailure(LiErrorHandler error);
	}
	// GameV Get By Id Callback
	public static interface LiGameVGetByIDCallback {
	
		public void onGetGameVComplete(GameV items);
		public void onGetGameVFailure(LiErrorHandler error);
	}

	// GameV GetArray Callback
		public static interface LiGameVGetArrayCallback {

		public void onGetGameVComplete(List<GameV> items);
		public void onGetGameVFailure(LiErrorHandler error);
	}
	// DataManager Get By Id Callback
	public static interface LiDataManagerGetByIDCallback {
	
		public void onGetDataManagerComplete(DataManager items);
		public void onGetDataManagerFailure(LiErrorHandler error);
	}

	// DataManager GetArray Callback
		public static interface LiDataManagerGetArrayCallback {

		public void onGetDataManagerComplete(List<DataManager> items);
		public void onGetDataManagerFailure(LiErrorHandler error);
	}
	// DataManString Get By Id Callback
	public static interface LiDataManStringGetByIDCallback {
	
		public void onGetDataManStringComplete(DataManString items);
		public void onGetDataManStringFailure(LiErrorHandler error);
	}

	// DataManString GetArray Callback
		public static interface LiDataManStringGetArrayCallback {

		public void onGetDataManStringComplete(List<DataManString> items);
		public void onGetDataManStringFailure(LiErrorHandler error);
	}
	// ScoreB Get By Id Callback
	public static interface LiScoreBGetByIDCallback {
	
		public void onGetScoreBComplete(ScoreB items);
		public void onGetScoreBFailure(LiErrorHandler error);
	}

	// ScoreB GetArray Callback
		public static interface LiScoreBGetArrayCallback {

		public void onGetScoreBComplete(List<ScoreB> items);
		public void onGetScoreBFailure(LiErrorHandler error);
	}
	// Levels Get By Id Callback
	public static interface LiLevelsGetByIDCallback {
	
		public void onGetLevelsComplete(Levels items);
		public void onGetLevelsFailure(LiErrorHandler error);
	}

	// Levels GetArray Callback
		public static interface LiLevelsGetArrayCallback {

		public void onGetLevelsComplete(List<Levels> items);
		public void onGetLevelsFailure(LiErrorHandler error);
	}
	// Colors Get By Id Callback
	public static interface LiColorsGetByIDCallback {
	
		public void onGetColorsComplete(Colors items);
		public void onGetColorsFailure(LiErrorHandler error);
	}

	// Colors GetArray Callback
		public static interface LiColorsGetArrayCallback {

		public void onGetColorsComplete(List<Colors> items);
		public void onGetColorsFailure(LiErrorHandler error);
	}
	// Languages Get By Id Callback
	public static interface LiLanguagesGetByIDCallback {
	
		public void onGetLanguagesComplete(Languages items);
		public void onGetLanguagesFailure(LiErrorHandler error);
	}

	// Languages GetArray Callback
		public static interface LiLanguagesGetArrayCallback {

		public void onGetLanguagesComplete(List<Languages> items);
		public void onGetLanguagesFailure(LiErrorHandler error);
	}
}

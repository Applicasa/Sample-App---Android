package com.applicasa.ApplicasaManager;

import java.util.List;

import applicasa.LiCore.LiErrorHandler;
import applicasa.kit.IAP.IAP;
import applicasa.kit.IAP.IAP.GetVirtualGoodKind;
import applicasa.kit.IAP.IAP.LiCurrency;
import applicasa.kit.IAP.LiInAppObserver;

import com.applicasa.VirtualCurrency.VirtualCurrency;
import com.applicasa.VirtualGood.VirtualGood;
import com.applicasa.VirtualGoodCategory.VirtualGoodCategory;

public class LiStore {
	
	private static String TAG = LiStore.class.getName();
	
	public static void RegisterLiInAppObserver(LiInAppObserver liInAppObserver)
	{
		IAP.RegisterLiInAppObserver(liInAppObserver);
	}
	
	/**
	 * Buy the Given virtual store - using Credits
	 * @param virtualGood
	 * @param quantity
	 * @return
	 */
	public static boolean BuyVirtualGoods(VirtualGood virtualGood, int quantity,LiCurrency currency) 
	{
		return  IAP.BuyVirtualGoods(virtualGood, quantity, currency);
	}
	
	/**
	 * Buy the given app Store item - using real money
	 * @param appStoreItem
	 * @return
	 */
	public static boolean BuyVirtualCurrency(VirtualCurrency virtualCurrency) {
		return IAP.BuyVirtualCurrency(virtualCurrency);
	}
	
	/**
	 * Give virtualGood by given quantity 
	 * @param virtualGood
	 * @param quantity
	 * @return
	 */
	public static boolean GiveVirtualGoods(VirtualGood virtualGood, int quantity) 
	{
		return  IAP.GiveVirtualGoods(virtualGood, quantity);
	}
	
	/**
	 * Give Coins
	 * @param coins
	 * @return
	 */
	public static  boolean GiveVirtualCurrency(int amount, LiCurrency currency) 
	{
		return IAP.GiveVirtualCurrency(amount, currency);
	}
	
	
	/**
	 * consumes quantity of virualGood
	 * @param virtualGood
	 * @param quantity
	 * @return
	 */
	public static boolean UseVirtualGoods(VirtualGood virtualGood, int quantity) 
	{
		return IAP.UseVirtualGoods( virtualGood, quantity); 
	}
	
	/**
	 * consumes coins
	 * @param coins
	 * @return
	 */
	public static boolean  UseVirtualCurrency(int amount, LiCurrency currency) 
	{
		return IAP.UseVirtualCurrency(amount, currency);
		
	}

	/**
	 * Get all Virtual Currency
	 * @return list of virtual Currency
	 */
	public static List<VirtualCurrency> GetAllVirtualCurrency() 
	{
		return IAP.GetAllVirtualCurrency();
	}
	
	/**
	 * Get all Virtual Currency By currency kind
	 * @return list of virtual Currency
	 */
	public static List<VirtualCurrency> GetAllVirtualCurrencyByKind(LiCurrency currency) 
	{
		return IAP.GetAllVirtualCurrencyByKind(currency);
	}

	/**
	 * Get all virtual good by get kind
	 * ALL - return all virtual Good
	 * HasInventory - returns all Virtual product with a positive inventory
	 * NoInventory - returns all Virtual product with a 0 inventory
	 * @param getVirtualGoodKind
	 * @return
	 */
	public static  List<VirtualGood> GetAllVirtualGoods(GetVirtualGoodKind getVirtualGoodKind)
	{
		return IAP.GetAllVirtualGoods(getVirtualGoodKind);
		
	}
	
	/**
	 * Get all virtual good Category
	 * @return
	 */
	public static  List<VirtualGoodCategory> GetAllVirtualGoodsCategory()
	{
		return IAP.GetAllVirtualGoodsCategory();
	} 
	
	
	/**
	 * Get all virtual good by get kind
	 * ALL - return all virtual Good
	 * HasInventory - returns all Virtual product with a positive inventory
	 * NoInventory - returns all Virtual product with a 0 inventory
	 * @param getVirtualGoodKind
	 * @return
	 */
	public static List<VirtualGood> GetVirtualGoodByCategory(VirtualGoodCategory virtualGoodCategory, GetVirtualGoodKind getVirtualGoodKind) throws LiErrorHandler {
		// TODO Auto-generated method stub
	 	 return IAP.GetVirtualGoodByCategory(virtualGoodCategory, getVirtualGoodKind );
	}
	
	
	/**
	 * return User's Balance
	 * @return
	 */
	public static int getUserCurrencyBalance(LiCurrency currency){return IAP.getUserCurrencyBalance(currency);}
	
	/**
	 * Get's the User's Inventory
	 * @throws LiErrorHandler
	 */
	public static void refreshInventory() throws LiErrorHandler
	{
		IAP.refreshInventory();
	}
	
	
	/**
	 * Get's the User's Inventory
	 * @throws LiErrorHandler
	 */
	public static void refreshStore() throws LiErrorHandler
	{
		IAP.refreshStore();
	}
	
	/**
	 *  Reload IPA
	 */
	public static void reloadIAPLocaly()
	{
		IAP.reloadIAPLocaly();
	}

	public static VirtualGood GetVirtualGoodById(String id) {
		// TODO Auto-generated method stub
		return IAP.GetVirtualGoodById(id);
	}
	
	public static VirtualCurrency GetVirtualCurrencyDealById(String id) {
		// TODO Auto-generated method stub
		return IAP.GetVirtualCurrencyDealById(id);
	}

	public static void notifyObserver() {
		// TODO Auto-generated method stub
		IAP.notifyObserver();
	}

	public static VirtualGood GetVirtualGoodDealById(String id) {
		// TODO Auto-generated method stub
		return IAP.GetVirtualGoodDealById(id);
	}
	
}

package com.applicasa.ApplicasaManager;

import java.util.List;

import applicasa.LiCore.promotion.sessions.LiEventManager;
import applicasa.LiCore.promotion.sessions.LiPromotionCallback;

import com.applicasa.Promotion.Promotion;

public class LiPromo {

	/**
	 * Sets the promotion callback
	 * @param liPromotionCallback
	 */
	public static void setPromoCallback(LiPromotionCallback liPromotionCallback)
	{
		LiEventManager.setPromoCallback(liPromotionCallback);
	}
	
	/**
	 * 
	 * @return the available promotions
	 */
	public static List<Promotion> GetAvailablePromotions()
	{
		return LiEventManager.GetAvailablePromotions();
	}
	
}

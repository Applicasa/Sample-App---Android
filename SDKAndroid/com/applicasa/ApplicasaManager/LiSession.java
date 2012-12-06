package com.applicasa.ApplicasaManager;

import android.content.Context;
import applicasa.LiCore.Applicasa;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.promotion.sessions.LiPromotionCallback;
import applicasa.LiCore.promotion.sessions.LiSessionManager;
import applicasa.LiCore.promotion.sessions.LiSessionManager.LiGameResult;

public class LiSession {

	/**
	 * Method to indicate when the app session starts
	 */
	public static void SessionStart(Context context,LiPromotionCallback promotionCallback)
	{
		LiSessionManager.SessionStart(context, promotionCallback);
	}
	
	/**
	 * Method to indicate when the app session Ends
	 * @throws LiErrorHandler 
	 */
	public static void SessionEnd(Context context) 
	{
		LiSessionManager.SessionEnd(context);
	}
	
	/**
	 * Method to indicate when the app session Resumes
	 */
	public static void SessionResume(Context context)
	{
		LiSessionManager.SessionResume(context);
	}
	/**
	 * Method to cal when user starts new level.
	 * On one level can exist  in a given time, 
	 * if there is a live level session it will be ended with values
	 * EndLevel(LevelResult.EXIT, 0, 0);
	 * @throws LiErrorHandler 
	 */
	public static void GameStart(String gameName, LiPromotionCallback promotionCallback) throws LiErrorHandler 
	{
		LiSessionManager.GameStart(gameName, promotionCallback);
	}
	
	/**
	 * Method to call when user Pauses game 
	 * @throws LiErrorHandler 
	 */
	public static void GamePause() throws LiErrorHandler
	{
		LiSessionManager.GamePause();
	}
	
	/**
	 * Method to call when user Resumes a game 
	 * @throws LiErrorHandler 
	 */
	public static void GameResume() throws LiErrorHandler
	{
		LiSessionManager.GameResume();
	}
	
	/**
	 * Method to call when user Finishes a game 
	 * @throws LiErrorHandler 
	 */
	public static void GameFinished(LiGameResult liGameResult, int mainCurrency,int secondaryCurrency, int score,int  bonus,LiPromotionCallback promotionCallback) throws LiErrorHandler
	{
		LiSessionManager.GameFinished(liGameResult, mainCurrency, secondaryCurrency, score, bonus, promotionCallback);
	}
}

package com.applicasa.ApplicasaManager;

import android.content.Context;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.promotion.sessions.LiPromotionCallback;
import applicasa.LiCore.promotion.sessions.LiSessionManager;
import applicasa.LiCore.promotion.sessions.LiSessionManager.LiGameResult;

public class LiSession {

	/**
	 * Method to indicate when the app session starts
	 */
	@Deprecated
	public static void SessionStart(Context context,LiPromotionCallback promotionCallback)
	{
		sessionStart(context, promotionCallback);
	}
	public static void sessionStart(Context context,LiPromotionCallback promotionCallback)
	{
		LiSessionManager.sessionStart(context, promotionCallback);
	}
	
	/**
	 * Method to indicate when the app session Resumes
	 */
	@Deprecated
	public static void SessionResume(Context context)
	{
		sessionResume(context);
	}
	public static void sessionResume(Context context)
	{
		LiSessionManager.sessionResume(context);
	}
	
	/**
	 * Method to indicate when the app session Ends
	 * @throws LiErrorHandler 
	 */
	@Deprecated
	public static void SessionEnd(Context context) 
	{
		sessionEnd(context);
	}
	public static void sessionEnd(Context context) 
	{
		LiSessionManager.sessionEnd(context);
	}
	
	
	/**
	 * Method to cal when user starts new level.
	 * On one level can exist  in a given time, 
	 * if there is a live level session it will be ended with values
	 * EndLevel(LevelResult.EXIT, 0, 0);
	 * @throws LiErrorHandler 
	 */
	public static void gameStart(String gameName, LiPromotionCallback promotionCallback) throws LiErrorHandler 
	{
		LiSessionManager.gameStart(gameName, promotionCallback);
	}
	
	/**
	 * Method to call when user Pauses game 
	 * @throws LiErrorHandler 
	 */
	public static void gamePause() throws LiErrorHandler
	{
		LiSessionManager.gamePause();
	}
	
	/**
	 * Method to call when user Resumes a game 
	 * @throws LiErrorHandler 
	 */
	public static void gameResume() throws LiErrorHandler
	{
		LiSessionManager.gameResume();
	}
	
	/**
	 * Method to call when user Finishes a game 
	 * @throws LiErrorHandler 
	 */
	public static void gameFinished(LiGameResult liGameResult, int mainCurrency,int secondaryCurrency, int score,int  bonus,LiPromotionCallback promotionCallback) throws LiErrorHandler
	{
		LiSessionManager.gameFinished(liGameResult, mainCurrency, secondaryCurrency, score, bonus, promotionCallback);
	}
}

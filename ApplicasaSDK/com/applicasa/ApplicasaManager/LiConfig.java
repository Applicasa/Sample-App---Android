package com.applicasa.ApplicasaManager;

public class LiConfig {

	/**
	* Enter Application ID
	*/
	private final static String APPLICATION_ID = "705E89";
	
	/**
	 * Enter Application Key
	 */
	private final static String APPLICATION_KEY = "179adc22b6213c63";
	
	/**
	 * Enable Applicasa Debug 
	 */
	private final static boolean ENABLE_APPLICASA_DEBUG = true;
	
	/**
	* Indicates whether working in live or sandbox environment
	*/
	private final static boolean SANDBOX = false;

	/**
	*	
	*	In app billing services
	*
	*/
	
		
	/**
	* Is IAP Enabled mode
	*/
	private final static boolean ENABLE_IAP = true;
	
	/**
	* Enter Google public key
	*/
	private final static String GOOGLE_PLAY_PUBLIC_KEY =  "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApGDOBXSVthJnYxJ7xcMGV7ixZ97Xw9CWN4KGtgItEG4Ltth1P261HCxAdplyK83X0XCFNSd/JXin2yD37eX7eTyeEk7HFxvCu7Y0MqB8L8dOkCyUcit859a9WjTYJG5DrBjU23e2DZ0WrnlvghE/A0FWzMzBDrJg30gafK4Iy8gXHVSFLDn/qPfvQgKB0X0GwUjteImoNLZemaht8LE40v8BapkHHmeizs5QCj/Mz5lyY6W7XDjlCGdWN+D4PfwB7T3j3Jpp8cvUaOl7W1pM8QqWyDuOUxSo14dC+TLEcpmYUlazbAEs9FN0NL/rb6pjxNw85d/CNtEmv8XoIlAHrQIDAQAB";
	
	
	/**
	 * Is IAP in sandbox mode
	 */
	private final static boolean ENABLE_IAP_SANDBOX = false;
	
	/**
	*	
	*	Push Configuration
	*
	*/
	
	/**
	 * Enable push services
	 */
	private final static boolean ENABLE_PUSH = true;
			
	/**
	* Enter GCM SenderID (projectId or email)
	*/
	private final static String GOOGLE_GCM = "423501440257";
 
 
	/**
	*	
	*	Facebook
	*
	*/
	
	/**
	* Indicate if Facebook allowed
	*/
	private final static boolean ENABLE_FACEBOOK = true;
	
	/**
	 * Enter FaceBook Application key
	 */
	private final static String FB_APPLICATION_KEY = "494708670563462";

	/**
	 * Enable Fb debug
	 */
	private static boolean ENABLE_FACEBOOK_DEBUG = false;	
	
	
	/**
	*	
	*	Location Services
	*/
	
	/**
	* Enable Location Services
	*/
	private final static boolean ENABLE_LOCATION_SERVICE = true;
	
	/**
	*
	* MMedia
	*/ 
	
	/**
	 * Should enable MMedia Promotions
	 */
	private final static boolean ENABLE_MMEDIA = true;
	
	/**
	*
	* SupersonicAds
	*/ 
	
	/**
	 * Should enable SuperSonic Promotions
	 */
	private final static boolean ENABLE_SUPERSONICADS = true;
	
	/**
	* Enter SuperSonic App ID
	*/
	private final static String SUPERSONICADS_APPID = "2d8de069";
	
	/**
	*
	* SponorPay
	*/ 
	
	/**
	 * Should enable SponserPay Promotions
	 */
	private final static boolean ENABLE_SPONSORPAY = true;
	
	/**
	* Enter SponserPay App ID
	*/
	
	private final static String SPONSORPAY_ID = "17287";
	/**
	 * Enter SponserPay SecurityToken 
	 */
	private final static String SPONSORPAY_SECURITYTOKEN = "250b3d9fdb4cc51cb6c67a775f39c275";
	
	
	/**
	*
	* AppNext
	*/
	/**
	 * Should enable AppNext Promotions
	 */
	private final static boolean ENABLE_APPNEXT = true;
	
	
	/**
	*
	* Chartboost
	*/
	
	/**
	* Should enable Chartboost Promotions
	*/
	 private final static boolean ENABLE_CHARTBOOST = true;
	
	/**
	 * Enter CHARTBOOST ID
	 */
	 private final static String CHARTBOOST_ID = "4f7b433509b6025804000002";
	 /**
	  * Enter CHARTBOOST SIGNATURE 
	  */
	 private final static String CHARTBOOST_SIGNATURE= "dd2d41b69ac01b80f443f5b6cf06096d457f82bd";
	
	
	
	/**
	*
	*	System configuration
	*
	*/
	
	
	/**
	 * define the pause time require for a session to be consider a new session
	 */
	private final static int SESSION_PAUSE_TIME = 15;
	
	
	/**
	 *  Minimum Framework Version - DO NOT ALTER
	 */
	private final static double FRAMEWORK_VERSION = 3.1;
	
	/**
	 *   SDK Version - DO NOT ALTER
	 */
	private final static double SDK_VERSION = 3.1;
		
	
	private final static String SCHEMA_VERISON = "3.0";
	
	/**
     * Schema Generated Timestamp in sec
     */
    private final static int SCHEMA_DATE = 1382511981;
	
	/**
	 * Enable offline Action
	 */
	private final static boolean SUPPORT_OFFLINE = true;
	
	
	/**
	* Enable parallel Action request (Add, Update, Delete)
	*/
	private final static boolean ENABLE_PARALLEL_ACTION = true;
	
	
	
	/** End of Basic Configuration file **/	
	
	
	/**
	*
	*		Getters Methods
	*
	*/
	
	
	public static String getApplicationId() {
		return APPLICATION_ID;
	}

	public static String getApplicationKey() {
		return APPLICATION_KEY;
	}

	public static String getFbApplicationKey() {
		return FB_APPLICATION_KEY;
	}

	public static boolean isPushEnabled() {
		return ENABLE_PUSH;
	}

	public static boolean isLocationServiceEnabled() {
		return ENABLE_LOCATION_SERVICE;
	}

	public static boolean isParallelActionEnabled() {
		return ENABLE_PARALLEL_ACTION;
	}

	public static double getFrameworkVersion() {
		return FRAMEWORK_VERSION;
	}

	public static double getSDKVersion() {
		return SDK_VERSION;
	}

	public static String getSchemaVersion() {
		return SCHEMA_VERISON;
	}

	public static boolean isApplicasaDebugEnabled() {
		return ENABLE_APPLICASA_DEBUG;
	}

	public static int getSchemaDate() {
		return SCHEMA_DATE;
	}

	public static boolean isSandbox() {
		return SANDBOX;
	}

	public static boolean isIAPEnabled() {
		return ENABLE_IAP;
	}

	public static boolean isSupportOffline() {
		return SUPPORT_OFFLINE;
	}

	public static boolean isFacebookEnabled()
	{
		return ENABLE_FACEBOOK;
	}
	
	public static boolean isFacebookDebugEnabled() {
		return ENABLE_FACEBOOK_DEBUG;
	}

	public static String getGoogleGCM()
	{
		return GOOGLE_GCM;
	}

	public static String getGooglePlayPublicKey() {
		return GOOGLE_PLAY_PUBLIC_KEY;
	}
	
	public static boolean isSandboxModeEnabled() {
		return ENABLE_IAP_SANDBOX;
	}

	 public static String getChartboostId() {
		return CHARTBOOST_ID;
	}
 
	public static String getChartboostSignature() {
		return CHARTBOOST_SIGNATURE;
	}
 
	public static boolean isChartboostEnabled() {
		return ENABLE_CHARTBOOST;
	}
	
	public static boolean isMMediaEnabled() {
		return ENABLE_MMEDIA;
	}
	
	public static boolean isSupersonicAdsEnabled() {
		return ENABLE_SUPERSONICADS;
	}
	
	public static String getSupersonicAdsAppId() {
		return SUPERSONICADS_APPID;
	}
	
	public static boolean isAppnextEnabled() {
		return ENABLE_APPNEXT;
	}
	
	public static String getSponserPayAppId() {
		return SPONSORPAY_ID;
	}
	
	public static String getSponserPaySecurityToken() {
		return SPONSORPAY_SECURITYTOKEN;
	}
	
	public static boolean isSponsorPayEnabled() {
		return ENABLE_SPONSORPAY;
	}
	
	
	public static int getSessionPauseTime()
	{
		return SESSION_PAUSE_TIME;
	}
	
	
	
}

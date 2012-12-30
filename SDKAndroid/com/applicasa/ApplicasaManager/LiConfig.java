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
	* Enter GCM SenderID (projectId or email)
	*/
	private final static String GOOGLE_GCM = "423501440257";
 
	/**
	 * Enter FaceBook Application key
	 */
	private final static String FB_APPLICATION_KEY = "494708670563462";


	/**
	 * Enable push services
	 */
	private final static boolean ENABLE_PUSH = false;
		
	/**
	 * Enable Location Services
	 */
	private final static boolean ENABLE_LOCATION_SERVICE = true;
	
	/**
	* Enable parallel Action request (Add, Update, Delete)
	*/
	private final static boolean ENABLE_PARALLEL_ACTION = true;
 
	/**
	 *  Minimum Framework Version - DO NOT ALTER
	 */
	private final static double FRAMEWORK_VERSION = 2.0;
	
	/**
	 *   SDK Version - DO NOT ALTER
	 */
	private final static double SDK_VERSION = 2.0;
		
	
	private final static int SERIES = 1;
		
	/**
	 * Enable Applicasa Debug 
	 */
	private final static boolean ENABLE_APPLICASA_DEBUG = true;

	/**
     * Schema Generated Timestamp in sec
     */
    private final static int SCHEMA_DATE = 1356879406;
	
	/**
	* Indicates whether working in live or sandbox environment
	*/
	private final static boolean SANDBOX = true;
	
	/**
	* Indicate if Facebook allowed
	*/
	private final static boolean ENABLE_FACEBOOK = false;
 
 	
	/**
	 * Enable offline Action
	 */
	private final static boolean SUPPORT_OFFLINE = true;
	
	/**
	 * Enable Fb debug
	 */
	private static boolean ENABLE_FACEBOOK_DEBUG = false;	

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

	public static int getSeries() {
		return SERIES;
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

	
	

	
}

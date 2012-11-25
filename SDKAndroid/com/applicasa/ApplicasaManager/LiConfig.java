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
	private final static boolean EnablePush = true;
		
	/**
	 * Enable Location Services
	 */
	private final static boolean EnableLocationService = true;
	
	/**
	* Enable parallel Action request (Add, Update, Delete)
	*/
	private final static boolean EnableParallelAction = true;
 
	/**
	 *  Minimum Framework Version - DO NOT ALTER
	 */
	private final static double FrameworkVersion = 2.0;
	
	/**
	 *   SDK Version - DO NOT ALTER
	 */
	private final static double SDKVersion = 2.0;
		
	
	private final static int Series = 1;
		
	/**
	 * Enable Applicasa Debug 
	 */
	private final static boolean EnableApplicasaDebug = true;

	/**
     * Schema Generated Timestamp in sec
     */
    private final static int SCHEMA_DATE = 1353260496;
	
	/**
	* Indicates whether working in live or sandbox environment
	*/
	private final static boolean SANDBOX = true;
	
	/**
	* Indicate if Facebook allowed
	*/
	private final static boolean EnableFacebook = true;
 
 	
	/**
	 * Enable offline Action
	 */
	private final static boolean SupportOffline = true;
	
	
	/** End of Basic Configuration file **/

	/**
	* Enter Google public key
	*/
	private final static String GOOGLE_PLAY_PUBLIC_KEY =  "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApGDOBXSVthJnYxJ7xcMGV7ixZ97Xw9CWN4KGtgItEG4Ltth1P261HCxAdplyK83X0XCFNSd/JXin2yD37eX7eTyeEk7HFxvCu7Y0MqB8L8dOkCyUcit859a9WjTYJG5DrBjU23e2DZ0WrnlvghE/A0FWzMzBDrJg30gafK4Iy8gXHVSFLDn/qPfvQgKB0X0GwUjteImoNLZemaht8LE40v8BapkHHmeizs5QCj/Mz5lyY6W7XDjlCGdWN+D4PfwB7T3j3Jpp8cvUaOl7W1pM8QqWyDuOUxSo14dC+TLEcpmYUlazbAEs9FN0NL/rb6pjxNw85d/CNtEmv8XoIlAHrQIDAQAB";
	
	
	public static String getGooglePlayPublicKey() {
		return GOOGLE_PLAY_PUBLIC_KEY;
	}

	
	/**
	 * Is IAP in sandbox mode
	 */
	private final static boolean IsInAppSandboxtMode = false;

	
	public static boolean isIsInappSandboxMode() {
		return IsInAppSandboxtMode;
	}	
	
	
	
	/**
	* Is IAP Enabled mode
	*/
	private final static boolean EnableIAP = true;
	
	public static String getApplicationId() {
		return APPLICATION_ID;
	}




	public static String getApplicationKey() {
		return APPLICATION_KEY;
	}




	public static String getFbApplicationKey() {
		return FB_APPLICATION_KEY;
	}




	public static boolean isEnablePush() {
		return EnablePush;
	}




	public static boolean isEnableLocationService() {
		return EnableLocationService;
	}




	public static boolean isEnableParallelAction() {
		return EnableParallelAction;
	}




	public static double getFrameworkVersion() {
		return FrameworkVersion;
	}




	public static double getSDKVersion() {
		return SDKVersion;
	}




	public static int getSeries() {
		return Series;
	}




	public static boolean isEnableApplicasaDebug() {
		return EnableApplicasaDebug;
	}




	public static int getSchemaDate() {
		return SCHEMA_DATE;
	}




	public static boolean isSandbox() {
		return SANDBOX;
	}




	public static boolean isEnableIAP() {
		return EnableIAP;
	}




	public static boolean isSupportOffline() {
		return SupportOffline;
	}
	

	public static boolean isEnableFacebook()
	{
		return EnableFacebook;
	}

	public static String getGoogleGCM()
	{
		return GOOGLE_GCM;
	}
	
//	// only in applicasa enviorment
//	private final static boolean DevelopmentMode = true;
//	
//	public static boolean isDevelopmentMode() {
//		return DevelopmentMode;
//	}
	

	
}

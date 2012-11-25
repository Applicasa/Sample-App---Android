package com.applicasa.Dynamic;

public class LiConfig {

	/**
	* Enter Application ID
	*/
	private final static String APPLICATION_ID = "1178";
	
	/**
	 * Enter Application Key
	 */
	private final static String APPLICATION_KEY = "ddab543d8655f546";
	
	/**
	* Enter GCM SenderID (projectId or email)
	*/
	private final static String GOOGLE_GCM = "776853278532";
 
	/**
	 * Enter FaceBook Application key
	 */
	private final static String FB_APPLICATION_KEY = "305314749565501";

	/**
	 * Enable push services
	 */
	private final static boolean EnablePush = false;
		
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
    private final static int SCHEMA_DATE = 1351590095;
	
	/**
	* Indicates whether working in live or sandbox environment
	*/
	private final static boolean SANDBOX = true;
	
	/**
	* Indicate if Facebook allowed
	*/
	private final static boolean EnableFacebook = false;
 
 	
	/**
	 * Enable offline Action
	 */
	private final static boolean SupportOffline = true;
	
	
	/** End of Basic Configuration file **/

	/**
	* Enter Google public key
	*/
	private final static String GOOGLE_PLAY_PUBLIC_KEY =  "<GOOGLE_PLAY_PUBLIC_KEY>";
	
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
	
	

	
}

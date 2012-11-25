package com.example.appvilleegg.sampleApp;

import android.app.Application;

public class LiApplication extends Application{
	
		  private static boolean activityVisible;

		  public static void activityResumed() {
		    activityVisible = true;
		  }

		  public static void activityPaused() {
		    activityVisible = false;
		  }

		  public static boolean isActivityVisible() {
		    return activityVisible;
		  }  
}

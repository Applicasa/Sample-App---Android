package com.applicasa.Languages;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import applicasa.LiCore.LiLocation;
import applicasa.LiCore.LiField;
import applicasa.LiJson.LiJSONObject;

public class LanguagesData {


	protected static Map<String, LiFieldLanguages> stringMap = new HashMap<String, LiFieldLanguages>();
	LiJSONObject incrementedFields = new LiJSONObject();
	public static boolean EnableOffline = true;
	public enum LiFieldLanguages implements LiField
	{
		Languages_None
	, LanguagesID
	, LanguagesLastUpdate
	, LanguagesText
	, LanguagesLanguageName

	;

		private LiFieldLanguages() {
			stringMap.put(this.toString(), this);
		}

		public static LiFieldLanguages getLiFieldLanguages(String key) {
			return stringMap.get(key);
	}
	}

	protected static Map<String, Object > languagesCallbacks = new HashMap<String, Object>();
	//Class Name 
	public final static String kClassName                =  "Languages";
	
	////
	//// Class fields name - Static Fields
	////
	////
	////
		public String LanguagesID;
	
		public GregorianCalendar LanguagesLastUpdate;
	
		public String LanguagesText;
	
		public String LanguagesLanguageName;
	
	
		public String getLanguagesID() {
			return LanguagesID;
		}
		
		public void setLanguagesID(String LanguagesID) {
			this.LanguagesID = LanguagesID;
		}
		
		public GregorianCalendar getLanguagesLastUpdate() {
			return LanguagesLastUpdate;
		}
		
		public void setLanguagesLastUpdate(GregorianCalendar LanguagesLastUpdate) {
			this.LanguagesLastUpdate = LanguagesLastUpdate;
		}
		
		public String getLanguagesText() {
			return LanguagesText;
		}
		
		public void setLanguagesText(String LanguagesText) {
			this.LanguagesText = LanguagesText;
		}
		
		public String getLanguagesLanguageName() {
			return LanguagesLanguageName;
		}
		
		public void setLanguagesLanguageName(String LanguagesLanguageName) {
			this.LanguagesLanguageName = LanguagesLanguageName;
		}
		
		public static String getLanguagesSortField(LanguagesData.LiFieldLanguages field)
		{
			return field.toString();
		}
	public Object getLanguagesFieldbySortType(LanguagesData.LiFieldLanguages field)
	{
		switch (field){
			case Languages_None:
				return LanguagesID;
				
			case LanguagesID:
				return LanguagesID;
				
			case LanguagesLastUpdate:
				return LanguagesLastUpdate;
				
			case LanguagesText:
				return LanguagesText;
				
			case LanguagesLanguageName:
				return LanguagesLanguageName;
				
			default:
				return "";
		}
		
	}
	
	protected boolean setLanguagesFieldbySortType(LanguagesData.LiFieldLanguages field, Object value)
	{
		switch (field){
			case Languages_None:
				break;
				
			case LanguagesID:
					LanguagesID = (String)value;
					break;
					
			case LanguagesLastUpdate:
					LanguagesLastUpdate = (GregorianCalendar)value;
					break;
				
			case LanguagesText:
					LanguagesText = (String)value;
					break;
					
			case LanguagesLanguageName:
					LanguagesLanguageName = (String)value;
					break;
					
			default:
				break;
		}
		return true;
	}
}

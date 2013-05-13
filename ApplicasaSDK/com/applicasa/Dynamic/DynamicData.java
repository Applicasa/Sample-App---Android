package com.applicasa.Dynamic;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import applicasa.LiCore.LiLocation;
import applicasa.LiCore.LiField;
import applicasa.LiJson.LiJSONObject;

public class DynamicData {


	protected static Map<String, LiFieldDynamic> stringMap = new HashMap<String, LiFieldDynamic>();
	LiJSONObject incrementedFields = new LiJSONObject();
	public static boolean EnableOffline = true;
	public enum LiFieldDynamic implements LiField
	{
		Dynamic_None
	, DynamicID
	, DynamicLastUpdate
	, DynamicText
	, DynamicNumber
	, DynamicReal
	, DynamicDate
	, DynamicBool
	, DynamicHtml
	, DynamicImage

	;

		private LiFieldDynamic() {
			stringMap.put(this.toString(), this);
		}

		public static LiFieldDynamic getLiFieldDynamic(String key) {
			return stringMap.get(key);
	}
	}

	protected static Map<String, Object > dynamicCallbacks = new HashMap<String, Object>();
	//Class Name 
	public final static String kClassName                =  "Dynamic";
	
	////
	//// Class fields name - Static Fields
	////
	////
	////
		public String DynamicID;
	
		public GregorianCalendar DynamicLastUpdate;
	
		public String DynamicText;
	
		public int DynamicNumber;
	
		public float DynamicReal;
	
		public GregorianCalendar DynamicDate;
	
		public Boolean DynamicBool;
	
		public String DynamicHtml;
	
		public String DynamicImage;
	
	
		public String getDynamicID() {
			return DynamicID;
		}
		
		public void setDynamicID(String DynamicID) {
			this.DynamicID = DynamicID;
		}
		
		public GregorianCalendar getDynamicLastUpdate() {
			return DynamicLastUpdate;
		}
		
		public void setDynamicLastUpdate(GregorianCalendar DynamicLastUpdate) {
			this.DynamicLastUpdate = DynamicLastUpdate;
		}
		
		public String getDynamicText() {
			return DynamicText;
		}
		
		public void setDynamicText(String DynamicText) {
			this.DynamicText = DynamicText;
		}
		
		public int getDynamicNumber() {
			return DynamicNumber;
		}
		
		public void setDynamicNumber(int DynamicNumber) {
			this.DynamicNumber = DynamicNumber;
		}
		
		public float getDynamicReal() {
			return DynamicReal;
		}
		
		public void setDynamicReal(float DynamicReal) {
			this.DynamicReal = DynamicReal;
		}
		
		public GregorianCalendar getDynamicDate() {
			return DynamicDate;
		}
		
		public void setDynamicDate(GregorianCalendar DynamicDate) {
			this.DynamicDate = DynamicDate;
		}
		
		public Boolean getDynamicBool() {
			return DynamicBool;
		}
		
		public void setDynamicBool(Boolean DynamicBool) {
			this.DynamicBool = DynamicBool;
		}
		
		public String getDynamicHtml() {
			return DynamicHtml;
		}
		
		public void setDynamicHtml(String DynamicHtml) {
			this.DynamicHtml = DynamicHtml;
		}
		
		public String getDynamicImage() {
			return DynamicImage;
		}
		
		public void setDynamicImage(String DynamicImage) {
			this.DynamicImage = DynamicImage;
		}
		
		public static String getDynamicSortField(DynamicData.LiFieldDynamic field)
		{
			return field.toString();
		}
	public Object getDynamicFieldbySortType(DynamicData.LiFieldDynamic field)
	{
		switch (field){
			case Dynamic_None:
				return DynamicID;
				
			case DynamicID:
				return DynamicID;
				
			case DynamicLastUpdate:
				return DynamicLastUpdate;
				
			case DynamicText:
				return DynamicText;
				
			case DynamicNumber:
				return DynamicNumber;
				
			case DynamicReal:
				return DynamicReal;
				
			case DynamicDate:
				return DynamicDate;
				
			case DynamicBool:
					return DynamicBool;
					
			case DynamicHtml:
				return DynamicHtml;
				
			case DynamicImage:
				return DynamicImage;
				
			default:
				return "";
		}
		
	}
	
	protected boolean setDynamicFieldbySortType(DynamicData.LiFieldDynamic field, Object value)
	{
		switch (field){
			case Dynamic_None:
				break;
				
			case DynamicID:
					DynamicID = (String)value;
					break;
					
			case DynamicLastUpdate:
					DynamicLastUpdate = (GregorianCalendar)value;
					break;
				
			case DynamicText:
					DynamicText = (String)value;
					break;
					
			case DynamicNumber:
					DynamicNumber = (Integer)value;
					break;
					
			case DynamicReal:
					DynamicReal = (Float)value;
					break;
					
			case DynamicDate:
					DynamicDate = (GregorianCalendar)value;
					break;
				
			case DynamicBool:
					DynamicBool = (Boolean)value;
					break;
					
			case DynamicHtml:
					DynamicHtml = (String)value;
					break;
					
			case DynamicImage:
					DynamicImage = (String)value;
					break;
					
			default:
				break;
		}
		return true;
	}
}

package com.applicasa.Colors;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import applicasa.LiCore.LiLocation;
import applicasa.LiCore.LiField;
import applicasa.LiJson.LiJSONObject;

public class ColorsData {


	protected static Map<String, LiFieldColors> stringMap = new HashMap<String, LiFieldColors>();
	LiJSONObject incrementedFields = new LiJSONObject();
	public static boolean EnableOffline = true;
	public enum LiFieldColors implements LiField
	{
		Colors_None
	, ColorsID
	, ColorsLastUpdate
	, ColorsNumber
	, ColorsColor

	;

		private LiFieldColors() {
			stringMap.put(this.toString(), this);
		}

		public static LiFieldColors getLiFieldColors(String key) {
			return stringMap.get(key);
	}
	}

	protected static Map<String, Object > colorsCallbacks = new HashMap<String, Object>();
	//Class Name 
	public final static String kClassName                =  "Colors";
	
	////
	//// Class fields name - Static Fields
	////
	////
	////
		public String ColorsID;
	
		public GregorianCalendar ColorsLastUpdate;
	
		public int ColorsNumber;
	
		public String ColorsColor;
	
	
		public String getColorsID() {
			return ColorsID;
		}
		
		public void setColorsID(String ColorsID) {
			this.ColorsID = ColorsID;
		}
		
		public GregorianCalendar getColorsLastUpdate() {
			return ColorsLastUpdate;
		}
		
		public void setColorsLastUpdate(GregorianCalendar ColorsLastUpdate) {
			this.ColorsLastUpdate = ColorsLastUpdate;
		}
		
		public int getColorsNumber() {
			return ColorsNumber;
		}
		
		public void setColorsNumber(int ColorsNumber) {
			this.ColorsNumber = ColorsNumber;
		}
		
		public String getColorsColor() {
			return ColorsColor;
		}
		
		public void setColorsColor(String ColorsColor) {
			this.ColorsColor = ColorsColor;
		}
		
		public static String getColorsSortField(ColorsData.LiFieldColors field)
		{
			return field.toString();
		}
	public Object getColorsFieldbySortType(ColorsData.LiFieldColors field)
	{
		switch (field){
			case Colors_None:
				return ColorsID;
				
			case ColorsID:
				return ColorsID;
				
			case ColorsLastUpdate:
				return ColorsLastUpdate;
				
			case ColorsNumber:
				return ColorsNumber;
				
			case ColorsColor:
				return ColorsColor;
				
			default:
				return "";
		}
		
	}
	
	protected boolean setColorsFieldbySortType(ColorsData.LiFieldColors field, Object value)
	{
		switch (field){
			case Colors_None:
				break;
				
			case ColorsID:
					ColorsID = (String)value;
					break;
					
			case ColorsLastUpdate:
					ColorsLastUpdate = (GregorianCalendar)value;
					break;
				
			case ColorsNumber:
					ColorsNumber = (Integer)value;
					break;
					
			case ColorsColor:
					ColorsColor = (String)value;
					break;
					
			default:
				break;
		}
		return true;
	}
}

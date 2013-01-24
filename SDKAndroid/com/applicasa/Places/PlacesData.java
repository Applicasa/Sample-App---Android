package com.applicasa.Places;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import applicasa.LiCore.LiLocation;
import applicasa.LiCore.LiField;
import applicasa.LiJson.LiJSONObject;

public class PlacesData {


	protected static Map<String, LiFieldPlaces> stringMap = new HashMap<String, LiFieldPlaces>();
	LiJSONObject incrementedFields = new LiJSONObject();
	public static boolean EnableOffline = true;
	public enum LiFieldPlaces implements LiField
	{
		Places_None
	, PlacesID
	, PlacesLastUpdate
	, PlacesLoc
	, PlacesName

	;

		private LiFieldPlaces() {
			stringMap.put(this.toString(), this);
		}

		public static LiFieldPlaces getLiFieldPlaces(String key) {
			return stringMap.get(key);
	}
	}

	protected static Map<String, Object > placesCallbacks = new HashMap<String, Object>();
	//Class Name 
	public final static String kClassName                =  "Places";
	
	////
	//// Class fields name - Static Fields
	////
	////
	////
		public String PlacesID;
	
		public GregorianCalendar PlacesLastUpdate;
	
		public LiLocation PlacesLoc;
	
		public String PlacesName;
	
		public double DistanceFromCurrent;
	
		public String getPlacesID() {
			return PlacesID;
		}
		
		public void setPlacesID(String PlacesID) {
			this.PlacesID = PlacesID;
		}
		
		public GregorianCalendar getPlacesLastUpdate() {
			return PlacesLastUpdate;
		}
		
		public void setPlacesLastUpdate(GregorianCalendar PlacesLastUpdate) {
			this.PlacesLastUpdate = PlacesLastUpdate;
		}
		
		public LiLocation getPlacesLoc() {
			return PlacesLoc;
		}
		
		public void setPlacesLoc(LiLocation PlacesLoc) {
			this.PlacesLoc = PlacesLoc;
		}
		
		public String getPlacesName() {
			return PlacesName;
		}
		
		public void setPlacesName(String PlacesName) {
			this.PlacesName = PlacesName;
		}
		
		public static String getPlacesSortField(PlacesData.LiFieldPlaces field)
		{
			return field.toString();
		}
	public Object getPlacesFieldbySortType(PlacesData.LiFieldPlaces field)
	{
		switch (field){
			case Places_None:
				return PlacesID;
				
			case PlacesID:
				return PlacesID;
				
			case PlacesLastUpdate:
				return PlacesLastUpdate;
				
			case PlacesName:
				return PlacesName;
				
			default:
				return "";
		}
		
	}
	
	protected boolean setPlacesFieldbySortType(PlacesData.LiFieldPlaces field, Object value)
	{
		switch (field){
			case Places_None:
				break;
				
			case PlacesID:
					PlacesID = (String)value;
					break;
					
			case PlacesLastUpdate:
					PlacesLastUpdate = (GregorianCalendar)value;
					break;
				
			case PlacesLoc:
					PlacesLoc = (LiLocation)value;
					break;
					
			case PlacesName:
					PlacesName = (String)value;
					break;
					
			default:
				break;
		}
		return true;
	}
}

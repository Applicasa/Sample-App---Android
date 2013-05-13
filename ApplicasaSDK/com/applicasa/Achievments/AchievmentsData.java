package com.applicasa.Achievments;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import applicasa.LiCore.LiLocation;
import applicasa.LiCore.LiField;
import applicasa.LiJson.LiJSONObject;

public class AchievmentsData {


	protected static Map<String, LiFieldAchievments> stringMap = new HashMap<String, LiFieldAchievments>();
	LiJSONObject incrementedFields = new LiJSONObject();
	public static boolean EnableOffline = true;
	public enum LiFieldAchievments implements LiField
	{
		Achievments_None
	, AchievmentsID
	, AchievmentsLastUpdate
	, AchievmentsPoints
	, AchievmentsDes

	;

		private LiFieldAchievments() {
			stringMap.put(this.toString(), this);
		}

		public static LiFieldAchievments getLiFieldAchievments(String key) {
			return stringMap.get(key);
	}
	}

	protected static Map<String, Object > achievmentsCallbacks = new HashMap<String, Object>();
	//Class Name 
	public final static String kClassName                =  "Achievments";
	
	////
	//// Class fields name - Static Fields
	////
	////
	////
		public String AchievmentsID;
	
		public GregorianCalendar AchievmentsLastUpdate;
	
		public int AchievmentsPoints;
	
		public String AchievmentsDes;
	
	
		public String getAchievmentsID() {
			return AchievmentsID;
		}
		
		public void setAchievmentsID(String AchievmentsID) {
			this.AchievmentsID = AchievmentsID;
		}
		
		public GregorianCalendar getAchievmentsLastUpdate() {
			return AchievmentsLastUpdate;
		}
		
		public void setAchievmentsLastUpdate(GregorianCalendar AchievmentsLastUpdate) {
			this.AchievmentsLastUpdate = AchievmentsLastUpdate;
		}
		
		public int getAchievmentsPoints() {
			return AchievmentsPoints;
		}
		
		public void setAchievmentsPoints(int AchievmentsPoints) {
			this.AchievmentsPoints = AchievmentsPoints;
		}
		
		public String getAchievmentsDes() {
			return AchievmentsDes;
		}
		
		public void setAchievmentsDes(String AchievmentsDes) {
			this.AchievmentsDes = AchievmentsDes;
		}
		
		public static String getAchievmentsSortField(AchievmentsData.LiFieldAchievments field)
		{
			return field.toString();
		}
	public Object getAchievmentsFieldbySortType(AchievmentsData.LiFieldAchievments field)
	{
		switch (field){
			case Achievments_None:
				return AchievmentsID;
				
			case AchievmentsID:
				return AchievmentsID;
				
			case AchievmentsLastUpdate:
				return AchievmentsLastUpdate;
				
			case AchievmentsPoints:
				return AchievmentsPoints;
				
			case AchievmentsDes:
				return AchievmentsDes;
				
			default:
				return "";
		}
		
	}
	
	protected boolean setAchievmentsFieldbySortType(AchievmentsData.LiFieldAchievments field, Object value)
	{
		switch (field){
			case Achievments_None:
				break;
				
			case AchievmentsID:
					AchievmentsID = (String)value;
					break;
					
			case AchievmentsLastUpdate:
					AchievmentsLastUpdate = (GregorianCalendar)value;
					break;
				
			case AchievmentsPoints:
					AchievmentsPoints = (Integer)value;
					break;
					
			case AchievmentsDes:
					AchievmentsDes = (String)value;
					break;
					
			default:
				break;
		}
		return true;
	}
}

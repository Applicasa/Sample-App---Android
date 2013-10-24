package com.applicasa.Levels;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import applicasa.LiCore.LiLocation;
import applicasa.LiCore.LiField;
import applicasa.LiJson.LiJSONObject;

public class LevelsData {


	protected static Map<String, LiFieldLevels> stringMap = new HashMap<String, LiFieldLevels>();
	LiJSONObject incrementedFields = new LiJSONObject();
	public static boolean EnableOffline = true;
	public enum LiFieldLevels implements LiField
	{
		Levels_None
	, LevelsID
	, LevelsLastUpdate
	, LevelsGtgtg
	, LevelsHTML
	, LevelsTgtggtg

	;

		private LiFieldLevels() {
			stringMap.put(this.toString(), this);
		}

		public static LiFieldLevels getLiFieldLevels(String key) {
			return stringMap.get(key);
	}
	}

	protected static Map<String, Object > levelsCallbacks = new HashMap<String, Object>();
	//Class Name 
	public final static String kClassName                =  "Levels";
	
	////
	//// Class fields name - Static Fields
	////
	////
	////
		public String LevelsID;
	
		public GregorianCalendar LevelsLastUpdate;
	
		public String LevelsGtgtg;
	
		public String LevelsHTML;
	
		public int LevelsTgtggtg;
	
	
		public String getLevelsID() {
			return LevelsID;
		}
		
		public void setLevelsID(String LevelsID) {
			this.LevelsID = LevelsID;
		}
		
		public GregorianCalendar getLevelsLastUpdate() {
			return LevelsLastUpdate;
		}
		
		public void setLevelsLastUpdate(GregorianCalendar LevelsLastUpdate) {
			this.LevelsLastUpdate = LevelsLastUpdate;
		}
		
		public String getLevelsGtgtg() {
			return LevelsGtgtg;
		}
		
		public void setLevelsGtgtg(String LevelsGtgtg) {
			this.LevelsGtgtg = LevelsGtgtg;
		}
		
		public String getLevelsHTML() {
			return LevelsHTML;
		}
		
		public void setLevelsHTML(String LevelsHTML) {
			this.LevelsHTML = LevelsHTML;
		}
		
		public int getLevelsTgtggtg() {
			return LevelsTgtggtg;
		}
		
		public void setLevelsTgtggtg(int LevelsTgtggtg) {
			this.LevelsTgtggtg = LevelsTgtggtg;
		}
		
		public static String getLevelsSortField(LevelsData.LiFieldLevels field)
		{
			return field.toString();
		}
	public Object getLevelsFieldbySortType(LevelsData.LiFieldLevels field)
	{
		switch (field){
			case Levels_None:
				return LevelsID;
				
			case LevelsID:
				return LevelsID;
				
			case LevelsLastUpdate:
				return LevelsLastUpdate;
				
			case LevelsGtgtg:
				return LevelsGtgtg;
				
			case LevelsHTML:
				return LevelsHTML;
				
			case LevelsTgtggtg:
				return LevelsTgtggtg;
				
			default:
				return "";
		}
		
	}
	
	protected boolean setLevelsFieldbySortType(LevelsData.LiFieldLevels field, Object value)
	{
		switch (field){
			case Levels_None:
				break;
				
			case LevelsID:
					LevelsID = (String)value;
					break;
					
			case LevelsLastUpdate:
					LevelsLastUpdate = (GregorianCalendar)value;
					break;
				
			case LevelsGtgtg:
					LevelsGtgtg = (String)value;
					break;
					
			case LevelsHTML:
					LevelsHTML = (String)value;
					break;
					
			case LevelsTgtggtg:
					LevelsTgtggtg = (Integer)value;
					break;
					
			default:
				break;
		}
		return true;
	}
}

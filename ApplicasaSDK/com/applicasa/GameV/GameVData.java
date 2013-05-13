package com.applicasa.GameV;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import applicasa.LiCore.LiLocation;
import applicasa.LiCore.LiField;
import applicasa.LiJson.LiJSONObject;

public class GameVData {


	protected static Map<String, LiFieldGameV> stringMap = new HashMap<String, LiFieldGameV>();
	LiJSONObject incrementedFields = new LiJSONObject();
	public static boolean EnableOffline = true;
	public enum LiFieldGameV implements LiField
	{
		GameV_None
	, GameVID
	, GameVLastUpdate
	, GameVValue
	, GameVFunction

	;

		private LiFieldGameV() {
			stringMap.put(this.toString(), this);
		}

		public static LiFieldGameV getLiFieldGameV(String key) {
			return stringMap.get(key);
	}
	}

	protected static Map<String, Object > gameVCallbacks = new HashMap<String, Object>();
	//Class Name 
	public final static String kClassName                =  "GameV";
	
	////
	//// Class fields name - Static Fields
	////
	////
	////
		public String GameVID;
	
		public GregorianCalendar GameVLastUpdate;
	
		public int GameVValue;
	
		public String GameVFunction;
	
	
		public String getGameVID() {
			return GameVID;
		}
		
		public void setGameVID(String GameVID) {
			this.GameVID = GameVID;
		}
		
		public GregorianCalendar getGameVLastUpdate() {
			return GameVLastUpdate;
		}
		
		public void setGameVLastUpdate(GregorianCalendar GameVLastUpdate) {
			this.GameVLastUpdate = GameVLastUpdate;
		}
		
		public int getGameVValue() {
			return GameVValue;
		}
		
		public void setGameVValue(int GameVValue) {
			this.GameVValue = GameVValue;
		}
		
		public String getGameVFunction() {
			return GameVFunction;
		}
		
		public void setGameVFunction(String GameVFunction) {
			this.GameVFunction = GameVFunction;
		}
		
		public static String getGameVSortField(GameVData.LiFieldGameV field)
		{
			return field.toString();
		}
	public Object getGameVFieldbySortType(GameVData.LiFieldGameV field)
	{
		switch (field){
			case GameV_None:
				return GameVID;
				
			case GameVID:
				return GameVID;
				
			case GameVLastUpdate:
				return GameVLastUpdate;
				
			case GameVValue:
				return GameVValue;
				
			case GameVFunction:
				return GameVFunction;
				
			default:
				return "";
		}
		
	}
	
	protected boolean setGameVFieldbySortType(GameVData.LiFieldGameV field, Object value)
	{
		switch (field){
			case GameV_None:
				break;
				
			case GameVID:
					GameVID = (String)value;
					break;
					
			case GameVLastUpdate:
					GameVLastUpdate = (GregorianCalendar)value;
					break;
				
			case GameVValue:
					GameVValue = (Integer)value;
					break;
					
			case GameVFunction:
					GameVFunction = (String)value;
					break;
					
			default:
				break;
		}
		return true;
	}
}

package com.applicasa.DataManager;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import applicasa.LiCore.LiLocation;
import applicasa.LiCore.LiField;
import applicasa.LiJson.LiJSONObject;

public class DataManagerData {


	protected static Map<String, LiFieldDataManager> stringMap = new HashMap<String, LiFieldDataManager>();
	LiJSONObject incrementedFields = new LiJSONObject();
	public static boolean EnableOffline = true;
	public enum LiFieldDataManager implements LiField
	{
		DataManager_None
	, DataManagerID
	, DataManagerLastUpdate
	, DataManagerAaa
	, DataManagerName

	;

		private LiFieldDataManager() {
			stringMap.put(this.toString(), this);
		}

		public static LiFieldDataManager getLiFieldDataManager(String key) {
			return stringMap.get(key);
	}
	}

	protected static Map<String, Object > dataManagerCallbacks = new HashMap<String, Object>();
	//Class Name 
	public final static String kClassName                =  "DataManager";
	
	////
	//// Class fields name - Static Fields
	////
	////
	////
		public String DataManagerID;
	
		public GregorianCalendar DataManagerLastUpdate;
	
		public int DataManagerAaa;
	
		public String DataManagerName;
	
	
		public String getDataManagerID() {
			return DataManagerID;
		}
		
		public void setDataManagerID(String DataManagerID) {
			this.DataManagerID = DataManagerID;
		}
		
		public GregorianCalendar getDataManagerLastUpdate() {
			return DataManagerLastUpdate;
		}
		
		public void setDataManagerLastUpdate(GregorianCalendar DataManagerLastUpdate) {
			this.DataManagerLastUpdate = DataManagerLastUpdate;
		}
		
		public int getDataManagerAaa() {
			return DataManagerAaa;
		}
		
		public void setDataManagerAaa(int DataManagerAaa) {
			this.DataManagerAaa = DataManagerAaa;
		}
		
		public String getDataManagerName() {
			return DataManagerName;
		}
		
		public void setDataManagerName(String DataManagerName) {
			this.DataManagerName = DataManagerName;
		}
		
		public static String getDataManagerSortField(DataManagerData.LiFieldDataManager field)
		{
			return field.toString();
		}
	public Object getDataManagerFieldbySortType(DataManagerData.LiFieldDataManager field)
	{
		switch (field){
			case DataManager_None:
				return DataManagerID;
				
			case DataManagerID:
				return DataManagerID;
				
			case DataManagerLastUpdate:
				return DataManagerLastUpdate;
				
			case DataManagerAaa:
				return DataManagerAaa;
				
			case DataManagerName:
				return DataManagerName;
				
			default:
				return "";
		}
		
	}
	
	protected boolean setDataManagerFieldbySortType(DataManagerData.LiFieldDataManager field, Object value)
	{
		switch (field){
			case DataManager_None:
				break;
				
			case DataManagerID:
					DataManagerID = (String)value;
					break;
					
			case DataManagerLastUpdate:
					DataManagerLastUpdate = (GregorianCalendar)value;
					break;
				
			case DataManagerAaa:
					DataManagerAaa = (Integer)value;
					break;
					
			case DataManagerName:
					DataManagerName = (String)value;
					break;
					
			default:
				break;
		}
		return true;
	}
}

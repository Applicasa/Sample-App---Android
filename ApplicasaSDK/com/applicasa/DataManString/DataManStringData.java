package com.applicasa.DataManString;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import applicasa.LiCore.LiLocation;
import applicasa.LiCore.LiField;
import applicasa.LiJson.LiJSONObject;

public class DataManStringData {


	protected static Map<String, LiFieldDataManString> stringMap = new HashMap<String, LiFieldDataManString>();
	LiJSONObject incrementedFields = new LiJSONObject();
	public static boolean EnableOffline = true;
	public enum LiFieldDataManString implements LiField
	{
		DataManString_None
	, DataManStringID
	, DataManStringLastUpdate
	, DataManStringKey
	, DataManStringValue

	;

		private LiFieldDataManString() {
			stringMap.put(this.toString(), this);
		}

		public static LiFieldDataManString getLiFieldDataManString(String key) {
			return stringMap.get(key);
	}
	}

	protected static Map<String, Object > dataManStringCallbacks = new HashMap<String, Object>();
	//Class Name 
	public final static String kClassName                =  "DataManString";
	
	////
	//// Class fields name - Static Fields
	////
	////
	////
		public String DataManStringID;
	
		public GregorianCalendar DataManStringLastUpdate;
	
		public String DataManStringKey;
	
		public String DataManStringValue;
	
	
		public String getDataManStringID() {
			return DataManStringID;
		}
		
		public void setDataManStringID(String DataManStringID) {
			this.DataManStringID = DataManStringID;
		}
		
		public GregorianCalendar getDataManStringLastUpdate() {
			return DataManStringLastUpdate;
		}
		
		public void setDataManStringLastUpdate(GregorianCalendar DataManStringLastUpdate) {
			this.DataManStringLastUpdate = DataManStringLastUpdate;
		}
		
		public String getDataManStringKey() {
			return DataManStringKey;
		}
		
		public void setDataManStringKey(String DataManStringKey) {
			this.DataManStringKey = DataManStringKey;
		}
		
		public String getDataManStringValue() {
			return DataManStringValue;
		}
		
		public void setDataManStringValue(String DataManStringValue) {
			this.DataManStringValue = DataManStringValue;
		}
		
		public static String getDataManStringSortField(DataManStringData.LiFieldDataManString field)
		{
			return field.toString();
		}
	public Object getDataManStringFieldbySortType(DataManStringData.LiFieldDataManString field)
	{
		switch (field){
			case DataManString_None:
				return DataManStringID;
				
			case DataManStringID:
				return DataManStringID;
				
			case DataManStringLastUpdate:
				return DataManStringLastUpdate;
				
			case DataManStringKey:
				return DataManStringKey;
				
			case DataManStringValue:
				return DataManStringValue;
				
			default:
				return "";
		}
		
	}
	
	protected boolean setDataManStringFieldbySortType(DataManStringData.LiFieldDataManString field, Object value)
	{
		switch (field){
			case DataManString_None:
				break;
				
			case DataManStringID:
					DataManStringID = (String)value;
					break;
					
			case DataManStringLastUpdate:
					DataManStringLastUpdate = (GregorianCalendar)value;
					break;
				
			case DataManStringKey:
					DataManStringKey = (String)value;
					break;
					
			case DataManStringValue:
					DataManStringValue = (String)value;
					break;
					
			default:
				break;
		}
		return true;
	}
}

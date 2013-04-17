package com.applicasa.Tips;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import applicasa.LiCore.LiField;
import applicasa.LiJson.LiJSONObject;

public class TipsData {


	protected static Map<String, LiFieldTips> stringMap = new HashMap<String, LiFieldTips>();
	LiJSONObject incrementedFields = new LiJSONObject();
	public static boolean EnableOffline = true;
	public enum LiFieldTips implements LiField
	{
		Tips_None
	, TipsID
	, TipsLastUpdate
	, TipsContent
	, TipsNum
	, TipsFdfsd

	;

		private LiFieldTips() {
			stringMap.put(this.toString(), this);
		}

		public static LiFieldTips getLiFieldTips(String key) {
			return stringMap.get(key);
	}
	}

	protected static Map<String, Object > tipsCallbacks = new HashMap<String, Object>();
	//Class Name 
	public final static String kClassName                =  "Tips";
	
	////
	//// Class fields name - Static Fields
	////
	////
	////
		public String TipsID;
	
		public GregorianCalendar TipsLastUpdate;
	
		public String TipsContent;
	
		public int TipsNum;
	
		public int TipsFdfsd;
	
	
		public String getTipsID() {
			return TipsID;
		}
		
		public void setTipsID(String TipsID) {
			this.TipsID = TipsID;
		}
		
		public GregorianCalendar getTipsLastUpdate() {
			return TipsLastUpdate;
		}
		
		public void setTipsLastUpdate(GregorianCalendar TipsLastUpdate) {
			this.TipsLastUpdate = TipsLastUpdate;
		}
		
		public String getTipsContent() {
			return TipsContent;
		}
		
		public void setTipsContent(String TipsContent) {
			this.TipsContent = TipsContent;
		}
		
		public int getTipsNum() {
			return TipsNum;
		}
		
		public void setTipsNum(int TipsNum) {
			this.TipsNum = TipsNum;
		}
		
		public int getTipsFdfsd() {
			return TipsFdfsd;
		}
		
		public void setTipsFdfsd(int TipsFdfsd) {
			this.TipsFdfsd = TipsFdfsd;
		}
		
		public static String getTipsSortField(TipsData.LiFieldTips field)
		{
			return field.toString();
		}
	public Object getTipsFieldbySortType(TipsData.LiFieldTips field)
	{
		switch (field){
			case Tips_None:
				return TipsID;
				
			case TipsID:
				return TipsID;
				
			case TipsLastUpdate:
				return TipsLastUpdate;
				
			case TipsContent:
				return TipsContent;
				
			case TipsNum:
				return TipsNum;
				
			case TipsFdfsd:
				return TipsFdfsd;
				
			default:
				return "";
		}
		
	}
	
	protected boolean setTipsFieldbySortType(TipsData.LiFieldTips field, Object value)
	{
		switch (field){
			case Tips_None:
				break;
				
			case TipsID:
					TipsID = (String)value;
					break;
					
			case TipsLastUpdate:
					TipsLastUpdate = (GregorianCalendar)value;
					break;
				
			case TipsContent:
					TipsContent = (String)value;
					break;
					
			case TipsNum:
					TipsNum = (Integer)value;
					break;
					
			case TipsFdfsd:
					TipsFdfsd = (Integer)value;
					break;
					
			default:
				break;
		}
		return true;
	}
}

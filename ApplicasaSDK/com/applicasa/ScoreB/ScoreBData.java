package com.applicasa.ScoreB;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import applicasa.LiCore.LiLocation;
import applicasa.LiCore.LiField;
import applicasa.LiJson.LiJSONObject;
import com.applicasa.User.User;

public class ScoreBData {


	protected static Map<String, LiFieldScoreB> stringMap = new HashMap<String, LiFieldScoreB>();
	LiJSONObject incrementedFields = new LiJSONObject();
	public static boolean EnableOffline = true;
	public enum LiFieldScoreB implements LiField
	{
		ScoreB_None
	, ScoreBID
	, ScoreBLastUpdate
	, ScoreBScore
	, ScoreBUser

	;

		private LiFieldScoreB() {
			stringMap.put(this.toString(), this);
		}

		public static LiFieldScoreB getLiFieldScoreB(String key) {
			return stringMap.get(key);
	}
	}

	protected static Map<String, Object > scoreBCallbacks = new HashMap<String, Object>();
	//Class Name 
	public final static String kClassName                =  "ScoreB";
	
	////
	//// Class fields name - Static Fields
	////
	////
	////
		public String ScoreBID;
	
		public GregorianCalendar ScoreBLastUpdate;
	
		public int ScoreBScore;
	
		public User ScoreBUser;
	
		public String getScoreBID() {
			return ScoreBID;
		}
		
		public void setScoreBID(String ScoreBID) {
			this.ScoreBID = ScoreBID;
		}
		
		public GregorianCalendar getScoreBLastUpdate() {
			return ScoreBLastUpdate;
		}
		
		public void setScoreBLastUpdate(GregorianCalendar ScoreBLastUpdate) {
			this.ScoreBLastUpdate = ScoreBLastUpdate;
		}
		
		public int getScoreBScore() {
			return ScoreBScore;
		}
		
		public void setScoreBScore(int ScoreBScore) {
			this.ScoreBScore = ScoreBScore;
		}
		
		public User getScoreBUser() {
			return ScoreBUser;
		}
		
		public void setScoreBUser(User ScoreBUser) {
			this.ScoreBUser = ScoreBUser;
		}
		
		public static String getScoreBSortField(ScoreBData.LiFieldScoreB field)
		{
			return field.toString();
		}
	public Object getScoreBFieldbySortType(ScoreBData.LiFieldScoreB field)
	{
		switch (field){
			case ScoreB_None:
				return ScoreBID;
				
			case ScoreBID:
				return ScoreBID;
				
			case ScoreBLastUpdate:
				return ScoreBLastUpdate;
				
			case ScoreBScore:
				return ScoreBScore;
				
			case ScoreBUser:
				return ScoreBUser.UserID;
				
			default:
				return "";
		}
		
	}
	
	protected boolean setScoreBFieldbySortType(ScoreBData.LiFieldScoreB field, Object value)
	{
		switch (field){
			case ScoreB_None:
				break;
				
			case ScoreBID:
					ScoreBID = (String)value;
					break;
					
			case ScoreBLastUpdate:
					ScoreBLastUpdate = (GregorianCalendar)value;
					break;
				
			case ScoreBScore:
					ScoreBScore = (Integer)value;
					break;
					
			case ScoreBUser:
					ScoreBUser =  new User((String)value);
					break;
					
			default:
				break;
		}
		return true;
	}
}

package com.applicasa.Foo;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import applicasa.LiCore.LiLocation;
import applicasa.LiCore.LiField;
import applicasa.LiJson.LiJSONObject;
import com.applicasa.User.User;

public class FooData {


	protected static Map<String, LiFieldFoo> stringMap = new HashMap<String, LiFieldFoo>();
	LiJSONObject incrementedFields = new LiJSONObject();
	public static boolean EnableOffline = true;
	public enum LiFieldFoo implements LiField
	{
		Foo_None
	, FooID
	, FooLastUpdate
	, FooName
	, FooDescription
	, FooBoolean
	, FooDate
	, FooImage
	, FooFile
	, FooLocation
	, FooNumber
	, FooAge
	, FooOwner

	;

		private LiFieldFoo() {
			stringMap.put(this.toString(), this);
		}

		public static LiFieldFoo getLiFieldFoo(String key) {
			return stringMap.get(key);
	}
	}

	protected static Map<String, Object > fooCallbacks = new HashMap<String, Object>();
	//Class Name 
	public final static String kClassName                =  "Foo";
	
	////
	//// Class fields name - Static Fields
	////
	////
	////
		public String FooID;
	
		public GregorianCalendar FooLastUpdate;
	
		public String FooName;
	
		public String FooDescription;
	
		public Boolean FooBoolean;
	
		public GregorianCalendar FooDate;
	
		public String FooImage;
	
		public String FooFile;
	
		public LiLocation FooLocation;
	
		public int FooNumber;
	
		public int FooAge;
	
		public User FooOwner;
		public double DistanceFromCurrent;
	
		public String getFooID() {
			return FooID;
		}
		
		public void setFooID(String FooID) {
			this.FooID = FooID;
		}
		
		public GregorianCalendar getFooLastUpdate() {
			return FooLastUpdate;
		}
		
		public void setFooLastUpdate(GregorianCalendar FooLastUpdate) {
			this.FooLastUpdate = FooLastUpdate;
		}
		
		public String getFooName() {
			return FooName;
		}
		
		public void setFooName(String FooName) {
			this.FooName = FooName;
		}
		
		public String getFooDescription() {
			return FooDescription;
		}
		
		public void setFooDescription(String FooDescription) {
			this.FooDescription = FooDescription;
		}
		
		public Boolean getFooBoolean() {
			return FooBoolean;
		}
		
		public void setFooBoolean(Boolean FooBoolean) {
			this.FooBoolean = FooBoolean;
		}
		
		public GregorianCalendar getFooDate() {
			return FooDate;
		}
		
		public void setFooDate(GregorianCalendar FooDate) {
			this.FooDate = FooDate;
		}
		
		public String getFooImage() {
			return FooImage;
		}
		
		public void setFooImage(String FooImage) {
			this.FooImage = FooImage;
		}
		
		public String getFooFile() {
			return FooFile;
		}
		
		public void setFooFile(String FooFile) {
			this.FooFile = FooFile;
		}
		
		public LiLocation getFooLocation() {
			return FooLocation;
		}
		
		public void setFooLocation(LiLocation FooLocation) {
			this.FooLocation = FooLocation;
		}
		
		public int getFooNumber() {
			return FooNumber;
		}
		
		public void setFooNumber(int FooNumber) {
			this.FooNumber = FooNumber;
		}
		
		public int getFooAge() {
			return FooAge;
		}
		
		public void setFooAge(int FooAge) {
			this.FooAge = FooAge;
		}
		
		public User getFooOwner() {
			return FooOwner;
		}
		
		public void setFooOwner(User FooOwner) {
			this.FooOwner = FooOwner;
		}
		
		public static String getFooSortField(FooData.LiFieldFoo field)
		{
			return field.toString();
		}
	public Object getFooFieldbySortType(FooData.LiFieldFoo field)
	{
		switch (field){
			case Foo_None:
				return FooID;
				
			case FooID:
				return FooID;
				
			case FooLastUpdate:
				return FooLastUpdate;
				
			case FooName:
				return FooName;
				
			case FooDescription:
				return FooDescription;
				
			case FooBoolean:
					return FooBoolean;
					
			case FooDate:
				return FooDate;
				
			case FooImage:
				return FooImage;
				
			case FooFile:
				return FooFile;
				
			case FooNumber:
				return FooNumber;
				
			case FooAge:
				return FooAge;
				
			case FooOwner:
				return FooOwner.UserID;
				
			default:
				return "";
		}
		
	}
	
	protected boolean setFooFieldbySortType(FooData.LiFieldFoo field, Object value)
	{
		switch (field){
			case Foo_None:
				break;
				
			case FooID:
					FooID = (String)value;
					break;
					
			case FooLastUpdate:
					FooLastUpdate = (GregorianCalendar)value;
					break;
				
			case FooName:
					FooName = (String)value;
					break;
					
			case FooDescription:
					FooDescription = (String)value;
					break;
					
			case FooBoolean:
					FooBoolean = (Boolean)value;
					break;
					
			case FooDate:
					FooDate = (GregorianCalendar)value;
					break;
				
			case FooImage:
					FooImage = (String)value;
					break;
					
			case FooFile:
					FooFile = (String)value;
					break;
					
			case FooLocation:
					FooLocation = (LiLocation)value;
					break;
					
			case FooNumber:
					FooNumber = (Integer)value;
					break;
					
			case FooAge:
					FooAge = (Integer)value;
					break;
					
			case FooOwner:
					FooOwner =  new User((String)value);
					break;
					
			default:
				break;
		}
		return true;
	}
}

package com.applicasa.Foo;
import java.util.ArrayList;
import java.util.List;
import java.util.GregorianCalendar;

import applicasa.LiCore.communication.LiUtility;

import applicasa.LiCore.LiLocation;

import applicasa.LiCore.communication.LiCallback.LiCallbackAction;
import com.applicasa.ApplicasaManager.LiCallbackQuery.LiFooGetByIDCallback;
import com.applicasa.ApplicasaManager.LiCallbackQuery.LiFooGetArrayCallback;
import com.applicasa.ApplicasaManager.LiManager.LiObject;

import android.database.Cursor;
import applicasa.LiCore.sqlDB.database.LiDbObject;
import applicasa.LiCore.communication.LiRequestConst.QueryKind;
import applicasa.LiCore.communication.LiUtility;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiErrorHandler.ApplicasaResponse;
import applicasa.LiCore.communication.LiRequestConst.RequestAction;
import applicasa.LiCore.communication.LiObjRequest;
import applicasa.LiCore.communication.LiRequestConst.RequestCallback;
import applicasa.LiCore.communication.LiRequestConst.LiObjResponse;
import applicasa.LiCore.communication.LiFilters;
import applicasa.LiCore.communication.LiQuery;
import applicasa.LiCore.communication.LiFilters.Operation;
import applicasa.LiCore.sqlDB.database.LiCoreDBmanager;
import applicasa.LiJson.LiJSONException;
import applicasa.LiJson.LiJSONObject;


import com.applicasa.User.User;

public class Foo extends FooData {
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////													 /////////////////////////////////////////
//////////////////////////////////							SAVE                     /////////////////////////////////////////
//////////////////////////////////													 /////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Saves the Object to Applicasa's Servers
	 * The method saves all of the item's value to the server
	 * If the Object has an ID the operation will update existing object in applicasa's server; otherwise an add operation will be called
	 * 
	 * In Order to Update a specific field Use the method saveFields
	 * @param actionCallback
	 * @return
	 * @throws LiErrorHandler
	 */
	public void save(LiCallbackAction liCallbackAction)  
	{
		LiObjRequest request = new LiObjRequest();
		
		// If Id is of hex representation and not 0, then the itemId is Mongo id
		if (FooID!= "0" && (LiUtility.isHex(FooID)|| FooID.startsWith("temp_") ))
		{
			request.setAction(RequestAction.UPDATE_ACTION);
			request.setRecordID(FooID);
			request.setIncrementedFields(incrementedFields);			
		}
		else
		{
			request.setAction(RequestAction.ADD_ACTION);
			request.setAddedObject(this);
		}
		
		request.setClassName(kClassName);
		request.setCallback(callbackHandler);
		request.setEnableOffline(EnableOffline);
		
		setActionCallback(liCallbackAction,request.requestID);
		// add the Values of the Object Item to the Request
		try{
			request.setParametersArrayValue(dictionaryRepresentation(false));
		}catch (LiErrorHandler e) {
			if (liCallbackAction != null)
				liCallbackAction.onFailure(e);
			else 
				return;
		}
		
		request.startASync();

	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////													 /////////////////////////////////////////
//////////////////////////////////						  DELETE                     /////////////////////////////////////////
//////////////////////////////////													 /////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	public void delete(LiCallbackAction liCallbackAction) 
	{
		// Verifies Item isn't null
		if (FooID == null || FooID == "") 
			if (liCallbackAction != null) liCallbackAction.onFailure(new LiErrorHandler(ApplicasaResponse.NULL_ITEM, "Missing Item ID"));
			else return;
		
		LiObjRequest request = new LiObjRequest();
		request.setAction(RequestAction.DELETE_ACTION);
		request.setClassName(kClassName);
		request.setCallback(callbackHandler);
		request.setRecordID(FooID);
		
		setActionCallback(liCallbackAction,request.requestID);
		request.setEnableOffline(EnableOffline);
		
		request.startASync();

	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////							  						 /////////////////////////////////////////
//////////////////////////////////						   GET                       /////////////////////////////////////////
//////////////////////////////////													 /////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	/**
	    * A- Synchronized function which returns an object from server by ID
	    * @param ID
	    * @return
	    * @throws LiErrorHandler
	    */
	    public static void getByID(String Id, QueryKind queryKind, LiFooGetByIDCallback liFooGetByIDCallback) 
	    {
	        if (Id != null)
	        {
	        	// Creates new Query
	        	LiQuery query= new LiQuery();
	        	
	        	// Create a where statement expression of ObjectId = 'id';  
		        LiFilters filter = new LiFilters(LiFieldFoo.FooID, Operation.EQUAL, Id);		        
		        query.setFilter(filter);
	        	
		    	LiObjRequest request = new LiObjRequest();
		        request.setClassName(kClassName);
		
		        request.setAction(RequestAction.GET_ACTION);
		        request.setGet(queryKind);
		       
		        request.setQueryToRequest(query);
		        request.setCallback(callbackHandler);
		        setGetCallback(liFooGetByIDCallback,request.requestID);
		       
		        request.startASync();
	        }
	    }

	    /**
	    * A- Synchronized Method to returns an object from server by filters
	    * @param ID
	    * @return
	    * @throws LiErrorHandler
	    */
	    public static void getArrayWithQuery(LiQuery query ,QueryKind queryKind, LiFooGetArrayCallback liFooGetArrayCallback) 
	    {
	        LiObjRequest request = new LiObjRequest();
	        request.setClassName(kClassName);
	        request.setAction(RequestAction.GET_ARRAY);
	        request.setGet(queryKind);
	        request.setQueryToRequest(query);
	        request.setCallback(callbackHandler);
	        setGetCallback(liFooGetArrayCallback,request.requestID);
	        request.startASync();
	    }
	   
		public static void getLocalyWithRawSQLQuery(String whereClause, String[] args, LiFooGetArrayCallback liFooGetArrayCallback)
	    {
			LiObjRequest request = new LiObjRequest();
	        request.setCallback(callbackHandler);
	        setGetCallback(liFooGetArrayCallback,request.requestID);
	        request.GetWithRawQuery(kClassName, whereClause, args);
	    }
	
		 /** Synchronized Method to returns an object from server by filters
		 * @param ID
		 * @return the list of items or null in case on an error
		 * @throws LiErrorHandler
		 */
		 public static List<Foo> getArrayWithQuery(LiQuery query ,QueryKind queryKind) throws LiErrorHandler 
		 {
			 LiObjRequest request = new LiObjRequest();
			 request.setClassName(kClassName);
			 request.setAction(RequestAction.GET_ARRAY);
			 request.setGet(queryKind);
			 request.setQueryToRequest(query);
			 LiObjResponse response = request.startSync();
			 
			 if (response.LiRespType.equals(ApplicasaResponse.RESPONSE_SUCCESSFUL))
			 {
			  Cursor cursor = request.getCursor();
			  return buildFooFromCursor(request.requestID, cursor);
			 }
			 
			 return null;
		 }

	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////													 /////////////////////////////////////////
//////////////////////////////////					    Upload File                  /////////////////////////////////////////
//////////////////////////////////													 /////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	/**
	* Method to Upload file 
	* @param fooField - The field to be updated with the file name in Applicasa server 
	* @param filePath - the path to the uploaded file
	* @param fooActionCallBack - call back to indicate when the upload was completed
	* @return
	* @throws LiErrorHandler
	*/
	public void updloadFile(LiFieldFoo liFieldFoo, String filePath, LiCallbackAction liCallbackAction)
	{
		LiObjRequest request = new LiObjRequest();
		
		request.setAction(RequestAction.UPLOAD_FILE);
		request.setClassName(Foo.kClassName);
		request.setRecordID(FooID);
		
		request.setFileFieldName(liFieldFoo);
		request.setFilePath(filePath);
		request.setAddedObject(this);
		request.setCallback(callbackHandler);
		setActionCallback(liCallbackAction,request.requestID);
		
		
		request.startASync();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////													 /////////////////////////////////////////
//////////////////////////////////						Callback                     /////////////////////////////////////////
//////////////////////////////////													 /////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	
static RequestCallback callbackHandler = new RequestCallback() {
		
	public void onCompleteGet(String requestID, Cursor cursor) {
		// TODO Auto-generated method stub
		List<Foo> returnList = new ArrayList<Foo>();

		returnList = buildFooFromCursor(requestID ,cursor);

		Object callback = fooCallbacks.get(requestID);
		if (callback != null && callback instanceof LiFooGetArrayCallback)
		{
			fooCallbacks.remove(requestID);
			((LiFooGetArrayCallback)callback).onGetFooComplete(returnList);
		}
		if (callback != null && callback instanceof LiFooGetByIDCallback )
		{
			fooCallbacks.remove(requestID);
			((LiFooGetByIDCallback)callback).onGetFooComplete(returnList.get(0));
		}
		
	}
	
	public void LiException(String requestID,LiErrorHandler ex) {
		// TODO Auto-generated method stub
		Object callback = fooCallbacks.get(requestID);
		if (callback != null && callback instanceof LiFooGetArrayCallback )
		{
			fooCallbacks.remove(requestID);
			((LiFooGetArrayCallback)callback).onGetFooFailure(ex);
		}		
		else if (callback != null && callback instanceof LiFooGetByIDCallback )
		{
			fooCallbacks.remove(requestID);
			((LiFooGetByIDCallback)callback).onGetFooFailure(ex);
		}
		else if (callback != null && callback instanceof LiCallbackAction )
		{
			fooCallbacks.remove(requestID);
			((LiCallbackAction)callback).onFailure(ex);
		}

	}
		public void onCompleteAction(String requestID, LiObjResponse response) {
			// TODO Auto-generated method stub
			Object callback = fooCallbacks.get(requestID);
			if (callback != null )
			{
				fooCallbacks.remove(requestID);
				if (response.action == RequestAction.ADD_ACTION)
					((Foo)response.addedObject).FooID = response.newObjID;
					
				if (response.action == RequestAction.UPLOAD_FILE)
				{
					((Foo)response.addedObject).setFooFieldbySortType((LiFieldFoo)response.field, response.newObjID);
					if (response.actionResponseList.get(0).objId != null && response.actionResponseList.get(0).requestID == requestID )
						((Foo)response.addedObject).FooID = response.actionResponseList.get(0).objId;
				}
								
				((LiCallbackAction)callback).onComplete(response.LiRespType, response.LiRespMsg, response.action,response.newObjID, LiObject.getLiObject(response.className));
			}
		}
	};
	
	/**
	 * 
	 * @param requestID
	 * @param cursor
	 * @deprecated use buildFooFromCursor
	 * @return
	 */
	@Deprecated
	public static List<Foo> BuildFooFromCursor(String requestID, Cursor cursor)
	{
		return buildFooFromCursor(requestID, cursor);
	}

	/**
	 * 
	 * @param requestID
	 * @param cursor
	 * @return
	 */
	public static List<Foo> buildFooFromCursor(String requestID ,Cursor cursor)
	{
		List<Foo> returnList = new ArrayList<Foo>();
		if (cursor == null || cursor.getCount() == 0 ) {return returnList; }// nothing received
		else
		{
			cursor.moveToFirst();
			ArrayList<String> idsList = LiObjRequest.IdsMap.get(requestID);
            ArrayList<String> idsToDelete = new ArrayList<String>();
            
            String id;
            while (!cursor.isAfterLast())
            {
                id = cursor.getString(0);
                if (idsList == null || idsList.contains(id))
                {
                    returnList.add(new Foo(cursor));                    
                }
                else
                {
                    idsToDelete.add(id);
                }
				cursor.moveToNext();
            }
            if (!idsToDelete.isEmpty())
			{
				LiObjRequest.DeleteUnlistedIds(kClassName,requestID, idsToDelete);
			}
			idsList = null;
			idsToDelete = null;			
		}
		
		cursor.close();
	
	
		return returnList;
		
	}
	
	
	private static void setGetCallback(LiFooGetArrayCallback getCallback, String reqID) {
		// TODO Auto-generated method stub
		fooCallbacks.put(reqID, getCallback);
	}
	
	private static void setGetCallback(LiFooGetByIDCallback getCallback, String reqID) {
		// TODO Auto-generated method stub
		fooCallbacks.put(reqID, getCallback);
	}

	private static void setActionCallback(LiCallbackAction actionCallback, String reqID) {
		// TODO Auto-generated method stub
		fooCallbacks.put(reqID, actionCallback);
	}
	
	
	 /** Synchronized Method that updates local storage according to request
	 * @return the item Count, if count of 1500 is the max number of values returned by the server.
	 * @throws LiErrorHandler
	 */
	 public static int updateLocalStorage(LiQuery query ,QueryKind queryKind) throws LiErrorHandler 
	 {
		 int recordsCount = 0;
		 LiObjRequest request = new LiObjRequest();
		 request.setClassName(kClassName);
		 request.setAction(RequestAction.GET_ARRAY);
		 request.setGet(queryKind);
		 request.setQueryToRequest(query);
		 LiObjResponse response = request.startSync();
		 
		 if (response.LiRespType.equals(ApplicasaResponse.RESPONSE_SUCCESSFUL))
		 {
			 Cursor cursor = request.getCursor();
			 if(cursor == null)
				return 0;
		
			 if (queryKind.compareTo(QueryKind.PAGER) != 0)
			 {
				 deleteItems(request.requestID,cursor);
			 }
			 
			 recordsCount = cursor.getCount();
			 cursor.close();
			 cursor = null;
		 }
		 
		 return recordsCount;
	 }
	 
	 public static void deleteItems(final String requestID ,final Cursor cursor)
	 {
		 new Thread(new Runnable() {
			
			public void run() {
					// TODO Auto-generated method stub
					if (cursor == null || cursor.getCount() == 0 ) {}// nothing received
					else
					{
						cursor.moveToFirst();
						ArrayList<String> idsList = LiObjRequest.IdsMap.get(requestID);
						ArrayList<String> idsToDelete = new ArrayList<String>();
						
						String id;
						while (!cursor.isAfterLast())
						{
							id = cursor.getString(0);
							if (idsList != null && !idsList.contains(id))
							{
								idsToDelete.add(id);
							}
							cursor.moveToNext();
						}
						if (!idsToDelete.isEmpty())
						{
							LiObjRequest.DeleteUnlistedIds(kClassName,requestID, idsToDelete);
						}
						idsList = null;
						idsToDelete = null;			
					}
				}
			}).run();
		}
 /** End of Basic SDK **/ 

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////                                                   ////////////////////////////////////////
///////////////////////////////////                    Init Method                    ////////////////////////////////////////
///////////////////////////////////                    Don't ALTER                    ////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Foo()
	{
		this.FooID = "0";
		(this.FooLastUpdate = new GregorianCalendar()).setTimeInMillis(0);
		this.FooName = "";
		this.FooDescription = "";
		this.FooBoolean = true;
		(this.FooDate = new GregorianCalendar()).setTimeInMillis(0);
		this.FooImage = "";
		this.FooFile = "";
		this.FooLocation = new LiLocation(0, 0);
		this.FooNumber = 0;
		this.FooAge = 0;
		this.FooOwner = new User("0");
	}

	public Foo(Cursor cursor) 
	{
		initWithCursor(cursor);
	}
	
	public Foo(Cursor cursor,String header,int level) 
	{
		initWithCursor(cursor,header,level);
	}
	
	public Foo(String FooID)
	{
		this.FooID = FooID;
	}

	public Foo(Foo item)
	{
		initWithObject(item);
	}

	/**
	* Init Object with Cursor
	* @param corsor
	* @return
	*/
	public Foo initWithCursor(Cursor cursor)
	{
		return initWithCursor(cursor,"",0);
	}
	
	/**
	* Init Object with Cursor
	* @param corsor
	* @return
	*/
	public Foo initWithCursor(Cursor cursor,String header,int level)
	{
		int columnIndex;
	
		columnIndex = cursor.getColumnIndex(header + LiFieldFoo.FooID.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.FooID = cursor.getString(columnIndex);
		
		columnIndex = cursor.getColumnIndex(header + LiFieldFoo.FooLastUpdate.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
		{
			long dateStr = cursor.getLong(columnIndex);
			GregorianCalendar gc= new GregorianCalendar();
			gc.setTimeInMillis(dateStr);
			this.FooLastUpdate = gc;
		}
		
		columnIndex = cursor.getColumnIndex(header + LiFieldFoo.FooName.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.FooName = cursor.getString(columnIndex);
		
		columnIndex = cursor.getColumnIndex(header + LiFieldFoo.FooDescription.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.FooDescription = cursor.getString(columnIndex);
		
		columnIndex = cursor.getColumnIndex(header + LiFieldFoo.FooBoolean.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
		{
			this.FooBoolean = cursor.getInt(columnIndex)==1?true:false;
		}
		
		columnIndex = cursor.getColumnIndex(header + LiFieldFoo.FooDate.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
		{
			long dateStr = cursor.getLong(columnIndex);
			GregorianCalendar gc= new GregorianCalendar();
			gc.setTimeInMillis(dateStr);
			this.FooDate = gc;
		}
		
		columnIndex = cursor.getColumnIndex(header + LiFieldFoo.FooImage.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.FooImage = cursor.getString(columnIndex);
		
		columnIndex = cursor.getColumnIndex(header + LiFieldFoo.FooFile.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.FooFile = cursor.getString(columnIndex);
		
		float Longitude = 0, Latitude  = 0;
		columnIndex = cursor.getColumnIndex(header + LiFieldFoo.FooLocation.toString()+"Long");
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			 Longitude= cursor.getFloat(columnIndex);
		columnIndex = cursor.getColumnIndex(header + LiFieldFoo.FooLocation.toString()+"Lat");
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			 Latitude= cursor.getFloat(columnIndex);
		this.FooLocation = new LiLocation(Longitude, Latitude);
		
		columnIndex = cursor.getColumnIndex(header + LiFieldFoo.FooNumber.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.FooNumber = cursor.getInt(columnIndex);
		
		columnIndex = cursor.getColumnIndex(header + LiFieldFoo.FooAge.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.FooAge = cursor.getInt(columnIndex);
		
		
		columnIndex = cursor.getColumnIndex(header + LiObjRequest.DistanceFromCurrent);
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.DistanceFromCurrent = LiUtility.convertPartialDistanceToKm(cursor.getDouble(columnIndex));
		
			columnIndex = cursor.getColumnIndex(header + LiFieldFoo.FooOwner.toString());
			if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST && level==0)
			{ 
				this.FooOwner = new User(cursor,LiFieldFoo.FooOwner.toString(),level+1);
			}
			else if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST){
				this.FooOwner = new User(cursor.getString(columnIndex));
			}
	
		return this;
	}
	
	/**
	* Initialize values with Object
	* @param item
	* @return
	*/
	public String initWithObject(Foo item)
	{
		this.FooID			= item.FooID;
		this.FooLastUpdate			= item.FooLastUpdate;
		this.FooName			= item.FooName;
		this.FooDescription			= item.FooDescription;
		this.FooBoolean			= item.FooBoolean;
		this.FooDate			= item.FooDate;
		this.FooImage			= item.FooImage;
		this.FooFile			= item.FooFile;
		this.FooLocation			= item.FooLocation;
		this.FooNumber			= item.FooNumber;
		this.FooAge			= item.FooAge;
		this.FooOwner			= item.FooOwner;
	
		return FooID;
	}
	
	/**
	* Function to add the given object fields to the request parameters list
	* @param item
	* @param request
	* @return
	*/
/**
* Initialize Dictionary with Foo item instance
* @param dictionary
* @return
*/
public LiJSONObject dictionaryRepresentation(boolean withFK) throws LiErrorHandler {

	try{
		LiJSONObject dictionary = new LiJSONObject();
		dictionary.put(LiFieldFoo.FooID, FooID);
	
		dictionary.put(LiFieldFoo.FooLastUpdate, LiUtility.convertDateToDictionaryRepresenataion(FooLastUpdate));
	
		dictionary.put(LiFieldFoo.FooName, FooName);
	
		dictionary.put(LiFieldFoo.FooDescription, FooDescription);
	
		dictionary.put(LiFieldFoo.FooBoolean, FooBoolean);
	
		dictionary.put(LiFieldFoo.FooDate, LiUtility.convertDateToDictionaryRepresenataion(FooDate));
	
		dictionary.put(LiFieldFoo.FooImage, FooImage);
	
		dictionary.put(LiFieldFoo.FooFile, FooFile);
	
		dictionary.put(LiFieldFoo.FooLocation, FooLocation.getJsonArrayRepresentation());
	
		dictionary.put(LiFieldFoo.FooNumber, FooNumber);
	
		dictionary.put(LiFieldFoo.FooAge, FooAge);
	
		if (withFK)
			dictionary.put(LiFieldFoo.FooOwner, FooOwner.dictionaryRepresentation(true));
		else
			dictionary.put(LiFieldFoo.FooOwner, FooOwner.UserID);
		return dictionary;
		}
		catch (LiJSONException ex)
		{
			throw new LiErrorHandler(ApplicasaResponse.INPUT_VALUES_ERROR, ex.getMessage());
		}
	}
	
	public static LiDbObject createDB() throws LiJSONException{
		LiDbObject dbObject = new LiDbObject();
		dbObject.put("LiClassName", kClassName);
		dbObject.put(LiFieldFoo.FooID, LiCoreDBmanager.PRIMARY_KEY,-1);
		dbObject.put(LiFieldFoo.FooLastUpdate, LiCoreDBmanager.DATE,0);
		dbObject.put(LiFieldFoo.FooName, LiCoreDBmanager.TEXT,"");
		dbObject.put(LiFieldFoo.FooDescription, LiCoreDBmanager.TEXT,"");
		dbObject.put(LiFieldFoo.FooBoolean, LiCoreDBmanager.BOOL,true);
		dbObject.put(LiFieldFoo.FooDate, LiCoreDBmanager.DATE,0);
		dbObject.put(LiFieldFoo.FooImage, LiCoreDBmanager.TEXT,"");
		dbObject.put(LiFieldFoo.FooFile, LiCoreDBmanager.TEXT,"");
		dbObject.put(LiFieldFoo.FooOwner, LiCoreDBmanager.FOREIGN_KEY+"_User","0");
		dbObject.put(LiFieldFoo.FooLocation, LiCoreDBmanager.LOCATION,"[0,0]");
		dbObject.put(LiFieldFoo.FooNumber, LiCoreDBmanager.INTEGER,0);
		dbObject.put(LiFieldFoo.FooAge, LiCoreDBmanager.INTEGER,0);
	return dbObject;
}
	public void increment(LiFieldFoo liFieldFoo) throws LiErrorHandler
	{
		increment(liFieldFoo, 1);
	}
		 
	public void increment(LiFieldFoo liFieldFoo, Object value) throws LiErrorHandler
	{
		String key = liFieldFoo.toString();
		float oldValueFloat = 0;
		int oldValueInt = 0;
		Object incrementedField = getFooFieldbySortType(liFieldFoo);
		try {
			if (incrementedField instanceof Integer)
			{
				int incInt;
				if (value instanceof Integer)
					incInt = (Integer)value;
				else
					 throw new LiErrorHandler(ApplicasaResponse.INPUT_VALUES_ERROR, "Incremented Value isn't of the same type as the requested field");
				int total = (Integer)incrementedField+incInt;
				setFooFieldbySortType(liFieldFoo, total);
				if (incrementedFields.has(liFieldFoo.toString()))
					oldValueInt = (Integer)incrementedFields.remove(key);
	
				incrementedFields.put(key, (oldValueInt+incInt));
			}
			else if (incrementedField instanceof Float)
			{
				float incFloat;
				 if (value instanceof Float)
					incFloat = (Float)value;
				 else
					incFloat = Float.valueOf((Integer)value);
				float total = (Float)incrementedField+incFloat;
				setFooFieldbySortType(liFieldFoo, total);
					if (incrementedFields.has(liFieldFoo.toString()))
						oldValueFloat = (Float)incrementedFields.remove(key);
				incrementedFields.put(key, (oldValueFloat+incFloat));
			}
			else
				throw new LiErrorHandler(ApplicasaResponse.INPUT_VALUES_ERROR,"Can't increase, Specified field is not Int or Float");
		} catch (LiJSONException e) {
			// TODO Auto-generated catch block
			throw new LiErrorHandler(ApplicasaResponse.INPUT_VALUES_ERROR,"Can't increase, Recheck inserted Values");
		}
	}
		 
	private void resetIncrementedFields() {
		// TODO Auto-generated method stub
		incrementedFields = new LiJSONObject();
	}
	

}

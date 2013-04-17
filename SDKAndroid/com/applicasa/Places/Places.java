package com.applicasa.Places;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import android.database.Cursor;
import applicasa.LiCore.LiErrorHandler;
import applicasa.LiCore.LiErrorHandler.ApplicasaResponse;
import applicasa.LiCore.LiLocation;
import applicasa.LiCore.communication.LiCallback.LiCallbackAction;
import applicasa.LiCore.communication.LiFilters;
import applicasa.LiCore.communication.LiFilters.Operation;
import applicasa.LiCore.communication.LiObjRequest;
import applicasa.LiCore.communication.LiQuery;
import applicasa.LiCore.communication.LiRequestConst.LiObjResponse;
import applicasa.LiCore.communication.LiRequestConst.QueryKind;
import applicasa.LiCore.communication.LiRequestConst.RequestAction;
import applicasa.LiCore.communication.LiRequestConst.RequestCallback;
import applicasa.LiCore.communication.LiUtility;
import applicasa.LiCore.sqlDB.database.LiCoreDBmanager;
import applicasa.LiCore.sqlDB.database.LiDbObject;
import applicasa.LiJson.LiJSONException;
import applicasa.LiJson.LiJSONObject;

import com.applicasa.ApplicasaManager.LiCallbackQuery.LiPlacesGetArrayCallback;
import com.applicasa.ApplicasaManager.LiCallbackQuery.LiPlacesGetByIDCallback;
import com.applicasa.ApplicasaManager.LiManager.LiObject;



public class Places extends PlacesData {
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
		if (PlacesID!= "0" && (LiUtility.isHex(PlacesID)|| PlacesID.startsWith("temp_") ))
		{
			request.setAction(RequestAction.UPDATE_ACTION);
			request.setRecordID(PlacesID);
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
		if (PlacesID == null || PlacesID == "") 
			if (liCallbackAction != null) liCallbackAction.onFailure(new LiErrorHandler(ApplicasaResponse.NULL_ITEM, "Missing Item ID"));
			else return;
		
		LiObjRequest request = new LiObjRequest();
		request.setAction(RequestAction.DELETE_ACTION);
		request.setClassName(kClassName);
		request.setCallback(callbackHandler);
		request.setRecordID(PlacesID);
		
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
	    public static void getByID(String Id, QueryKind queryKind, LiPlacesGetByIDCallback liPlacesGetByIDCallback) 
	    {
	        if (Id != null)
	        {
	        	// Creates new Query
	        	LiQuery query= new LiQuery();
	        	
	        	// Create a where statement expression of ObjectId = 'id';  
		        LiFilters filter = new LiFilters(LiFieldPlaces.PlacesID, Operation.EQUAL, Id);		        
		        query.setFilter(filter);
	        	
		    	LiObjRequest request = new LiObjRequest();
		        request.setClassName(kClassName);
		
		        request.setAction(RequestAction.GET_ACTION);
		        request.setGet(queryKind);
		       
		        request.setQueryToRequest(query);
		        request.setCallback(callbackHandler);
		        setGetCallback(liPlacesGetByIDCallback,request.requestID);
		       
		        request.startASync();
	        }
	    }

	    /**
	    * A- Synchronized Method to returns an object from server by filters
	    * @param ID
	    * @return
	    * @throws LiErrorHandler
	    */
	    public static void getArrayWithQuery(LiQuery query ,QueryKind queryKind, LiPlacesGetArrayCallback liPlacesGetArrayCallback) 
	    {
	        LiObjRequest request = new LiObjRequest();
	        request.setClassName(kClassName);
	        request.setAction(RequestAction.GET_ARRAY);
	        request.setGet(queryKind);
	        request.setQueryToRequest(query);
	        request.setCallback(callbackHandler);
	        setGetCallback(liPlacesGetArrayCallback,request.requestID);
	        request.startASync();
	    }
	   
		public static void getLocalyWithRawSQLQuery(String whereClause, String[] args, LiPlacesGetArrayCallback liPlacesGetArrayCallback)
	    {
			LiObjRequest request = new LiObjRequest();
	        request.setCallback(callbackHandler);
	        setGetCallback(liPlacesGetArrayCallback,request.requestID);
	        request.GetWithRawQuery(kClassName, whereClause, args);
	    }
	
		 /** Synchronized Method to returns an object from server by filters
		 * @param ID
		 * @return the list of items or null in case on an error
		 * @throws LiErrorHandler
		 */
		 public static List<Places> getArrayWithQuery(LiQuery query ,QueryKind queryKind) throws LiErrorHandler 
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
			  return buildPlacesFromCursor(request.requestID, cursor);
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
	* @param placesField - The field to be updated with the file name in Applicasa server 
	* @param filePath - the path to the uploaded file
	* @param placesActionCallBack - call back to indicate when the upload was completed
	* @return
	* @throws LiErrorHandler
	*/
	public void updloadFile(LiFieldPlaces liFieldPlaces, String filePath, LiCallbackAction liCallbackAction)
	{
		LiObjRequest request = new LiObjRequest();
		
		request.setAction(RequestAction.UPLOAD_FILE);
		request.setClassName(Places.kClassName);
		request.setRecordID(PlacesID);
		
		request.setFileFieldName(liFieldPlaces);
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
		List<Places> returnList = new ArrayList<Places>();

		returnList = buildPlacesFromCursor(requestID ,cursor);

		Object callback = placesCallbacks.get(requestID);
		if (callback != null && callback instanceof LiPlacesGetArrayCallback)
		{
			placesCallbacks.remove(requestID);
			((LiPlacesGetArrayCallback)callback).onGetPlacesComplete(returnList);
		}
		if (callback != null && callback instanceof LiPlacesGetByIDCallback )
		{
			placesCallbacks.remove(requestID);
			((LiPlacesGetByIDCallback)callback).onGetPlacesComplete(returnList.get(0));
		}
		
	}
	
	public void LiException(String requestID,LiErrorHandler ex) {
		// TODO Auto-generated method stub
		Object callback = placesCallbacks.get(requestID);
		if (callback != null && callback instanceof LiPlacesGetArrayCallback )
		{
			placesCallbacks.remove(requestID);
			((LiPlacesGetArrayCallback)callback).onGetPlacesFailure(ex);
		}		
		else if (callback != null && callback instanceof LiPlacesGetByIDCallback )
		{
			placesCallbacks.remove(requestID);
			((LiPlacesGetByIDCallback)callback).onGetPlacesFailure(ex);
		}
		else if (callback != null && callback instanceof LiCallbackAction )
		{
			placesCallbacks.remove(requestID);
			((LiCallbackAction)callback).onFailure(ex);
		}

	}
		public void onCompleteAction(String requestID, LiObjResponse response) {
			// TODO Auto-generated method stub
			Object callback = placesCallbacks.get(requestID);
			if (callback != null )
			{
				placesCallbacks.remove(requestID);
				if (response.action == RequestAction.ADD_ACTION)
					((Places)response.addedObject).PlacesID = response.newObjID;
				if (response.action == RequestAction.UPLOAD_FILE)
					((Places)response.addedObject).setPlacesFieldbySortType((LiFieldPlaces)response.field, response.newObjID);
				
				((LiCallbackAction)callback).onComplete(response.LiRespType, response.LiRespMsg, response.action,response.newObjID, LiObject.getLiObject(response.className));
			}
		}
	};
	
	/**
	 * 
	 * @param requestID
	 * @param cursor
	 * @deprecated use buildPlacesFromCursor
	 * @return
	 */
	@Deprecated
	public static List<Places> BuildPlacesFromCursor(String requestID, Cursor cursor)
	{
		return buildPlacesFromCursor(requestID, cursor);
	}

	/**
	 * 
	 * @param requestID
	 * @param cursor
	 * @return
	 */
	public static List<Places> buildPlacesFromCursor(String requestID ,Cursor cursor)
	{
		List<Places> returnList = new ArrayList<Places>();
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
                if (idsList == null || idsList.contains(id))
                {
                    returnList.add(new Places(cursor));                    
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
	
	
	private static void setGetCallback(LiPlacesGetArrayCallback getCallback, String reqID) {
		// TODO Auto-generated method stub
		placesCallbacks.put(reqID, getCallback);
	}
	
	private static void setGetCallback(LiPlacesGetByIDCallback getCallback, String reqID) {
		// TODO Auto-generated method stub
		placesCallbacks.put(reqID, getCallback);
	}

	private static void setActionCallback(LiCallbackAction actionCallback, String reqID) {
		// TODO Auto-generated method stub
		placesCallbacks.put(reqID, actionCallback);
	} /** End of Basic SDK **/ 

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////                                                   ////////////////////////////////////////
///////////////////////////////////                    Init Method                    ////////////////////////////////////////
///////////////////////////////////                    Don't ALTER                    ////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Places()
	{
		this.PlacesID = "0";
		(this.PlacesLastUpdate = new GregorianCalendar()).setTimeInMillis(0);
		this.PlacesLoc = new LiLocation(0, 0);
		this.PlacesName = "";
	}

	public Places(Cursor cursor) 
	{
		initWithCursor(cursor);
	}
	
	public Places(Cursor cursor,String header,int level) 
	{
		initWithCursor(cursor,header,level);
	}
	
	public Places(String PlacesID)
	{
		this.PlacesID = PlacesID;
	}

	/**
	* Init Object with Cursor
	* @param corsor
	* @return
	*/
	public Places initWithCursor(Cursor cursor)
	{
		return initWithCursor(cursor,"",0);
	}
	
	/**
	* Init Object with Cursor
	* @param corsor
	* @return
	*/
	public Places initWithCursor(Cursor cursor,String header,int level)
	{
		int columnIndex;
	
		columnIndex = cursor.getColumnIndex(header + LiFieldPlaces.PlacesID.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.PlacesID = cursor.getString(columnIndex);
		
		columnIndex = cursor.getColumnIndex(header + LiFieldPlaces.PlacesLastUpdate.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
		{
			long dateStr = cursor.getLong(columnIndex);
			GregorianCalendar gc= new GregorianCalendar();
			gc.setTimeInMillis(dateStr);
			this.PlacesLastUpdate = gc;
		}
		
		float Longitude = 0, Latitude  = 0;
		columnIndex = cursor.getColumnIndex(header + LiFieldPlaces.PlacesLoc.toString()+"Long");
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			 Longitude= cursor.getFloat(columnIndex);
		columnIndex = cursor.getColumnIndex(header + LiFieldPlaces.PlacesLoc.toString()+"Lat");
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			 Latitude= cursor.getFloat(columnIndex);
		this.PlacesLoc = new LiLocation(Longitude, Latitude);
		
		columnIndex = cursor.getColumnIndex(header + LiFieldPlaces.PlacesName.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.PlacesName = cursor.getString(columnIndex);
		
		
		columnIndex = cursor.getColumnIndex(header + LiObjRequest.DistanceFromCurrent);
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.DistanceFromCurrent = LiUtility.convertPartialDistanceToKm(cursor.getDouble(columnIndex));
		
	
		return this;
	}
	
	/**
	* Initialize values with Object
	* @param item
	* @return
	*/
	public String initWithObject(Places item)
	{
		this.PlacesID			= item.PlacesID;
		this.PlacesLastUpdate			= item.PlacesLastUpdate;
		this.PlacesLoc			= item.PlacesLoc;
		this.PlacesName			= item.PlacesName;
	
		return PlacesID;
	}
	
	/**
	* Function to add the given object fields to the request parameters list
	* @param item
	* @param request
	* @return
	*/
/**
* Initialize Dictionary with Places item instance
* @param dictionary
* @return
*/
public LiJSONObject dictionaryRepresentation(boolean withFK) throws LiErrorHandler {

	try{
		LiJSONObject dictionary = new LiJSONObject();
		dictionary.put(LiFieldPlaces.PlacesID, PlacesID);
	
		dictionary.put(LiFieldPlaces.PlacesLastUpdate, LiUtility.convertDateToDictionaryRepresenataion(PlacesLastUpdate));
	
		dictionary.put(LiFieldPlaces.PlacesLoc, PlacesLoc.getJsonArrayRepresentation());
	
		dictionary.put(LiFieldPlaces.PlacesName, PlacesName);
	
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
		dbObject.put(LiFieldPlaces.PlacesID, LiCoreDBmanager.PRIMARY_KEY,-1);
		dbObject.put(LiFieldPlaces.PlacesLastUpdate, LiCoreDBmanager.DATE,0);
		dbObject.put(LiFieldPlaces.PlacesLoc, LiCoreDBmanager.LOCATION,"[0,0]");
		dbObject.put(LiFieldPlaces.PlacesName, LiCoreDBmanager.TEXT,"");
	return dbObject;
}
	public void increment(LiFieldPlaces liFieldPlaces) throws LiErrorHandler
	{
		increment(liFieldPlaces, 1);
	}
		 
	public void increment(LiFieldPlaces liFieldPlaces, Object value) throws LiErrorHandler
	{
		String key = liFieldPlaces.toString();
		float oldValueFloat = 0;
		int oldValueInt = 0;
		Object incrementedField = getPlacesFieldbySortType(liFieldPlaces);
		try {
			if (incrementedField instanceof Integer)
			{
				int incInt;
				if (value instanceof Integer)
					incInt = (Integer)value;
				else
					 throw new LiErrorHandler(ApplicasaResponse.INPUT_VALUES_ERROR, "Incremented Value isn't of the same type as the requested field");
				int total = (Integer)incrementedField+incInt;
				setPlacesFieldbySortType(liFieldPlaces, total);
				if (incrementedFields.has(liFieldPlaces.toString()))
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
				setPlacesFieldbySortType(liFieldPlaces, total);
					if (incrementedFields.has(liFieldPlaces.toString()))
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

package com.applicasa.Dynamic;
import java.util.ArrayList;
import java.util.List;
import java.util.GregorianCalendar;

import applicasa.LiCore.communication.LiUtility;


import applicasa.LiCore.communication.LiCallback.LiCallbackAction;
import com.applicasa.ApplicasaManager.LiCallbackQuery.LiDynamicGetByIDCallback;
import com.applicasa.ApplicasaManager.LiCallbackQuery.LiDynamicGetArrayCallback;
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



public class Dynamic extends DynamicData {
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
		if (DynamicID!= "0" && (LiUtility.isHex(DynamicID)|| DynamicID.startsWith("temp_") ))
		{
			request.setAction(RequestAction.UPDATE_ACTION);
			request.setRecordID(DynamicID);
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
		if (DynamicID == null || DynamicID == "") 
			if (liCallbackAction != null) liCallbackAction.onFailure(new LiErrorHandler(ApplicasaResponse.NULL_ITEM, "Missing Item ID"));
			else return;
		
		LiObjRequest request = new LiObjRequest();
		request.setAction(RequestAction.DELETE_ACTION);
		request.setClassName(kClassName);
		request.setCallback(callbackHandler);
		request.setRecordID(DynamicID);
		
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
	    public static void getByID(String Id, QueryKind queryKind, LiDynamicGetByIDCallback liDynamicGetByIDCallback) 
	    {
	        if (Id != null)
	        {
	        	// Creates new Query
	        	LiQuery query= new LiQuery();
	        	
	        	// Create a where statement expression of ObjectId = 'id';  
		        LiFilters filter = new LiFilters(LiFieldDynamic.DynamicID, Operation.EQUAL, Id);		        
		        query.setFilter(filter);
	        	
		    	LiObjRequest request = new LiObjRequest();
		        request.setClassName(kClassName);
		
		        request.setAction(RequestAction.GET_ACTION);
		        request.setGet(queryKind);
		       
		        request.setQueryToRequest(query);
		        request.setCallback(callbackHandler);
		        setGetCallback(liDynamicGetByIDCallback,request.requestID);
		       
		        request.startASync();
	        }
	    }

	    /**
	    * A- Synchronized Method to returns an object from server by filters
	    * @param ID
	    * @return
	    * @throws LiErrorHandler
	    */
	    public static void getArrayWithQuery(LiQuery query ,QueryKind queryKind, LiDynamicGetArrayCallback liDynamicGetArrayCallback) 
	    {
	        LiObjRequest request = new LiObjRequest();
	        request.setClassName(kClassName);
	        request.setAction(RequestAction.GET_ARRAY);
	        request.setGet(queryKind);
	        request.setQueryToRequest(query);
	        request.setCallback(callbackHandler);
	        setGetCallback(liDynamicGetArrayCallback,request.requestID);
	        request.startASync();
	    }
	   
		public static void getLocalyWithRawSQLQuery(String whereClause, String[] args, LiDynamicGetArrayCallback liDynamicGetArrayCallback)
	    {
			LiObjRequest request = new LiObjRequest();
	        request.setCallback(callbackHandler);
	        setGetCallback(liDynamicGetArrayCallback,request.requestID);
	        request.GetWithRawQuery(kClassName, whereClause, args);
	    }
	
		 /** Synchronized Method to returns an object from server by filters
		 * @param ID
		 * @return the list of items or null in case on an error
		 * @throws LiErrorHandler
		 */
		 public static List<Dynamic> getArrayWithQuery(LiQuery query ,QueryKind queryKind) throws LiErrorHandler 
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
			  return buildDynamicFromCursor(request.requestID, cursor);
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
	* @param dynamicField - The field to be updated with the file name in Applicasa server 
	* @param filePath - the path to the uploaded file
	* @param dynamicActionCallBack - call back to indicate when the upload was completed
	* @return
	* @throws LiErrorHandler
	*/
	public void updloadFile(LiFieldDynamic liFieldDynamic, String filePath, LiCallbackAction liCallbackAction)
	{
		LiObjRequest request = new LiObjRequest();
		
		request.setAction(RequestAction.UPLOAD_FILE);
		request.setClassName(Dynamic.kClassName);
		request.setRecordID(DynamicID);
		
		request.setFileFieldName(liFieldDynamic);
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
		List<Dynamic> returnList = new ArrayList<Dynamic>();

		returnList = buildDynamicFromCursor(requestID ,cursor);

		Object callback = dynamicCallbacks.get(requestID);
		if (callback != null && callback instanceof LiDynamicGetArrayCallback)
		{
			dynamicCallbacks.remove(requestID);
			((LiDynamicGetArrayCallback)callback).onGetDynamicComplete(returnList);
		}
		if (callback != null && callback instanceof LiDynamicGetByIDCallback )
		{
			dynamicCallbacks.remove(requestID);
			((LiDynamicGetByIDCallback)callback).onGetDynamicComplete(returnList.get(0));
		}
		
	}
	
	public void LiException(String requestID,LiErrorHandler ex) {
		// TODO Auto-generated method stub
		Object callback = dynamicCallbacks.get(requestID);
		if (callback != null && callback instanceof LiDynamicGetArrayCallback )
		{
			dynamicCallbacks.remove(requestID);
			((LiDynamicGetArrayCallback)callback).onGetDynamicFailure(ex);
		}		
		else if (callback != null && callback instanceof LiDynamicGetByIDCallback )
		{
			dynamicCallbacks.remove(requestID);
			((LiDynamicGetByIDCallback)callback).onGetDynamicFailure(ex);
		}
		else if (callback != null && callback instanceof LiCallbackAction )
		{
			dynamicCallbacks.remove(requestID);
			((LiCallbackAction)callback).onFailure(ex);
		}

	}
		public void onCompleteAction(String requestID, LiObjResponse response) {
			// TODO Auto-generated method stub
			Object callback = dynamicCallbacks.get(requestID);
			if (callback != null )
			{
				dynamicCallbacks.remove(requestID);
				if (response.action == RequestAction.ADD_ACTION)
					((Dynamic)response.addedObject).DynamicID = response.newObjID;
					
				if (response.action == RequestAction.UPLOAD_FILE)
				{
					((Dynamic)response.addedObject).setDynamicFieldbySortType((LiFieldDynamic)response.field, response.newObjID);
					if (response.actionResponseList.get(0).objId != null && response.actionResponseList.get(0).requestID == requestID )
						((Dynamic)response.addedObject).DynamicID = response.actionResponseList.get(0).objId;
				}
								
				((LiCallbackAction)callback).onComplete(response.LiRespType, response.LiRespMsg, response.action,response.newObjID, LiObject.getLiObject(response.className));
			}
		}
	};
	
	/**
	 * 
	 * @param requestID
	 * @param cursor
	 * @deprecated use buildDynamicFromCursor
	 * @return
	 */
	@Deprecated
	public static List<Dynamic> BuildDynamicFromCursor(String requestID, Cursor cursor)
	{
		return buildDynamicFromCursor(requestID, cursor);
	}

	/**
	 * 
	 * @param requestID
	 * @param cursor
	 * @return
	 */
	public static List<Dynamic> buildDynamicFromCursor(String requestID ,Cursor cursor)
	{
		List<Dynamic> returnList = new ArrayList<Dynamic>();
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
                    returnList.add(new Dynamic(cursor));                    
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
	
	
	private static void setGetCallback(LiDynamicGetArrayCallback getCallback, String reqID) {
		// TODO Auto-generated method stub
		dynamicCallbacks.put(reqID, getCallback);
	}
	
	private static void setGetCallback(LiDynamicGetByIDCallback getCallback, String reqID) {
		// TODO Auto-generated method stub
		dynamicCallbacks.put(reqID, getCallback);
	}

	private static void setActionCallback(LiCallbackAction actionCallback, String reqID) {
		// TODO Auto-generated method stub
		dynamicCallbacks.put(reqID, actionCallback);
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
	public Dynamic()
	{
		this.DynamicID = "0";
		(this.DynamicLastUpdate = new GregorianCalendar()).setTimeInMillis(0);
		this.DynamicText = "";
		this.DynamicNumber = 0;
		this.DynamicReal = 0f;
		(this.DynamicDate = new GregorianCalendar()).setTimeInMillis(0);
		this.DynamicBool = true;
		this.DynamicHtml = "";
		this.DynamicImage = "";
	}

	public Dynamic(Cursor cursor) 
	{
		initWithCursor(cursor);
	}
	
	public Dynamic(Cursor cursor,String header,int level) 
	{
		initWithCursor(cursor,header,level);
	}
	
	public Dynamic(String DynamicID)
	{
		this.DynamicID = DynamicID;
	}

	public Dynamic(Dynamic item)
	{
		initWithObject(item);
	}

	/**
	* Init Object with Cursor
	* @param corsor
	* @return
	*/
	public Dynamic initWithCursor(Cursor cursor)
	{
		return initWithCursor(cursor,"",0);
	}
	
	/**
	* Init Object with Cursor
	* @param corsor
	* @return
	*/
	public Dynamic initWithCursor(Cursor cursor,String header,int level)
	{
		int columnIndex;
	
		columnIndex = cursor.getColumnIndex(header + LiFieldDynamic.DynamicID.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.DynamicID = cursor.getString(columnIndex);
		
		columnIndex = cursor.getColumnIndex(header + LiFieldDynamic.DynamicLastUpdate.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
		{
			long dateStr = cursor.getLong(columnIndex);
			GregorianCalendar gc= new GregorianCalendar();
			gc.setTimeInMillis(dateStr);
			this.DynamicLastUpdate = gc;
		}
		
		columnIndex = cursor.getColumnIndex(header + LiFieldDynamic.DynamicText.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.DynamicText = cursor.getString(columnIndex);
		
		columnIndex = cursor.getColumnIndex(header + LiFieldDynamic.DynamicNumber.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.DynamicNumber = cursor.getInt(columnIndex);
		
		columnIndex = cursor.getColumnIndex(header +LiFieldDynamic.DynamicReal.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.DynamicReal = cursor.getFloat(columnIndex);
		
		columnIndex = cursor.getColumnIndex(header + LiFieldDynamic.DynamicDate.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
		{
			long dateStr = cursor.getLong(columnIndex);
			GregorianCalendar gc= new GregorianCalendar();
			gc.setTimeInMillis(dateStr);
			this.DynamicDate = gc;
		}
		
		columnIndex = cursor.getColumnIndex(header + LiFieldDynamic.DynamicBool.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
		{
			this.DynamicBool = cursor.getInt(columnIndex)==1?true:false;
		}
		
		columnIndex = cursor.getColumnIndex(header + LiFieldDynamic.DynamicHtml.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.DynamicHtml = cursor.getString(columnIndex);
		
		columnIndex = cursor.getColumnIndex(header + LiFieldDynamic.DynamicImage.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.DynamicImage = cursor.getString(columnIndex);
		
	
		return this;
	}
	
	/**
	* Initialize values with Object
	* @param item
	* @return
	*/
	public String initWithObject(Dynamic item)
	{
		this.DynamicID			= item.DynamicID;
		this.DynamicLastUpdate			= item.DynamicLastUpdate;
		this.DynamicText			= item.DynamicText;
		this.DynamicNumber			= item.DynamicNumber;
		this.DynamicReal			= item.DynamicReal;
		this.DynamicDate			= item.DynamicDate;
		this.DynamicBool			= item.DynamicBool;
		this.DynamicHtml			= item.DynamicHtml;
		this.DynamicImage			= item.DynamicImage;
	
		return DynamicID;
	}
	
	/**
	* Function to add the given object fields to the request parameters list
	* @param item
	* @param request
	* @return
	*/
/**
* Initialize Dictionary with Dynamic item instance
* @param dictionary
* @return
*/
public LiJSONObject dictionaryRepresentation(boolean withFK) throws LiErrorHandler {

	try{
		LiJSONObject dictionary = new LiJSONObject();
		dictionary.put(LiFieldDynamic.DynamicID, DynamicID);
	
		dictionary.put(LiFieldDynamic.DynamicLastUpdate, LiUtility.convertDateToDictionaryRepresenataion(DynamicLastUpdate));
	
		dictionary.put(LiFieldDynamic.DynamicText, DynamicText);
	
		dictionary.put(LiFieldDynamic.DynamicNumber, DynamicNumber);
	
		dictionary.put(LiFieldDynamic.DynamicReal, DynamicReal);
	
		dictionary.put(LiFieldDynamic.DynamicDate, LiUtility.convertDateToDictionaryRepresenataion(DynamicDate));
	
		dictionary.put(LiFieldDynamic.DynamicBool, DynamicBool);
	
		dictionary.put(LiFieldDynamic.DynamicHtml, DynamicHtml);
	
		dictionary.put(LiFieldDynamic.DynamicImage, DynamicImage);
	
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
		dbObject.put(LiFieldDynamic.DynamicID, LiCoreDBmanager.PRIMARY_KEY,-1);
		dbObject.put(LiFieldDynamic.DynamicLastUpdate, LiCoreDBmanager.DATE,0);
		dbObject.put(LiFieldDynamic.DynamicText, LiCoreDBmanager.TEXT,"");
		dbObject.put(LiFieldDynamic.DynamicNumber, LiCoreDBmanager.INTEGER,0);
		dbObject.put(LiFieldDynamic.DynamicReal, LiCoreDBmanager.REAL,0f);
		dbObject.put(LiFieldDynamic.DynamicDate, LiCoreDBmanager.DATE,0);
		dbObject.put(LiFieldDynamic.DynamicBool, LiCoreDBmanager.BOOL,true);
		dbObject.put(LiFieldDynamic.DynamicHtml, LiCoreDBmanager.TEXT,"");
		dbObject.put(LiFieldDynamic.DynamicImage, LiCoreDBmanager.TEXT,"");
	return dbObject;
}
	public void increment(LiFieldDynamic liFieldDynamic) throws LiErrorHandler
	{
		increment(liFieldDynamic, 1);
	}
		 
	public void increment(LiFieldDynamic liFieldDynamic, Object value) throws LiErrorHandler
	{
		String key = liFieldDynamic.toString();
		float oldValueFloat = 0;
		int oldValueInt = 0;
		Object incrementedField = getDynamicFieldbySortType(liFieldDynamic);
		try {
			if (incrementedField instanceof Integer)
			{
				int incInt;
				if (value instanceof Integer)
					incInt = (Integer)value;
				else
					 throw new LiErrorHandler(ApplicasaResponse.INPUT_VALUES_ERROR, "Incremented Value isn't of the same type as the requested field");
				int total = (Integer)incrementedField+incInt;
				setDynamicFieldbySortType(liFieldDynamic, total);
				if (incrementedFields.has(liFieldDynamic.toString()))
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
				setDynamicFieldbySortType(liFieldDynamic, total);
					if (incrementedFields.has(liFieldDynamic.toString()))
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

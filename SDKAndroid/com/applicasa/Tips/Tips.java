package com.applicasa.Tips;
import java.util.ArrayList;
import java.util.List;
import java.util.GregorianCalendar;

import applicasa.LiCore.communication.LiUtility;


import applicasa.LiCore.communication.LiCallback.LiCallbackAction;
import com.applicasa.ApplicasaManager.LiCallbackQuery.LiTipsGetByIDCallback;
import com.applicasa.ApplicasaManager.LiCallbackQuery.LiTipsGetArrayCallback;
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



public class Tips extends TipsData {
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
		if (TipsID!= "0" && (LiUtility.isHex(TipsID)|| TipsID.startsWith("temp_") ))
		{
			request.setAction(RequestAction.UPDATE_ACTION);
			request.setRecordID(TipsID);
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
		if (TipsID == null || TipsID == "") 
			if (liCallbackAction != null) liCallbackAction.onFailure(new LiErrorHandler(ApplicasaResponse.NULL_ITEM, "Missing Item ID"));
			else return;
		
		LiObjRequest request = new LiObjRequest();
		request.setAction(RequestAction.DELETE_ACTION);
		request.setClassName(kClassName);
		request.setCallback(callbackHandler);
		request.setRecordID(TipsID);
		
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
	    public static void getByID(String Id, QueryKind queryKind, LiTipsGetByIDCallback liTipsGetByIDCallback) 
	    {
	        if (Id != null)
	        {
	        	// Creates new Query
	        	LiQuery query= new LiQuery();
	        	
	        	// Create a where statement expression of ObjectId = 'id';  
		        LiFilters filter = new LiFilters(LiFieldTips.TipsID, Operation.EQUAL, Id);		        
		        query.setFilter(filter);
	        	
		    	LiObjRequest request = new LiObjRequest();
		        request.setClassName(kClassName);
		
		        request.setAction(RequestAction.GET_ACTION);
		        request.setGet(queryKind);
		       
		        request.setQueryToRequest(query);
		        request.setCallback(callbackHandler);
		        setGetCallback(liTipsGetByIDCallback,request.requestID);
		       
		        request.startASync();
	        }
	    }

	    /**
	    * A- Synchronized Method to returns an object from server by filters
	    * @param ID
	    * @return
	    * @throws LiErrorHandler
	    */
	    public static void getArrayWithQuery(LiQuery query ,QueryKind queryKind, LiTipsGetArrayCallback liTipsGetArrayCallback) 
	    {
	        LiObjRequest request = new LiObjRequest();
	        request.setClassName(kClassName);
	        request.setAction(RequestAction.GET_ARRAY);
	        request.setGet(queryKind);
	        request.setQueryToRequest(query);
	        request.setCallback(callbackHandler);
	        setGetCallback(liTipsGetArrayCallback,request.requestID);
	        request.startASync();
	    }
	   
		public static void getLocalyWithRawSQLQuery(String whereClause, String[] args, LiTipsGetArrayCallback liTipsGetArrayCallback)
	    {
			LiObjRequest request = new LiObjRequest();
	        request.setCallback(callbackHandler);
	        setGetCallback(liTipsGetArrayCallback,request.requestID);
	        request.GetWithRawQuery(kClassName, whereClause, args);
	    }
	
		 /** Synchronized Method to returns an object from server by filters
		 * @param ID
		 * @return the list of items or null in case on an error
		 * @throws LiErrorHandler
		 */
		 public static List<Tips> getArrayWithQuery(LiQuery query ,QueryKind queryKind) throws LiErrorHandler 
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
			  return buildTipsFromCursor(request.requestID, cursor);
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
	* @param tipsField - The field to be updated with the file name in Applicasa server 
	* @param filePath - the path to the uploaded file
	* @param tipsActionCallBack - call back to indicate when the upload was completed
	* @return
	* @throws LiErrorHandler
	*/
	public void updloadFile(LiFieldTips liFieldTips, String filePath, LiCallbackAction liCallbackAction)
	{
		LiObjRequest request = new LiObjRequest();
		
		request.setAction(RequestAction.UPLOAD_FILE);
		request.setClassName(Tips.kClassName);
		request.setRecordID(TipsID);
		
		request.setFileFieldName(liFieldTips);
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
		List<Tips> returnList = new ArrayList<Tips>();

		returnList = buildTipsFromCursor(requestID ,cursor);

		Object callback = tipsCallbacks.get(requestID);
		if (callback != null && callback instanceof LiTipsGetArrayCallback)
		{
			tipsCallbacks.remove(requestID);
			((LiTipsGetArrayCallback)callback).onGetTipsComplete(returnList);
		}
		if (callback != null && callback instanceof LiTipsGetByIDCallback )
		{
			tipsCallbacks.remove(requestID);
			((LiTipsGetByIDCallback)callback).onGetTipsComplete(returnList.get(0));
		}
		
	}
	
	public void LiException(String requestID,LiErrorHandler ex) {
		// TODO Auto-generated method stub
		Object callback = tipsCallbacks.get(requestID);
		if (callback != null && callback instanceof LiTipsGetArrayCallback )
		{
			tipsCallbacks.remove(requestID);
			((LiTipsGetArrayCallback)callback).onGetTipsFailure(ex);
		}		
		else if (callback != null && callback instanceof LiTipsGetByIDCallback )
		{
			tipsCallbacks.remove(requestID);
			((LiTipsGetByIDCallback)callback).onGetTipsFailure(ex);
		}
		else if (callback != null && callback instanceof LiCallbackAction )
		{
			tipsCallbacks.remove(requestID);
			((LiCallbackAction)callback).onFailure(ex);
		}

	}
		public void onCompleteAction(String requestID, LiObjResponse response) {
			// TODO Auto-generated method stub
			Object callback = tipsCallbacks.get(requestID);
			if (callback != null )
			{
				tipsCallbacks.remove(requestID);
				if (response.action == RequestAction.ADD_ACTION)
					((Tips)response.addedObject).TipsID = response.newObjID;
				if (response.action == RequestAction.UPLOAD_FILE)
					((Tips)response.addedObject).setTipsFieldbySortType((LiFieldTips)response.field, response.newObjID);
				
				((LiCallbackAction)callback).onComplete(response.LiRespType, response.LiRespMsg, response.action,response.newObjID, LiObject.getLiObject(response.className));
			}
		}
	};
	
	/**
	 * 
	 * @param requestID
	 * @param cursor
	 * @deprecated use buildTipsFromCursor
	 * @return
	 */
	@Deprecated
	public static List<Tips> BuildTipsFromCursor(String requestID, Cursor cursor)
	{
		return buildTipsFromCursor(requestID, cursor);
	}

	/**
	 * 
	 * @param requestID
	 * @param cursor
	 * @return
	 */
	public static List<Tips> buildTipsFromCursor(String requestID ,Cursor cursor)
	{
		List<Tips> returnList = new ArrayList<Tips>();
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
                    returnList.add(new Tips(cursor));                    
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
	
	
	private static void setGetCallback(LiTipsGetArrayCallback getCallback, String reqID) {
		// TODO Auto-generated method stub
		tipsCallbacks.put(reqID, getCallback);
	}
	
	private static void setGetCallback(LiTipsGetByIDCallback getCallback, String reqID) {
		// TODO Auto-generated method stub
		tipsCallbacks.put(reqID, getCallback);
	}

	private static void setActionCallback(LiCallbackAction actionCallback, String reqID) {
		// TODO Auto-generated method stub
		tipsCallbacks.put(reqID, actionCallback);
	} /** End of Basic SDK **/ 

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////                                                   ////////////////////////////////////////
///////////////////////////////////                    Init Method                    ////////////////////////////////////////
///////////////////////////////////                    Don't ALTER                    ////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public Tips()
	{
		this.TipsID = "0";
		(this.TipsLastUpdate = new GregorianCalendar()).setTimeInMillis(0);
		this.TipsContent = "";
		this.TipsNum = 0;
		this.TipsFdfsd = 0;
	}

	public Tips(Cursor cursor) 
	{
		initWithCursor(cursor);
	}
	
	public Tips(Cursor cursor,String header,int level) 
	{
		initWithCursor(cursor,header,level);
	}
	
	public Tips(String TipsID)
	{
		this.TipsID = TipsID;
	}

	/**
	* Init Object with Cursor
	* @param corsor
	* @return
	*/
	public Tips initWithCursor(Cursor cursor)
	{
		return initWithCursor(cursor,"",0);
	}
	
	/**
	* Init Object with Cursor
	* @param corsor
	* @return
	*/
	public Tips initWithCursor(Cursor cursor,String header,int level)
	{
		int columnIndex;
	
		columnIndex = cursor.getColumnIndex(header + LiFieldTips.TipsID.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.TipsID = cursor.getString(columnIndex);
		
		columnIndex = cursor.getColumnIndex(header + LiFieldTips.TipsLastUpdate.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
		{
			long dateStr = cursor.getLong(columnIndex);
			GregorianCalendar gc= new GregorianCalendar();
			gc.setTimeInMillis(dateStr);
			this.TipsLastUpdate = gc;
		}
		
		columnIndex = cursor.getColumnIndex(header + LiFieldTips.TipsContent.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.TipsContent = cursor.getString(columnIndex);
		
		columnIndex = cursor.getColumnIndex(header + LiFieldTips.TipsNum.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.TipsNum = cursor.getInt(columnIndex);
		
		columnIndex = cursor.getColumnIndex(header + LiFieldTips.TipsFdfsd.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.TipsFdfsd = cursor.getInt(columnIndex);
		
	
		return this;
	}
	
	/**
	* Initialize values with Object
	* @param item
	* @return
	*/
	public String initWithObject(Tips item)
	{
		this.TipsID			= item.TipsID;
		this.TipsLastUpdate			= item.TipsLastUpdate;
		this.TipsContent			= item.TipsContent;
		this.TipsNum			= item.TipsNum;
		this.TipsFdfsd			= item.TipsFdfsd;
	
		return TipsID;
	}
	
	/**
	* Function to add the given object fields to the request parameters list
	* @param item
	* @param request
	* @return
	*/
/**
* Initialize Dictionary with Tips item instance
* @param dictionary
* @return
*/
public LiJSONObject dictionaryRepresentation(boolean withFK) throws LiErrorHandler {

	try{
		LiJSONObject dictionary = new LiJSONObject();
		dictionary.put(LiFieldTips.TipsID, TipsID);
	
		dictionary.put(LiFieldTips.TipsLastUpdate, LiUtility.convertDateToDictionaryRepresenataion(TipsLastUpdate));
	
		dictionary.put(LiFieldTips.TipsContent, TipsContent);
	
		dictionary.put(LiFieldTips.TipsNum, TipsNum);
	
		dictionary.put(LiFieldTips.TipsFdfsd, TipsFdfsd);
	
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
		dbObject.put(LiFieldTips.TipsID, LiCoreDBmanager.PRIMARY_KEY,-1);
		dbObject.put(LiFieldTips.TipsLastUpdate, LiCoreDBmanager.DATE,0);
		dbObject.put(LiFieldTips.TipsContent, LiCoreDBmanager.TEXT,"");
		dbObject.put(LiFieldTips.TipsNum, LiCoreDBmanager.INTEGER,0);
		dbObject.put(LiFieldTips.TipsFdfsd, LiCoreDBmanager.INTEGER,0);
	return dbObject;
}
	public void increment(LiFieldTips liFieldTips) throws LiErrorHandler
	{
		increment(liFieldTips, 1);
	}
		 
	public void increment(LiFieldTips liFieldTips, Object value) throws LiErrorHandler
	{
		String key = liFieldTips.toString();
		float oldValueFloat = 0;
		int oldValueInt = 0;
		Object incrementedField = getTipsFieldbySortType(liFieldTips);
		try {
			if (incrementedField instanceof Integer)
			{
				int incInt;
				if (value instanceof Integer)
					incInt = (Integer)value;
				else
					 throw new LiErrorHandler(ApplicasaResponse.INPUT_VALUES_ERROR, "Incremented Value isn't of the same type as the requested field");
				int total = (Integer)incrementedField+incInt;
				setTipsFieldbySortType(liFieldTips, total);
				if (incrementedFields.has(liFieldTips.toString()))
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
				setTipsFieldbySortType(liFieldTips, total);
					if (incrementedFields.has(liFieldTips.toString()))
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

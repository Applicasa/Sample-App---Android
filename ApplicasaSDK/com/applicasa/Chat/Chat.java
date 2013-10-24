package com.applicasa.Chat;
import java.util.ArrayList;
import java.util.List;
import java.util.GregorianCalendar;

import applicasa.LiCore.communication.LiUtility;


import applicasa.LiCore.communication.LiCallback.LiCallbackAction;
import com.applicasa.ApplicasaManager.LiCallbackQuery.LiChatGetByIDCallback;
import com.applicasa.ApplicasaManager.LiCallbackQuery.LiChatGetArrayCallback;
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
import com.applicasa.User.User;

public class Chat extends ChatData {
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
		if (ChatID!= "0" && (LiUtility.isHex(ChatID)|| ChatID.startsWith("temp_") ))
		{
			request.setAction(RequestAction.UPDATE_ACTION);
			request.setRecordID(ChatID);
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
		if (ChatID == null || ChatID == "") 
			if (liCallbackAction != null) liCallbackAction.onFailure(new LiErrorHandler(ApplicasaResponse.NULL_ITEM, "Missing Item ID"));
			else return;
		
		LiObjRequest request = new LiObjRequest();
		request.setAction(RequestAction.DELETE_ACTION);
		request.setClassName(kClassName);
		request.setCallback(callbackHandler);
		request.setRecordID(ChatID);
		
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
	    public static void getByID(String Id, QueryKind queryKind, LiChatGetByIDCallback liChatGetByIDCallback) 
	    {
	        if (Id != null)
	        {
	        	// Creates new Query
	        	LiQuery query= new LiQuery();
	        	
	        	// Create a where statement expression of ObjectId = 'id';  
		        LiFilters filter = new LiFilters(LiFieldChat.ChatID, Operation.EQUAL, Id);		        
		        query.setFilter(filter);
	        	
		    	LiObjRequest request = new LiObjRequest();
		        request.setClassName(kClassName);
		
		        request.setAction(RequestAction.GET_ACTION);
		        request.setGet(queryKind);
		       
		        request.setQueryToRequest(query);
		        request.setCallback(callbackHandler);
		        setGetCallback(liChatGetByIDCallback,request.requestID);
		       
		        request.startASync();
	        }
	    }

	    /**
	    * A- Synchronized Method to returns an object from server by filters
	    * @param ID
	    * @return
	    * @throws LiErrorHandler
	    */
	    public static void getArrayWithQuery(LiQuery query ,QueryKind queryKind, LiChatGetArrayCallback liChatGetArrayCallback) 
	    {
	        LiObjRequest request = new LiObjRequest();
	        request.setClassName(kClassName);
	        request.setAction(RequestAction.GET_ARRAY);
	        request.setGet(queryKind);
	        request.setQueryToRequest(query);
	        request.setCallback(callbackHandler);
	        setGetCallback(liChatGetArrayCallback,request.requestID);
	        request.startASync();
	    }
	   
		public static void getLocalyWithRawSQLQuery(String whereClause, String[] args, LiChatGetArrayCallback liChatGetArrayCallback)
	    {
			LiObjRequest request = new LiObjRequest();
	        request.setCallback(callbackHandler);
	        setGetCallback(liChatGetArrayCallback,request.requestID);
	        request.GetWithRawQuery(kClassName, whereClause, args);
	    }
	
		 /** Synchronized Method to returns an object from server by filters
		 * @param ID
		 * @return the list of items or null in case on an error
		 * @throws LiErrorHandler
		 */
		 public static List<Chat> getArrayWithQuery(LiQuery query ,QueryKind queryKind) throws LiErrorHandler 
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
			  return buildChatFromCursor(request.requestID, cursor);
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
	* @param chatField - The field to be updated with the file name in Applicasa server 
	* @param filePath - the path to the uploaded file
	* @param chatActionCallBack - call back to indicate when the upload was completed
	* @return
	* @throws LiErrorHandler
	*/
	public void updloadFile(LiFieldChat liFieldChat, String filePath, LiCallbackAction liCallbackAction)
	{
		LiObjRequest request = new LiObjRequest();
		
		request.setAction(RequestAction.UPLOAD_FILE);
		request.setClassName(Chat.kClassName);
		request.setRecordID(ChatID);
		
		request.setFileFieldName(liFieldChat);
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
		List<Chat> returnList = new ArrayList<Chat>();

		returnList = buildChatFromCursor(requestID ,cursor);

		Object callback = chatCallbacks.get(requestID);
		if (callback != null && callback instanceof LiChatGetArrayCallback)
		{
			chatCallbacks.remove(requestID);
			((LiChatGetArrayCallback)callback).onGetChatComplete(returnList);
		}
		if (callback != null && callback instanceof LiChatGetByIDCallback )
		{
			chatCallbacks.remove(requestID);
			((LiChatGetByIDCallback)callback).onGetChatComplete(returnList.get(0));
		}
		
	}
	
	public void LiException(String requestID,LiErrorHandler ex) {
		// TODO Auto-generated method stub
		Object callback = chatCallbacks.get(requestID);
		if (callback != null && callback instanceof LiChatGetArrayCallback )
		{
			chatCallbacks.remove(requestID);
			((LiChatGetArrayCallback)callback).onGetChatFailure(ex);
		}		
		else if (callback != null && callback instanceof LiChatGetByIDCallback )
		{
			chatCallbacks.remove(requestID);
			((LiChatGetByIDCallback)callback).onGetChatFailure(ex);
		}
		else if (callback != null && callback instanceof LiCallbackAction )
		{
			chatCallbacks.remove(requestID);
			((LiCallbackAction)callback).onFailure(ex);
		}

	}
		public void onCompleteAction(String requestID, LiObjResponse response) {
			// TODO Auto-generated method stub
			Object callback = chatCallbacks.get(requestID);
			if (callback != null )
			{
				chatCallbacks.remove(requestID);
				if (response.action == RequestAction.ADD_ACTION)
					((Chat)response.addedObject).ChatID = response.newObjID;
					
				if (response.action == RequestAction.UPLOAD_FILE)
				{
					((Chat)response.addedObject).setChatFieldbySortType((LiFieldChat)response.field, response.newObjID);
					if (response.actionResponseList.get(0).objId != null && response.actionResponseList.get(0).requestID == requestID )
						((Chat)response.addedObject).ChatID = response.actionResponseList.get(0).objId;
				}
								
				((LiCallbackAction)callback).onComplete(response.LiRespType, response.LiRespMsg, response.action,response.newObjID, LiObject.getLiObject(response.className));
			}
		}
	};
	
	/**
	 * 
	 * @param requestID
	 * @param cursor
	 * @deprecated use buildChatFromCursor
	 * @return
	 */
	@Deprecated
	public static List<Chat> BuildChatFromCursor(String requestID, Cursor cursor)
	{
		return buildChatFromCursor(requestID, cursor);
	}

	/**
	 * 
	 * @param requestID
	 * @param cursor
	 * @return
	 */
	public static List<Chat> buildChatFromCursor(String requestID ,Cursor cursor)
	{
		List<Chat> returnList = new ArrayList<Chat>();
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
                    returnList.add(new Chat(cursor));                    
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
	
	
	private static void setGetCallback(LiChatGetArrayCallback getCallback, String reqID) {
		// TODO Auto-generated method stub
		chatCallbacks.put(reqID, getCallback);
	}
	
	private static void setGetCallback(LiChatGetByIDCallback getCallback, String reqID) {
		// TODO Auto-generated method stub
		chatCallbacks.put(reqID, getCallback);
	}

	private static void setActionCallback(LiCallbackAction actionCallback, String reqID) {
		// TODO Auto-generated method stub
		chatCallbacks.put(reqID, actionCallback);
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
	public Chat()
	{
		this.ChatID = "0";
		(this.ChatLastUpdate = new GregorianCalendar()).setTimeInMillis(0);
		this.ChatIsSender = true;
		this.ChatText = "";
		this.ChatGhjgjgj = "";
		this.ChatSender = new User("0");
		this.ChatReciepent = new User("0");
	}

	public Chat(Cursor cursor) 
	{
		initWithCursor(cursor);
	}
	
	public Chat(Cursor cursor,String header,int level) 
	{
		initWithCursor(cursor,header,level);
	}
	
	public Chat(String ChatID)
	{
		this.ChatID = ChatID;
	}

	public Chat(Chat item)
	{
		initWithObject(item);
	}

	/**
	* Init Object with Cursor
	* @param corsor
	* @return
	*/
	public Chat initWithCursor(Cursor cursor)
	{
		return initWithCursor(cursor,"",0);
	}
	
	/**
	* Init Object with Cursor
	* @param corsor
	* @return
	*/
	public Chat initWithCursor(Cursor cursor,String header,int level)
	{
		int columnIndex;
	
		columnIndex = cursor.getColumnIndex(header + LiFieldChat.ChatID.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.ChatID = cursor.getString(columnIndex);
		
		columnIndex = cursor.getColumnIndex(header + LiFieldChat.ChatLastUpdate.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
		{
			long dateStr = cursor.getLong(columnIndex);
			GregorianCalendar gc= new GregorianCalendar();
			gc.setTimeInMillis(dateStr);
			this.ChatLastUpdate = gc;
		}
		
		columnIndex = cursor.getColumnIndex(header + LiFieldChat.ChatIsSender.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
		{
			this.ChatIsSender = cursor.getInt(columnIndex)==1?true:false;
		}
		
		columnIndex = cursor.getColumnIndex(header + LiFieldChat.ChatText.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.ChatText = cursor.getString(columnIndex);
		
		columnIndex = cursor.getColumnIndex(header + LiFieldChat.ChatGhjgjgj.toString());
		if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST)
			this.ChatGhjgjgj = cursor.getString(columnIndex);
		
			columnIndex = cursor.getColumnIndex(header + LiFieldChat.ChatSender.toString());
			if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST && level==0)
			{ 
				this.ChatSender = new User(cursor,LiFieldChat.ChatSender.toString(),level+1);
			}
			else if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST){
				this.ChatSender = new User(cursor.getString(columnIndex));
			}
			columnIndex = cursor.getColumnIndex(header + LiFieldChat.ChatReciepent.toString());
			if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST && level==0)
			{ 
				this.ChatReciepent = new User(cursor,LiFieldChat.ChatReciepent.toString(),level+1);
			}
			else if (columnIndex != LiCoreDBmanager.COLUMN_NOT_EXIST){
				this.ChatReciepent = new User(cursor.getString(columnIndex));
			}
	
		return this;
	}
	
	/**
	* Initialize values with Object
	* @param item
	* @return
	*/
	public String initWithObject(Chat item)
	{
		this.ChatID			= item.ChatID;
		this.ChatLastUpdate			= item.ChatLastUpdate;
		this.ChatIsSender			= item.ChatIsSender;
		this.ChatText			= item.ChatText;
		this.ChatGhjgjgj			= item.ChatGhjgjgj;
		this.ChatSender			= item.ChatSender;
		this.ChatReciepent			= item.ChatReciepent;
	
		return ChatID;
	}
	
	/**
	* Function to add the given object fields to the request parameters list
	* @param item
	* @param request
	* @return
	*/
/**
* Initialize Dictionary with Chat item instance
* @param dictionary
* @return
*/
public LiJSONObject dictionaryRepresentation(boolean withFK) throws LiErrorHandler {

	try{
		LiJSONObject dictionary = new LiJSONObject();
		dictionary.put(LiFieldChat.ChatID, ChatID);
	
		dictionary.put(LiFieldChat.ChatLastUpdate, LiUtility.convertDateToDictionaryRepresenataion(ChatLastUpdate));
	
		dictionary.put(LiFieldChat.ChatIsSender, ChatIsSender);
	
		dictionary.put(LiFieldChat.ChatText, ChatText);
	
		dictionary.put(LiFieldChat.ChatGhjgjgj, ChatGhjgjgj);
	
		if (withFK)
			dictionary.put(LiFieldChat.ChatSender, ChatSender.dictionaryRepresentation(true));
		else
			dictionary.put(LiFieldChat.ChatSender, ChatSender.UserID);
		if (withFK)
			dictionary.put(LiFieldChat.ChatReciepent, ChatReciepent.dictionaryRepresentation(true));
		else
			dictionary.put(LiFieldChat.ChatReciepent, ChatReciepent.UserID);
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
		dbObject.put(LiFieldChat.ChatID, LiCoreDBmanager.PRIMARY_KEY,-1);
		dbObject.put(LiFieldChat.ChatLastUpdate, LiCoreDBmanager.DATE,0);
		dbObject.put(LiFieldChat.ChatIsSender, LiCoreDBmanager.BOOL,true);
		dbObject.put(LiFieldChat.ChatSender, LiCoreDBmanager.FOREIGN_KEY+"_User","0");
		dbObject.put(LiFieldChat.ChatText, LiCoreDBmanager.TEXT,"");
		dbObject.put(LiFieldChat.ChatReciepent, LiCoreDBmanager.FOREIGN_KEY+"_User","0");
		dbObject.put(LiFieldChat.ChatGhjgjgj, LiCoreDBmanager.TEXT,"");
	return dbObject;
}
	public void increment(LiFieldChat liFieldChat) throws LiErrorHandler
	{
		increment(liFieldChat, 1);
	}
		 
	public void increment(LiFieldChat liFieldChat, Object value) throws LiErrorHandler
	{
		String key = liFieldChat.toString();
		float oldValueFloat = 0;
		int oldValueInt = 0;
		Object incrementedField = getChatFieldbySortType(liFieldChat);
		try {
			if (incrementedField instanceof Integer)
			{
				int incInt;
				if (value instanceof Integer)
					incInt = (Integer)value;
				else
					 throw new LiErrorHandler(ApplicasaResponse.INPUT_VALUES_ERROR, "Incremented Value isn't of the same type as the requested field");
				int total = (Integer)incrementedField+incInt;
				setChatFieldbySortType(liFieldChat, total);
				if (incrementedFields.has(liFieldChat.toString()))
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
				setChatFieldbySortType(liFieldChat, total);
					if (incrementedFields.has(liFieldChat.toString()))
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

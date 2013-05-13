package com.applicasa.Chat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import applicasa.LiCore.LiLocation;
import applicasa.LiCore.LiField;
import applicasa.LiJson.LiJSONObject;
import com.applicasa.User.User;
import com.applicasa.User.User;

public class ChatData {


	protected static Map<String, LiFieldChat> stringMap = new HashMap<String, LiFieldChat>();
	LiJSONObject incrementedFields = new LiJSONObject();
	public static boolean EnableOffline = true;
	public enum LiFieldChat implements LiField
	{
		Chat_None
	, ChatID
	, ChatLastUpdate
	, ChatIsSender
	, ChatText
	, ChatGhjgjgj
	, ChatSender
	, ChatReciepent

	;

		private LiFieldChat() {
			stringMap.put(this.toString(), this);
		}

		public static LiFieldChat getLiFieldChat(String key) {
			return stringMap.get(key);
	}
	}

	protected static Map<String, Object > chatCallbacks = new HashMap<String, Object>();
	//Class Name 
	public final static String kClassName                =  "Chat";
	
	////
	//// Class fields name - Static Fields
	////
	////
	////
		public String ChatID;
	
		public GregorianCalendar ChatLastUpdate;
	
		public Boolean ChatIsSender;
	
		public String ChatText;
	
		public String ChatGhjgjgj;
	
		public User ChatSender;
		public User ChatReciepent;
	
		public String getChatID() {
			return ChatID;
		}
		
		public void setChatID(String ChatID) {
			this.ChatID = ChatID;
		}
		
		public GregorianCalendar getChatLastUpdate() {
			return ChatLastUpdate;
		}
		
		public void setChatLastUpdate(GregorianCalendar ChatLastUpdate) {
			this.ChatLastUpdate = ChatLastUpdate;
		}
		
		public Boolean getChatIsSender() {
			return ChatIsSender;
		}
		
		public void setChatIsSender(Boolean ChatIsSender) {
			this.ChatIsSender = ChatIsSender;
		}
		
		public String getChatText() {
			return ChatText;
		}
		
		public void setChatText(String ChatText) {
			this.ChatText = ChatText;
		}
		
		public String getChatGhjgjgj() {
			return ChatGhjgjgj;
		}
		
		public void setChatGhjgjgj(String ChatGhjgjgj) {
			this.ChatGhjgjgj = ChatGhjgjgj;
		}
		
		public User getChatSender() {
			return ChatSender;
		}
		
		public void setChatSender(User ChatSender) {
			this.ChatSender = ChatSender;
		}
		
		public User getChatReciepent() {
			return ChatReciepent;
		}
		
		public void setChatReciepent(User ChatReciepent) {
			this.ChatReciepent = ChatReciepent;
		}
		
		public static String getChatSortField(ChatData.LiFieldChat field)
		{
			return field.toString();
		}
	public Object getChatFieldbySortType(ChatData.LiFieldChat field)
	{
		switch (field){
			case Chat_None:
				return ChatID;
				
			case ChatID:
				return ChatID;
				
			case ChatLastUpdate:
				return ChatLastUpdate;
				
			case ChatIsSender:
					return ChatIsSender;
					
			case ChatText:
				return ChatText;
				
			case ChatGhjgjgj:
				return ChatGhjgjgj;
				
			case ChatSender:
				return ChatSender.UserID;
				
			case ChatReciepent:
				return ChatReciepent.UserID;
				
			default:
				return "";
		}
		
	}
	
	protected boolean setChatFieldbySortType(ChatData.LiFieldChat field, Object value)
	{
		switch (field){
			case Chat_None:
				break;
				
			case ChatID:
					ChatID = (String)value;
					break;
					
			case ChatLastUpdate:
					ChatLastUpdate = (GregorianCalendar)value;
					break;
				
			case ChatIsSender:
					ChatIsSender = (Boolean)value;
					break;
					
			case ChatText:
					ChatText = (String)value;
					break;
					
			case ChatGhjgjgj:
					ChatGhjgjgj = (String)value;
					break;
					
			case ChatSender:
					ChatSender =  new User((String)value);
					break;
					
			case ChatReciepent:
					ChatReciepent =  new User((String)value);
					break;
					
			default:
				break;
		}
		return true;
	}
}

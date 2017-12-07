package chat;

import java.io.Serializable;
import java.util.Vector;

public class ChatRoom implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7033408661297230161L;
	private String names;
	private int roomId;
	private Vector<String> chatMessages;
	
	private transient String latestMsg;
	private transient String latestTime;
	
	public ChatRoom() {  };
	
	public ChatRoom(String names, int roomId) {
		this.names = names;
		this.roomId = roomId;
	}
	
	public int getRoomId() {
		return roomId;
	}
	
	public Vector<String> getChatMessages() {
		return chatMessages;
	}
	
	public String getNames() {
		return names;
	}
	
	public void setNames(String names) {
		this.names = names;
	}

}

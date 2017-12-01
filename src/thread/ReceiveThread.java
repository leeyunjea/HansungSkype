package thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Vector;

import chat.ChatRoom;
import main.MainFrame;
import protocol.Protocol;
import database.UserInfo;

public class ReceiveThread extends Thread {

	private MainFrame mainFrame;

	private String id;
	private String pw;
	private UserInfo user;
	private Vector<UserInfo> users;

	private Socket socket = null;
	private DataInputStream dataInputStream = null;
	private DataOutputStream dataOutputStream = null;
	private ObjectInputStream objectInputStream = null;
	private ObjectOutputStream objectOutputStream = null;
	
	private String buffer;

	public ReceiveThread(String id, String pw, MainFrame mainFrame) {
		this.id = id;
		this.pw = pw;
		this.mainFrame = mainFrame;
		initServer();
	}

	public void initServer() {
		try {
			socket = new Socket(MainFrame.SERVER_IP, MainFrame.SERVER_PORT);
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			debug.Debug.log("Connect Error");
		} catch (IOException e) {
			debug.Debug.log("Connect Error");
		}
	}

	public void run() {
		try {
			String msg = id + "," + pw;
			dataOutputStream.writeUTF(msg);
			if (dataInputStream.readInt() == Protocol.LOGIN_SUCCESS) {
				debug.Debug.log("Login Success");
				// msg = dataInputStream.readUTF();
				// stringArr = msg.split(",");
				// name = stringArr[0];
				// stateMsg = stringArr[1];
				// image = stringArr[2];

				user = (UserInfo) objectInputStream.readObject();
				users = (Vector<UserInfo>) objectInputStream.readObject();
				mainFrame.createUser(user);
				mainFrame.setUsers(users);
				mainFrame.getLogin().loginSuccess(mainFrame.getUser());
				

				while (true) {
					switch (dataInputStream.readInt()) {
					case Protocol.CLIENT_LOGIN:
						buffer = dataInputStream.readUTF();
						InetAddress address = (InetAddress) objectInputStream.readObject();
						System.out.println(buffer + " " + address + " Client Login");
						for(int i=0; i<users.size(); i++) {
							if(users.get(i).getId().equals(buffer)) {
								users.get(i).setConnectionState(true);
								debug.Debug.log(users.get(i).getId() + " is Login");
								break;
							}
							
						}
						mainFrame.getHome().repaint();
						break;
					case Protocol.CLIENT_LOGOUT:
						buffer = dataInputStream.readUTF();
						getUser(buffer).setConnectionState(false);
						mainFrame.getHome().repaint();
						break;
					case Protocol.CHAT_ROOM_RESPONSE:
						int roomId = dataInputStream.readInt();
						String member = dataInputStream.readUTF();
						ChatRoom chatRoom = new ChatRoom(member, roomId);
						debug.Debug.log(id + " ReceiveThread  Get : CHAT_ROOM_RESPONSE\nmember : " + member + " roomId : " + roomId);
						mainFrame.getHome().getFriendsPanel().setChatRoom(member, chatRoom);
						mainFrame.addRoom(chatRoom);
						break;
					case Protocol.MSG_RELAY:
						buffer = dataInputStream.readUTF();
						debug.Debug.log(id + " ReceiveThread  Get : MSG_RELAY   buffer : " + buffer);
						break;
					case Protocol.CONVERSATION_RESPONSE:
						debug.Debug.log("Get Conversation_Response!!!!!");
						Vector<ChatRoom> rooms = (Vector<ChatRoom>) objectInputStream.readObject();
						debug.Debug.log(rooms.toString() + "  rooms.size() : " + rooms.size());
						for(int i=0; i<rooms.size(); i++) {
							debug.Debug.log("member : " + rooms.get(i).getNames() + "  roomId : " + rooms.get(i).getRoomId());
							debug.Debug.log(rooms.get(i).getChatMessages().toString());
						}
						break;
					}
				}
			} else if (dataInputStream.readInt() == Protocol.LOGIN_FAIL) {
				debug.Debug.log("Login Fail");
				close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			socket.close();
			dataInputStream.close();
			dataOutputStream.close();
		} catch (IOException e) {
			debug.Debug.log("Close Error");
		}
		interrupt();
	}

	public UserInfo getUser(String id) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId().equals(id))
				return users.get(i);
		}
		return null;
	}

	public void writeInt(int i) {
		try {
			dataOutputStream.writeInt(i);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void writeUTF(String msg) {
		try {
			dataOutputStream.writeUTF(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void conversationListRequest() {
		try {
			dataOutputStream.writeInt(Protocol.CONVERSATION_REQUEST);
			dataOutputStream.writeUTF(id);
			debug.Debug.log("conversation request   id : " + id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public Vector<ChatRoom> getConversation() {
//		Vector<ChatRoom> rooms = null;
//		try {
//			rooms = (Vector<ChatRoom>) objectInputStream.readObject();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return rooms;
//	}

}

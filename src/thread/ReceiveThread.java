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
import home.BoardPanel;
import home.ChatRoomListPanel;
import home.FriendsListBoardPanel;
import home.SmallMessageFrame;

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
	private SmallMessageFrame smallMessageFrame = null;

	private String buffer;
	private Vector<ChatRoom> rooms;

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
			rooms = (Vector<ChatRoom>) objectInputStream.readObject();

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

				if (rooms != null) {
					for (int i = 0; i < rooms.size(); i++) {
						String member = rooms.get(i).getNames();
						debug.Debug.log(rooms.get(i).getNames());
						mainFrame.getHome().getFriendsPanel().setFirstChatRoom(member, rooms.get(i));
					}
				}

				while (true) {
					switch (dataInputStream.readInt()) {
					case Protocol.CLIENT_LOGIN:
						buffer = dataInputStream.readUTF();
						InetAddress address = (InetAddress) objectInputStream.readObject();
						System.out.println(buffer + " " + address + " Client Login");
						for (int i = 0; i < users.size(); i++) {
							if (users.get(i).getId().equals(buffer)) {
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
						debug.Debug.log(id + " ReceiveThread  Get : CHAT_ROOM_RESPONSE\nmember : " + member
								+ " roomId : " + roomId);
						mainFrame.getHome().getFriendsPanel().setChatRoom(member, chatRoom);
						// mainFrame.addRoom(chatRoom);
						break;
					case Protocol.MSG_RELAY:
						buffer = dataInputStream.readUTF();
						debug.Debug.log(id + " ReceiveThread  Get : MSG RELAY buffer : " + buffer);
						String buffers[] = buffer.split("::::");

//						ChatRoomListPanel a = mainFrame.getHome().getChatRoomsPanel()
//								.getChatRoomListPanel(Integer.parseInt(buffers[0]));
//						if (a != null) {
//							a.getChatRoom().getChatMessages().add(buffer);
//							a.repaint();
//						}
						//윤재
						ChatRoomListPanel a = mainFrame.getHome().getChatRoomsPanel()
								.getChatRoomListPanel(Integer.parseInt(buffers[0]));
//						if(mainFrame.getChatRoom(Integer.parseInt(buffers[0])) != null)
//							mainFrame.getChatRoom(Integer.parseInt(buffers[0])).getChatMessages().add(buffer);
//						
//						if(rooms != null) {
//							for(int i=0; i<rooms.size(); i++) {
//								if(rooms.get(i).getRoomId() == Integer.parseInt(buffers[0]))
//									if(rooms.get(i).getChatMessages() != null)
//										rooms.get(i).getChatMessages().add(buffer);
//							}
//						}
//						
						if (a != null) {
							a.getChatRoom().getChatMessages().add(buffer);
							mainFrame.getHome().getChatRoomsPanel().setChatRooms();
							a.invalidate();
							a.repaint();
						}
						//

						if (buffers[1].equals(mainFrame.getUser().getId())) {
							String space = "";
							for (int i = 0; i < 160; i++) {
								space += " ";
							}
							space += buffers[3];
							mainFrame.getHome().getFriendsListBoardPanel().getTextArea().append(space + "\n");
						} else if (!(mainFrame.getHome().getBoard() instanceof FriendsListBoardPanel)) {
							if (smallMessageFrame != null) {
								smallMessageFrame.threadInterrupt();
								smallMessageFrame.dispose();
							}
							smallMessageFrame = new SmallMessageFrame(mainFrame, buffer, this);
							//debug.Debug.log("getFriendsListBoardPanel = null buffer = " + buffer);
						} else if (mainFrame.getHome().getFriendsListBoardPanel().getF().getId().equals(buffers[1])) {
							mainFrame.getHome().getFriendsListBoardPanel().getTextArea()
									.append(buffers[1] + ": " + buffers[3] + "\n");
						} else {
							smallMessageFrame = new SmallMessageFrame(mainFrame, buffer, this);
						}
						//debug.Debug.log("if문 밖 yunjae MSG_RELAY roomId = " + buffers[0]);
						// System.out.println("yunjae rooms size = " +
						// rooms.size());

						if (rooms != null) {

							System.out.println("yunjae rooms size = " + rooms.size());
							for (int i = 0; i < rooms.size(); i++) {
								if (Integer.toString(rooms.get(i).getRoomId()).equals(buffers[0])) {
									// 윤재
									rooms.get(i).getChatMessages().add(buffer);
									//debug.Debug.log("yunjae MSG_RELAY buffer = " + buffers);
									//debug.Debug.log(
											//"yunjae MSG_RELAY message : " + rooms.get(i).getChatMessages().toString());
								}
							}
						}
						//debug.Debug.log("yun " + id + " ReceiveThread  Get : MSG_RELAY   buffer : " + buffer);

						break;
					case Protocol.CONVERSATION_RESPONSE: // 대화 클릭할때 서버에서 메세지를
															// 보내줌
						debug.Debug.log("Get Conversation_Response!!!!!");
						this.rooms = (Vector<ChatRoom>) objectInputStream.readObject();
						mainFrame.setChatRooms(rooms);
						for (int i = 0; i < rooms.size(); i++) {
							debug.Debug.log("yun : " + rooms.get(i).getChatMessages().toString());
						}
						// if (rooms != null) {
						// for (int i = 0; i < rooms.size(); i++) {
						// String member2 = rooms.get(i).getNames();
						// debug.Debug.log(rooms.get(i).getNames());
						// mainFrame.getHome().getFriendsPanel().setFirstChatRoom(member2,
						// rooms.get(i));
						// }
						// }
						// 윤재
						// mainFrame.setChatRooms(rooms);
						// mainFrame.get;
						mainFrame.getHome().getChatRoomsPanel().setChatRoom(rooms);
						//
						//debug.Debug.log(rooms.toString() + "  rooms.size() : " + rooms.size());

						for (int i = 0; i < rooms.size(); i++) {
							//debug.Debug.log(
							//		"member : " + rooms.get(i).getNames() + "  roomId : " + rooms.get(i).getRoomId());
							//debug.Debug.log("yun : " + rooms.get(i).getChatMessages().toString());
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

	public void setSmallMessageFrame() {
		smallMessageFrame = null;
	}

	// public Vector<ChatRoom> getConversation() {
	// Vector<ChatRoom> rooms = null;
	// try {
	// rooms = (Vector<ChatRoom>) objectInputStream.readObject();
	// } catch (ClassNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return rooms;
	// }

}

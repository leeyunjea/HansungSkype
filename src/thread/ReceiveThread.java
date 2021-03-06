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

import javax.swing.JPanel;
import javax.swing.plaf.multi.MultiFileChooserUI;

import audio.AudioReceiver;
import audio.AudioServer;
import chat.ChatRoom;
import main.MainFrame;
import protocol.Protocol;
import sns.SNS;
import database.UserInfo;
import home.BoardPanel;
import home.ChatRoomListPanel;
import home.FriendsListBoardPanel;
import home.FriendsListPanel;
import home.MultiChatBoardPanel;
import home.SmallMessageFrame;
import home.VoiceReceiveFrame;

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
	private Vector<SNS> snss;

	private VoiceReceiveFrame voiceReceiveFrame;

	private Vector<AudioReceiver> audioReceivers;
	private AudioReceiver audioReceiver;
	private AudioServer audioServer;

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
			audioReceivers = new Vector<AudioReceiver>();
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

				// if (rooms != null) {
				// for (int i = 0; i < rooms.size(); i++) {
				// String member = rooms.get(i).getNames();
				// debug.Debug.log(rooms.get(i).getNames());
				// mainFrame.getHome().getFriendsPanel().setFirstChatRoom(member,
				// rooms.get(i));
				// }
				// }

				while (true) {
					switch (dataInputStream.readInt()) {
					case 0:
						this.rooms = (Vector<ChatRoom>) objectInputStream.readObject();
						for (int i = 0; i < rooms.size(); i++) {
							String names = rooms.get(i).getNames();
							System.out.println(rooms.get(i).getNames());
							System.out.println(rooms.get(i).getChatMessages().toString());
							mainFrame.getHome().getFriendsPanel().setChatRoom(names, rooms.get(i));
						}

						mainFrame.setChatRooms(rooms);
						mainFrame.getHome().getChatRoomsPanel().setChatRoom(rooms);
						break;
					case 1004:
						snss = (Vector<SNS>) objectInputStream.readObject();
						if (snss != null) {
							mainFrame.setListSNS(snss);
							mainFrame.getHome().getBoardPanel().setListSNS(snss);
							debug.Debug.log("1004 snss size = " + snss.size());
							for (int i = 0; i < snss.size(); i++) {
								System.out.println("윤재 : snss writer = " + snss.get(i).getWriter() + "msg = "
										+ snss.get(i).getMsg());
							}
						}
						break;
					case Protocol.CLIENT_LOGIN:
						buffer = dataInputStream.readUTF();
						InetAddress address = (InetAddress) objectInputStream.readObject();
						System.out.println(buffer + " " + address + " Client Login");
						for (int i = 0; i < users.size(); i++) {
							if (users.get(i).getId().equals(buffer)) {
								users.get(i).setConnectionState(true);
								users.get(i).setIp(address);
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
						String members[] = member.split(",");
						if (members.length == 2)
							mainFrame.getHome().getFriendsPanel().setChatRoom(member, chatRoom);
						// mainFrame.addRoom(chatRoom);
						break;
					case Protocol.MSG_RELAY:
						buffer = dataInputStream.readUTF();
						debug.Debug.log(id + " ReceiveThread  Get : MSG RELAY buffer : " + buffer);
						String buffers[] = buffer.split("::::");
						JPanel b = mainFrame.getHome().getChatRoomsPanel()
								.getChatRoomListPanel(Integer.parseInt(buffers[0]));

						if (b instanceof ChatRoomListPanel && b != null) {
							ChatRoomListPanel a = (ChatRoomListPanel) b;
							a.getChatRoom().getChatMessages().add(buffer);
							mainFrame.getHome().getChatRoomsPanel().setChatRooms();
							a.invalidate();
							a.repaint();
						} else if (b instanceof MultiChatBoardPanel && b != null) {
							MultiChatBoardPanel a = (MultiChatBoardPanel) b;
							a.getChatRoom().getChatMessages().add(buffer);
							mainFrame.getHome().getChatRoomsPanel().setChatRooms();
							a.invalidate();
							a.repaint();
						}
						if (buffers[1].equals(mainFrame.getUser().getId())) {
							String space = "";
							for (int i = 0; i < 160; i++) {
								space += " ";
							}
							space += buffers[3];
							if (mainFrame.getHome().getFriendsListBoardPanel() != null)
								mainFrame.getHome().getFriendsListBoardPanel().getTextArea().append(space + "\n");
							else if (mainFrame.getHome().getMultiChatBoardPanel() != null) {
								// mainFrame.getHome().setBoard(new
								// MultiChatBoardPanel(mainFrame, buffers[2]
								// ,mainFrame.getChatRoom(buffers[2])));
								mainFrame.getHome().getMultiChatBoardPanel().getTextArea().append(space + "\n");
							}
						}

						else if (mainFrame.getHome().getBoard() instanceof MultiChatBoardPanel
								&& mainFrame.getHome().getMultiChatBoardPanel() != null) {
							mainFrame.getHome().getMultiChatBoardPanel().getTextArea()
									.append(buffers[1] + ": " + buffers[3] + "\n");
						}

						else if (!(mainFrame.getHome().getBoard() instanceof FriendsListBoardPanel)) {
							if (smallMessageFrame != null) {
								smallMessageFrame.threadInterrupt();
								smallMessageFrame.dispose();
							}
							smallMessageFrame = new SmallMessageFrame(mainFrame, buffer, this);
						} else if (mainFrame.getHome().getFriendsListBoardPanel().getF().getId().equals(buffers[3])) {
							mainFrame.getHome().getFriendsListBoardPanel().getTextArea()
									.append(buffers[1] + ": " + buffers[3] + "\n");
						}

						else {
							smallMessageFrame = new SmallMessageFrame(mainFrame, buffer, this);
						}

						if (rooms != null) {

							System.out.println("yunjae rooms size = " + rooms.size());
							for (int i = 0; i < rooms.size(); i++) {
								if (Integer.toString(rooms.get(i).getRoomId()).equals(buffers[0])) {
									// 윤재
									rooms.get(i).getChatMessages().add(buffer);
									// System.out.println("버퍼에 추가함");
								}
							}
						}
						break;
					case Protocol.CONVERSATION_RESPONSE: // 대화 클릭할때 서버에서 메세지를
															// 보내줌
						// debug.Debug.log("Get Conversation_Response!!!!!");
						this.rooms = (Vector<ChatRoom>) objectInputStream.readObject();
						mainFrame.setChatRooms(this.rooms);
						for (int i = 0; i < rooms.size(); i++) {
							// debug.Debug.log("yun : " +
							// rooms.get(i).getChatMessages().toString());
							String names = rooms.get(i).getNames();
							String tmp[] = names.split(",");
							if (tmp.length == 2)
								mainFrame.getHome().getFriendsPanel().setChatRoom(names, rooms.get(i));
						}
						mainFrame.getHome().getChatRoomsPanel().setChatRoom(rooms);
						Vector<ChatRoomListPanel> v = mainFrame.getHome().getChatRoomsPanel().getChatRoomListPanels();
						for (int i = 0; i < v.size(); i++) {
							for (int j = 0; j < rooms.size(); j++) {
								if (v.get(i).getRoomId() == rooms.get(j).getRoomId()) {
									v.get(i).setChatRoom(rooms.get(j));
								}
							}
						}
						break;
					case Protocol.MSG_ADD_USER_RESPONSE:
						buffer = dataInputStream.readUTF();
						buffers = buffer.split("::::");
						String oldName = buffers[0];
						String newName = buffers[1];
						if (mainFrame.getHome().getBoard() instanceof MultiChatBoardPanel)
							((MultiChatBoardPanel) mainFrame.getHome().getBoard()).setNames(newName);
						System.out.println("Receive   GET MSG_ADD_USER_RESPONSE    oldName : " + oldName
								+ "   newName : " + newName);
						if (mainFrame.getHome().getChatRoomsPanel().getChatRoomListPanel(oldName) != null)
							((ChatRoomListPanel) mainFrame.getHome().getChatRoomsPanel().getChatRoomListPanel(oldName))
									.setNames(newName);
						Vector<ChatRoomListPanel> vv = mainFrame.getHome().getChatRoomsPanel().getChatRoomListPanels();
						for (int i = 0; i < vv.size(); i++) {
							for (int j = 0; j < rooms.size(); j++) {
								if (vv.get(i).getChatRoom().getNames().equals(oldName)) {
									vv.get(i).setNames(newName);
									vv.get(i).getChatRoom().setNames(newName);
								}
							}
						}
						dataOutputStream.writeInt(Protocol.CONVERSATION_REQUEST);
						dataOutputStream.writeUTF(id);
						break;
					case Protocol.CALL_RESPONSE:
						int readPort = dataInputStream.readInt();
						int writePort = dataInputStream.readInt();
						InetAddress partnerAddress = (InetAddress) objectInputStream.readObject();
						debug.Debug.log("ReceiveThread  Get : CALL_RESPONSE   writePort : " + writePort
								+ "  readPort : " + readPort + "   address : " + partnerAddress.getHostAddress());
						audioReceiver = new AudioReceiver(readPort);
						audioReceivers.add(audioReceiver);
						audioServer = new AudioServer(partnerAddress, writePort);
						audioReceiver.start();
						audioServer.start();
						break;
					case Protocol.CALLING:
						String partnerId = dataInputStream.readUTF();
						UserInfo partner = getUser(partnerId);
						voiceReceiveFrame = new VoiceReceiveFrame(user, partner, this, false);
						break;
					case Protocol.SNS_RESPONSE:
						int check = dataInputStream.readInt();
						debug.Debug.log("SNS_RESPONSE check = " + check);
						Vector<SNS> s = (Vector<SNS>) objectInputStream.readObject();
						debug.Debug.log("SNS_RESPONSE s size! = " + s.size() + "  s.toStrig" + s.toString());
						mainFrame.setListSNS(s);
						mainFrame.getHome().getBoardPanel().setListSNS(s);
						for (int i = 0; i < s.size(); i++) {
							System.out.println("SNS_RESPONSE 윤재 : s writer = " + s.get(i).getWriter() + "msg = "
									+ s.get(i).getMsg());
						}
						break;
					case Protocol.CALL_DISCONNECT:
						debug.Debug.log("GET DISCONNECT");
						InetAddress disAddress = (InetAddress) objectInputStream.readObject();
						int disPort = dataInputStream.readInt();
						for(int i=0; i<audioReceivers.size(); i++) {
							if(audioReceivers.get(i).getPort() == disPort) {
								audioReceivers.get(i).remove();
								audioReceivers.remove(i);
							}
						}
						audioServer.removeUser(disAddress);
						break;
					case Protocol.CALL_END:
						debug.Debug.log("GET CALL_END");
						for(int i=0; i<audioReceivers.size(); i++) {
							audioReceivers.get(i).remove();
							audioReceivers.remove(i);
						}
						audioServer.remove();
						break;
					case Protocol.CALL_ADD:
						int addUserPort = dataInputStream.readInt();
						InetAddress addUserAddress = (InetAddress) objectInputStream.readObject();
						audioReceiver = new AudioReceiver(addUserPort);
						audioReceivers.add(audioReceiver);
						audioReceiver.start();
						audioServer.addUser(addUserAddress);
						break;
					case Protocol.CALL_ADD_RESPONSE:
						int port = dataInputStream.readInt();
						int count = dataInputStream.readInt();
						int start = 9001;
						for (int i = 0; i < count - 1; i++) {
							if (i == 0) {
								InetAddress userAddress = (InetAddress) objectInputStream.readObject();
								audioServer = new AudioServer(userAddress, port);
							} else {
								InetAddress userAddress = (InetAddress) objectInputStream.readObject();
								audioServer.addUser(userAddress);
							}
						}
						for (int i = 0; i < count - 1; i++) {
							audioReceiver = new AudioReceiver(start);
							audioReceivers.add(audioReceiver);
							audioReceiver.start();
							start += 1;
						}
						audioServer.start();
						break;
					}
				}
			} else if (dataInputStream.readInt() == Protocol.LOGIN_FAIL) {
				debug.Debug.log("Login Fail");
				close();
			}
		} catch (

		IOException e) {
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

	public Vector<SNS> getListSNS() {
		return snss;
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

	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	public DataOutputStream getDataOutputStream() {
		return dataOutputStream;
	}

	public UserInfo getUser(InetAddress address) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getIp().equals(address))
				return users.get(i);
		}
		return null;
	}

	public boolean isAudioServer() {
		if (audioServer == null)
			return false;

		return true;
	}

	public boolean isAudioReceiver() {
		if (audioReceiver == null)
			return false;

		return true;
	}

	public AudioServer getAudioServer() {
		return audioServer;
	}
	
	public Vector<AudioReceiver> getAudioReceivers() {
		return audioReceivers;
	}
	
	public void audioClose() {
		audioServer.remove();
		for(int i=0; i<audioReceivers.size(); i++) {
			audioReceivers.get(i).remove();
		}
		audioServer = null;
	}
}

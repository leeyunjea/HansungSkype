package thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

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

				user = (UserInfo)objectInputStream.readObject();
				users = (Vector<UserInfo>)objectInputStream.readObject();

				mainFrame.createUser(user);
				mainFrame.setUsers(users);
				mainFrame.getLogin().loginSuccess(mainFrame.getUser());
				
				while(true) {
					switch(dataInputStream.readInt()) {
					case Protocol.CLIENT_LOGIN:
						buffer = dataInputStream.readUTF();
						InetAddress address = (InetAddress) objectInputStream.readObject();
						UserInfo connectClient = getUser(buffer);
						connectClient.setConnectionState(true);
						connectClient.setIp(address);
						mainFrame.getHome().repaint();
						break;
					case Protocol.CLIENT_LOGOUT:
						buffer = dataInputStream.readUTF();
						getUser(buffer).setConnectionState(false);
						mainFrame.getHome().repaint();
						break;
					}
				}
			} else if (dataInputStream.readInt() == Protocol.LOGIN_FAIL) {
				debug.Debug.log("Login Fail");
				close();
			}
		} catch (IOException e) {
			debug.Debug.log("Login Fail");
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
		for(int i=0; i<users.size(); i++) {
			if(users.get(i).getId().equals(id))
				return users.get(i);
		}
		return null;
	}

}

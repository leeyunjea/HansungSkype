package thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import main.MainFrame;
import protocol.Protocol;

public class ReceiveThread extends Thread {
	
	private String id;
	private String pw;
	private String name;
	private String stateMsg;
	private String image;
	private String stringArr[];
	private String msg;
	
	private Socket socket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	
	private MainFrame mainFrame;
	
	public ReceiveThread(String id, String pw, MainFrame mainFrame) {
		this.id = id;
		this.pw = pw;
		this.mainFrame = mainFrame;
	}
	
	public void initServer() {
		try {
			socket = new Socket(MainFrame.SERVER_IP, MainFrame.SERVER_PORT);
			dataInputStream = new DataInputStream(socket.getInputStream());
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
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
			if(dataInputStream.readInt() == Protocol.LOGIN_SUCCESS) {
				debug.Debug.log("Login Success");
				msg = dataInputStream.readUTF();
				stringArr = msg.split(",");
				name = stringArr[0];
				stateMsg = stringArr[1];
				image = stringArr[2];
				mainFrame.createUser(id, pw, name, stateMsg, image);
				mainFrame.getLogin().loginSuccess(mainFrame.getUser());
			}
			else if(dataInputStream.readInt() == Protocol.LOGIN_FAIL) {
				debug.Debug.log("Login Fail");
				close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	

}

package user;

import java.util.Vector;

public class UserInfo {
	
	private String name;
	private String stateMessage;
	private String image;
	private String id;
	private String pw;
	
	public UserInfo(String name, String stateMessage, String image, String id, String pw) {
		super();
		this.name = name;
		this.stateMessage = stateMessage;
		this.image = image;
		this.id = id;
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStateMessage() {
		return stateMessage;
	}

	public void setStateMessage(String stateMessage) {
		this.stateMessage = stateMessage;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}
	

}


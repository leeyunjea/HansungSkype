package sns;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SNS  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8035835495347193008L;
	private String writer; //작성자
	private String msg; //내용
	private String date;
	
	public SNS() {	}
	
	public SNS(String writer, String msg) {
		this.writer = writer;
		this.msg = msg;
	}
	
	
	public String getWriter() {
		return writer;
	}


	public String getMsg() {
		return msg;
	}


	public String getDate() {
		return date;
	}


}

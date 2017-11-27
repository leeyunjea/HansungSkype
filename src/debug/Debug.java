package debug;

public class Debug {
	
	static public void errorHandler(Exception e) {
		System.out.println(e.getMessage());
	}
	
	static public void log(String msg) {
		System.out.println(msg);
	}

}

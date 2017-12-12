package protocol;

public class Protocol {
	
	public static final int LOGIN_REQUEST = 1; // 로그인 요청
	public static final int LOGIN_SUCCESS = 2; // 로그인 성공
	public static final int LOGIN_FAIL = 3; // 로그인 실패
	public static final int CLIENT_LOGIN = 4; // 다른 클라이언트 로그인
	public static final int CLIENT_LOGOUT = 5; // 다른 클라이언트 로그아웃
	public static final int MSG_REQUEST = 6; // 메세지 요청
	public static final int MSG_RELAY = 7; // 서버에서 목적지로 메세지 전달
	public static final int CHAT_ROOM_REQUEST = 8; // 서버에서 목적지로 메세지 전달
	public static final int CHAT_ROOM_RESPONSE = 9; // 서버에서 목적지로 메세지 전달
	public static final int MSG_ADD_USER_REQUEST = 10; // 서버에서 목적지로 메세지 전달
	public static final int MSG_ADD_USER_RESPONSE = 11; // 서버에서 목적지로 메세지 전달
	public static final int CONVERSATION_REQUEST = 12; // 서버에서 목적지로 메세지 전달
	public static final int CONVERSATION_RESPONSE = 13; // 서버에서 목적지로 메세지 전달
	public static final int CONVERSATION = 14; // 목적지에서 서버로 메세지 수신 완료
	public static final int CALL_REQUEST = 15; // 전화 요청
	public static final int CALL_RESPONSE = 16;
	public static final int CALL_ADD_REQUEST = 17;
	public static final int CALL_DISCONNECT = 18;
	public static final int SNS_REQUEST = 19;
	public static final int SNS_RESPONSE = 20;
	public static final int CALLING = 21;
	public static final int CALLING_OK = 22;
	public static final int CALL_ADD_RESPONSE = 23;
	public static final int CALL_ADD = 24;
	
//	public static final int STATE_REQUEST = 8; // 상태메세지 변경 요청
//	public static final int STATE_RESPONSE = 9; // 상태메세지 변경 응답
//	public static final int STATE_FAIL = 10; // 상태메세지 변경 실패
//	public static final int STATE_SEND = 11; // 상태메세지 변경 전송
//	public static final int STATE_SUCCESS = 12; // 상태메세지 변경 전송
//	public static final int SNS_REQUEST = 13; // SNS 글 요청
//	public static final int SNS_SUCCESS = 14; // SNS 글 성공
//	public static final int SNS_FAIL = 15; // SNS 글 실패
//	public static final int CALL_REQUEST = 16; // 전화 요청
//	public static final int CALL_INFO_SEND = 17; // 상대방 정보 수신
//	public static final int CALL_SIGNAL = 18; // 상대방에게 전화 요청
//	public static final int CALL_RECEIVE = 19; // 전화 요청 수락
//	public static final int CALL_DENY = 20; // 전화 요청 거절
//	public static final int CALL_CONNECT_INFO = 21; // 연결 완료(서버로 전화 연결 정보 전달)
//	public static final int CALL_ADD_USER_REQUEST = 22; // 전화 사용자 추가 요청
//	public static final int CALL_SESSION_ADD_INFO = 23; // 사용자 추가시 세션 정보 전달
//	public static final int CALL_SUCCESS_ADD_INFO = 24; // 사용자 추가 정보 확인
	
}

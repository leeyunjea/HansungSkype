package protocol;

public class Protocol {
	
	public static final int LOGIN_REQUEST = 1; // �α��� ��û
	public static final int LOGIN_SUCCESS = 2; // �α��� ����
	public static final int LOGIN_FAIL = 3; // �α��� ����
	public static final int CLIENT_LOGIN = 4; // �ٸ� Ŭ���̾�Ʈ �α���
	public static final int CLIENT_LOGOUT = 5; // �ٸ� Ŭ���̾�Ʈ �α׾ƿ�
	public static final int MSG_REQUEST = 6; // �޼��� ��û
	public static final int MSG_RELAY = 7; // �������� �������� �޼��� ����
	public static final int CHAT_ROOM_REQUEST = 8; // �������� �������� �޼��� ����
	public static final int CHAT_ROOM_RESPONSE = 9; // �������� �������� �޼��� ����
	public static final int MSG_ADD_USER_REQUEST = 10; // �������� �������� �޼��� ����
	public static final int MSG_ADD_USER_RESPONSE = 11; // �������� �������� �޼��� ����
	public static final int CONVERSATION_REQUEST = 12; // �������� �������� �޼��� ����
	public static final int CONVERSATION_RESPONSE = 13; // �������� �������� �޼��� ����
	public static final int CONVERSATION = 14; // ���������� ������ �޼��� ���� �Ϸ�4
//	public static final int STATE_REQUEST = 8; // ���¸޼��� ���� ��û
//	public static final int STATE_RESPONSE = 9; // ���¸޼��� ���� ����
//	public static final int STATE_FAIL = 10; // ���¸޼��� ���� ����
//	public static final int STATE_SEND = 11; // ���¸޼��� ���� ����
//	public static final int STATE_SUCCESS = 12; // ���¸޼��� ���� ����
//	public static final int SNS_REQUEST = 13; // SNS �� ��û
//	public static final int SNS_SUCCESS = 14; // SNS �� ����
//	public static final int SNS_FAIL = 15; // SNS �� ����
//	public static final int CALL_REQUEST = 16; // ��ȭ ��û
//	public static final int CALL_INFO_SEND = 17; // ���� ���� ����
//	public static final int CALL_SIGNAL = 18; // ���濡�� ��ȭ ��û
//	public static final int CALL_RECEIVE = 19; // ��ȭ ��û ����
//	public static final int CALL_DENY = 20; // ��ȭ ��û ����
//	public static final int CALL_CONNECT_INFO = 21; // ���� �Ϸ�(������ ��ȭ ���� ���� ����)
//	public static final int CALL_ADD_USER_REQUEST = 22; // ��ȭ ����� �߰� ��û
//	public static final int CALL_SESSION_ADD_INFO = 23; // ����� �߰��� ���� ���� ����
//	public static final int CALL_SUCCESS_ADD_INFO = 24; // ����� �߰� ���� Ȯ��
	
}

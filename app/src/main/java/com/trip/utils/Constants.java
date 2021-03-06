package com.trip.utils;

public class Constants {

	public static final String STR_OPCODE="opcode";
	//	Opcodes
	public static final int I_OPCODE_REGISTER=1;
	public static final int I_OPCODE_CHECK_USER=2;
	public static final int I_OPCODE_LOGIN = 3;
	public static final int I_OPCODE_GOOGLE = 9;
	public static final int I_OPCODE_ADD_TRIP = 4;
	public static final int I_OPCODE_ADD_EXPENSE = 5;
	public static final int I_OPCODE_ADD_TRIP_QR = 6;
	public static final int I_OPCODE_UPDATE_EXPENSE = 7;
	public static final int I_OPCODE_SHOW_USER_EXPENSES = 8;
	//	End
	public static final String STR_USER = "user";
	public static final String STR_STATUS_EXISTS_OPCODE = "01";
	public static final String STR_STATUS_DO_NOT_EXIST_OPCODE = "00";
	public static final String STR_STATUS = "status";
	public static final String STR_STATUS_OK = "ok";
	public static final String STR_STATUS_ERROR = "error";
	public static final String STR_PASSWORD = "password";
	public static final String STR_USER_ID = "userID";
	public static final String STR_REQUEST = "request";
	public static final String STR_DATA = "data";
	public static final String STR_RESULT = "result";
	public static final String STR_ERROR = "error";
	public static final String DATABASE = "expense.db";
	public static final String STR_SUCCESS = "success";
	public static final String STR_INVALIDATE ="invalidate";
	public static final String STR_FAILURE ="failure";
	public static final String STR_AUTH ="auth";
	public static final String USER_ID = "userid";
	public static final String STR_TRIP_NAME = "trip_name";
	public static final String STR_DATE = "date";
	public static final String STR_TRIP_ID = "trip_id";
	public static final String STR_ADMIN_ID = "admin_id";
	public static final String STR_USERS = "users";
	public static final String STR_EXPENSE_NAME = "expense_name";
	public static final String STR_EXPENSE_DETAIL = "expense_detail";
	public static final String STR_EXPENSE_AMOUNT = "expense_amount";
	public static final String STR_EXPENSE_ID = "expense_id";
	public static final String STR_ADD_PEOPLE_TAG = "add_people";
	public static final String STR_DETAIL_TO_DISPLAY = "detail to display";
	public static final int I_NOTIFICATION_ID = 1001;
	public static final int I_NOTIFICATION_ID_NE = 1002;
	public static final String STR_TRIP_POSITION = "STR_TRIP_POSITION";
	public static final String STR_SYNCHED = "S";
	public static final String STR_NOT_SYNCHED = "N";
	public static final String STR_UPDATED = "U";
	public static final String STR_OPEN = "O";
	public static final String STR_CLOSED = "C";
	public static final String STR_QR_ADDED = "Q";
	public static final String STR_DELETED = "D";
	public static final String STR_EXITED = "E";
	public static final String STR_EXPENSE_DETAIL_ARR = "expense_detail_array";
	public static final String STR_POSITION="position";
	public static final String STR_DELETE_EXPENSE = "delete_expense";
	public static final String STR_EXP_USR_ID = "expense_userid";
	public static final String STR_TRIP = "trip";
	public static final String STR_TRIP_UPDATED = "TU";
	public static final String STR_USER_ADDED = "UA";
	public static final String STR_TRIP_DELETED = "TD";
	public static final String STR_SHOW_TAB = "show_tab";
	public static final String STR_EXPENSE_ADDED = "EA";
	public static final String STR_EXPENSE_UPDATED = "EU";
	public static final String STR_EXPENSE_DELETED = "ED";
	public static final String STR_USER_DELETED = "UD";
	public static final String STR_ITEM_PURCHASED = "IP";
	public static final int NOTIFICATION_ID_TRIP = 1;
	public static final String STR_IS_FROM_GCM = "from_gcm";
	public static final String STR_GROUP = "group";
	public static final String STR_STATUS_READ = "R";
	public static final String STR_STATUS_UNREAD = "U";
	public static final String STR_PREFERENCE = "preference";
	public static final String STR_COUNT = "count";
	public static final String STR_NEW_UPDATES = " New Updates!!";
	public static final String STR_CLICK = "Click to view.";
	public static final String STR_APP_NOTIFICATION = "New notification from expense manager!!";
	public static final String STR_TRIP_ADDED = "TA";
	public static final String STR_NO = "N";
	public static final String STR_YES = "Y";
	public static final String STR_UNSYNCED = "U";
	public static final String STR_YOU = "You";
	public static final String STR_ERROR_AMT = "Amount and total doesn't match!!";
	public static final Object STR_DISTRIBUTION_ADDED = "DA";
	public static final String STR_NO_EDIT = "This expense cannot be edited as a user in this expense has exited this expense group!!";
	public static final String STR_NO_DELETE = "This expense cannot be deleted as a user in this expense has exited this expense group!!";
	public static final String STR_SETTLE_FIRST = "There are debts that you have to settle or debts others "
			+ "have to pay you. Settle them before exiting this EG!!";
	public static final String STR_NOT_EXITED = "NE";
	public static final String STR_NOT_UPDATED = "NU";
	public static final String STR_NOT_DELETED = "ND";
	public static final String STR_ERROR_STATUS = "E";
	public static final String STR_NOT_SYNCED = "NS";
	public static final String STR_VERSION = "version";
	public static final String STR_VERSION_CODE = "version code";
	
	public static final String STR_LICENSE_1 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCg";
	public static final String STR_LICENSE_2 =  "KCAQEAqDlntTQC7RNTdjDCTqaPVPbHzbceUaUyKMIGjgEqVvvyJ07RIBg4Ais9oP5F";
	public static final String STR_LICENSE_3 =  "1icXP7hsgD1XlVnzH/ybq1xHb0Pu70UJd5j8WisSRG90RSR+smSwi/mofel/WDROGZ";
	public static final String STR_LICENSE_4 =  "zXLsbultCTkFjWpjRXmOkfFV44668EPESPXTcwh/YDmBIuPNCgoOiHmkT7G6F+HIsp";
	public static final String STR_LICENSE_5 =  "uBNs6t7l9qSiuuxk1obCwkHsS8eVQTi3hvft7bt7ou4ObFA6B7dRTDbw+Gxw/QWZCK";
	public static final String STR_LICENSE_6 =  "96fOb8WJUeNe0399RV+KLcITgHhmitXwS9RdL3jdVtYA9/AEOzoY5xEr56Yp8ka8x/";
	public static final String STR_LICENSE_7 =  "lAseP9jIkSiHoYJSHQIDAQAB";
	public static final String STR_PURCHASED = "purchased";
	public static final String STR_PURCHASE_ID = "purchas_id";
	public static final String STR_SKU_PREMIUM = "466678";
	public static final String STR_TITLE = "title";
	public static final String STR_CONTENT = "content";
	public static final String STR_ACTION = "action";
	public static final String STR_LAYOUT = "layout";
	public static final String STR_TEXT = "text";
	public static final String STR_AMOUNT = "amount";
	public static final int AUTH_CODE_REQUEST_CODE = 1000;
	public static final String STR_ERR_NO_ACCTS = "Seems like you don't have an account associated with this "
			+ "phone. Please add an account and continue.";
	public static final String STR_ERR_FETCH_ACCT = "Seems like we are unable to create an account for you."
			+ ":(\nPlease try again after some time.";

	public static final int REQUEST_CODE_PICK_ACCOUNT = 1000;
	public static final int REQUEST_CODE_RECOVER_FROM_AUTH_ERROR = 1001;
	public static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1002;
	public static final String STR_SYNC_NEEDED = "sync";
	public static final String STR_NOT_PURCHASED = "not_purchased";
	public static final String STR_LOG_OUT = "LO";
	public static final String STR_NEEDS_SYNC = "need_sync";
	public static final String STR_SYNC_PROBLEM = "The sync was incomplete. Seems "
			+ "like there is a problem with your internet connection. Please fix "
			+ "it and swipe down to try again.";
	public static final String STR_LOGIN_DIFF_DEV = "Seems like you are trying to log in using another device"
			+ "with the same email id. If you do so your other device will be logged out. In order to avoid "
			+ "that please upgrade and support this application.\n";

	public static final int ORDER_ID = 1001;
	
}

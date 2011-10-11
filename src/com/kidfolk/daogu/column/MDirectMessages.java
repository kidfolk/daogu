package com.kidfolk.daogu.column;

import android.provider.BaseColumns;

public class MDirectMessages implements BaseColumns {
	public static final String ID = "id";// 私信ID
	public static final String TEXT = "text";// 私信内容
	public static final String SENDER_ID = "sender_id";// 发送人UID
	public static final String RECIPIENT_ID = "recipient_id";// 接受人UID
	public static final String CREATED_AT = "created_at";// 发送时间
//	public static final String SENDER_SCREEN_NAME = "sender_screen_name";// 发送人昵称
//	public static final String RECIPIENT_SCREEN_NAME = "recipient_screen_name";// 接受人昵称
//	public static final String SENDER = "sender";// 发送人信息，参考user说明
//	public static final String RECIPIENT = "recipient";// 接受人信息，参考user说明

}

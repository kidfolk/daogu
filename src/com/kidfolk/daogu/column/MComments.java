package com.kidfolk.daogu.column;

import android.provider.BaseColumns;

public class MComments implements BaseColumns {

	public static final String ID = "id";// 评论ID
	public static final String TEXT = "text";// 评论内容
	public static final String SOURCE = "source";// 评论来源
	public static final String FAVORITED = "favorited";// 是否收藏
	public static final String TRUNCATED = "truncated";// 是否被截断
	public static final String CREATED_AT = "created_at";// 评论时间
	public static final String USER = "user";// 评论人信息,结构参考user
	public static final String STATUS = "status";// 评论的微博,结构参考status
	public static final String REPLY_COMMENT = "reply_comment";// 评论来源，数据结构跟comment一致
}

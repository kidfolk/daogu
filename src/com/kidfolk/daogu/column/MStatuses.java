package com.kidfolk.daogu.column;

import android.provider.BaseColumns;

public class MStatuses implements BaseColumns {
//	created_at: 创建时间
//	id: 微博ID
//	text：微博信息内容
//	source: 微博来源
//	favorited: 是否已收藏(正在开发中，暂不支持)
//	truncated: 是否被截断
//	in_reply_to_status_id: 回复ID
//	in_reply_to_user_id: 回复人UID
//	in_reply_to_screen_name: 回复人昵称
//	thumbnail_pic: 缩略图
//	bmiddle_pic: 中型图片
//	original_pic：原始图片
//	user: 作者信息
//	retweeted_status: 转发的博文，内容为status，如果不是转发，则没有此字段
	public static final String CREATED_AT = "createAt";//创建时间
	public static final String ID = "id";//微博ID
	public static final String TEXT =  "text";//微博信息内容
	public static final String SOURCE =  "source";//微博来源
	public static final String IS_TRUNCATED =  "isTruncated";//是否被截断
	public static final String IN_REPLY_TO_STATUS_ID = "inReplyToStatusId";//回复ID
	public static final String IN_REPLY_TO_USER_ID = "inReplyToUserId";//回复人UID
	public static final String IS_FAVORITED = "isFavorited";//是否已收藏(正在开发中，暂不支持)
	public static final String IN_REPLY_TO_SCREENNAME = "inReplyToScreenName";//回复人昵称
//	public static final String LATITUDE = "latitude";
//	public static final String LONGITUDE = "longitude";
	public static final String THUMBNAIL_PIC = "thumbnail_pic";//缩略图
	public static final String BMIDDLE_PIC = "bmiddle_pic";//中型图片
	public static final String ORIGINAL_PIC = "original_pic";//原始图片
//	public static final String RETWEET_DETAILS = "retweetDetails";  
	public static final String RETWEETED_STATUS = "retweeted_status";//转发的博文，内容为status，如果不是转发，则没有此字段
//	public static final String MID = "mid";
	public static final String USER = "user";//作者信息

}

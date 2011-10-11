package com.kidfolk.daogu.column;

import android.provider.BaseColumns;

public class MUsers implements BaseColumns {
//	id: 用户UID
//	screen_name: 微博昵称
//	name: 友好显示名称，如Bill Gates(此特性暂不支持)
//	province: 省份编码（参考省份编码表）
//	city: 城市编码（参考城市编码表）
//	location：地址
//	description: 个人描述
//	url: 用户博客地址
//	profile_image_url: 自定义图像
//	domain: 用户个性化URL
//	gender: 性别,m--男，f--女,n--未知
//	followers_count: 粉丝数
//	friends_count: 关注数
//	statuses_count: 微博数
//	favourites_count: 收藏数
//	created_at: 创建时间
//	following: 是否已关注(此特性暂不支持)
//	verified: 加V标示，是否微博认证用户
	public static final String ID = "id";//用户UID
	public static final String SCREEN_NAME = "screen_name";//微博昵称
	public static final String NAME = "name";// 友好显示名称，如Bill Gates(此特性暂不支持)
	public static final String PROVINCE = "province";// 省份编码（参考省份编码表）
	public static final String CITY = "city";// 城市编码（参考城市编码表）
	public static final String LOCATION = "location";//地址
	public static final String DESCRIPTION = "description";//个人描述
	public static final String URL = "url";//用户博客地址
	public static final String PROFILE_IMAGE_URL = "profile_image_url";//自定义图像
	public static final String DOMAIN = "domain";//用户个性化URL
	public static final String GENDER = "gender";//性别,m--男，f--女,n--未知
	public static final String FOLLOWERS_COUNT = "followers_count";// 粉丝数
	public static final String FRIENDS_COUNT = "friends_count";//关注数
	public static final String STATUSES_COUNT = "statuses_count";//微博数
	public static final String FAVOURITES_COUNT = "favourites_count";//收藏数
	public static final String CREATED_AT = "created_at";// 创建时间
	public static final String FOLLOWING = "following";// 是否已关注(此特性暂不支持)
	public static final String VERIFIED = "verified";// 加V标示，是否微博认证用户

}

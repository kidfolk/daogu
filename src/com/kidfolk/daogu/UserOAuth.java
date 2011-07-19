package com.kidfolk.daogu;
/**
 * 用户pojo类
 * @author kidfolk
 *
 */
public class UserOAuth {
	
	private String id;//用户编号
	private String accesstoken;//
	private String accesstoken_secret;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	public String getAccesstoken_secret() {
		return accesstoken_secret;
	}
	public void setAccesstoken_secret(String accesstoken_secret) {
		this.accesstoken_secret = accesstoken_secret;
	}
	
	

}

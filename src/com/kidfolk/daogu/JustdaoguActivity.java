package com.kidfolk.daogu;

import java.util.List;

import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.http.AccessToken;
import weibo4android.http.RequestToken;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class JustdaoguActivity extends Activity {
	public Handler handler;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// TODO 欢迎动画
		setContentView(R.layout.splash);

		// 初始化
		// 设置应用的app_key和app_secret
		Weibo.CONSUMER_KEY = "858183481";
		Weibo.CONSUMER_SECRET = "bf5d084a6ac0233ec0cc3510a4943a6e";
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret",
				Weibo.CONSUMER_SECRET);

		
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				JustdaoguActivity.this.finish();
			}
		};
		
		init();
		
	}

	private void init() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// 查询user.xml,操作文件
				XMLReaderAndWriter<User> userreader = new UserReaderAndWriter(
						getApplicationContext());
				List<User> userList = userreader.reader();
				if (null != userList && userList.size() > 0) {
					// 有用户
					User user = userList.get(0);
					AccessToken accessToken = new AccessToken(user
							.getAccesstoken(), user.getAccesstoken_secret());

					OAuthConstant.getInstance().setAccessToken(accessToken);
					OAuthConstant.getInstance().setTokenSecret(
							accessToken.getTokenSecret());
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri
							.parse("daogu://WeiboListActivity"));
					startActivity(intent);
				} else {
					// 没有用户则进行认证
					oauth();
				}
				//想ui线程发送消息，请求结束activity
				Message msg = new Message();
				msg.what=0;
				JustdaoguActivity.this.handler.sendMessage(msg);
			}

		}).start();

	}

	/**
	 * 跳转到新浪微博web认证页面进行认证
	 */
	private void oauth() {
		Weibo weibo = OAuthConstant.getInstance().getWeibo();
		RequestToken requestToken = null;
		try {
			requestToken = weibo
					.getOAuthRequestToken("daogu://WeiboListActivity");// 设置认证完成后的回调界面
			Uri uri = Uri.parse(requestToken.getAuthorizationURL()
					+ "&display=mobile");
			OAuthConstant.getInstance().setRequestToken(requestToken);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);// 跳转到浏览器进行OAuth认证
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
}
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

public class JustdaoguActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Weibo.CONSUMER_KEY = "858183481";
		Weibo.CONSUMER_SECRET = "bf5d084a6ac0233ec0cc3510a4943a6e";
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret",
				Weibo.CONSUMER_SECRET);

		// 查询user.xml
		XMLReaderAndWriter<User> userreader = new UserReaderAndWriter(
				getApplicationContext());
		List<User> userList = userreader.reader();
		if (userList != null && userList.size() > 0) {
			// 有用户
			User user = userList.get(0);
			AccessToken accessToken = new AccessToken(user.getAccesstoken(), user.getAccesstoken_secret());
			
			OAuthConstant.getInstance().setAccessToken(accessToken);
			OAuthConstant.getInstance().setTokenSecret(
					accessToken.getTokenSecret());
			Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("daogu://WeiboListActivity"));
//			intent.putExtra("isOAuth", true);
			startActivity(intent);
		} else {
			Weibo weibo = OAuthConstant.getInstance().getWeibo();
			RequestToken requestToken = null;
			try {
				requestToken = weibo
						.getOAuthRequestToken("daogu://WeiboListActivity");// 设置认证完成后的回调界面
				Uri uri = Uri.parse(requestToken.getAuthorizationURL()
						+ "&display=mobile");
				OAuthConstant.getInstance().setRequestToken(requestToken);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//				intent.putExtra("isOAuth", false);
				startActivity(intent);// 跳转到浏览器进行OAuth认证

			} catch (WeiboException e) {
				e.printStackTrace();
			}
		}
		 this.finish();
	}
}
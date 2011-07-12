package com.kidfolk.daogu;

import java.util.ArrayList;
import java.util.List;

import weibo4android.Paging;
import weibo4android.Status;
import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.http.AccessToken;
import weibo4android.http.RequestToken;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class WeiboListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Weibo weibo = OAuthConstant.getInstance().getWeibo();
		Uri uri = this.getIntent().getData();
		String oauth_verifier = uri.getQueryParameter("oauth_verifier");
//		Intent intent = getIntent();
//		boolean isOAuth = intent.getExtras().getBoolean("isOAuth");
		if (null==oauth_verifier) {
			//已经授权
			weibo.setOAuthAccessToken(OAuthConstant.getInstance().getAccessToken());
		} else {
			/* 保存授权信息 */
			
			try {
				RequestToken requestToken = OAuthConstant.getInstance()
						.getRequestToken();
				AccessToken accessToken = requestToken.getAccessToken(oauth_verifier);// 获得授权码
				OAuthConstant.getInstance().setAccessToken(accessToken);
				OAuthConstant.getInstance().setTokenSecret(
						accessToken.getTokenSecret());
				User user = new User();
				user.setId(String.valueOf(accessToken.getUserId()));
				user.setAccesstoken(accessToken.getToken());
				user.setAccesstoken_secret(accessToken.getTokenSecret());
				List<User> userList = new ArrayList<User>();
				userList.add(user);
				XMLReaderAndWriter<User> writer = new UserReaderAndWriter(
						getApplicationContext());
				writer.writer(userList);
			} catch (WeiboException e) {
				e.printStackTrace();
			}
		}
		/* 显示我的微博和关注人的微博 */
		
		try {
			List<Status> statusList = weibo.getHomeTimeline();
			DaoguAdapter adapter = new DaoguAdapter(this, statusList);
			this.setListAdapter(adapter);
		} catch (WeiboException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

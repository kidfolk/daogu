package com.kidfolk.daogu;

import java.util.List;

import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.http.AccessToken;
import weibo4android.http.RequestToken;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;

public class SplashActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// TODO 欢迎动画
		setContentView(R.layout.splash);
		initProgressBar = (ProgressBar) findViewById(R.id.init_progressBar);

		new InitTask().execute(this);

	}

	/**
	 * do init task include set consumer_key do authentication for the first
	 * start find the stored authentication data for the next start
	 * 
	 * @author nickyxu
	 * 
	 */
	private class InitTask extends AsyncTask<Context, Integer, Intent> {

		@Override
		protected void onPreExecute() {
			// 初始化
			// 设置应用的app_key和app_secret
			Weibo.CONSUMER_KEY = "858183481";
			Weibo.CONSUMER_SECRET = "bf5d084a6ac0233ec0cc3510a4943a6e";
			System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
			System.setProperty("weibo4j.oauth.consumerSecret",
					Weibo.CONSUMER_SECRET);
			// super.onPreExecute();
		}

		@Override
		protected Intent doInBackground(Context... params) {
			publishProgress(1);
			// 查询user.xml,操作文件
			XMLReaderAndWriter<UserOAuth> userreader = new UserReaderAndWriter(
					params[0]);
			List<UserOAuth> userList = userreader.reader();
			Intent intent = null;
			publishProgress(2);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (null != userList && userList.size() > 0) {
				publishProgress(3);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 有用户
				UserOAuth user = userList.get(0);
				AccessToken accessToken = new AccessToken(
						user.getAccesstoken(), user.getAccesstoken_secret());

				OAuthConstant.getInstance().setAccessToken(accessToken);
				OAuthConstant.getInstance().setTokenSecret(
						accessToken.getTokenSecret());
				intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("daogu://WeiboListActivity"));
				publishProgress(4);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				// 没有用户则进行认证
				intent = oauth();
			}
			publishProgress(5);
			return intent;
		}

		@Override
		protected void onPostExecute(Intent result) {
			startActivity(result);
			SplashActivity.this.finish();
		}
		

		@Override
		protected void onProgressUpdate(Integer... values) {
			initProgressBar.setProgress(values[0]);
		}

		/**
		 * 跳转到新浪微博web认证页面进行认证
		 */
		private Intent oauth() {
			Weibo weibo = OAuthConstant.getInstance().getWeibo();
			RequestToken requestToken = null;
			Intent intent = null;
			try {
				requestToken = weibo
						.getOAuthRequestToken("daogu://WeiboListActivity");// 设置认证完成后的回调界面
				Uri uri = Uri.parse(requestToken.getAuthorizationURL()
						+ "&display=mobile");
				OAuthConstant.getInstance().setRequestToken(requestToken);
				intent = new Intent(Intent.ACTION_VIEW, uri);
			} catch (WeiboException e) {
				e.printStackTrace();
			}
			return intent;
		}
	}
	
	private ProgressBar initProgressBar;
}
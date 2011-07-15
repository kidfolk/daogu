package com.kidfolk.daogu;

import weibo4android.Status;
import weibo4android.WeiboException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class TweetWeiboActivity extends Activity {
	private EditText weiboContent;
	private ProgressDialog progressDialog;
	private Handler handler;
	private static final int TWEET_START = 0;
	private static final int TWEET_END = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tweet);

		// 根据id获得view
		ImageButton backButton = (ImageButton) findViewById(R.id.back);
		Button tweetButton = (Button) findViewById(R.id.tweet);
		weiboContent = (EditText) findViewById(R.id.weiboContent);
		//创建progressdialog
		
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==0){
					if(((Integer)msg.obj)==TWEET_START){
						//开始发微博
						progressDialog = ProgressDialog.show(TweetWeiboActivity.this, null, "正在发送微博……", true, false, null);
					}else if(((Integer)msg.obj)==TWEET_END){
						progressDialog.dismiss();
					}
				}
			}
		};

		ActionListener listener = new ActionListener();
		// 设置view的监听器
		backButton.setOnClickListener(listener);
		tweetButton.setOnClickListener(listener);
	}

	class ActionListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.back:
				TweetWeiboActivity.this.finish();
				break;
			case R.id.tweet:
				//发布微博线程
				Thread tweetThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						
						Message msg = new Message();
						msg.obj = TWEET_START;
						msg.what = 0;
						//发送一个消息给ui线程，显示progressdialog
						TweetWeiboActivity.this.handler.sendMessage(msg);
						String content = weiboContent.getText().toString();
						Status status = null;
						if (content.equals("")) {
							// 微博内容为空
						} else {
							try {
								status = OAuthConstant.getInstance().getWeibo()
										.updateStatus(content);
								if(null!=status){
									Message msg1 = new Message();
									msg1.obj = TWEET_END;
									msg1.what = 0;
									TweetWeiboActivity.this.handler.sendMessage(msg1);
									TweetWeiboActivity.this.setResult(RESULT_OK);
									TweetWeiboActivity.this.finish();
								}
							} catch (WeiboException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				});
				tweetThread.start();
				
				break;
			}

		}

	}

}

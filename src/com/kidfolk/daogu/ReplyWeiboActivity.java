package com.kidfolk.daogu;

import java.net.URLEncoder;

import org.apache.http.client.utils.URLEncodedUtils;

import weibo4android.Comment;
import weibo4android.Status;
import weibo4android.WeiboException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ReplyWeiboActivity extends Activity {
	private EditText replyEditText;
	private ImageButton cancelButton;
	private Button replyButton;
	private TextView screennameTextView;
	private TextView textTextView;
	private Status status;
	private Handler handler;
	private static final int COMMENT_OK = 0;
	private static final int COMMENT_FAILURE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reply);

		replyEditText = (EditText) findViewById(R.id.replyContent);
		cancelButton = (ImageButton) findViewById(R.id.cancel);
		replyButton = (Button) findViewById(R.id.reply);
		screennameTextView = (TextView) findViewById(R.id.screenname);
		textTextView = (TextView) findViewById(R.id.text);

		OnClickListener listener = new Listener();
		cancelButton.setOnClickListener(listener);
		replyButton.setOnClickListener(listener);

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch(msg.what){
				case COMMENT_OK:
					Toast.makeText(ReplyWeiboActivity.this, "评论成功！", Toast.LENGTH_SHORT).show();
					break;
				case COMMENT_FAILURE:
					Toast.makeText(ReplyWeiboActivity.this, "评论失败！", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};

		Intent intent = getIntent();
		status = (Status) intent.getExtras().get("status");

		screennameTextView.setText(status.getUser().getScreenName());
		textTextView.setText(status.getText());
	}

	class Listener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.cancel:
				ReplyWeiboActivity.this.finish();
				break;
			case R.id.reply:
				new Thread(new Runnable() {

					@Override
					public void run() {
						String replyContent = replyEditText.getText()
								.toString();
						try {
							// Comment comment = OAuthConstant
							// .getInstance()
							// .getWeibo()
							// .reply(String.valueOf(status.getId()),
							// String.valueOf(status
							// .getRetweeted_status()
							// .getId()),
							// URLEncoder.encode(replyContent));
							Comment comment = OAuthConstant
									.getInstance()
									.getWeibo()
									.updateComment(
											URLEncoder.encode(replyContent),
											String.valueOf(status.getId()),
											null);
							Message msg = new Message();
							if (null != comment) {
								msg.what = COMMENT_OK;
							} else {
								msg.what = COMMENT_FAILURE;
							}
							ReplyWeiboActivity.this.handler.sendMessage(msg);
						} catch (WeiboException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}).start();

				break;
			}

		}

	}

}

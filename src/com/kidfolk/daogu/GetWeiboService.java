package com.kidfolk.daogu;

import java.util.List;

import weibo4android.Paging;
import weibo4android.Status;
import weibo4android.Weibo;
import weibo4android.WeiboException;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.SlidingDrawer;

public class GetWeiboService extends Service {

	private List<Status> lastStatusList;
	private boolean hasNewWeibo = false;
	private IBinder weiboBinder = new WeiboBinder();
	private static final String TAG = "GetWeiboService";
	private static final int GET_NEW_WEIBO = 0;

	public class WeiboBinder extends Binder {
		public GetWeiboService getService() {
			return GetWeiboService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.v(TAG, "绑定成功");
		return weiboBinder;
	}

	public void startGetNewWeibo(final List<Status> preList) {
		final Weibo weibo = OAuthConstant.getInstance().getWeibo();
		final Paging paging = new Paging();
		Thread thread = new Thread() {

			@Override
			public void run() {
				Log.v(TAG, "开始获取新微博");
				List<Status> pStatus = preList;
				while (true) {
					hasNewWeibo = false;
					if (null != pStatus) {
						paging.setSinceId(pStatus.get(0).getId());
						try {
							List<Status> statusList = weibo
									.getHomeTimeline(paging);
							if (statusList.size() > 0) {
								Log.v(TAG, "取得数据");
								pStatus = statusList;
								lastStatusList = pStatus;// 将最新的微博保存到列表
								hasNewWeibo = true;
								showNotification(lastStatusList);
							}
						} catch (WeiboException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						sleep(1000*60*5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		};
		thread.start();
	}

	public void showNotification(List<Status> statusList) {
		Log.v(TAG, "通知");
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		int icon = R.drawable.icon; // icon from resources
		CharSequence tickerText = "Hello"; // ticker-text
		long when = System.currentTimeMillis(); // notification time
		Context context = getApplicationContext(); // application Context
		CharSequence contentTitle = "捣鼓"; // expanded message title
		CharSequence contentText = "您有" + statusList.size() + "条新微博！"; // expanded
																		// message
																		// text

		Intent notificationIntent = new Intent(this, GetWeiboService.class);
		PendingIntent contentIntent = PendingIntent.getService(this, 0,
				notificationIntent, 0);

		Notification notification = new Notification(icon, tickerText, when);
		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);

		mNotificationManager.notify(GET_NEW_WEIBO, notification);
	}

	public List<Status> getNewWeibo() {
		if (hasNewWeibo) {
			return lastStatusList;
		} else {
			return null;
		}
	}

}

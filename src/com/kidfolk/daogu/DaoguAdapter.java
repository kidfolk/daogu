package com.kidfolk.daogu;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import weibo4android.RetweetDetails;
import weibo4android.Status;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DaoguAdapter extends BaseAdapter {
	private Context context;

	private List<Status> list;

	public DaoguAdapter(Context context, List<Status> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {

		return list.size();
	}

	public void setList(List<Status> list) {
		this.list = list;
	}

	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// get tweet layout
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout linearLayout = (LinearLayout) inflater.inflate(
				R.layout.row, null);
		TextView screenname = (TextView) linearLayout
				.findViewById(R.id.screenname);
		TextView create_at = (TextView) linearLayout
				.findViewById(R.id.create_at);
		TextView text = (TextView) linearLayout.findViewById(R.id.text);
		ImageView pic = (ImageView) linearLayout.findViewById(R.id.pic);

		Status status = list.get(position);

		screenname.setText(status.getUser().getScreenName());
		create_at.setText(status.getCreatedAt().toLocaleString());
		text.setText(status.getText());
		// add tweet pic
		String pic_url = status.getThumbnail_pic();
		// set tweet pic
		setImageFromURL(pic, pic_url);

		// see if has retweet
		// RetweetDetails retweetDetials = status.getRetweeted_status();
		Status retweetStatus = status.getRetweeted_status();
		if (null != retweetStatus) {
			// has retweet
			// get retweet layout
			LinearLayout retweet = (LinearLayout) linearLayout
					.findViewById(R.id.retweet);
			TextView subText = (TextView) linearLayout
					.findViewById(R.id.subtext);
			ImageView subpic = (ImageView) linearLayout
					.findViewById(R.id.subpic);
			// retweetStatus = retweetDetials.getRetweetStatus();
			retweet.setVisibility(View.VISIBLE);
			// set retweet content
			subText.setText("@" + retweetStatus.getUser().getScreenName() + ":"
					+ retweetStatus.getText());
			String thumbnail_pic_url = retweetStatus.getThumbnail_pic();
			// set retweet pic
			setImageFromURL(subpic, thumbnail_pic_url);

		}
		return linearLayout;
	}

	/**
	 * 
	 * @param subpic
	 *            ImageView
	 * @param thumbnail_pic_url
	 *            Image's url
	 */
	protected void setImageFromURL(final ImageView subpic,
			final String thumbnail_pic_url) {
		if (!"".equals(thumbnail_pic_url)) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					URL url;
					InputStream is = null;
					BufferedInputStream bis = null;
					try {
						url = new URL(thumbnail_pic_url);
						is = url.openStream();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					bis = new BufferedInputStream(is);
					final Bitmap bm = BitmapFactory.decodeStream(bis);
					subpic.post(new Runnable() {

						@Override
						public void run() {
							subpic.setImageBitmap(bm);
						}
					});
				}
			}).start();

		}
	}

}

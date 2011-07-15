package com.kidfolk.daogu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Xml;
/**
 * 读写用户xml文件
 * @author kidfolk
 *
 */
public class UserReaderAndWriter implements XMLReaderAndWriter<User> {

	private static final String FILENAME = "users.xml";
	private Context context;

	public UserReaderAndWriter(Context context) {
		this.context = context;
	}

	/**
	 * 从xml文件中解析出用户信息
	 */
	@Override
	public List<User> reader() {
		List<User> userList = null;
		XmlPullParser parser = Xml.newPullParser();
		FileInputStream fis = null;
		try {
			File file = context.getFileStreamPath(FILENAME);
			boolean flag = file.createNewFile();
			if (!flag) {
				// 文件已经存在
				fis = context.openFileInput(FILENAME);
				parser.setInput(fis, null);
				int eventType = parser.getEventType();
				User user = null;
				boolean done = false;
				while (eventType != XmlPullParser.END_DOCUMENT && !done) {
					String tagname = null;
					switch (eventType) {
					case XmlPullParser.START_DOCUMENT:
						userList = new ArrayList<User>();
						break;
					case XmlPullParser.START_TAG:
						tagname = parser.getName();
						if (tagname.equalsIgnoreCase("user")) {
							user = new User();
						} else if (user != null) {
							if (tagname.equalsIgnoreCase("id")) {
								user.setId(parser.nextText());
							} else if (tagname.equalsIgnoreCase("accesstoken")) {
								user.setAccesstoken(parser.nextText());
							} else if (tagname
									.equalsIgnoreCase("accesstokenSecret")) {
								user.setAccesstoken_secret(parser.nextText());
							}
						}
						break;
					case XmlPullParser.END_TAG:
						tagname = parser.getName();
						if (tagname.equalsIgnoreCase("user") && user != null) {
							userList.add(user);
						} else if (tagname.equalsIgnoreCase("users")) {
							done = true;
						}
						break;
					}
					eventType = parser.next();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

		}
		return userList;
	}

	/**
	 * 往xml文件中写入用户信息
	 */
	@Override
	public void writer(List<User> userList) {
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		FileOutputStream fos = null;
		try {
			fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "users");
			for (User user : userList) {
				serializer.startTag("", "user");
				serializer.startTag("", "id");
				serializer.text(user.getId());
				serializer.endTag("", "id");
				serializer.startTag("", "accesstoken");
				serializer.text(user.getAccesstoken());
				serializer.endTag("", "accesstoken");
				serializer.startTag("", "accesstokenSecret");
				serializer.text(user.getAccesstoken_secret());
				serializer.endTag("", "accesstokenSecret");
				serializer.endTag("", "user");
			}
			serializer.endTag("", "users");
			serializer.endDocument();
			fos.write(writer.toString().getBytes());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				fos.close();
				writer.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}

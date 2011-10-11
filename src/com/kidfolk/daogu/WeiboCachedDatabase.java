package com.kidfolk.daogu;

import java.util.List;

import weibo4android.Status;
import weibo4android.http.HTMLEntity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;

import com.kidfolk.daogu.column.MComments;
import com.kidfolk.daogu.column.MDirectMessages;
import com.kidfolk.daogu.column.MStatuses;
import com.kidfolk.daogu.column.MUsers;

public class WeiboCachedDatabase extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "weibocache.db";
	private static final String STATUS_TABLE_NAME = "status";
	private static final String USER_TABLE_NAME = "user";
	private static final String COMMENT_TABLE_NAME = "comment";
	private static final String DIRECT_MESSAGE_TABLE_NAME = "directMessage";
	private static final int VERSION = 2;
//	private Context context;
//	private static WeiboCachedDatabase instance;

	public WeiboCachedDatabase(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
//		this.context = context;
	}
	
//	public static WeiboCachedDatabase getInstance(){
//		if(null==instance){
//			Context context = DaoguApplication.getDaoguApplicationContext();
//			instance = new WeiboCachedDatabase(context);
//		}
//		return instance;
//	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//create table status
		db.execSQL("CREATE TABLE "+STATUS_TABLE_NAME+" ("
				+MStatuses._ID+" INTEGER,"
				+MStatuses.BMIDDLE_PIC+" TEXT,"
				+MStatuses.CREATED_AT+" DATE,"
				+MStatuses.ID+" INTEGER PRIMARY KEY,"
				+MStatuses.IN_REPLY_TO_SCREENNAME+" TEXT,"
				+MStatuses.IN_REPLY_TO_STATUS_ID+" INTEGER,"
				+MStatuses.IN_REPLY_TO_USER_ID+" INTEGER,"
				+MStatuses.IS_FAVORITED+" BOOLEAN,"
				+MStatuses.IS_TRUNCATED+" BOOLEAN,"
//				+MStatuses.LATITUDE+" INTEGER,"
//				+MStatuses.LONGITUDE+" INTEGER,"
//				+MStatuses.MID+" TEXT,"
				+MStatuses.ORIGINAL_PIC+" TEXT,"
//				+MStatuses.RETWEET_DETAILS+" "
				+MStatuses.RETWEETED_STATUS+" INTEGER,"
				+MStatuses.SOURCE+" TEXT,"
				+MStatuses.TEXT+" TEXT,"
				+MStatuses.THUMBNAIL_PIC+" TEXT,"
				+MStatuses.USER+" INTEGER,"
				+"FOREIGN KEY("+MStatuses.USER+") REFERENCES "+USER_TABLE_NAME+"("+MUsers.ID+")"
				+");");
		
		//create table user
		db.execSQL("CREATE TABLE "+USER_TABLE_NAME+" ("
				+MUsers._ID+" INTEGER,"
				+MUsers.CITY+" TEXT,"
				+MUsers.CREATED_AT+" DATE,"
				+MUsers.DESCRIPTION+" TEXT,"
				+MUsers.DOMAIN+" TEXT,"
				+MUsers.FAVOURITES_COUNT+" INTEGER,"
				+MUsers.FOLLOWERS_COUNT+" INTEGER,"
				+MUsers.FOLLOWING+" BOOLEAN,"
				+MUsers.FRIENDS_COUNT+" INTEGER,"
				+MUsers.GENDER+" CHAR,"
				+MUsers.ID+" INTEGER PRIMARY KEY,"
				+MUsers.LOCATION+" TEXT,"
				+MUsers.NAME+" TEXT,"
				+MUsers.PROFILE_IMAGE_URL+" TEXT,"
				+MUsers.PROVINCE+" INTEGER,"
				+MUsers.SCREEN_NAME+" TEXT,"
				+MUsers.STATUSES_COUNT+" INTEGER,"
				+MUsers.URL+" TEXT,"
				+MUsers.VERIFIED+" BOOLEAN"
				+");");
		
		//create table comment
		db.execSQL("CREATE TABLE "+COMMENT_TABLE_NAME+" ("
				+MComments._ID+" INTEGER,"
				+MComments.CREATED_AT+" DATE,"
				+MComments.FAVORITED+" BOOLEAN"
				+MComments.ID+" INTEGER PRIMARY KEY,"
				+MComments.REPLY_COMMENT+" INTEGER,"//foreign key
				+MComments.SOURCE+" TEXT,"
				+MComments.STATUS+" INTEGER,"//foreign key
				+MComments.TEXT+" TEXT,"
				+MComments.TRUNCATED+" BOOLEAN,"
				+MComments.USER+" INTEGER,"//foreign key
				+"FOREIGN KEY("+MComments.STATUS+") REFERENCES "+STATUS_TABLE_NAME+"("+MStatuses.ID+"),"
				+"FOREIGN KEY("+MComments.USER+") REFERENCES "+USER_TABLE_NAME+"("+MUsers.ID+")"
				+");");
		
		//create table directMessage
		db.execSQL("CREATE TABLE "+DIRECT_MESSAGE_TABLE_NAME+" ("
				+MDirectMessages._ID+" INTEGER,"
				+MDirectMessages.CREATED_AT+" DATE,"
				+MDirectMessages.ID+" INTEGER PRIMARY KEY,"
				+MDirectMessages.RECIPIENT_ID+" INTEGER,"//foreign key
				+MDirectMessages.SENDER_ID+" INTEGER,"//foreign key
				+MDirectMessages.TEXT+" TEXT,"
				+"FOREIGN KEY("+MDirectMessages.RECIPIENT_ID+") REFERENCES "+USER_TABLE_NAME+"("+MUsers.ID+"),"
				+"FOREIGN KEY("+MDirectMessages.SENDER_ID+") REFERENCES "+USER_TABLE_NAME+"("+MUsers.ID+")"
				+");");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");

        // Kills the table and existing data
        db.execSQL("DROP TABLE IF EXISTS "+STATUS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+COMMENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+DIRECT_MESSAGE_TABLE_NAME);
        
        onCreate(db);

	}
	
	public int insertToStatus(List<Status> statusList){
		if(null==statusList||statusList.size()==0){
			return 0;
		}
		int size = statusList.size();
		SQLiteDatabase db = getWritableDatabase();
		Status status = null;
		java.text.DateFormat df = java.text.DateFormat.getDateInstance();
		db.beginTransaction();
		for(int i=0;i<size;i++){
			status = statusList.get(i);
			db.execSQL("INSERT INTO "+STATUS_TABLE_NAME
					+"("
					+MStatuses.BMIDDLE_PIC+","
					+MStatuses.CREATED_AT+","
					+MStatuses.ID+","
					+MStatuses.IN_REPLY_TO_SCREENNAME+","
					+MStatuses.IN_REPLY_TO_STATUS_ID+","
					+MStatuses.IN_REPLY_TO_USER_ID+","
					+MStatuses.IS_FAVORITED+","
					+MStatuses.IS_TRUNCATED+","
//					+MStatuses.LATITUDE+" INTEGER,"
//					+MStatuses.LONGITUDE+" INTEGER,"
//					+MStatuses.MID+" TEXT,"
					+MStatuses.ORIGINAL_PIC+","
//					+MStatuses.RETWEET_DETAILS+" "
					+MStatuses.RETWEETED_STATUS+","
					+MStatuses.SOURCE+","
					+MStatuses.TEXT+","
					+MStatuses.THUMBNAIL_PIC+","
					+MStatuses.USER
					+")"
					+" VALUES("
					+"\""+status.getBmiddle_pic()+"\","
					+df.format(status.getCreatedAt())+","
					+status.getId()+","
					+"\""+status.getInReplyToScreenName()+"\","
					+status.getInReplyToStatusId()+","
					+status.getInReplyToUserId()+","
					+"\""+status.isFavorited()+"\","
					+"\""+status.isTruncated()+"\","
					+"\""+status.getOriginal_pic()+"\","
					+((null==status.getRetweeted_status())?"\"\"":status.getRetweeted_status().getId())+","
//					+status.getRetweeted_status().getId()+","
					//TODO parse the source content
					+"\""+"\","
//					+"\""+status.getSource()+"\","
					+"\""+status.getText().getBytes()+"\","
					+"\""+status.getThumbnail_pic()+"\","
					+status.getUser().getId()+")"
					);
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();
		return size;
	}
	
	private static final String TAG = "WeiboCachedDatabase";

}

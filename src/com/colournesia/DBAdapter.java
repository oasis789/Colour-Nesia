package com.colournesia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_SCORE = "score";

	private static final String DATABASE_NAME = "highscoresDB";
	private static final String TABLE_NAME = "highscorestable";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table highscorestable (_id integer primary key autoincrement, "
			+ "name text not null, score integer);";

	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context c) {
		this.context = c;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			try {
				db.execSQL(DATABASE_CREATE);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}
	}

	public DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		DBHelper.close();
	}

	public long insertScore(String name, int score) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, name);
		cv.put(KEY_SCORE, score);
		return db.insert(TABLE_NAME, null, cv);
	}

	public Cursor getAllScores() {
		return db.query(TABLE_NAME, new String[] { KEY_ROWID, KEY_NAME,
				KEY_SCORE }, null, null, null, null, KEY_SCORE + " DESC");
	}

}

package com.example.doitlist.sqlite;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class SqliteManager extends SQLiteOpenHelper{

	final static private int DB_VERSION = 1;
	
	public SqliteManager(Context context){
		super(context, null, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(
				"CREATE TABLE task_list(" +
				"  task varchar," +
				"  deadline datetime, " +
				"  priority tinyint default 0, " +
				"  status tinyint default 0);"
				);
		
		db.execSQL("insert into task_list(task, deadline, priority, status) values ('foo', '', 0, 0);");
		db.execSQL("insert into task_list(task, deadline, priority, status) values ('foo', '', 0, 0);");
		db.execSQL("insert into task_list(task, deadline, priority, status) values ('foo', '', 0, 0);");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
	}
}

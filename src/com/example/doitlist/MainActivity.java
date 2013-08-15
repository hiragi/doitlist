package com.example.doitlist;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

import org.apache.http.ParseException;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.doitlist.sqlite.SqliteManager;

public class MainActivity extends Activity {

	private static final String TAG = "OHISHI";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		actionBar.addTab(actionBar.newTab()
				.setText("ALL")
				.setTabListener(new TabListener<Tab1Fragment>(
						this, "tag1", Tab1Fragment.class)));
		actionBar.addTab(actionBar.newTab()
				.setText("DONE")
				.setTabListener(new TabListener<Tab2Fragment>(
						this, "tag2", Tab2Fragment.class)));
		actionBar.addTab(actionBar.newTab()
				.setText("UNDONE")
				.setTabListener(new TabListener<Tab3Fragment>(
						this, "tag3", Tab3Fragment.class)));
		
		// set up the list
		ListView listView = (ListView)MainActivity.this.findViewById(R.id.listview);
		final ArrayAdapter<Task> adapter = new TaskAdapter(MainActivity.this, R.layout.row);
		listView.setAdapter(adapter);
		

		SqliteManager manager = new SqliteManager(this);
		SQLiteDatabase db = manager.getReadableDatabase();
		
		Cursor c = db.query("task_list", new String[]{"task", "deadline"}, null, null, null, null, null);
		
		Log.i(TAG, c.getString(1));
		
		String dateString = c.getString(1);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		Date convertedDate = new Date(0);
		
		try{
			convertedDate = (Date) dateFormat.parse(dateString);
		}
		catch(ParseException e){
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		
		boolean isEof = c.moveToFirst();
		
		while(isEof){
			adapter.add(new Task(c.getString(0), convertedDate));
			isEof = c.moveToNext();
		}
		
		c.close();
		
		db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
class Task{
	private String task;
	private Date deadline;
		
	public Task(String task, Date deadline){
		this.task = task;
		this.deadline = deadline;
	}
			
	public String getTask(){
		return this.task;
	}
			
	public String getDeadline(){
		//String date = this.deadline.year + "/" + this.deadline.month + "/" + this.deadline.monthDay + " " + this.deadline.hour + ":" + this.deadline.minute;
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		return df.format(this.deadline);
	}
}

class TaskAdapter extends ArrayAdapter<Task>{
	private ArrayList<Task> tasks = new ArrayList<Task>();
	private LayoutInflater inflater;
	private int layout;
		
	public TaskAdapter(Context context, int textViewResourceId){
		super(context, textViewResourceId);
		this.inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		this.layout = textViewResourceId;
	}
		
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View view = convertView;
		if(convertView == null){
			view = this.inflater.inflate(this.layout, null);
		}
			
		Task task = this.tasks.get(position);
		
		((TextView) view.findViewById(R.id.title)).setText(task.getTask());
		((TextView) view.findViewById(R.id.deadline)).setText(task.getDeadline());

		return view;
	}
		
	@Override
	public void add(Task task){
		super.add(task);
		this.tasks.add(task);
	}
}
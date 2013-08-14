package com.example.doitlist;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		actionBar.addTab(actionBar.newTab()
				.setText("List")
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

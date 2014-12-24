package com.colournesia;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.colournesia.R;

public class MainActivity extends Activity {

	Context context;
	Random random = new Random();
	LevelManager levelManager;
	int currentViewID;
	PaintManager paintManager;
	ShapeView currentView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
	    getWindow().setBackgroundDrawableResource(R.drawable.color_rectangle);
		levelManager = LevelManager.getLevelManager(this);
		levelManager.onlyDrawBorder = false;
		currentViewID = levelManager.currentViewID;
		paintManager = levelManager.paintManager;
		GetCurrentView();
		setContentView(currentView);
		setTitle("Level " + levelManager.currentLevel);

		new HoldImageOnScreen().execute((Void[]) null);
	}

	private void GetCurrentView() {
		// TODO Auto-generated method stub
		String className = Constants.viewIDtoViewName.get(currentViewID);
		Log.v("MainActivity", "Getting Shape View: "+className);
		Class viewClass = null;
		try {
			viewClass = Class.forName("com.colournesia." + className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v("MainActivity", "ClassNotFound " + className);
		}
		Class[] parameters = new Class[] { Context.class, PaintManager.class };
		Constructor ctor = null;

		try {
			ctor = viewClass.getConstructor(parameters);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v("MainActivity", "No such Method");

		}
		try {
			currentView = (ShapeView) ctor.newInstance(new Object[] { context,
					paintManager });//Getting an error here somewhere!!!!!
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v("MainActivity", "Illegal Argument");

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v("MainActivity", "Instantiation Exception");

		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v("MainActivity", "IllegalAccessExcpetion");

		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v("MainActivity", "InvocationTargetException");

		}

	}

	private class HoldImageOnScreen extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(levelManager.timeShown);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			Intent intent = new Intent(context, UserInteractActivity.class);
			startActivity(intent);
			finish();
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	}

}

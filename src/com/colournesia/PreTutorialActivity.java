package com.colournesia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;

public class PreTutorialActivity extends Activity {
    Context context;
    LevelManager levelManager;
    PaintManager paintManager;
    OneSquaredShapeView currentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	context = this;
	getWindow().setBackgroundDrawableResource(R.drawable.color_rectangle);
	levelManager = LevelManager.getLevelManager(this);
	levelManager.reset();
	levelManager.onlyDrawBorder = false;
	paintManager = levelManager.paintManager;
	currentView = new OneSquaredShapeView(context, paintManager);
	setContentView(currentView);
	setTitle("Tutorial");
	// new HoldImageOnScreen().execute((Void[]) null);

	CountDownTimer cdt = new CountDownTimer(levelManager.timeShown, 10) {

	    @Override
	    public void onFinish() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(context, TutorialActivity.class);
		startActivity(intent);
		finish();
	    }

	    @Override
	    public void onTick(long millisUntilFinished) {
		// TODO Auto-generated method stub
		// Change the Progress Bar
	    }

	};
	cdt.start();
    }

    @Override
    public void onBackPressed() {
	// TODO Auto-generated method stub
    }

}

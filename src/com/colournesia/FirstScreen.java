package com.colournesia;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.samsung.sdraw.SDrawLibrary;

public class FirstScreen extends Activity implements OnClickListener {

    private Button startButton;
    private Button howToPlayButton;
    private Button highScoresButton;
    private Button tutorialButton;
    private SharedPreferences prefs;
    private Editor editor;
    private String prefName = "MyPrefs";
    boolean firstTimePlaying;
    LevelManager levelManager;
    Dialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	d = new AlertDialog.Builder(this)
		.setTitle(getResources().getString(R.string.how_to_play_title))
		.setMessage(getResources().getString(R.string.instructions))
		.setNeutralButton("Start the Tutorial",
			new DialogInterface.OnClickListener() {

			    @Override
			    public void onClick(DialogInterface dialog,
				    int which) {
				// TODO Auto-generated method stub
				FirstScreen.this.onClick(tutorialButton);
			    }
			})
		.setPositiveButton("Start the Game",
			new DialogInterface.OnClickListener() {

			    @Override
			    public void onClick(DialogInterface dialog,
				    int which) {
				// TODO Auto-generated method stub
				FirstScreen.this.onClick(startButton);
			    }
			})
		.setNegativeButton("Quit",
			new DialogInterface.OnClickListener() {

			    @Override
			    public void onClick(DialogInterface dialog,
				    int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			    }
			}).create();
	d.setCancelable(false);
	levelManager = LevelManager.getLevelManager(this);
	levelManager.sPenEnabled = SDrawLibrary.isSupportedModel();
	prefs = getSharedPreferences(prefName, MODE_PRIVATE);
	editor = prefs.edit();
	firstTimePlaying = prefs.getBoolean("FIRST_TIME_PLAYING", true);
	startButton = (Button) findViewById(R.id.startButton);
	howToPlayButton = (Button) findViewById(R.id.infoButton);
	highScoresButton = (Button) findViewById(R.id.highScores);
	tutorialButton = (Button) findViewById(R.id.tutorialButton);
	tutorialButton.setOnClickListener(this);
	highScoresButton.setOnClickListener(this);
	howToPlayButton.setOnClickListener(this);
	startButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.startButton:
	    if (firstTimePlaying) {
		editor.putBoolean("FIRST_TIME_PLAYING", false);
		editor.commit();
		firstTimePlaying = false;
		levelManager.reset();
		d.show();
	    } else {
		Intent i = new Intent(this, MainActivity.class);
		levelManager.reset();
		startActivity(i);
	    }
	    break;
	case R.id.infoButton:
	    editor.putBoolean("FIRST_TIME_PLAYING", false);
	    editor.commit();
	    firstTimePlaying = false;
	    d.show();
	    break;
	case R.id.highScores:
	    Intent k = new Intent(this, ScoresActivity.class);
	    startActivity(k);
	    break;
	case R.id.tutorialButton:
	    editor.putBoolean("FIRST_TIME_PLAYING", false);
	    editor.commit();
	    firstTimePlaying = false;
	    Intent l = new Intent(this, PreTutorialActivity.class);
	    startActivity(l);
	    break;
	}

    }

}

package com.colournesia;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ScoresActivity extends Activity {

    private DBAdapter adapter;
    private TableLayout tbl;
    private EditText etName;
    private Button btnSaveScore;
    private LevelManager levelManager;
    private Context context;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.scoresactivity);
	context = this;
	levelManager = LevelManager.getLevelManager(this);
	tbl = (TableLayout) findViewById(R.id.scoreTable);
	etName = (EditText) findViewById(R.id.etName);
	btnSaveScore = (Button) findViewById(R.id.btnSaveScore);
	btnSaveScore.setOnClickListener(saveScoreBtnListener);
	ll = (LinearLayout) findViewById(R.id.llSaveScore);
	if (levelManager.saveScore) {
	    ll.setVisibility(View.VISIBLE);
	}
	adapter = new DBAdapter(this);
	adapter = adapter.open();
	setUpAllScores();
    }

    @Override
    protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	levelManager.saveScore = false;
	levelManager.reset();
    }

    private void setUpAllScores() {
	// TODO Auto-generated method stub
	Cursor c = adapter.getAllScores();
	LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	if (c.moveToFirst()) {
	    for (int i = 0; i < c.getCount(); i++) {
		int currentScore = c
			.getInt(c.getColumnIndex(adapter.KEY_SCORE));
		String currentName = c.getString(c
			.getColumnIndex(adapter.KEY_NAME));
		View newScoreRow = inflater.inflate(R.layout.scoretablerow,
			null);
		TextView no = (TextView) newScoreRow.findViewById(R.id.number);
		no.setText(Integer.toString(i + 1));
		TextView scoretv = (TextView) newScoreRow
			.findViewById(R.id.newScore);
		scoretv.setText(Integer.toString(currentScore));
		TextView nametv = (TextView) newScoreRow
			.findViewById(R.id.newName);
		nametv.setText(currentName);
		tbl.addView(newScoreRow);
		c.moveToNext();
	    }

	}

    }

    private OnClickListener saveScoreBtnListener = new OnClickListener() {

	@Override
	public void onClick(View v) {
	    // TODO Auto-generated method stub
	    String name = etName.getText().toString();
	    if (name.equals("")) {
		Toast.makeText(context, "Please enter your name!!",
			Toast.LENGTH_SHORT).show();
	    } else {
		btnSaveScore.setClickable(false);
		etName.setEnabled(false);
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(etName.getWindowToken(), 0);
		int currentScore = levelManager.score;
		adapter.insertScore(name, currentScore);
		Toast.makeText(context, "Your score has been saved",
			Toast.LENGTH_SHORT).show();
		TableRow trHeading = (TableRow) findViewById(R.id.trHeading);
		tbl.removeAllViews();
		tbl.addView(trHeading);
		setUpAllScores();
		levelManager.saveScore = false;
		ll.setVisibility(View.INVISIBLE);
	    }

	}

    };

}

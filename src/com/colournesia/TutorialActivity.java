package com.colournesia;

import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.samsung.spen.settings.SettingFillingInfo;
import com.samsung.spensdk.SCanvasConstants;
import com.samsung.spensdk.SCanvasView;
import com.samsung.spensdk.applistener.SCanvasInitializeListener;
import com.samsung.spensdk.applistener.SPenTouchListener;
import com.samsung.spensdk.applistener.SettingFillingChangeListener;
import com.samsung.spensdk.applistener.SettingViewShowListener;

public class TutorialActivity extends Activity {

    private OneSquaredShapeView currentView;
    RelativeLayout msCanvasContainer;
    private SCanvasView mSCanvas;
    private Bitmap bitmap;
    private float xTouch, yTouch;
    private Rect[] rect;
    PaintManager paintManager;
    private SettingFillingInfo fillingInfo;
    private SettingFillingInfo[] rectFillingInfo;
    private boolean[] rectangleTouched;
    private Canvas canvas;
    private Button doneButton;
    private Context context;
    private LinearLayout colourSelected;
    private RelativeLayout settingsView;
    private LinearLayout b;
    private TextView instructions;
    private int rightColour;
    private LevelManager levelManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	levelManager = LevelManager.getLevelManager(this);
	levelManager.onlyDrawBorder = true;
	context = this;
	setTitle("Tutorial");
	setContentView(R.layout.tutorialactivity);
	init();
    }
    
    

    @Override
    protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	levelManager.reset();
    }



    private void init() {
	// TODO Auto-generated method stub
	paintManager = levelManager.paintManager;
	rightColour = paintManager.returnPaint()[0].getColor();
	currentView = new OneSquaredShapeView(this, paintManager);
	doneButton = (Button) findViewById(R.id.btutorialDone);
	doneButton.setOnClickListener(doneButtonListener);
	mSCanvas = new SCanvasView(this);
	msCanvasContainer = (RelativeLayout) findViewById(R.id.tutorial_canvas_container);
	b = (LinearLayout) findViewById(R.id.lltutorialBlackLine);
	instructions = (TextView) findViewById(R.id.tutorialinstructions);
	instructions.setText("Touch the palette to choose a colour");
	msCanvasContainer.addView(mSCanvas);
	mSCanvas.setSCanvasInitializeListener(new SCanvasInitializeListener() {

	    @Override
	    public void onInitialized() {
		// TODO Auto-generated method stub
		HashMap<String, Integer> settingResourceMapInt = new HashMap<String, Integer>();
		HashMap<String, String> settingResourceMapString = new HashMap<String, String>();
		mSCanvas.createSettingView(msCanvasContainer,
			settingResourceMapInt, settingResourceMapString);
		mSCanvas.setSettingViewSizeOption(
			SCanvasConstants.SCANVAS_SETTINGVIEW_FILLING,
			SCanvasConstants.SCANVAS_SETTINGVIEW_SIZE_MINI);
		mSCanvas.setSettingViewShowListener(newListener);
		mSCanvas.showSettingView(
			SCanvasConstants.SCANVAS_SETTINGVIEW_FILLING, true);
		currentView.layout(mSCanvas.getLeft(), mSCanvas.getTop(),
			mSCanvas.getRight(), mSCanvas.getBottom());
		bitmap = Bitmap.createBitmap(currentView.getWidth(),
			currentView.getHeight(), Bitmap.Config.ARGB_8888);
		canvas = new Canvas(bitmap);
		currentView.draw(canvas);
		mSCanvas.setCanvasBitmap(bitmap);
		rect = new Rect[currentView.getRect().length];
		rect = currentView.getRect();
		rectangleTouched = new boolean[rect.length];
		rectFillingInfo = new SettingFillingInfo[rect.length];
		for (int i = 0; i < rect.length; i++) {
		    rectangleTouched[i] = false;
		    rectFillingInfo[i] = new SettingFillingInfo();
		    rectFillingInfo[i].setFillingColor(0);
		}
		fillingInfo = mSCanvas.getSettingFillingInfo();
		mSCanvas.setSPenTouchListener(SPenListener);
		mSCanvas.setSettingFillingChangeListener(fillingChangeListener);
		mSCanvas.setCanvasMode(SCanvasConstants.SCANVAS_MODE_INPUT_FILLING);
	    }

	});

    }

    private SettingViewShowListener newListener = new SettingViewShowListener() {

	@Override
	public void onEraserSettingViewShow(boolean arg0) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onFillingSettingViewShow(boolean arg0) {
	    // TODO Auto-generated method stub
	    if (!arg0) {
		mSCanvas.toggleShowSettingView(SCanvasConstants.SCANVAS_SETTINGVIEW_FILLING);
	    }
	}

	@Override
	public void onPenSettingViewShow(boolean arg0) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onTextSettingViewShow(boolean arg0) {
	    // TODO Auto-generated method stub

	}

    };

    public SettingFillingChangeListener fillingChangeListener = new SettingFillingChangeListener() {

	@Override
	public void onFillingColorChanged(int arg0) {
	    // TODO Auto-generated method stub
	    if (fillingInfo != null) {
		fillingInfo.setFillingColor(arg0);
		b.setBackgroundColor(arg0);
		instructions
			.setText("Now touch the shape to fill it with the selected colour");
	    }

	}

    };

    public SPenTouchListener SPenListener = new SPenTouchListener() {

	@Override
	public void onTouchButtonDown(View arg0, MotionEvent arg1) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onTouchButtonUp(View arg0, MotionEvent arg1) {
	    // TODO Auto-generated method stub

	}

	@Override
	public boolean onTouchFinger(View arg0, MotionEvent arg1) {
	    // TODO Auto-generated method stub

	    xTouch = arg1.getX();
	    yTouch = arg1.getY();
	    boolean touched = false;

	    for (int i = 0; i < rect.length; i++) {
		if (((rect[i].left + 5) < (int) xTouch
			&& (int) xTouch < (rect[i].right - 5)
			&& (rect[i].top + 5) < (int) yTouch && (int) yTouch < (rect[i].bottom - 5))) {
		    rectangleTouched[i] = true;
		    touched = true;
		    rectFillingInfo[i].setFillingColor(fillingInfo
			    .getFillingColor());
		    instructions
			    .setText("Now press the 'Done' Button to see if you are correct");
		}
	    }
	    if (touched) {
		return false;
	    } else {
		return true;
	    }

	}

	@Override
	public boolean onTouchPen(View arg0, MotionEvent arg1) {
	    // TODO Auto-generated method stub

	    xTouch = arg1.getX();
	    yTouch = arg1.getY();
	    boolean touched = false;
	    for (int i = 0; i < rect.length; i++) {
		if (((rect[i].left + 5) < (int) xTouch
			&& (int) xTouch < (rect[i].right - 5)
			&& (rect[i].top + 5) < (int) yTouch && (int) yTouch < (rect[i].bottom - 5))) {
		    rectangleTouched[i] = true;
		    touched = true;
		    rectFillingInfo[i].setFillingColor(fillingInfo
			    .getFillingColor());
		    instructions
			    .setText("Now press the 'Done' Button to see if you are correct");

		}
	    }

	    if (touched) {
		return false;
	    } else {
		return true;
	    }
	}

	@Override
	public boolean onTouchPenEraser(View arg0, MotionEvent arg1) {
	    // TODO Auto-generated method stub
	    return false;
	}

    };

    public OnClickListener doneButtonListener = new OnClickListener() {

	@Override
	public void onClick(View arg0) {
	    // TODO Auto-generated method stub;

	    boolean matched = true;
	    boolean touched = true;
	    for (int i = 0; i < rect.length; i++) {
		if (!rectangleTouched[i]) {
		    touched = false;
		} else if (!MatchColors(i)) {
		    matched = false;
		    instructions.setText("Please select the correct colour");
		}

		if (matched && touched) {
		    Toast.makeText(context, "You Won", Toast.LENGTH_SHORT)
			    .show();
		    Dialog d = new AlertDialog.Builder(context)
			    .setTitle("You Won")
			    .setMessage("You have now completed the tutorial")
			    .setPositiveButton("Start The Game",
				    new DialogInterface.OnClickListener() {

					@Override
					public void onClick(
						DialogInterface dialog,
						int which) {
					    // TODO Auto-generated method stub
					    Intent i = new Intent(context,
						    MainActivity.class);
					    startActivity(i);
					    finish();
					}
				    })
			    .setNegativeButton("Quit",
				    new DialogInterface.OnClickListener() {

					@Override
					public void onClick(
						DialogInterface dialog,
						int which) {
					    // TODO Auto-generated method stub
					    finish();
					}
				    }).create();
		    d.setCancelable(false);
		    d.show();

		}

	    }

	}
    };

    private boolean MatchColors(int i) {
	// TODO Auto-generated method stub
	int fillColor = rectFillingInfo[i].getFillingColor();
	int originalColor = paintManager.returnPaint()[i].getColor();
	int distance = (int) (Math.pow(
		(Color.red(originalColor) - Color.red(fillColor)), 2)
		+ Math.pow(
			(Color.green(originalColor) - Color.green(fillColor)),
			2) + Math.pow(
		(Color.blue(originalColor) - Color.blue(fillColor)), 2));
	double percent = (Math.pow(distance, 0.5) / Math.pow(Math.pow(255, 2)
		+ Math.pow(255, 2) + Math.pow(255, 2), 0.5)) * 100;
	Log.v("Distance",
		Integer.toString(i) + ":"
			+ Integer.toString((int) Math.pow(distance, 0.5)));
	Log.v("Percent Match",
		Integer.toString(i) + ":" + Double.toString(percent));
	if (percent < 12) {
	    return true;
	} else {
	    return false;
	}

    }

}

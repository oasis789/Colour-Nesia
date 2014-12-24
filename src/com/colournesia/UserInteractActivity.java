package com.colournesia;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colournesia.R;
import com.samsung.sdraw.SettingView;
import com.samsung.spen.settings.SettingFillingInfo;
import com.samsung.spensdk.SCanvasConstants;
import com.samsung.spensdk.SCanvasView;
import com.samsung.spensdk.applistener.SCanvasInitializeListener;
import com.samsung.spensdk.applistener.SPenTouchListener;
import com.samsung.spensdk.applistener.SettingFillingChangeListener;
import com.samsung.spensdk.applistener.SettingViewShowListener;

public class UserInteractActivity extends Activity {

    private int currentViewID;
    private ShapeView currentView;
    private LevelManager levelManager;
    private RelativeLayout msCanvasContainer;
    private SCanvasView mSCanvas;
    private Bitmap mBitmap;
    private TextView timerTextView;
    private TextView scoreTextView;
    private float xTouch, yTouch;
    private Rect[] rect;
    PaintManager paintManager;
    private SettingFillingInfo fillingInfo;
    private SettingFillingInfo[] rectFillingInfo;
    private boolean[] rectangleTouched;
    private Canvas canvas;
    private CountDownTimer cdt;
    private long timeLeft;
    private Button doneButton;
    private boolean showAnswerScreen = false;
    private Animation shakeAnimation;
    private Context context;
    private ArrayList<Integer> drawBlank;
    private LinearLayout colourSelected;
    private RelativeLayout settingsView;
    LinearLayout b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	context = this;
	drawBlank = new ArrayList<Integer>();
	levelManager = LevelManager.getLevelManager(this);
	levelManager.onlyDrawBorder = true;
	paintManager = levelManager.paintManager;
	shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
	shakeAnimation.setRepeatCount(3);
	currentViewID = levelManager.currentViewID;
	// view = new ViewManager(this, VIEW_ID);
	setTitle("Level " + levelManager.currentLevel);
	setContentView(R.layout.userinteractactivity);
	init();
	// timerTextView.setTextSize(20);
	cdt = new CountDownTimer(levelManager.timeForGuess, 10) {

	    @Override
	    public void onFinish() {
		// TODO Show Time Finish Dialog
		timerTextView.setText("00:00");
		final Dialog d = new AlertDialog.Builder(context)
			.setTitle(
				"YOU FAILED LEVEL " + levelManager.currentLevel
					+ " !!!")
			.setMessage("Your score was: " + levelManager.score)
			.setNeutralButton("Save Your Score",
				new DialogInterface.OnClickListener() {

				    @Override
				    public void onClick(DialogInterface dialog,
					    int which) {
					levelManager.saveScore = true;
					Intent i = new Intent(context,
						ScoresActivity.class);
					startActivity(i);
					finish();
				    }
				})
			.setNegativeButton("Quit",
				new DialogInterface.OnClickListener() {

				    @Override
				    public void onClick(DialogInterface dialog,
					    int which) {
					// TODO Auto-generated method stub
					levelManager.reset();
					finish();
				    }
				})
			.setPositiveButton("Show Answer",
				new DialogInterface.OnClickListener() {

				    @Override
				    public void onClick(DialogInterface dialog,
					    int which) {
					// TODO Auto-generated method stub
					doneButton.setText("Save Your Score");
					Bitmap b = mSCanvas.getBitmap(true);
					Canvas canvas = new Canvas(b);
					Paint p = new Paint();
					p.setColor(Color.BLACK);
					p.setStyle(Style.STROKE);
					p.setStrokeWidth(5);
					// paintManager.SetStyleFilled();
					for (int i = 0; i < rect.length; i++) {
					    canvas.drawRect(
						    rect[i],
						    paintManager.returnPaint()[i]);
					    if (!MatchColors(i)) {
						p.setColor(Color.RED);
					    } else {
						p.setColor(Color.BLACK);
					    }
					    canvas.drawRect(rect[i], p);
					}
					mSCanvas.setCanvasBitmap(b);
					timerTextView.setText("Answer");
					showAnswerScreen = true;
					mSCanvas.showSettingView(
						SCanvasConstants.SCANVAS_SETTINGVIEW_FILLING,
						false);
					Toast.makeText(
						context,
						"Press the Back Key to Return to the Main Menu",
						Toast.LENGTH_SHORT).show();
				    }
				}).create();
		d.setCancelable(false);
		d.show();
	    }

	    @Override
	    public void onTick(long arg0) {
		timeLeft = arg0 / 10;
		String stimeLeft = null;
		String fractionOfSecond = null;
		String seconds = null;
		stimeLeft = String.valueOf(timeLeft);
		// Greater than 10s
		if (timeLeft > 1000) {
		    fractionOfSecond = stimeLeft.substring(2);
		    seconds = stimeLeft.substring(0, 2);
		} else if (timeLeft > 100) {
		    // Greater than 1s
		    fractionOfSecond = stimeLeft.substring(1);
		    seconds = "0" + stimeLeft.substring(0, 1);
		    if (timeLeft > 500 && timeLeft < 510) {
			timerTextView.setTextColor(Color.RED);
			Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			v.vibrate(350);
			mSCanvas.startAnimation(shakeAnimation);
		    }
		} else if (timeLeft > 10) {
		    // Greater than 0.1s
		    fractionOfSecond = stimeLeft.substring(0);
		    seconds = "00";
		} else {
		    // Less than 0.1s
		    fractionOfSecond = "0" + stimeLeft.substring(0);
		    seconds = "00";
		}

		timerTextView.setText(seconds + ":" + fractionOfSecond);
		// timerTextView.setGravity(Gravity.CENTER);
	    }

	}.start();
    }

    @Override
    protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	finish();
	cdt.cancel();
    }

    @Override
    public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
	levelManager.reset();
	finish();

    }

    private void init() {
	// TODO Auto-generated method stub
	doneButton = (Button) findViewById(R.id.bDone);
	doneButton.setOnClickListener(doneButtonListener);
	timerTextView = (TextView) findViewById(R.id.tvTimer);
	scoreTextView = (TextView) findViewById(R.id.tvScore);
	scoreTextView.setText(Integer.toString(levelManager.score));
	mSCanvas = new SCanvasView(this);
	msCanvasContainer = (RelativeLayout) findViewById(R.id.canvas_container);
	b = (LinearLayout) findViewById(R.id.llBlackLine);
	GetCurrentView();
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
		DisplayMetrics displayMetrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) getApplicationContext()
			.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(displayMetrics);
		int screenWidth = displayMetrics.widthPixels;
		int screenHeight = displayMetrics.heightPixels;
		RelativeLayout parent = (RelativeLayout) findViewById(R.id.userinteractll);
		LinearLayout top = (LinearLayout) findViewById(R.id.topll);
		currentView.layout(mSCanvas.getLeft(), mSCanvas.getTop(),
			mSCanvas.getRight(), mSCanvas.getBottom());
		mBitmap = Bitmap.createBitmap(currentView.getWidth(),
			currentView.getHeight(), Bitmap.Config.ARGB_8888);
		canvas = new Canvas(mBitmap);
		currentView.draw(canvas);
		mSCanvas.setCanvasBitmap(mBitmap);
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
	    if(!arg0){
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

    private void GetCurrentView() {
	// TODO Auto-generated method stub
	// TODO Auto-generated method stub
	String className = Constants.viewIDtoViewName.get(currentViewID);
	Log.v("MainActivity", className);
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
		    paintManager });
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

    public SettingFillingChangeListener fillingChangeListener = new SettingFillingChangeListener() {

	@Override
	public void onFillingColorChanged(int arg0) {
	    // TODO Auto-generated method stub
	    if (!showAnswerScreen) {
		if (fillingInfo != null) {
		    fillingInfo.setFillingColor(arg0);
		    b.setBackgroundColor(arg0);
		    // colourSelected.setBackgroundResource(R.drawable.border);
		}
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
	    if (!showAnswerScreen) {
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
		    }
		}

		if (touched) {
		    return false;
		} else {
		    return true;
		}

	    } else {
		return true;
	    }

	}

	@Override
	public boolean onTouchPen(View arg0, MotionEvent arg1) {
	    // TODO Auto-generated method stub
	    if (!showAnswerScreen) {
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
		    }
		}

		if (touched) {
		    return false;
		} else {
		    return true;
		}
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
	    if (!showAnswerScreen) {
		boolean matched = true;
		boolean touched = true;
		drawBlank.clear();
		for (int i = 0; i < rect.length; i++) {
		    if (!rectangleTouched[i]) {
			touched = false;
		    } else if (!MatchColors(i)) {
			matched = false;
			drawBlank.add(i);
		    }
		}

		if (matched && touched) {
		    Toast.makeText(context, "You Won", Toast.LENGTH_SHORT)
			    .show();
		    levelManager.score += timeLeft * levelManager.difficulty;
		    cdt.cancel();
		    Dialog d = new AlertDialog.Builder(context)
			    .setTitle(
				    "You Completed Level "
					    + levelManager.currentLevel)
			    .setMessage(
				    "You finished level "
					    + levelManager.currentLevel
					    + " with " + timeLeft / 100
					    + " seconds remaining"
					    + "\nYour score is: "
					    + levelManager.score)
			    .setPositiveButton("Next Level",
				    new DialogInterface.OnClickListener() {

					@Override
					public void onClick(
						DialogInterface dialog,
						int which) {
					    // TODO Auto-generated method stub
					    levelManager.update();
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
					    levelManager.reset();
					    finish();
					}
				    }).create();
		    d.setCancelable(false);
		    d.show();

		} else {
		    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		    v.vibrate(250);
		    if (!touched) {
			Toast.makeText(context,
				"Please Fill All The Rectangles!!",
				Toast.LENGTH_SHORT).show();
		    } else if (!matched) {
			Toast.makeText(context, "Try Again!!!",
				Toast.LENGTH_SHORT).show();
			Bitmap bitmap = Bitmap.createBitmap(
				currentView.getWidth(),
				currentView.getHeight(),
				Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			mSCanvas.clearAll(false);
			currentView.setBlankRect(drawBlank, canvas);
			mSCanvas.setCanvasBitmap(bitmap);
		    }
		}
	    } else {
		levelManager.saveScore = true;
		Intent i = new Intent(context, ScoresActivity.class);
		startActivity(i);
		finish();
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

package com.colournesia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import android.content.Context;
import android.util.Log;

public class LevelManager {

    public int currentViewID;
    public int currentLevel;
    public int timeShown;
    public int timeForGuess;
    public int colorPercentMatch;
    public int score;
    public Context context;
    public PaintManager paintManager;
    public int noOfRects;
    public Random random;
    public int ranNum;
    public Iterator it;
    public int difficulty;
    public boolean onlyDrawBorder;
    public boolean saveScore;
    ArrayList<Integer> currentViewIDArrayList = new ArrayList<Integer>();
    public boolean sPenEnabled;
    public int noOfRetries;

    // No getter methods so there is only one instance of each of the
    // variables!!!!

    // Constructor for Singleton Object ie the first time it is created
    private LevelManager(Context context) {
	this.context = context;
	random = new Random();
	reset();
    }

    public void reset() {
	currentViewID = 1;
	currentLevel = 1;
	score = 0;
	noOfRetries = 0;
	timeShown = Constants.EASY_TIME_SHOWN;
	timeForGuess = Constants.EASY_TIME_FOR_GUESS;
	colorPercentMatch = Constants.EASY_LEVEL_COLOR_PERCENT;
	difficulty = Constants.DIFFICULTY_EASY;
	it = Constants.noOfRectstoViewIDSorted.entrySet().iterator();
	currentViewIDArrayList = new ArrayList<Integer>();
	AssignView();
    }

    public void retry() {
	noOfRetries++;
	paintManager = new PaintManager(noOfRects, colorPercentMatch);
    }

    private static LevelManager levelManager;

    public static synchronized LevelManager getLevelManager(Context context) {
	if (levelManager == null) {
	    levelManager = new LevelManager(context);
	}

	return levelManager;
    }

    private void AssignView() {
	// TODO Auto-generated method stub
	if (currentViewIDArrayList.size() != 0) {
	    getViewFromList();
	} else {
	    if (it.hasNext()) {
		Map.Entry entry = (Map.Entry) it.next();
		noOfRects = (Integer) entry.getKey();
		Log.v("LevelManager", "No of Rects: " + noOfRects);
		Integer[] currentViewIDArray = (Integer[]) entry.getValue();
		for (int index = 0; index < currentViewIDArray.length; index++) {
		    currentViewIDArrayList.add(currentViewIDArray[index]);
		}
		getViewFromList();
		Log.v("LevelManager", "Current View ID: " + currentViewID);
	    } else {
		// TODO: When all the views have been shown at least once
		Log.v("LevelManager", "Iterator does not have a next value");
	    }
	}

	if (currentViewID == 12) {
	    noOfRects -= 2;
	}

	paintManager = new PaintManager(noOfRects, colorPercentMatch);

    }

    private void getViewFromList() {
	ranNum = random.nextInt(currentViewIDArrayList.size());
	currentViewID = currentViewIDArrayList.get(ranNum);
	currentViewIDArrayList.remove(ranNum);
    }

    private void checkLevels() {
	if (currentLevel > Constants.MEDIUM_LEVEL
		&& currentLevel < Constants.HARD_LEVEL) {
	    timeShown = Constants.MEDIUM_TIME_SHOWN;
	    timeForGuess = Constants.MEDIUM_TIME_FOR_GUESS;
	    colorPercentMatch = Constants.MEDIUM_LEVEL_COLOR_PERCENT;
	    difficulty = Constants.DIFFICULTY_MEDIUM;

	}
	if (currentLevel > Constants.HARD_LEVEL) {
	    timeShown = Constants.HARD_TIME_SHOWN;
	    timeForGuess = Constants.HARD_TIME_FOR_GUESS;
	    colorPercentMatch = Constants.HARD_LEVEL_COLOR_PERCENT;
	    difficulty = Constants.DIFFICULTY_HARD;
	}
    }

    public void update() {
	currentLevel += 1;
	noOfRetries = 0;
	Log.v("LevelManager", "Updating level, Next level is: " + currentLevel);
	checkLevels();
	AssignView();

    }

}

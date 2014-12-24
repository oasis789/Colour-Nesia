package com.colournesia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.util.Log;

public class ColorManager {

	private int[] colors;
	private int noOfColors;
	private Random random;
	private List<Integer> listOfColors;
	private int colorPercentMatch;

	public ColorManager(int noOfColors, int colorPercentMatch) {
		this.noOfColors = noOfColors;
		this.colorPercentMatch = colorPercentMatch;
		colors = new int[noOfColors];
		random = new Random();
		listOfColors = new ArrayList<Integer>();
		for (int i = 0; i < Constants.NO_OF_COLORS; i++) {
			listOfColors.add(Constants.COLORS[i]);
		}
		addColors();
	}

	private void addColors() {
		// TODO Auto-generated method stub
		for (int i = 0; i < noOfColors; i++) {
			int r = random.nextInt(Constants.NO_OF_COLORS - i);
			colors[i] = listOfColors.get(r);
			if (i > 2) {
				while (!MatchColors(colors[i], colors[i - 1])) {
					r = random.nextInt(listOfColors.size());
					colors[i] = listOfColors.get(r);
				}
			}
			Log.v("ColorManager", "Color Number " + i + " : " + colors[i]);
			listOfColors.remove(r);
		}

	}

	public int[] returnColors() {
		return colors;
	}

	private boolean MatchColors(int c1, int c2) {
		// TODO Auto-generated method stub
		int distance = (int) (Math.pow((Color.red(c2) - Color.red(c1)), 2)
				+ Math.pow((Color.green(c2) - Color.green(c1)), 2) + Math.pow(
				(Color.blue(c2) - Color.blue(c1)), 2));
		double percent = (Math.pow(distance, 0.5) / Math.pow(Math.pow(255, 2)
				+ Math.pow(255, 2) + Math.pow(255, 2), 0.5)) * 100;
		Log.v("ColorManager", "Percent = " + percent);
		if (percent > colorPercentMatch) {
			Log.v("ColorManager", "Match Colors returns True ");
			return true;
		} else {
			Log.v("ColorManager", "Match Colors returns False");
			return false;
		}

	}
}

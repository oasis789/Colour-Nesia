package com.colournesia;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Constants {

	public static final int NO_OF_COLORS = 14;
	public static final int NO_OF_VIEWS = 13;

	public static final TreeMap<Integer, Integer[]> noOfRectstoViewIDSorted = createViewIDMap();

	private static TreeMap<Integer, Integer[]> createViewIDMap() {
		TreeMap<Integer, Integer[]> map = new TreeMap<Integer, Integer[]>();
		
		map.put(1, new Integer[]{1});// Key: No Of Rects = 1; Value: ViewID = 1;
		map.put(2, new Integer[]{3,5});
		map.put(3, new Integer[]{6,9});
		map.put(4, new Integer[]{2,4,7,8,11,13});
		map.put(5, new Integer[]{10,12});

		return map;
	}

	public static final Map<Integer, String> viewIDtoViewName = createViewNameMap();

	private static Map<Integer, String> createViewNameMap() {
		// TODO Auto-generated method stub
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "OneSquaredShapeView");// Key: ViewID-1,
											// Value:FourSquaredShapeView
		map.put(2, "FourSquaredShapeView");
		map.put(3, "TwoRectangleShapeView");
		map.put(4, "FourRectangleShapeView");
		map.put(5, "TRectangleShapeView");
		map.put(6, "IRectangleShapeView");
		map.put(7, "JRectangleShapeView");
		map.put(8, "ERectangleShapeView");
		map.put(9, "FRectangleShapeView");
		map.put(10, "FiveRectangleDecreasingShapeView");
		map.put(11, "FaceRectangleShapeView");
		map.put(12, "CarRectangleShapeView");
		map.put(13, "PyramidRectangleShapeView");

		return map;
	}

	public static final int YELLOW = -131283;
	public static final int ORANGE = -31907;
	public static final int RED = -65536;
	public static final int PINK = -46647;
	public static final int LILAC = -3504641;
	public static final int LT_BLUE = -13063937;
	public static final int DK_BLUE = -16776961;
	public static final int GREEN = -15283079;
	public static final int DK_GREEN = -16673746;
	public static final int SWP_GREEN = -16488658;
	public static final int LT_GRAY = -5921371;
	public static final int GRAY = -9276814;
	public static final int BLACK = -15527149;
	public static final int WHITE = -1;
	public static final int[] COLORS = { YELLOW, ORANGE, RED, PINK, LILAC,
			LT_BLUE, DK_BLUE, GREEN, DK_GREEN, SWP_GREEN, LT_GRAY, GRAY, BLACK,
			WHITE };

	public static final int DIFFICULTY_EASY = 1;
	public static final int DIFFICULTY_MEDIUM = 2;
	public static final int DIFFICULTY_HARD = 3;

	public static final int MEDIUM_LEVEL = 5;
	public static final int HARD_LEVEL = 10;

	public static final int MEDIUM_LEVEL_COLOR_PERCENT = 25;
	public static final int HARD_LEVEL_COLOR_PERCENT = 10;
	public static final int EASY_LEVEL_COLOR_PERCENT = 50;

	public static final int EASY_TIME_SHOWN = 2000;
	public static final int MEDIUM_TIME_SHOWN = 1300;
	public static final int HARD_TIME_SHOWN = 900;

	public static final int EASY_TIME_FOR_GUESS = 20000;
	public static final int MEDIUM_TIME_FOR_GUESS = 13000;
	public static final int HARD_TIME_FOR_GUESS = 7000;

}

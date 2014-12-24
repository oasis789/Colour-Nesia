package com.colournesia;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class PaintManager {

	private Paint[] paint;
	private int noOfPaint;
	private ColorManager colorManager;
	private int[] colors;
	private int colorPercentMatch;
	private Paint[] strokePaint;

	public PaintManager(int noOfPaint, int colorPercentMatch) {
		this.noOfPaint = noOfPaint;
		this.colorPercentMatch = colorPercentMatch;
		paint = new Paint[noOfPaint];
		strokePaint = new Paint[noOfPaint];
		colorManager = new ColorManager(noOfPaint, colorPercentMatch);
		colors = new int[noOfPaint];
		FillPaintWithColor();
	}

	public PaintManager(Paint[] paint) {
		this.paint = paint;
	}

	private void FillPaintWithColor() {
		// TODO Auto-generated method stub
		colors = colorManager.returnColors();
		for (int i = 0; i < noOfPaint; i++) {
			paint[i] = new Paint();
			paint[i].setColor(colors[i]);
		}
	}

	public void SetStyleFilled() {
		for (int i = 0; i < noOfPaint; i++) {
			paint[i].setStyle(Style.FILL_AND_STROKE);
		}
	}                                                                                                                                                                                                                               

	public Paint[] getStyleStroked() {
		for (int i = 0; i < noOfPaint; i++) {
			strokePaint[i] = new Paint();
			strokePaint[i].setStyle(Style.STROKE);
			strokePaint[i].setColor(Color.BLACK);
			strokePaint[i].setStrokeWidth(5);
		}

		return strokePaint;
	}

	public Paint[] returnPaint() {
		return paint;
	}

}

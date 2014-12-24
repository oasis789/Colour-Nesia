package com.colournesia;

import android.content.Context;
import android.graphics.Rect;

public class PyramidRectangleShapeView extends ShapeView {

	private static final int VIEW_ID = 13;
	int NO_OF_RECT = 4;
	private Rect[] rect = new Rect[NO_OF_RECT];


	public PyramidRectangleShapeView(Context context, PaintManager paintManager) {
		super(context, VIEW_ID, paintManager);
		
	}
	
	private int w,h;
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		this.w = w;
		this.h = h;
		rect[0] = new Rect(w / 8, h * 11 / 16, w * 7 / 8, h * 7 / 8);
		rect[1] = new Rect(w * 7 / 32, h / 2, w * 25 / 32, h * 11 / 16);
		rect[2] = new Rect(w * 5 / 16, h * 5 / 16, w * 11 / 16, h / 2);
		rect[3] = new Rect(w * 13 / 32, h / 8, w * 19 / 32, h * 5 / 16);
		setRect(rect);
		
	}

}

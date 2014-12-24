package com.colournesia;

import android.content.Context;
import android.graphics.Rect;

public class FiveRectangleDecreasingShapeView extends ShapeView {


	private static final int VIEW_ID = 10;
	int NO_OF_RECT = 5;
	private Rect[] rect = new Rect[NO_OF_RECT];


	public FiveRectangleDecreasingShapeView(Context context, PaintManager paintManager) {
		super(context, VIEW_ID, paintManager);
		

	}
	
	private int w,h;
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		this.w = w;
		this.h = h;
		rect[0] = new Rect(w / 16, h / 16, w * 15 / 16, h * 3 / 16);
		rect[1] = new Rect(w / 16, h / 4, w * 3 / 4, h * 3 / 8);
		rect[2] = new Rect(w / 16, h * 7 / 16, w * 9 / 16, h * 9 / 16);
		rect[3] = new Rect(w / 16, h * 5 / 8, w * 3 / 8, h * 3 / 4);
		rect[4] = new Rect(w / 16, h * 13 / 16, w * 3 / 16, h * 15 / 16);
		setRect(rect);
		
	}
}

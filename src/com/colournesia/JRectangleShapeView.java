package com.colournesia;

import android.content.Context;
import android.graphics.Rect;

public class JRectangleShapeView extends ShapeView {

	private static final int VIEW_ID = 7;
	int NO_OF_RECT = 4;
	private Rect[] rect = new Rect[NO_OF_RECT];


	public JRectangleShapeView(Context context, PaintManager paintManager) {
		super(context, VIEW_ID, paintManager);
		
	}
	
	private int w,h;
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		this.w = w;
		this.h = h;
		rect[0] = new Rect(w / 8, h / 8, w * 7 / 8, h / 4);
		rect[1] = new Rect(w * 7 / 16, h / 4, w * 9 / 16, h * 3 / 4);
		rect[2] = new Rect(w / 8, h * 3 / 4, w * 9 / 16, h * 27 / 32);
		rect[3] = new Rect(w / 8, h / 2, w / 4, h * 3 / 4);
		setRect(rect);
		
	}

}

package com.colournesia;

import android.content.Context;
import android.graphics.Rect;

public class FaceRectangleShapeView extends ShapeView {

	private static final int VIEW_ID = 11;
	int NO_OF_RECT = 4;
	private Rect[] rect = new Rect[NO_OF_RECT];

	public FaceRectangleShapeView(Context context, PaintManager paintManager) {
		super(context, VIEW_ID, paintManager);
		

	}
	
	private int w,h;
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		this.w = w;
		this.h = h;
		rect[0] = new Rect(w / 8, h / 8, w * 3 / 8, h * 5 / 16);
		rect[1] = new Rect(w * 5 / 8, h / 8, w * 7 / 8, h * 5 / 16);
		rect[2] = new Rect(w * 3 / 8, h * 13 / 32, w * 5 / 8, h * 19 / 32);
		rect[3] = new Rect(w / 8, h * 11 / 16, w * 7 / 8, h * 7 / 8);
		setRect(rect);;
		
	}
}

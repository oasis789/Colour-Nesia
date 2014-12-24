package com.colournesia;

import android.content.Context;
import android.graphics.Rect;

public class FourSquaredShapeView extends ShapeView {

	private static final int VIEW_ID = 2;
	int NO_OF_RECT = 4;
	private Rect[] rect = new Rect[NO_OF_RECT];
	
	public FourSquaredShapeView(Context context, PaintManager paintManager) {
		super(context, VIEW_ID, paintManager);
		
	}
	
	private int w,h;
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		this.w = w;
		this.h = h;
		rect[0] = new Rect(w / 4, h / 4, w / 2, h / 2);
		rect[1] = new Rect(w / 4, h / 2, w / 2, h * 3 / 4);
		rect[2] = new Rect(w / 2, h / 4, w * 3 / 4, h / 2);
		rect[3] = new Rect(w / 2, h / 2, w * 3 / 4, h * 3 / 4);
		setRect(rect);
		
	}


}

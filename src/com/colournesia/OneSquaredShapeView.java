package com.colournesia;

import android.content.Context;
import android.graphics.Rect;

public class OneSquaredShapeView extends ShapeView {

    private static final int VIEW_ID = 1;
    private static final int NO_OF_RECT = 1;
    private Rect[] rect = new Rect[NO_OF_RECT];

    public OneSquaredShapeView(Context context, PaintManager paintManager) {
	super(context, VIEW_ID, paintManager);
    }

    private int w, h;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	// TODO Auto-generated method stub
	super.onSizeChanged(w, h, oldw, oldh);
	this.w = w;
	this.h = h;
	rect[0] = new Rect(w / 4, h / 4, w * 3 / 4, h * 3 / 4);
	setRect(rect);

    }

}

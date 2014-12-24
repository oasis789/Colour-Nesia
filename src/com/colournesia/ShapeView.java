package com.colournesia;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

public class ShapeView extends View {

    private int VIEW_ID;
    public PaintManager paintManager;
    private int w;
    private int h;
    private Rect[] rect;
    private Paint[] strokePaint;
    LevelManager levelManager;
    boolean drawBlank = false;
    ArrayList<Integer> drawBlankRect;

    public ShapeView(Context context, int viewID, PaintManager paintManager) {
	super(context);
	this.VIEW_ID = viewID;
	this.paintManager = paintManager;
	levelManager = LevelManager.getLevelManager(context);
	rect = new Rect[paintManager.returnPaint().length];
	strokePaint = new Paint[this.paintManager.returnPaint().length];
	// setBackgroundResource(R.drawable.color_rectangle);
	setFocusable(true);
	setFocusableInTouchMode(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	// TODO Auto-generated method stub
	super.onSizeChanged(w, h, oldw, oldh);
	this.w = w;
	this.h = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
	super.onDraw(canvas);
	paintManager.SetStyleFilled();
	strokePaint = paintManager.getStyleStroked();

	if (!levelManager.onlyDrawBorder) {

	    if (drawBlank) {
		for (int i = 0; i < paintManager.returnPaint().length; i++) {
		    if (!drawBlankRect.contains(i)) {
			Log.v("ShapeView", "Rect: " + i
				+ "is being drawn fully");
			DrawRects(canvas, i);
		    }
		}
		drawBlank = false;
	    } else {
		for (int i = 0; i < paintManager.returnPaint().length; i++) {
		    DrawRects(canvas, i);
		}

	    }

	}

	for (int i = 0; i < paintManager.returnPaint().length; i++) {
	    DrawBorder(canvas, i);
	}

    }

    private void DrawRects(Canvas canvas, int i) {
	// TODO Auto-generated method stub
	// Log.v("ShapeView", "Rectangle No:" + i + " Coords(l,t,r,b): "
	// + rect[i].left + ", " + rect[i].top + ", " + rect[i].right
	// + ", " + rect[i].bottom);
	canvas.drawRect(rect[i], paintManager.returnPaint()[i]);
	// Log.v("ShapeView",
	// "Drawing Filled Rectangle: " + i + ", with Color: "
	// + paintManager.returnPaint()[i].getColor());

    }

    public int getViewID() {
	return VIEW_ID;
    }

    public void DrawBorder(Canvas canvas, int i) {
	// TODO Auto-generated method stub
	canvas.drawRect(rect[i], strokePaint[i]);
	// Log.v("ShapeView", "Drawing Border of Rectangle " + i);

    }
    
    public Rect[] getRect() {
	return rect;
    }

    public void setRect(Rect[] rect) {
	this.rect = rect;
    }

    public void setBlankRect(ArrayList<Integer> arrayList, Canvas canvas) {
	levelManager.onlyDrawBorder = false;
	drawBlank = true;
	drawBlankRect = arrayList;
	onDraw(canvas);
	levelManager.onlyDrawBorder = true;
    }

}

package com.colournesia;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;

public class CarRectangleShapeView extends ShapeView {


	private static final int VIEW_ID = 12;
	int NO_OF_RECT = 3;
	private Random random;
	private int ranNum;
	private ArrayList<Integer> availableColors;
	private Rect[] rect = new Rect[NO_OF_RECT];


	public CarRectangleShapeView(Context context, PaintManager paintManager) {
		super(context, VIEW_ID, paintManager);
		
		random = new Random();
		getAvailableColors();
		ranNum = random.nextInt(availableColors.size());
	}
	
	private int w,h;
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		this.w = w;
		this.h = h;
		rect[0] = new Rect(w/8, h *17/64, w /2, h / 2);
		rect[1] = new Rect(w / 8, h / 2, w* 7/8, h * 5 / 8);
		rect[2] = new Rect(w /16, h * 19 / 32, w /8, h * 5 / 8);
		setRect(rect);
		
	}


	private void getAvailableColors() {
		availableColors = new ArrayList<Integer>();
		for (int i = 0; i < Constants.NO_OF_COLORS; i++) {
			availableColors.add(Constants.COLORS[i]);
		}
		for (int i = 0; i < NO_OF_RECT; i++) {
			if (availableColors.contains(paintManager.returnPaint()[i].getColor())) {
				availableColors.remove(paintManager.returnPaint()[i]);
			}
		}
	}



	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint p = new Paint();
		p.setColor(availableColors.get(ranNum));
		p.setStyle(Style.FILL);
		canvas.drawCircle((float) w * 5 / 16, (float) h * 11 / 16, h / 16,
				p);
		ranNum = random.nextInt(availableColors.size());
		p.setColor(availableColors.get(ranNum));
		canvas.drawCircle((float) w * 11 / 16, (float) h * 11 / 16, h / 16,
				p);
		p.setColor(Color.BLACK);
		p.setStyle(Style.STROKE);
		p.setStrokeWidth(5);
		canvas.drawCircle((float) w * 5 / 16, (float) h * 11 / 16, h / 16,
				p);
		canvas.drawCircle((float) w * 11 / 16, (float) h * 11 / 16, h / 16,
				p);
	}



}

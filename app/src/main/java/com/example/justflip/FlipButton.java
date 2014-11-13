package com.example.justflip;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

public class FlipButton extends Button {
	
	public static final int UP_COLOR = Color.parseColor("#2c3e50");   // midnight blue #2c3e50
	public static final int DOWN_COLOR = Color.parseColor("#ecf0f1"); // white
	
	public int upside; // 1 => up , 0 => down

	public FlipButton(Context context) {
		super(context);
		
		// determine whether "tile" is initially flip up or down
		upside = (int) Math.round(Math.random());
		
		// set corresponding color
		this.setColor();
		
	}
	
	public void setColor() {
		
		if (upside == 1) {
			this.setBackgroundColor(UP_COLOR);
		} else if (upside == 0) {
			this.setBackgroundColor(DOWN_COLOR);
		}
		
	}
	
	public void flipButton() {
		
		if (upside == 1) {
			upside = 0;
		} else if (upside == 0) {
			upside = 1;
		}
		
		this.setColor();
		
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	    final int width = getMeasuredWidth();

	    setMeasuredDimension(width, width);
	    
	}

}

package edu.byui.cit.maintenance;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;


public class CarButton extends AppCompatButton {

	public CarButton(Context context) {
		super(context);
	}

	public CarButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CarButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override

	public void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec / 4 );
		int height = (int)Math.round(width * 0.75);
		setMeasuredDimension(width, height);
	}

}


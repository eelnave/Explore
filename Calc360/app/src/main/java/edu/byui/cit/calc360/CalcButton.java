package edu.byui.cit.calc360;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;


public class CalcButton extends AppCompatButton {
	public CalcButton(Context context) {
		super(context);
	}

	public CalcButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CalcButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = (int)Math.round(width * 1.05);
		setMeasuredDimension(width, height);
	}
}

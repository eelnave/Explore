package edu.byui.cit.kindness;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;


public final class IconButton extends AppCompatButton {
	public IconButton(Context context) {
		super(context);
	}

	public IconButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public IconButton(Context context, AttributeSet attrs, int defStyleAttr) {
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

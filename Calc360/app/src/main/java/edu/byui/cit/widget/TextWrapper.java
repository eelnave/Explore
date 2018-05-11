package edu.byui.cit.widget;

import android.view.View;
import android.widget.TextView;


public final class TextWrapper extends WidgetWrapper {
	private final TextView view;

	public TextWrapper(View parent, int resID) {
		super(parent, resID);
		this.view = parent.findViewById(resID);
	}

	@Override
	public final TextView getView() {
		return view;
	}

	@Override
	public final boolean isEnabled() {
		return view.isEnabled();
	}

	@Override
	public final void setEnabled(boolean enabled) {
		view.setEnabled(enabled);
	}

	@Override
	public final boolean hasFocus() {
		return view.hasFocus();
	}

	@Override
	public final void requestFocus() {
		view.requestFocus();
	}

	@Override
	public final void setText(CharSequence str) {
		view.setText(str);
	}

	public final void setText(int resID) {
		view.setText(resID);
	}

	@Override
	public final void clear() {
		view.setText(null);
	}
}

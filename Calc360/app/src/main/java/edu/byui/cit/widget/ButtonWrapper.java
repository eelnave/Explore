package edu.byui.cit.widget;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import edu.byui.cit.calc360.Calc360;


public final class ButtonWrapper extends WidgetWrapper
		implements OnClickListener {
	private final Button button;
	private final ClickListener listener;

	public ButtonWrapper(View parent, int resID, ClickListener listener) {
		super(parent, resID);
		this.button = parent.findViewById(resID);
		this.listener = listener;
		button.setOnClickListener(this);
	}

	@Override
	public final Button getView() {
		return button;
	}

	@Override
	public final boolean isEnabled() {
		return button.isEnabled();
	}

	@Override
	public final void setEnabled(boolean enabled) {
		button.setEnabled(enabled);
	}

	@Override
	public final boolean hasFocus() {
		return button.hasFocus();
	}

	@Override
	public final void requestFocus() {
		button.requestFocus();
	}

	@Override
	public final void onClick(View button) {
		try {
			listener.clicked(this);
		}
		catch (NumberFormatException ex) {
			// Do nothing.
		}
		catch (Exception ex) {
			Log.e(Calc360.TAG, "exception", ex);
		}
	}
}

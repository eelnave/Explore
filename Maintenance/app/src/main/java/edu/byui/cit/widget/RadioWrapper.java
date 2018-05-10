package edu.byui.cit.widget;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

import edu.byui.cit.maintenance.MainActivity;


public final class RadioWrapper extends WidgetWrapper implements OnClickListener {
	private final RadioButton radio;
	private final ClickListener listener;

	public RadioWrapper(View parent, int resID,
			String prefsKey, ClickListener listener) {
		super(parent, resID);
		this.radio = parent.findViewById(resID);
		this.listener = listener;
		radio.setOnClickListener(this);
	}

	public final boolean isChecked() {
		return radio.isChecked();
	}

	public final boolean performClick() {
		return radio.performClick();
	}

	@Override
	public final boolean isEnabled() {
		return radio.isEnabled();
	}

	@Override
	public final void setEnabled(boolean enabled) {
		radio.setEnabled(enabled);
	}

	@Override
	public final boolean hasFocus() {
		return radio.hasFocus();
	}

	@Override
	public final void requestFocus() {
		radio.requestFocus();
	}

	@Override
	public final void onClick(View button) {
		try {
			listener.clicked(button);
		}
		catch (Exception ex) {
			Log.e(MainActivity.TAG, "exception", ex);
		}
	}
}

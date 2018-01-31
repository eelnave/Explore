package edu.byui.cit.text;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

import edu.byui.cit.calc360.CalcFragment;


public final class RadioWrapper extends ControlWrapper implements OnClickListener {
	private final RadioButton radio;
	private final ClickListener listener;


	public RadioWrapper(View parent, int resID, CalcFragment calculator) {
		this(parent, resID, calculator, null);
	}

	public RadioWrapper(View parent, int resID, ClickListener listener) {
		this(parent, resID, null, listener);
	}

	private RadioWrapper(View parent, int resID,
			CalcFragment calculator, ClickListener listener) {
		super(parent, resID, calculator);
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
		if (calculator != null) {
			calculator.callCompute();
		}
		else {
			listener.clicked(button);
		}
	}
}

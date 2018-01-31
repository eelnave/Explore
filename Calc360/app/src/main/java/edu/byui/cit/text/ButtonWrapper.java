package edu.byui.cit.text;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import edu.byui.cit.calc360.CalcFragment;


public final class ButtonWrapper extends ControlWrapper implements OnClickListener {
	private final Button button;
	private final ClickListener listener;

	public ButtonWrapper(View parent, int resID, CalcFragment calculator) {
		this(parent, resID, calculator, null);
	}

	public ButtonWrapper(View parent, int resID, ClickListener listener) {
		this(parent, resID, null, listener);
	}

	private ButtonWrapper(View parent, int resID,
			CalcFragment calculator, ClickListener listener) {
		super(parent, resID, calculator);
		this.button = parent.findViewById(resID);
		this.listener = listener;
		button.setOnClickListener(this);
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
		if (calculator != null) {
			calculator.callCompute();
		}
		else {
			listener.clicked(button);
		}
	}
}

package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.EditDecimal;


public final class FiveFunction extends CalcFragment {
	private final NumberFormat fmtrDec;
	private EditText decDisplay;
	private final BinOp noOp, add, subt, mult, div;
	private double left;
	private BinOp operator;

	public FiveFunction() {
		super();
		fmtrDec = NumberFormat.getInstance();
		fmtrDec.setMinimumFractionDigits(0);
		noOp = new NoOp();
		add = new Add();
		subt = new Subt();
		mult = new Mult();
		div = new Div();
		operator = noOp;
		left = 0;
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.five_function, container, false);

		decDisplay = view.findViewById(R.id.decDisplay);

		OnClickListener clear = new ClearClick();
		view.findViewById(R.id.btnC).setOnClickListener(clear);
		view.findViewById(R.id.btnCE).setOnClickListener(new ClearEntryClick());

		view.findViewById(R.id.btn0).setOnClickListener(new DigitClick('0'));
		view.findViewById(R.id.btn1).setOnClickListener(new DigitClick('1'));
		view.findViewById(R.id.btn2).setOnClickListener(new DigitClick('2'));
		view.findViewById(R.id.btn3).setOnClickListener(new DigitClick('3'));
		view.findViewById(R.id.btn4).setOnClickListener(new DigitClick('4'));
		view.findViewById(R.id.btn5).setOnClickListener(new DigitClick('5'));
		view.findViewById(R.id.btn6).setOnClickListener(new DigitClick('6'));
		view.findViewById(R.id.btn7).setOnClickListener(new DigitClick('7'));
		view.findViewById(R.id.btn8).setOnClickListener(new DigitClick('8'));
		view.findViewById(R.id.btn9).setOnClickListener(new DigitClick('9'));
		view.findViewById(R.id.btnDec).setOnClickListener(new DecimalClick());

		view.findViewById(R.id.btnNeg).setOnClickListener(new NegClick());
		view.findViewById(R.id.btnSqrt).setOnClickListener(new SqrtClick());
		view.findViewById(R.id.btnDiv).setOnClickListener(new DivClick());
		view.findViewById(R.id.btnMult).setOnClickListener(new MultClick());
		view.findViewById(R.id.btnSub).setOnClickListener(new SubtClick());
		view.findViewById(R.id.btnAdd).setOnClickListener(new AddClick());
		view.findViewById(R.id.btnEq).setOnClickListener(new EqClick());

		clear.onClick(null);
		return view;
	}


	private final class ClearClick implements OnClickListener {
		@Override
		public void onClick(View button) {
			decDisplay.setText("0");
			operator = noOp;
			left = 0;
		}
	}

	private final class ClearEntryClick implements OnClickListener {
		@Override
		public void onClick(View button) {
			decDisplay.setText("0");
		}
	}


	private abstract class BinOp {
		abstract void compute();
	}

	private final class NoOp extends BinOp {
		@Override
		void compute() {
			left = EditDecimal.getDec(decDisplay);
		}
	}

	private final class Add extends BinOp {
		@Override
		void compute() {
			double right = EditDecimal.getDec(decDisplay);
			double result = left + right;
			decDisplay.setText(fmtrDec.format(result));
			left = result;
		}
	}

	private final class Subt extends BinOp {
		@Override
		void compute() {
			double right = EditDecimal.getDec(decDisplay);
			double result = left - right;
			decDisplay.setText(fmtrDec.format(result));
			left = result;
		}
	}

	private final class Mult extends BinOp {
		@Override
		void compute() {
			double right = EditDecimal.getDec(decDisplay);
			double result = left * right;
			decDisplay.setText(fmtrDec.format(result));
			left = result;
		}
	}

	private final class Div extends BinOp {
		@Override
		void compute() {
			double right = EditDecimal.getDec(decDisplay);
			double result = left / right;
			decDisplay.setText(fmtrDec.format(result));
			left = result;
		}
	}


	private final class DigitClick implements OnClickListener {
		private final char digit;

		DigitClick(char digit) {
			this.digit = digit;
		}

		@Override
		public void onClick(View button) {
			String text = decDisplay.getText().toString();
			if (text.equals("0")) {
				decDisplay.setText(Character.toString(digit));
			}
			else if (countDigits(text) < 15) {
				decDisplay.getText().append(digit);
			}
		}
	}

	private static int countDigits(String s) {
		int n = 0;
		for (int i = 0; i < s.length(); ++i) {
			if (Character.isDigit(s.charAt(i))) {
				++n;
			}
		}
		return n;
	}


	private final class DecimalClick implements OnClickListener {
		@Override
		public void onClick(View button) {
			String text = decDisplay.getText().toString();
			text = text.replace(".", "") + '.';
			decDisplay.setText(text);
		}
	}


	private final class SqrtClick implements OnClickListener {
		@Override
		public void onClick(View button) {
			double number = Math.sqrt(EditDecimal.getDec(decDisplay));
			decDisplay.setText(fmtrDec.format(number));
		}
	}

	private final class NegClick implements OnClickListener {
		@Override
		public void onClick(View button) {
			double number = -EditDecimal.getDec(decDisplay);
			decDisplay.setText(fmtrDec.format(number));
		}
	}


	private final class DivClick implements OnClickListener {
		@Override
		public void onClick(View button) {
			operator.compute();
			operator = div;
			decDisplay.setText("0");
		}
	}

	private final class MultClick implements OnClickListener {
		@Override
		public void onClick(View button) {
			operator.compute();
			operator = mult;
			decDisplay.setText("0");
		}
	}

	private final class SubtClick implements OnClickListener {
		@Override
		public void onClick(View button) {
			operator.compute();
			operator = subt;
			decDisplay.setText("0");
		}
	}

	private final class AddClick implements OnClickListener {
		@Override
		public void onClick(View button) {
			operator.compute();
			operator = add;
			decDisplay.setText("0");
		}
	}

	private final class EqClick implements OnClickListener {
		@Override
		public void onClick(View button) {
			operator.compute();
			left = 0;
			operator = noOp;
		}
	}
}

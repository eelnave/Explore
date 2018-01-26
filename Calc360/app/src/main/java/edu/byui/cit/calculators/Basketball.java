package edu.byui.cit.calculators;

import java.text.NumberFormat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.byui.cit.calc360.OmniFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.text.ButtonWrapper;
import edu.byui.cit.text.ClickListener;
import edu.byui.cit.text.TextWrapper;


public final class Basketball extends OmniFragment {
	private final NumberFormat fmtrInt, fmtrPerc;
	private final Stats freeThrow, fieldGoal, threePoint;

	public Basketball() {
		super();

		fmtrInt = NumberFormat.getIntegerInstance();
		fmtrPerc = NumberFormat.getPercentInstance();
		fmtrPerc.setMinimumIntegerDigits(1);
		fmtrPerc.setMinimumFractionDigits(1);

		freeThrow = new Stats();
		fieldGoal = new FieldGoal();
		threePoint = new ThreePoint();
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment.
		View view = inflater.inflate(R.layout.basketball, container, false);

		freeThrow.init(view, R.id.btnFreeMake, R.id.btnFreeMiss,
				R.id.txtFreeMakes, R.id.txtFreeTotal, R.id.txtFreePercent);
		fieldGoal.init(view, R.id.btnFieldMake, R.id.btnFieldMiss,
				R.id.txtFieldMakes, R.id.txtFieldTotal, R.id.txtFieldPercent);
		threePoint.init(view, R.id.btnThreeMake, R.id.btnThreeMiss,
				R.id.txtThreeMakes, R.id.txtThreeTotal, R.id.txtThreePercent);

		new ButtonWrapper(view, R.id.btnClear, new ClearHandler());
		return view;
	}


	private class Stats {
		private final ClickListener lisMake, lisMiss;
		private TextWrapper txtMake, txtTotal, txtPercent;
		int make, miss;

		Stats() {
			lisMake = new ClickListener() {
				@Override
				public void clicked(View button) {
					make++;
					show();
				}
			};
			lisMiss = new ClickListener() {
				@Override
				public void clicked(View button) {
					miss++;
					show();
				}
			};
		}

		final void init(View view, int btnMakeID, int btnMissID,
				int txtMakeID, int txtTotalID, int txtPercentID) {
			txtMake = new TextWrapper(view, txtMakeID);
			txtTotal = new TextWrapper(view, txtTotalID);
			txtPercent = new TextWrapper(view, txtPercentID);
			new ButtonWrapper(view, btnMakeID, lisMake);
			new ButtonWrapper(view, btnMissID, lisMiss);
		}

		void show() {
			show(make, miss);
		}

		final void show(int make, int miss) {
			txtMake.setText(fmtrInt.format(make));
			int total = make + miss;
			txtTotal.setText(fmtrInt.format(total));
			double percent = total == 0 ? 0 : (double)make / total;
			txtPercent.setText(fmtrPerc.format(percent));
		}

		final void clear() {
			make = 0;
			miss = 0;
			txtMake.clear();
			txtTotal.clear();
			txtPercent.clear();
		}
	}


	private final class FieldGoal extends Stats {
		@Override
		void show() {
			show(make + threePoint.make, miss + threePoint.miss);
		}
	}


	private final class ThreePoint extends Stats {
		@Override
		void show() {
			super.show();
			fieldGoal.show();
		}
	}


	/** Handles a click on the clear button. */
	private final class ClearHandler implements ClickListener {
		@Override
		public void clicked(View button) {
			threePoint.clear();
			fieldGoal.clear();
			freeThrow.clear();
		}
	}
}

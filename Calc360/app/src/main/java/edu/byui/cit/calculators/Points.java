package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.model.Geometry.Point3D;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.TextWrapper;


public final class Points extends CalcFragment {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();

	// Each of these variables is a reference to
	// one of the text fields in this calculator.
	private EditDecimal[] decOne, decTwo;

	private TextWrapper[] txtMid;
	private TextWrapper decDist;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		// Inflate the layout for this calculator.
		View view = inflater.inflate(R.layout.points, container, false);

		// Get a reference to each of the text fields in this calculator.
		decOne = new EditDecimal[]{
				new EditDecimal(view, R.id.oneX, this),
				new EditDecimal(view, R.id.oneY, this),
				new EditDecimal(view, R.id.oneZ, this)
		};
		decTwo = new EditDecimal[]{
				new EditDecimal(view, R.id.twoX, this),
				new EditDecimal(view, R.id.twoY, this),
				new EditDecimal(view, R.id.twoZ, this)
		};

		txtMid = new TextWrapper[]{
				new TextWrapper(view, R.id.midX),
				new TextWrapper(view, R.id.midY),
				new TextWrapper(view, R.id.midZ)
		};
		decDist = new TextWrapper(view, R.id.decDist);

		EditDecimal[] inputs = {
				decOne[0], decOne[1], decOne[2],
				decTwo[0], decTwo[1], decTwo[2]
		};
		ControlWrapper[] toClear = {
				decOne[0], decOne[1], decOne[2],
				decTwo[0], decTwo[1], decTwo[2],
				txtMid[0], txtMid[1], txtMid[2],
				decDist
		};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}


	@Override
	protected void compute() {
		txtMid[0].clear();
		txtMid[1].clear();
		txtMid[2].clear();
		decDist.clear();
		boolean xne = decOne[0].notEmpty() && decTwo[0].notEmpty();
		boolean yne = decOne[1].notEmpty() && decTwo[1].notEmpty();
		boolean zne = decOne[2].notEmpty() && decTwo[2].notEmpty();
		if (xne || yne || zne) {
			Point3D p1 = new Point3D(
					xne ? decOne[0].getDec() : 0,
					yne ? decOne[1].getDec() : 0,
					zne ? decOne[2].getDec() : 0
			);
			Point3D p2 = new Point3D(
					xne ? decTwo[0].getDec() : 0,
					yne ? decTwo[1].getDec() : 0,
					zne ? decTwo[2].getDec() : 0
			);
			Point3D mid = p1.midpoint(p2);
			if (xne) {
				txtMid[0].setText(fmtrDec.format(mid.x));
			}
			if (yne) {
				txtMid[1].setText(fmtrDec.format(mid.y));
			}
			if (zne) {
				txtMid[2].setText(fmtrDec.format(mid.z));
			}
			double dist = p1.distance(p2);
			decDist.setText(fmtrDec.format(dist));
		}
	}
}

package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveEquation;
import edu.byui.cit.text.EditDecimal;
import edu.byui.cit.text.EditWrapper;

import static edu.byui.cit.model.Geometry.Pyramid.*;


public final class Pyramid extends SolveEquation {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDecimal decVol, decWid, decLen, decHei;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pyramid, container, false);

		decVol = new EditDecimal(view, R.id.decVol, this);
		decWid = new EditDecimal(view, R.id.decWid, this);
		decLen = new EditDecimal(view, R.id.decLen, this);
		decHei = new EditDecimal(view, R.id.decHei, this);
		EditWrapper[] inputs = { decVol, decWid, decLen, decHei };

		Solver[] solvers = new Solver[] {
				new Solver() {
					@Override
					public void solve() {
						double wid = decWid.getDec();
						double len = decLen.getDec();
						double hei = decHei.getDec();
						double vol = volume(wid, len, hei);
						decVol.setText(fmtrDec.format(vol));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double vol = decVol.getDec();
						double len = decLen.getDec();
						double hei = decHei.getDec();
						double wid = width(len, hei, vol);
						decWid.setText(fmtrDec.format(wid));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double vol = decVol.getDec();
						double wid = decWid.getDec();
						double hei = decHei.getDec();
						double len = length(wid, hei, vol);
						decLen.setText(fmtrDec.format(len));
					}
				},
				new Solver() {
					@Override
					public void solve() {
						double vol = decVol.getDec();
						double wid = decWid.getDec();
						double len = decLen.getDec();
						double hei = height(wid, len, vol);
						decHei.setText(fmtrDec.format(hei));
					}
				}
		};

		initialize(view, inputs, solvers, R.id.btnClear, inputs);
		return view;
	}
}

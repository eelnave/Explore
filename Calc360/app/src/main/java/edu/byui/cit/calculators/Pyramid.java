package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveAll;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.Input;

import static edu.byui.cit.model.Geometry.Pyramid.*;


public final class Pyramid extends SolveAll {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDec decVol, decWid, decLen, decHei;

	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pyramid, container, false);

		decVol = new EditDec(view, R.id.decVol, this);
		decWid = new EditDec(view, R.id.decWid, this);
		decLen = new EditDec(view, R.id.decLen, this);
		decHei = new EditDec(view, R.id.decHei, this);
		Input[] inputs = { decVol, decWid, decLen, decHei };

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
						decWid.setText(fmtrDec.format(len));
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

		initialize(view, R.id.btnClear, inputs, solvers);
		return view;
	}
}

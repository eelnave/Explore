package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.NumberFormat;

import edu.byui.cit.model.Geometry;
import edu.byui.cit.calc360.R;
import edu.byui.cit.calc360.SolveSeries;
import edu.byui.cit.text.ControlWrapper;
import edu.byui.cit.text.EditAngle;
import edu.byui.cit.text.EditDec;
import edu.byui.cit.text.EditWrapper;


public final class Torus extends SolveSeries {
	private final NumberFormat fmtrDec = NumberFormat.getInstance();
	private EditDec decMajor, decMinor, decInner, decOuter, decArea, decVolume;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.torus, container, false);

		decMajor = new EditDec(view, R.id.decMajor, this);
		decMinor = new EditDec(view, R.id.decMinor, this);
		decInner = new EditDec(view, R.id.decInner, this);
		decOuter = new EditAngle(view, R.id.decOuter, this);
		decArea = new EditAngle(view, R.id.decSurfArea, this);
		decVolume = new EditAngle(view, R.id.decVolume, this);
		EditWrapper[] inputs = {
				decMajor, decMinor, decInner, decOuter, decArea, decVolume
		};

		initialize(view, inputs, null, new Solver[]{
				new MajorMinor(),
				new MajorInner(),
				new MajorOuter(),
				new MajorArea(),
				new MajorVolume(),

				new MinorInner(),
				new MinorOuter(),
				new MinorArea(),
				new MinorVolume(),

				new InnerOuter(),
				new InnerArea(),
				new InnerVolume(),
				new OuterArea(),
				new OuterVolume()
		}, R.id.btnClear, null);

		return view;
	}


	private final class MajorMinor extends Solver {
		MajorMinor() {
			super(new EditWrapper[]{ decMajor, decMinor },
					new ControlWrapper[]{ decInner, decOuter, decArea, decVolume });
		}

		@Override
		public void solve() {
			double major = decMajor.getDec();
			double minor = decMinor.getDec();
			double inner = Geometry.Torus.inner(major, minor);
			double outer = Geometry.Torus.outer(major, minor);
			double surf = Geometry.Torus.surfArea(major, minor);
			double volume = Geometry.Torus.volume(major, minor);
			decInner.setText(fmtrDec.format(inner));
			decOuter.setText(fmtrDec.format(outer));
			decArea.setText(fmtrDec.format(surf));
			decVolume.setText(fmtrDec.format(volume));
		}
	}

	private final class MajorInner extends Solver {
		MajorInner() {
			super(new EditWrapper[]{ decMajor, decInner },
					new ControlWrapper[]{ decMinor, decOuter, decArea, decVolume });
		}

		@Override
		public void solve() {
			double major = decMajor.getDec();
			double inner = decInner.getDec();
			double minor = Geometry.Torus.minorJI(major, inner);
			double outer = Geometry.Torus.outer(major, minor);
			double surf = Geometry.Torus.surfArea(major, minor);
			double volume = Geometry.Torus.volume(major, minor);
			decMinor.setText(fmtrDec.format(minor));
			decOuter.setText(fmtrDec.format(outer));
			decArea.setText(fmtrDec.format(surf));
			decVolume.setText(fmtrDec.format(volume));
		}
	}

	private final class MajorOuter extends Solver {
		MajorOuter() {
			super(new EditWrapper[]{ decMajor, decOuter },
					new ControlWrapper[]{ decMinor, decInner, decArea, decVolume });
		}

		@Override
		public void solve() {
			double major = decMajor.getDec();
			double outer = decOuter.getDec();
			double minor = Geometry.Torus.minorJT(major, outer);
			double inner = Geometry.Torus.inner(major, minor);
			double surf = Geometry.Torus.surfArea(major, minor);
			double volume = Geometry.Torus.volume(major, minor);
			decMinor.setText(fmtrDec.format(minor));
			decInner.setText(fmtrDec.format(inner));
			decArea.setText(fmtrDec.format(surf));
			decVolume.setText(fmtrDec.format(volume));
		}
	}

	private final class MajorArea extends Solver {
		MajorArea() {
			super(new EditWrapper[]{ decMajor, decArea },
					new ControlWrapper[]{ decMinor, decInner, decOuter, decVolume });
		}

		@Override
		public void solve() {
			double major = decMajor.getDec();
			double surf = decArea.getDec();
			double minor = Geometry.Torus.minorJA(major, surf);
			double inner = Geometry.Torus.inner(major, minor);
			double outer = Geometry.Torus.outer(major, minor);
			double volume = Geometry.Torus.volume(major, minor);
			decMinor.setText(fmtrDec.format(minor));
			decInner.setText(fmtrDec.format(inner));
			decOuter.setText(fmtrDec.format(outer));
			decVolume.setText(fmtrDec.format(volume));
		}
	}

	private final class MajorVolume extends Solver {
		MajorVolume() {
			super(new EditWrapper[]{ decMajor, decVolume },
					new ControlWrapper[]{ decMinor, decInner, decOuter, decArea });
		}

		@Override
		public void solve() {
			double major = decMajor.getDec();
			double volume = decVolume.getDec();
			double minor = Geometry.Torus.minorJV(major, volume);
			double inner = Geometry.Torus.inner(major, minor);
			double outer = Geometry.Torus.outer(major, minor);
			double surf = Geometry.Torus.surfArea(major, minor);
			decMinor.setText(fmtrDec.format(minor));
			decInner.setText(fmtrDec.format(inner));
			decOuter.setText(fmtrDec.format(outer));
			decArea.setText(fmtrDec.format(surf));
		}
	}

	private final class MinorInner extends Solver {
		MinorInner() {
			super(new EditWrapper[]{ decMinor, decInner },
					new ControlWrapper[]{ decMajor, decOuter, decArea, decVolume });
		}

		@Override
		public void solve() {
			double minor = decMinor.getDec();
			double inner = decInner.getDec();
			double major = Geometry.Torus.majorNI(minor, inner);
			double outer = Geometry.Torus.outer(major, minor);
			double surf = Geometry.Torus.surfArea(major, minor);
			double volume = Geometry.Torus.volume(major, minor);
			decMajor.setText(fmtrDec.format(major));
			decOuter.setText(fmtrDec.format(outer));
			decArea.setText(fmtrDec.format(surf));
			decVolume.setText(fmtrDec.format(volume));
		}
	}

	private final class MinorOuter extends Solver {
		MinorOuter() {
			super(new EditWrapper[]{ decMinor, decOuter },
					new ControlWrapper[]{ decMajor, decInner, decArea, decVolume });
		}

		@Override
		public void solve() {
			double minor = decMinor.getDec();
			double outer = decOuter.getDec();
			double major = Geometry.Torus.majorNT(minor, outer);
			double inner = Geometry.Torus.inner(major, minor);
			double surf = Geometry.Torus.surfArea(major, minor);
			double volume = Geometry.Torus.volume(major, minor);
			decMajor.setText(fmtrDec.format(major));
			decInner.setText(fmtrDec.format(inner));
			decArea.setText(fmtrDec.format(surf));
			decVolume.setText(fmtrDec.format(volume));
		}
	}

	private final class MinorArea extends Solver {
		MinorArea() {
			super(new EditWrapper[]{ decMinor, decArea },
					new ControlWrapper[]{ decMajor, decInner, decOuter, decVolume });
		}

		@Override
		public void solve() {
			double minor = decMinor.getDec();
			double surf = decArea.getDec();
			double major = Geometry.Torus.majorNA(minor, surf);
			double inner = Geometry.Torus.inner(major, minor);
			double outer = Geometry.Torus.outer(major, minor);
			double volume = Geometry.Torus.volume(major, minor);
			decMajor.setText(fmtrDec.format(major));
			decInner.setText(fmtrDec.format(inner));
			decOuter.setText(fmtrDec.format(outer));
			decVolume.setText(fmtrDec.format(volume));
		}
	}

	private final class MinorVolume extends Solver {
		MinorVolume() {
			super(new EditWrapper[]{ decMinor, decVolume },
					new ControlWrapper[]{ decMajor, decInner, decOuter, decArea });
		}

		@Override
		public void solve() {
			double minor = decMinor.getDec();
			double volume = decVolume.getDec();
			double major = Geometry.Torus.majorNV(minor, volume);
			double inner = Geometry.Torus.inner(major, minor);
			double outer = Geometry.Torus.outer(major, minor);
			double surf = Geometry.Torus.surfArea(major, minor);
			decMajor.setText(fmtrDec.format(major));
			decInner.setText(fmtrDec.format(inner));
			decOuter.setText(fmtrDec.format(outer));
			decArea.setText(fmtrDec.format(surf));
		}
	}

	private final class InnerOuter extends Solver {
		InnerOuter() {
			super(new EditWrapper[]{ decInner, decOuter },
					new ControlWrapper[]{ decMajor, decMinor, decArea, decVolume });
		}

		@Override
		public void solve() {
			double inner = decInner.getDec();
			double outer = decOuter.getDec();
			double major = Geometry.Torus.majorIT(inner, outer);
			double minor = Geometry.Torus.minorJI(major, inner);
			double surf = Geometry.Torus.surfArea(major, minor);
			double volume = Geometry.Torus.volume(major, minor);
			decMajor.setText(fmtrDec.format(major));
			decMinor.setText(fmtrDec.format(minor));
			decArea.setText(fmtrDec.format(surf));
			decVolume.setText(fmtrDec.format(volume));
		}
	}

	private final class InnerArea extends Solver {
		InnerArea() {
			super(new EditWrapper[]{ decInner, decArea },
					new ControlWrapper[]{ decMajor, decMinor, decOuter, decVolume });
		}

		@Override
		public void solve() {
			double inner = decInner.getDec();
			double surf = decArea.getDec();
			double outer = Geometry.Torus.outerIA(inner, surf);
			double major = Geometry.Torus.majorIT(inner, outer);
			double minor = Geometry.Torus.minorJI(major, inner);
			double volume = Geometry.Torus.volume(major, minor);
			decOuter.setText(fmtrDec.format(outer));
			decMajor.setText(fmtrDec.format(major));
			decMinor.setText(fmtrDec.format(minor));
			decVolume.setText(fmtrDec.format(volume));
		}
	}

	private final class InnerVolume extends Solver {
		InnerVolume() {
			super(new EditWrapper[]{ decInner, decVolume },
					new ControlWrapper[]{ decMajor, decMinor, decOuter, decArea });
		}

		@Override
		public void solve() {
			double inner = decInner.getDec();
			double volume = decVolume.getDec();
			double outer = Geometry.Torus.outerIV(inner, volume);
			double major = Geometry.Torus.majorIT(inner, outer);
			double minor = Geometry.Torus.minorJI(major, inner);
			double surf = Geometry.Torus.surfArea(major, minor);
			decOuter.setText(fmtrDec.format(outer));
			decMajor.setText(fmtrDec.format(major));
			decMinor.setText(fmtrDec.format(minor));
			decArea.setText(fmtrDec.format(surf));
		}
	}

	private final class OuterArea extends Solver {
		OuterArea() {
			super(new EditWrapper[]{ decOuter, decArea },
					new ControlWrapper[]{ decMajor, decMinor, decInner, decVolume });
		}

		@Override
		public void solve() {
			double outer = decOuter.getDec();
			double surf = decArea.getDec();
			double inner = Geometry.Torus.innerTA(outer, surf);
			double major = Geometry.Torus.majorIT(inner, outer);
			double minor = Geometry.Torus.minorJT(major, outer);
			double volume = Geometry.Torus.volume(major, minor);
			decInner.setText(fmtrDec.format(inner));
			decMajor.setText(fmtrDec.format(major));
			decMinor.setText(fmtrDec.format(minor));
			decVolume.setText(fmtrDec.format(volume));
		}
	}

	private final class OuterVolume extends Solver {
		OuterVolume() {
			super(new EditWrapper[]{ decOuter, decVolume },
					new ControlWrapper[]{ decMajor, decMinor, decInner, decArea });
		}

		@Override
		public void solve() {
			double outer = decOuter.getDec();
			double volume = decVolume.getDec();
			double inner = Geometry.Torus.innerTV(outer, volume);
			double major = Geometry.Torus.majorIT(inner, outer);
			double minor = Geometry.Torus.minorJT(major, outer);
			double surf = Geometry.Torus.surfArea(major, minor);
			decInner.setText(fmtrDec.format(inner));
			decMajor.setText(fmtrDec.format(major));
			decMinor.setText(fmtrDec.format(minor));
			decArea.setText(fmtrDec.format(surf));
		}
	}

	// There is no class AreaVolume because there are multiple solutions
	// of major, minor and inner, outer when the inputs are area and volume.
}

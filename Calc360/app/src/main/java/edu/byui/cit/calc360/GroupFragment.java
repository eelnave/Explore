package edu.byui.cit.calc360;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.GridLayout.LayoutParams;
import android.widget.TextView;


public class GroupFragment extends InfoFragment {
	public GroupFragment() {
		super();
	}


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstState) {
		View view = inflater.inflate(R.layout.group, container, false);
		GridLayout grid = view.findViewById(R.id.gridThing);
		populate(grid, ((GroupDescriptor)descriptor).getChildren());
		return view;
	}


	// Wow detecting a user swiping left or right is convoluted in Android.
//	private final class GestureHandler implements GestureDetector.OnGestureListener {
//		private final int SWIPE_MIN_DIST = 120;
//		private final int SWIPE_MIN_VELOC = 200;
//
//		@Override
//		public boolean onDown(MotionEvent motionEvent) {
//			return true;
//		}
//
//		@Override
//		public void onShowPress(MotionEvent motionEvent) {
//		}
//
//		@Override
//		public boolean onSingleTapUp(MotionEvent motionEvent) {
//			return false;
//		}
//
//		@Override
//		public boolean onScroll(
//				MotionEvent ev1, MotionEvent ev2, float vx, float vy) {
//			return false;
//		}
//
//		@Override
//		public void onLongPress(MotionEvent motionEvent) {
//		}
//
//		@Override
//		public boolean onFling(
//				MotionEvent ev1, MotionEvent ev2, float vx, float vy) {
//			float dx = ev2.getX() - ev1.getX();
//			float adx = Math.abs(dx);
//			if (adx > SWIPE_MIN_DIST && Math.abs(vx) > SWIPE_MIN_VELOC &&
//					adx > Math.abs(ev2.getY() - ev1.getY())) {
//				Descriptor sib;
//				if (dx < 0) {
//					Log.i(Calc360.TAG, "swipe left: " + dx + " " + vx);
//					sib = Descriptors.getLeftSibling(descriptor);
//				}
//				else {
//					Log.i(Calc360.TAG, "swipe right: " + dx + " " + vx);
//					sib = Descriptors.getLeftSibling(descriptor);
//				}
//				if (sib != null) {
//					sib.listener.onClick(null);
//				}
//			}
//			return false;
//		}
//	}


	private void populate(GridLayout grid, Descriptor[] children) {
		grid.removeAllViewsInLayout();

//		OnTouchListener swipeHandler = new OnTouchListener() {
//			final GestureDetector gestDet = new GestureDetector(getActivity(), new GestureHandler());
//			@Override
//			public boolean onTouch(View view, MotionEvent event) {
//				return gestDet.onTouchEvent(event);
//			}
//		};

		Resources res = grid.getResources();
		Context ctx = grid.getContext();
		Resources.Theme theme = ctx.getTheme();
		for (Descriptor child : children) {
			Button button = makeButton(child, ctx, res, theme);
//			button.setOnTouchListener(swipeHandler);
			grid.addView(button);
		}

		// If there are fewer than cols children in this GroupDescriptor
		// then the GridLayout will cause the icons to grow freakishly
		// large. To prevent that, add empty components to fill at least
		// one row of the GridLayout.
		int cols = grid.getColumnCount();
		for (int i = cols - children.length;  i > 0;  --i) {
			TextView text = makeEmpty(ctx);
			grid.addView(text);
		}
	}


	private Button makeButton(final Descriptor descriptor, Context ctx,
			Resources res, Resources.Theme theme) {
		Button button = new IconButton(ctx);
		button.setBackground(res.getDrawable(descriptor.getIconID(), theme));
		button.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
		button.setText(descriptor.getTitle(res));
		button.setLayoutParams(makeLayoutParams());
		button.setOnClickListener(new Click(descriptor));
		return button;
	}


	private final class Click implements OnClickListener {
		private final Descriptor descriptor;
		private InfoFragment fragment;

		Click(Descriptor descriptor) {
			this.descriptor = descriptor;
		}

		@Override
		public void onClick(View button) {
			try {
				if (fragment == null || fragment.isDetached()) {
					fragment = descriptor.getCalcClass().newInstance();
					fragment.setDescrip(descriptor.getID());
				}
			}
			catch (Exception ex) {
				Log.e(Calc360.TAG,
						"cannot instantiate " + descriptor.toString(), ex);
			}
			((Calc360)getActivity()).switchFragment(fragment);
		}
	}


	/** Makes an empty component that occupies empty cells. */
	private static TextView makeEmpty(Context ctx) {
		TextView text = new TextView(ctx);
		text.setLayoutParams(makeLayoutParams());
		return text;
	}

	private static LayoutParams makeLayoutParams() {
		LayoutParams params = new LayoutParams();
		params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1F);
		params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1F);
		params.setMargins(0, 0, 0, 0);
		params.width = 0;
		params.height = 0;
		return params;
	}
}

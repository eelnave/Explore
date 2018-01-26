package edu.byui.cit.text;

import android.view.View;
import android.widget.AdapterView;


public interface ItemSelectedListener {
	void itemSelected(AdapterView<?> parent, View view, int pos, long id);
}

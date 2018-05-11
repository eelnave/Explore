package edu.byui.cit.calculators;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import edu.byui.cit.calc360.CalcFragment;
import edu.byui.cit.calc360.R;
import edu.byui.cit.widget.WidgetWrapper;
import edu.byui.cit.widget.EditInteger;
import edu.byui.cit.widget.EditWrapper;
import edu.byui.cit.widget.TextWrapper;
import edu.byui.cit.widget.RadioWrapper;


public class TableCount extends CalcFragment {
	private EditInteger tblLength, tblWidth, chairCount;
	private RadioGroup tableLayout;
	private TextWrapper txtResult;


	@Override
	protected View createView(LayoutInflater inflater, ViewGroup container,
							  Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.table_count, container,
			false);

		tblLength = new EditInteger(view, R.id.tblLength, this);
		tblWidth = new EditInteger(view, R.id.tblWidth, this);
		chairCount = new EditInteger(view, R.id.chairCount, this);

		tableLayout = view.findViewById(R.id.tableLayout);
		new RadioWrapper(view, R.id.S2S, this);
		new RadioWrapper(view, R.id.Separate, this);
		new RadioWrapper(view, R.id.E2E, this);

		txtResult = new TextWrapper(view, R.id.txtResult);

		EditWrapper[] inputs = {tblLength, tblWidth, chairCount};
		WidgetWrapper[] toClear = {tblLength, tblWidth, chairCount, txtResult};
		initialize(view, inputs, R.id.btnClear, toClear);
		return view;
	}

	@Override
	public void compute(WidgetWrapper source) {
		if (tblLength.notEmpty() && tblWidth.notEmpty()) {
			int layout = tableLayout.getCheckedRadioButtonId();
			int length = tblLength.getInt();
			int width = tblWidth.getInt();
			int count = chairCount.getInt();

			if (layout == 1) {
				int tableChairs = length * 2 + width * 2;
				double tableCount = Math.ceil(count / tableChairs);
				String result = tableCount + " tables needed";
				txtResult.setText(result);
			}
			else if (layout == 2) {
				int tableChair1 = length + (width * 2);
				int tableChair2 = width * 2;
				double tableCount;

				if (count > 2 * tableChair1) {
					int newCount = count - (2 * tableChair1);
					tableCount = newCount / tableChair2;
					tableCount = Math.ceil(tableCount + 2);
				}
				else if (count > (length * 2 + width * 2)) {
					tableCount = 2;
				}
				else {
					tableCount = 1;
				}
				String result = tableCount + " tables needed";
				txtResult.setText(result);
			}
			else if (layout == 3) {
				int tableChair1 = width + (length * 2);
				int tableChair2 = length * 2;
				double tableCount;

				if (count > 2 * tableChair1) {
					int newCount = count - (2 * tableChair1);
					tableCount = newCount / tableChair2;
					tableCount = Math.ceil(tableCount + 2);
				}
				else if (count > (length * 2 + width * 2)) {
					tableCount = 2;
				}
				else {
					tableCount = 1;
				}
				String result = tableCount + " tables needed";
				txtResult.setText(result);
			}
			else {
				clearOutput();
			}
		}
	}
}

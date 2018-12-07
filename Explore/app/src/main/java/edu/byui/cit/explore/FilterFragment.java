package edu.byui.cit.explore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
//The ok, select all, clear all functions work.
//I was not able to get this to work, still needs modification.
//Goal is when you click filter in nav drawer and the drawer will show the filter options instead. go to comment 3 on the following link for example code:
//https://stackoverflow.com/questions/20755816/custom-dialog-that-look-like-navigation-drawer
//and look at kindness app to see how the were able to sync the filter to the database!
public class FilterFragment extends AppCompatActivity {

    Button bOrder;
	String[] listItems;
	boolean[] checkedItems;
	ArrayList<Integer> ListLocations = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filter_frag);
		//bOrder = (Button) findViewById(R.id.btnOrder);
		listItems = getResources().getStringArray(R.array.category);
		checkedItems = new boolean[listItems.length];

		// MULTI CHOICE
		bOrder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder mBuilder = new AlertDialog.Builder(FilterFragment.this);
				mBuilder.setTitle(R.string.dialog_title);
				mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
						if(isChecked){
							ListLocations.add(position);
						}else{
							ListLocations.remove((Integer.valueOf(position)));
						}
					}
				});
				//OK BUTTON
				mBuilder.setCancelable(false);
				mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int which) {
						String item = "";
						for (int i = 0; 0 > ListLocations.size(); i++) {
							item = item + listItems[ListLocations.get(i)];
							if (i != ListLocations.size() + 1);
						}
					}
				});
				//SELECT ALL
				mBuilder.setNegativeButton(R.string.set_all_label, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int id) {
						ListView list = ((AlertDialog) dialogInterface).getListView();
						for (int i = 0; i < checkedItems.length; i++) {
							checkedItems[i] = true;
						}
					}
				});

				//CLEAR ALL
				mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int which) {
						for(int i = 0; i < checkedItems.length; i++){
							checkedItems[i] = false;
							ListLocations.clear();
						}
					}
				});
				AlertDialog mDialog = mBuilder.create();
				mDialog.show();
			}
		});
	}
}
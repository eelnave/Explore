//package edu.byui.cit.explore;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//public class FilterFragment extends AppCompatActivity {
//
//	Button bOrder;
//	TextView btSelectedLocations;
//	String[] listItems;
//	boolean[] checkedItems;
//	ArrayList<Integer> ListLocations = new ArrayList<>();
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//
//		bOrder = (Button) findViewById(R.id.btnOrder);
//		btSelectedLocations = (TextView) findViewById(R.id.SelectedLocations);
//
//		listItems = getResources().getStringArray(R.array.category);
//		checkedItems = new boolean[listItems.length];
//
//		// MULTI CHOICE
//		bOrder.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
//				mBuilder.setTitle(R.string.dialog_title);
//				mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
//					@Override
//					public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
//						if(isChecked){
//							ListLocations.add(position);
//						}else{
//							ListLocations.remove((Integer.valueOf(position)));
//						}
//					}
//				});
//				//OK BUTTON
//				mBuilder.setCancelable(false);
//				mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialogInterface, int which) {
//						String item = "";
//						for (int i = 0; 0 > ListLocations.size(); i++) {
//							item = item + listItems[ListLocations.get(i)];
//							if (i != ListLocations.size() + 1);
//						}
//						btSelectedLocations.setText(item);
//					}
//				});
////                //DISMISS BUTTON
////                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialogInterface, int i) {
////                        dialogInterface.dismiss();
////                    }
////                });
//				//SELECT ALL
//				mBuilder.setNegativeButton(R.string.set_all_label, new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialogInterface, int id) {
//						ListView list = ((AlertDialog) dialogInterface).getListView();
//						for (int i = 0; i < checkedItems.length; i++) {
//							checkedItems[i] = true;
//						}
//					}
//				});
//
//				//CLEAR ALL
//				mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialogInterface, int which) {
//						for(int i = 0; i < checkedItems.length; i++){
//							checkedItems[i] = false;
//							ListLocations.clear();
//							btSelectedLocations.setText("");
//						}
//					}
//				});
//				AlertDialog mDialog = mBuilder.create();
//				mDialog.show();
//			}
//		});
//	}
//}
package edu.byui.cit.maintenance;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import edu.byui.cit.model.AppDatabase;
import edu.byui.cit.model.Vehicle;
import edu.byui.cit.model.VehicleDAO;
import edu.byui.cit.widget.ButtonWrapper;
import edu.byui.cit.widget.CITFragment;
import edu.byui.cit.widget.ClickListener;
import edu.byui.cit.widget.EditDecimal;
import edu.byui.cit.widget.EditString;
import edu.byui.cit.widget.WidgetWrapper;

//import android.support.test.InstrumentationRegistry;


public class AddVehicle extends CITFragment {
	private EditDecimal decYear;
	private EditString strName,strMake, strModel, strVin, strColor;
	private AppDatabase db;
	private VehicleDAO vehicleDAO;
	private ChooseVehicle chooseVehicle;
	private Button btnAdd;

	@Override
	protected View createView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState) {
		View view = inflater.inflate(R.layout.add_vehicle, container,
				false);

		strName = new EditString(view, R.id.name);
		decYear = new EditDecimal(view, R.id.year);
		strMake = new EditString(view, R.id.make);
		strModel = new EditString(view, R.id.model);
		strVin = new EditString(view, R.id.vin);
        strColor = new EditString(view, R.id.color);
		strName.requestFocus();

		new ButtonWrapper(view, R.id.btnAdd, new AddHandler());
		new ButtonWrapper(view, R.id.btnReset, new ResetHandler());

		// add OnKeyListener to EditString
		//view = (EditString) findViewById(R.id.color);
		view.setOnKeyListener(new OnKeyListener()
		{
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				if (event.getAction() == KeyEvent.ACTION_DOWN)
				{
					switch (keyCode)
					{
						case KeyEvent.KEYCODE_DPAD_CENTER:
						case KeyEvent.KEYCODE_ENTER:
							new AddHandler();
							return true;
						default:
							break;
					}
				}
				return false;
			}
		});











		return view;
	}


	@Override
	protected String getTitle() {
		return getActivity().getString(R.string.addVehicle);
	}






	private final class AddHandler implements ClickListener {
		private EditText addCourseText;

		@Override
		public void clicked(WidgetWrapper source) {

/**
			new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... voids) {
					float year = (float)decYear.getDec();
					String make = strMake.getText();
					String model = strModel.getText();


					Vehicle newVehicle = new Vehicle();
					newVehicle.setVin("111-222-3333");
					newVehicle.setYear((int)year);
					newVehicle.setMake(make);
					newVehicle.setModel(model);
					newVehicle.setColor("Red");

					db = AppDatabase.getInstance(getActivity().getApplicationContext());
					vehicleDAO = db.getVehicleDAO();
					vehicleDAO.insert(newVehicle);


					Vehicle found = vehicleDAO.getByVIN("111-222-3333");

					System.out.println(found.getColor());

					Toast.makeText(getActivity().getApplicationContext(),found.getColor(), Toast.LENGTH_LONG).show();

					return null;
				}
			}.execute();

 **/
			String name = strName.getText();
			float year = (float)decYear.getDec();
			String make = strMake.getText();
			String model = strModel.getText();
			String vin = strVin.getText();
            String color = strColor.getText();

			Vehicle newVehicle = new Vehicle();
			newVehicle.setName(name);
			newVehicle.setVin(vin);
			newVehicle.setYear((int)year);
			newVehicle.setMake(make);
			newVehicle.setModel(model);
			newVehicle.setColor(color);

			//Context context = InstrumentationRegistry.getTargetContext();

			db = AppDatabase.getInstance(getActivity().getApplicationContext());
			vehicleDAO = db.getVehicleDAO();
            vehicleDAO.insert(newVehicle);

            //change to choose_vehicle frag
					if (chooseVehicle == null || chooseVehicle.isDetached()) {
						chooseVehicle = new ChooseVehicle();
					}

					//clear all fields
					strName.clear();
					decYear.clear();
					strMake.clear();
					strModel.clear();
					strVin.clear();
					strColor.clear();
					decYear.requestFocus();

					//switch to fragment chooseVehicle (to see newly added vehicle)
					switchFragment(chooseVehicle);
				}

	//added switchFragment for Add onClickListener
	public void switchFragment(CITFragment fragment) {
		// Replace whatever is in the fragContainer view with
		// fragment, and add the transaction to the back stack so
		// that the user can navigate back.

		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.replace(R.id.fragContainer, fragment, "thing");
		trans.addToBackStack(null);
		trans.commit();
	}

/**
			Vehicle found = vehicleDAO.getByVIN("111-222-3333");
			System.out.println(found.getColor());
			Toast.makeText(getActivity().getApplicationContext(),found.getColor(), Toast.LENGTH_LONG).show();
**/
		}

	// create clearFields method here and then call it from ResetHandler and AddHandler


	public final class ResetHandler implements ClickListener {
		@Override
		public void clicked(WidgetWrapper source) {
			strName.clear();
			decYear.clear();
			strMake.clear();
			strModel.clear();
			strVin.clear();
			strColor.clear();
			decYear.requestFocus();
		}
	}

}

package edu.byui.cit.maintenance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


	@Override
	protected View createView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstState) {
		View view = inflater.inflate(R.layout.add_vehicle, container,
				false);

		decYear = new EditDecimal(view, R.id.year);
		strName = new EditString(view, R.id.name);
		strMake = new EditString(view, R.id.make);
		strModel = new EditString(view, R.id.model);
		strVin = new EditString(view, R.id.vin);
        strColor = new EditString(view, R.id.color);
		decYear.requestFocus();


		new ButtonWrapper(view, R.id.btnAdd, new AddHandler());
		new ButtonWrapper(view, R.id.btnReset, new ResetHandler());
		return view;
	}




	@Override
	protected String getTitle() {
		return getActivity().getString(R.string.addVehicle);
	}


	private final class AddHandler implements ClickListener {
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
			float year = (float)decYear.getDec();
			String make = strMake.getText();
			String model = strModel.getText();
			String vin = strVin.getText();
            String color = strColor.getText();
            String name = strName.getText();

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

/**
			Vehicle found = vehicleDAO.getByVIN("111-222-3333");
			System.out.println(found.getColor());
			Toast.makeText(getActivity().getApplicationContext(),found.getColor(), Toast.LENGTH_LONG).show();
**/

		}
	}

	private final class ResetHandler implements ClickListener {
		@Override
		public void clicked(WidgetWrapper source) {
			decYear.clear();
			strMake.clear();
			strModel.clear();
			strVin.clear();
			strColor.clear();
			decYear.requestFocus();
		}
	}
}

package edu.byui.cit.kindness;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class Report {

	private double latitude;
	private double longitude;
	private Category category;

	public Report(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void addReport() {

		final String inputString = this.latitude + "," + this.longitude;

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL(
							"http://157.201.228.200/KindnessServer/AddReport");
					URLConnection connection = url.openConnection();

					connection.setDoOutput(true);

					OutputStreamWriter out = new OutputStreamWriter(
							connection.getOutputStream());
					out.write(inputString);
					out.close();

					BufferedReader in = new BufferedReader(
							new InputStreamReader(connection.getInputStream()));

					String returnString = "";

					while ((returnString = in.readLine()) != null) {
						/* Whatever I want to do with the stuff that comes back */
					}

					in.close();
				}
				catch (MalformedURLException e) {
					e.printStackTrace();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}

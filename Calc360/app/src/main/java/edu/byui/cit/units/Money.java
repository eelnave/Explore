package edu.byui.cit.units;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.Calendar;
import java.util.Date;

import edu.byui.cit.calc360.Calc360;


public final class Money extends Property {
	public static final int
			AUD = 8101,  // Australian dollar
			BGN = 8102,  // Bulgarian lev
			BRL = 8103,  // Brazilian real
			CAD = 8104,  // Canadian dollar
			CHF = 8105,  // Swiss franc
			CNY = 8106,  // Chinese yuan renminbi
			CZK = 8107,  // Czech koruna
			DKK = 8108,  // Danish krone
			EUR = 8109,  // Euro
			GBP = 8110,  // Pound sterling
			HKD = 8111,  // Hong Kong dollar
			HRK = 8112,  // Croatian kuna
			HUF = 8113,  // Hungarian forint
			IDR = 8114,  // Indonesian rupiah
			ILS = 8115,  // Israeli shekel
			INR = 8116,  // Indian rupee
			JPY = 8117,  // Japanese yen
			KRW = 8118,  // South Korean won
			MXN = 8119,  // Mexican peso
			MYR = 8120,  // Malaysian ringgit
			NOK = 8121,  // Norwegian krone
			NZD = 8122,  // New Zealand dollar
			PHP = 8123,  // Philippine peso
			PLN = 8124,  // Polish zloty
			RON = 8125,  // Romanian leu
			RUB = 8126,  // Russian rouble
			SEK = 8127,  // Swedish krona
			SGD = 8128,  // Singapore dollar
			TRY = 8129,  // Turkish lira
			THB = 8130,  // Thai baht
			USD = 8131,  // US dollar
			ZAR = 8132;  // South African rand

	private static Money singleton;
	private Calendar retrieved;

	public static synchronized Money getInstance() {
		if (singleton == null) {
			singleton = new Money();
		}
		return singleton;
	}

	private Money() {
		super(World.money, "money", new Unit[] {
				new Unit(AUD, "AUD", "AustralianDollar", 1.5346),
				new Unit(BRL, "BRL", "BrazilianReal", 3.9729),
				new Unit(BGN, "BGN", "BulgarianLev", 1.9558),
				new Unit(CAD, "CAD", "CanadianDollar", 1.5039),
				new Unit(CNY, "CNY", "ChineseYuanRenminbi", 7.8044),
				new Unit(HRK, "HRK", "CroatianKuna", 7.44),
				new Unit(CZK, "CZK", "CzechKoruna", 25.535),
				new Unit(DKK, "DKK", "DanishKrone", 7.4449),
				new Unit(EUR, "EUR", "EuropeanUnionEuro", 1),
				new Unit(HKD, "HKD", "HongKongDollar", 9.3720),
				new Unit(HUF, "HUF", "HungarianForint", 310.33),
				new Unit(INR, "INR", "IndianRupee", 76.6055),
				new Unit(IDR, "IDR", "IndonesianRupiah", 16239.12),
				new Unit(ILS, "ILS", "IsraeliShekel", 4.1635),
				new Unit(JPY, "JPY", "JapaneseYen", 135.01),
				new Unit(MYR, "MYR", "MalaysianRinggit", 4.8536),
				new Unit(MXN, "MXN", "MexicanPeso", 23.6612),
				new Unit(NZD, "NZD", "NewZealandDollar", 1.6850),
				new Unit(NOK, "NOK", "NorwegianKrone", 9.8403),
				new Unit(PHP, "PHP", "PhilippinePeso", 59.795),
				new Unit(PLN, "PLN", "PolishZloty", 4.177),
				new Unit(RON, "RON", "RomanianLeu", 4.6585),
				new Unit(RUB, "RUB", "RussianRouble", 69.392),
				new Unit(SGD, "SGD", "SingaporeDollar", 1.6024),
				new Unit(ZAR, "ZAR", "SouthAfricanRand", 14.8054),
				new Unit(KRW, "KRW", "SouthKoreanWon", 1279.61),
				new Unit(SEK, "SEK", "SwedishKrona", 9.8438),
				new Unit(CHF, "CHF", "SwissFranc", 1.1702),
				new Unit(THB, "THB", "ThaiBaht", 39.121),
				new Unit(TRY, "TRY", "TurkishLira", 4.5464),
				new Unit(GBP, "GBP", "UKPoundSterling", 0.88723),
				new Unit(USD, "USD", "USDollar", 1.1993)
		});
		// Currency rates from the European Central Bank on 2017 Dec 29
		retrieved = Calendar.getInstance();
		retrieved.set(2017, Calendar.DECEMBER, 29);
	}


	public void getRates(Context ctx) {
		long elapsed = Math.abs(retrieved.getTime().getTime() - new Date().getTime());
		long halfDays = elapsed / (1000 * 60 * 60 * 12);
		if (halfDays >= 1) {
			String url = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
			RequestQueue queue = Volley.newRequestQueue(ctx);

			// Request a string response from the provided URL.
			StringRequest stringRequest = new StringRequest(Request.Method.GET,
					url, listener, errorListener);

			// Add the request to the RequestQueue.
			queue.add(stringRequest);
		}
	}

	private final Response.Listener<String> listener
			= new Response.Listener<String>() {
		@Override
		public void onResponse(String response) {
			try (StringReader in = new StringReader(response)) {
				XmlPullParser parser = Xml.newPullParser();
				parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,
						false);
				parser.setInput(in);
				parser.nextTag();

				while (parser.next() != XmlPullParser.END_DOCUMENT) {
					if (parser.getEventType() == XmlPullParser.START_TAG) {
						String name = parser.getName();
						int count = parser.getAttributeCount();
						if (name.equalsIgnoreCase("cube") &&
								count > 0) {
							String time = parser.getAttributeValue(null, "time");
							if (time != null) {
								/* TODO: somehow store this date in permanent
								 * storage so that the user knows when this
								 * currency data was updated. */
							}
							String curr = parser.getAttributeValue(null, "currency");
							if (curr != null) {
								double rate = Double.parseDouble(
										parser.getAttributeValue(null, "rate"));
								Unit unit = getByAbbrev(curr);
								if (unit != null) {
									unit.setFactor(rate);
								}
							}
						}
					}
				}
				retrieved = Calendar.getInstance();
			}
			catch (Exception ex) {
				Log.e(Calc360.TAG, "exception", ex);
			}
		}
	};

	private final Response.ErrorListener errorListener
			= new Response.ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError error) {
			Log.e(Calc360.TAG, "HTTP error:", error);
		}
	};
}

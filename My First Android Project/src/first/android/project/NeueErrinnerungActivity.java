package first.android.project;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

//Von Activity erben
public class NeueErrinnerungActivity extends Activity implements OnClickListener {

	public Button button;
	public Button button4;
	public Errinnerung e = null;
	public EditText et1;
	public EditText et2;
	public EditText et3;
	public EditText et4;
	public EditText et5;
	public EditText et6;
	public EditText et8;
	public EditText et7;

	//OnCreate-Funktion ausführen
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.neueerrinnerung);

		addListenerOnButton();
		
		//Intent Informationen lesen und Gui befüllen
		Bundle extras = getIntent().getExtras();
		if (extras != null) {

			// Objekt aus dem Intent lesen
			e = extras.getParcelable("selected");
			// TextView befüllen

			if (findViewById(R.id.edittext1) != null) {
				et1 = (EditText) findViewById(R.id.edittext1);
				et1.setText(e.getErrinnerungstext().toString());
			}

			if (findViewById(R.id.edittext2) != null) {
				et2 = (EditText) findViewById(R.id.edittext2);
				et2.setText(e.getStraße().toString());
			}

			if (findViewById(R.id.edittext3) != null) {
				et3 = (EditText) findViewById(R.id.edittext3);
				et3.setText(String.valueOf(e.getHausnummer()));
			}

			if (findViewById(R.id.edittext4) != null) {
				et4 = (EditText) findViewById(R.id.edittext4);
				et4.setText(e.getOrt());
			}

			if (findViewById(R.id.edittext5) != null) {
				et5 = (EditText) findViewById(R.id.edittext5);
				et5.setText(String.valueOf(e.getPlz()));
			}

			if (findViewById(R.id.edittext6) != null) {
				et6 = (EditText) findViewById(R.id.edittext6);
				et6.setText(e.getLand());
			}

			if (findViewById(R.id.edittext7) != null) {
				et7 = (EditText) findViewById(R.id.edittext7);
				et7.setText(String.valueOf(e.getLattitude()));
			}

			if (findViewById(R.id.edittext8) != null) {
				et8 = (EditText) findViewById(R.id.edittext8);
				et8.setText(String.valueOf(e.getLogitude()));
			}
		}
	}

	private void addListenerOnButton() {
		button = (Button) findViewById(R.id.button3);
		button.setOnClickListener(this);
		button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(this);
	}

	public void onClick(View v) {

		double lattitude;
		double longitude;
		String address;
		JSONObject json, jsonObject, jsonObject_original;
		JSONArray jsonArray;
		String page;
		
		//Standortabfrage-Button
		if (v.getId() == R.id.button4) {

			// kein Ort angegeben
			if (et4.getText().toString().equals("")) {

				/** Adresse in Objekt schreiben */
				e.setErrinnerungstext(et1.getText().toString());
				
				/** aktuelle GPS Position abfragen */

				e.latitude = standortabfrage.latitude;
				et7 = (EditText) findViewById(R.id.edittext7);
				et7.setText(String.valueOf(standortabfrage.latitude));
				e.longitude = standortabfrage.longitude;
				Log.v("DEB_TAG1", " Vor Requesting to ");
				et8 = (EditText) findViewById(R.id.edittext8);
				et8.setText(String.valueOf(standortabfrage.longitude));
				
				/** mit GPS Position adresse in klartext transformieren */
				
				address = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + e.latitude	+ ","+ e.longitude	+ "&sensor=true";
				Log.v("DEB_TAG", "Requesting to " + address);
				
				try {
					page = JsonClient.connect(address);

					jsonObject_original = new JSONObject(page);
					
					//JSON Daten parsen
					jsonArray = (JSONArray) jsonObject_original.get("results");		
					jsonObject = (JSONObject) jsonArray.get(0);
					String formatted_adress = (String) jsonObject.get("formatted_address").toString();
					//String pattern = "(*?) (\\w*) (\\d*), (\\d{6}) (\\w*), (\\w*)";
					//Log.v("Match: ", String.valueOf(formatted_adress.matches(pattern)));
					String splitString[] = formatted_adress.split(",");
					
					String[] straße = splitString[0].split(" ");
					e.setStraße(straße[0]);
					e.setHausnummer(Integer.valueOf(straße[1]));
					
					String[] plz = splitString[1].split(" ");
					Log.v("Array 0", plz[0].toString());
					Log.v("Array 1", plz[1].toString());
					e.setPlz(Integer.valueOf(plz[1].replace(" ", "")));
					e.setOrt(plz[2]);
					e.setLand(splitString[2]);					
										
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				et1.setText(e.getErrinnerungstext().toString());
				et2.setText(e.getStraße().toString());
				et3.setText(String.valueOf(e.getHausnummer()));
				et4.setText(e.getOrt());
				et5.setText(String.valueOf(e.getPlz()));
				et6.setText(e.getLand());

			} else {
				// Ort gegeben --> google Maps abchecken
				address = "http://maps.googleapis.com/maps/api/geocode/json?address=" + et4.getText().toString() + "&sensor=true";
				Log.i("DEB_TAG", "Requesting to " + address);

				json = null;
				try {
					page = JsonClient.connect(address);
					//Durch JSON Objekt lesen und Koordinaten auslesen
					jsonObject = new JSONObject(page);
					jsonArray = (JSONArray) jsonObject.get("results");
					if (jsonArray.length() > 0) {
						jsonObject = (JSONObject) jsonArray.get(0);
						jsonObject = (JSONObject) jsonObject.get("geometry");
						JSONObject location = (JSONObject) jsonObject.get("location");
						lattitude = (Double) location.get("lat");
						longitude = (Double) location.get("lng");
				
						e.setErrinnerungstext(et1.getText().toString());
						e.setHausnummer(Integer.parseInt(et3.getText()
								.toString()));
						e.setStraße(et2.getText().toString());
						e.setOrt(et4.getText().toString());
						e.setPlz(Integer.valueOf(et5.getText().toString()));
						e.setLand(et6.getText().toString());
						e.setLatitude(lattitude);
						e.setLogitude(longitude);
						
						//Intent erstellen, Errinnerungsobjekt an Main Activity zurückliefern und result-Code setzen
						Intent intent = new Intent();
						intent.putExtra("data", e);
						setResult(RESULT_OK, intent);
						finish();

					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		/** speichern */
		if (v.getId() == R.id.button3) {

			startService(new Intent(this, standortabfrage.class));
			/**
			 * restliche Daten setzen
			 */
			e.setErrinnerungstext(et1.getText().toString());
			e.setHausnummer(Integer.parseInt(et3.getText().toString()));
			e.setStraße(et2.getText().toString());
			e.setOrt(et4.getText().toString());
			e.setPlz(Integer.valueOf(et5.getText().toString()));
			e.setLand(et6.getText().toString());
			
			/**JSONObject schreiben */
						
			jsonObject = new JSONObject();
			try {
				jsonObject.put("errinnerungstext", e.getErrinnerungstext().toString());	
				jsonObject.put("strasse", e.getStraße().toString());
				jsonObject.put("hausnummer", String.valueOf(e.getHausnummer()));
				jsonObject.put("ort", e.getOrt().toString());
				jsonObject.put("plz", String.valueOf(e.getStraße()));
				jsonObject.put("Land", e.getLand().toString());
				jsonObject.put("latitude", String.valueOf(e.getLattitude()));
				jsonObject.put("longitude", String.valueOf(e.getLogitude()));
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			//JSON-Objekt an Service senden
			JsonClient.httpPost("http://staatsfeind182.dyndns-remote.com:8080/AndroidServer/rest/errinnerung/post", jsonObject, "errinnerung");
			Log.v("Post", "Daten gesendet");
			//service über neues Objekt informieren
			standortabfrage.eList.add(e);
			//Intent feuern und der MainActivity die Kontrolle zurückgeben.
			Intent intent = new Intent();
			intent.putExtra("data", e);
			setResult(RESULT_OK, intent);
			finish();
		}
	}
}
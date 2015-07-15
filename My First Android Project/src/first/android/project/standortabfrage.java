package first.android.project;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

//Services müssen von Service erben. LocationListener wird für GPS benötigt.
public class standortabfrage extends Service implements LocationListener {
	
	public LocationManager locationManager;
	public static double latitude;
	public static double longitude;
	public static ArrayList<Errinnerung> eList = new ArrayList<Errinnerung>();
	public Errinnerung e = null;
	
	public standortabfrage () {
		super();
	}
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		//locationManagerNET = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		//locationManagerNET.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
		
		String address = "http://staatsfeind182.dyndns-remote.com:8080/AndroidServer/rest/errinnerung/errinnerung";
		String page;
		
		try {
			//Errinnerung von Web-Service holen
			page = JsonClient.connect(address);
			
			Log.v("Test page: ", page);
			//JSON-Objekt erstellen
			JSONObject jsonObject_original = new JSONObject(page);
			
			Log.v("Test JSON: ", jsonObject_original.get("errinnerungstext").toString());
			
			//Errinnerungsobjekt erstellen
			Errinnerung e = new Errinnerung(jsonObject_original.get("errinnerungstext").toString(), 
										    jsonObject_original.get("strasse").toString(),
										    Integer.valueOf(jsonObject_original.get("hausnummer").toString()),
										    Integer.valueOf(jsonObject_original.get("plz").toString()),
										    jsonObject_original.get("ort").toString(),
										    jsonObject_original.get("land").toString(),
										    Double.valueOf(jsonObject_original.get("latitude").toString()),
										    Double.valueOf(jsonObject_original.get("longitude").toString())
										   );
			eList.add(e);
							
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	
	public void onDestroy() {
		locationManager.removeUpdates(this);
		super.onDestroy();
	}
	
	//Funktion um festzulegen, was bei Loation_Change passiert
	public void onLocationChanged(Location loc) {

		loc.getLatitude();
		loc.getLongitude();
		String Text = "My current location is:  Latitud = " + loc.getLatitude() + "Longitud = " + loc.getLongitude();
	
		latitude = loc.getLatitude();
		longitude = loc.getLongitude();
		
		//Abgleich ob Koordinaten mit einer Errinnerung übereinstimmen, wenn ja dann NotificationIntent starten
		Iterator<Errinnerung> it = eList.iterator();
		while (it.hasNext()) {
			Log.i("drinnen", "drinnen");
			Errinnerung e = (Errinnerung) it.next();
			double eLattitude =  (double)Math.round(e.getLattitude()*100)/100;;
			double rLattitude =  (double)Math.round(latitude*100)/100;;
			double eLogitude =   (double)Math.round(e.getLogitude() *100)/100;
			double rLogitude =   (double)Math.round(longitude *100)/100;
			if (eLattitude == rLattitude && eLogitude == rLogitude) {
				notifiyGps(e);
			}
		}
	}

	public void onProviderDisabled(String provider)	{
		Toast.makeText( getApplicationContext(), "Gps deaktiviert", Toast.LENGTH_SHORT ).show();
	}


	public void onProviderEnabled(String provider)	{
		Toast.makeText( getApplicationContext(), "Gps aktiviert", Toast.LENGTH_SHORT).show();
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub	
	}

	public LocationManager getLocationManager() {
		return locationManager;
	}

	public void setLocationManager(LocationManager locationManager) {
		this.locationManager = locationManager;
	}
	
	public void update(Errinnerung e) {
		eList.add(e);
	}
	
	/**
	 * Notification Bar setzen
	 */
	public void notifiyGps(Errinnerung e) {
		final int LIST_UPDATE_NOTIFICATION = 100;
		Log.v("in gps", "Hello");
		
		//Instanz des NotificationManagers holen
		Context context = this.getApplicationContext();
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(NOTIFICATION_SERVICE);

		//Errinnerungstext holen
		String tickerText = e.errinnerungstext.toString();
		//Notification-Icon
		 Notification notif = new Notification(R.drawable.ic_launcher, tickerText,
		            System.currentTimeMillis());
		
		//Notification-Vibration
		 notif.defaults |= Notification.DEFAULT_VIBRATE;
	    //Notification-Sound
		 notif.defaults |= Notification.DEFAULT_SOUND;

		 //Intent für die Notification anlegen
		Intent notificationIntent = new Intent(context, MyFirstAndroidProjectActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,	notificationIntent, 0);

		String errinnerungstitel = getString(R.string.errinnerungstext, e.errinnerungstext.toString());
		
		Log.v("e:", tickerText);
		
		notif.setLatestEventInfo(context, errinnerungstitel, tickerText,	contentIntent);
		//notification feuern.
		notificationManager.notify(LIST_UPDATE_NOTIFICATION, notif);
		onDestroy();
	}	
}
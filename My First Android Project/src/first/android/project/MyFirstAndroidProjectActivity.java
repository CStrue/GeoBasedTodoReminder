package first.android.project;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

//von Activity erben
public class MyFirstAndroidProjectActivity<T> extends Activity {

	Button buttonEinstellungen;
	Button buttonNeueErrinnerung;
	private static final int GET_CODE = 0;
	public Errinnerung e;
	public ListView lv;
	public long itemId;
	MyOwAdapter adapter;
	Timer myTimer;

	private static final int HELLO_ID = 1;

	/**
	 * Called when the activity is first created.
	 * 
	 * @param <T>
	 */
	@Override
	public void onCreate(Bundle iCicle) {
		super.onCreate(iCicle);
		setContentView(R.layout.main);

		// start service GPS
		Log.v("Service noch nicht started", "service noch nicht gestartet");
		startService(new Intent(this, standortabfrage.class));
		Log.v("Service started", "service gestartet");

		buttonEinstellungen = (Button) findViewById(R.id.button1);
		buttonNeueErrinnerung = (Button) findViewById(R.id.button2);

		buttonEinstellungen.setOnClickListener(new OnClickListener() {
			//Intent wenn neue Errinnerung angelget werden soll
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClassName(getPackageName(), getPackageName()
						+ ".NeueErrinnerungActivity");

				// Trick - sonst gibts aus irgendeinem Grund nen Nullpointer
				// exception beim anlegen!
				e = new Errinnerung("", "", 0, 0, "", "", 0.00, 0.00);
				intent.putExtra("selected", e);
				//Intent starten und auf asynchrone Antwort warten - 
				//geht auch ohne Antwort, dann kommt man aber nicht mehr in die MainActivity bzw. kann kein Objekt zurückgeben
				startActivityForResult(intent, GET_CODE);
			}
		});

		//Settings-Button - nicht implementiert --> daher Verweiß auf neue Erinnerungs Activity
		buttonNeueErrinnerung.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClassName(getPackageName(), getPackageName()
						+ ".NeueErrinnerungActivity");
				startActivity(intent);

			}
		});
		
		//Erinnerungsliste mit Objekten des Services befüllen
		ArrayList<Errinnerung> errinnerungList = new ArrayList<Errinnerung>();
		errinnerungList = standortabfrage.eList;
		//adapter für ListView initialisieren
		adapter = new MyOwAdapter(this, R.layout.row, errinnerungList);
		lv = (ListView) findViewById(R.id.listView1);
		//adapter zu listView hinzufügen
		lv.setAdapter(adapter);
		//OnItemClickListener hinzufügen und mit Funktionalität befüllen
		lv.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//bei KLick auf Erinnerung die NeueErrinnerungActivity starten
				Intent intent = new Intent();
				intent.setClassName(getPackageName(), getPackageName()
						+ ".NeueErrinnerungActivity");
				//Das selektierte Item aus der Adapterliste raussuchen --> daher war es notwendig den eigenen Adapter zu schreiben
				//Default-Adapter kann nur String-Objekte verarbeiten, aber keine eigenen Datentypen
				e = (Errinnerung) lv.getAdapter().getItem(arg2);
				//Intent mit Erinnerungsobjekt befüllen --> hierfür wird putExtra() benötigt.
				intent.putExtra("selected", e);
				//Trick: Im Adapter wird das Objekt entfernt und bei speichern wieder hinzugefügt. Sonst ist das Updaten nicht möglich...
				adapter.remove(e);
				//Intent starten und auf ReturnCode warten --> Alternative ist startActivity, hat aber diverse Nachteile
				startActivityForResult(intent, GET_CODE);
			}
		});

	}

	/** Asynch recall empfangen von Neuer ErrinnerungsActivity */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == GET_CODE) {
			//Wenn der Result Code i.O. ist füge das veränderte Erinnerungsobjekt wieder zum Adapter hinzu.
			if (resultCode == RESULT_OK) {

				// Objekt aus dem Intent lese
				e = (Errinnerung) data.getParcelableExtra("data");
				adapter.add(e);
			} else {
			}
		}
	}
}
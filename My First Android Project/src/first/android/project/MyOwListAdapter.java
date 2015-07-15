package first.android.project;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/** Diese Klasse implementiert einen Adapter für die ListViw. Dies wird benötigt um die Errinnerungsobjekte in einer Android Listview darzustellen. */
class MyOwAdapter extends ArrayAdapter<Errinnerung> {

    private ArrayList<Errinnerung> items;
    private Context context;
	
	// COnstructor erstellen
    public MyOwAdapter(Context context, int textViewResourceId, ArrayList<Errinnerung> items) {
            super(context, textViewResourceId, items);
            this.items = items;
            this.context = context;
    }

	/** Die Funktion liefert das selektierte Errinnerungsobjekt der View, wenn es angeklickt wird*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.row, null);
            }
            Errinnerung e = items.get(position);
            if (e != null) {
                    TextView tt = (TextView) v.findViewById(R.id.toptext);
                    if (tt != null) {
                          tt.setText(e.getErrinnerungstext());                            
                    }
            }
            return v;
    }
    
    public void removeObject(Errinnerung e) {
    	items.remove(e);
    }
    
	//liefert liefert die Liste der Erinnerungen des Adapters
    public ArrayList getErrinnerungsList () {
    	return items;
    }
    
	//setzt die Liste der Errinnerungsobjekte der View.
    public void setErrinnerungsList(ArrayList<Errinnerung> eList) {
    	items = eList;
    }

}

package first.android.project;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/** Die Klasse implementiert das Parcelable Interface um bei Intents als Objekt verwendet werden zu kˆnnen.*/
public class Errinnerung implements Comparable<Errinnerung>, Parcelable{

	public String errinnerungstext = "";
	public String straﬂe = "";
	public int hausnummer = 0;
	public int plz = 0;
	public String ort ="";
	public String land = "";
	public double latitude;
	public double longitude;
	
	// WICHTIG!!! Parcelable protocol benˆtigt ein Parcelable.Creator...
	public static final Parcelable.Creator<Errinnerung> CREATOR = new Parcelable.Creator<Errinnerung>() {

		public Errinnerung createFromParcel(Parcel in) {
			return new Errinnerung(in);
		}

		public Errinnerung[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
	public Errinnerung (String Errinnerungstext, String Straﬂe, int Hausnummer, int Plz, String Ort, String Land, double Lattitude, double Logitude) {
		this.errinnerungstext = Errinnerungstext;
		this.straﬂe = Straﬂe;
		this.hausnummer = Hausnummer;
		this.plz = Plz;
		this.ort = Ort;
		this.land = Land;
		this.latitude = Lattitude;
		this.longitude = Logitude;
	}
	
	//Funktion muss implementiert werden, damit Parcelable Objekt gelesen werden kan
	public Errinnerung(Parcel in) {
		readFromParcel(in);
	}

	public String getErrinnerungstext() {
		return errinnerungstext;
	}

	public void setErrinnerungstext(String errinnerungstext) {
		this.errinnerungstext = errinnerungstext;
	}

	
	public String getStraﬂe() {
		return straﬂe;
	}

	public void setStraﬂe(String straﬂe) {
		this.straﬂe = straﬂe;
	}

	public int getHausnummer() {
		return hausnummer;
	}

	public void setHausnummer(int hausnummer) {
		this.hausnummer = hausnummer;
	}

	public int getPlz() {
		return plz;
	}

	public void setPlz(int plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public double getLattitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLogitude() {
		return longitude;
	}

	public void setLogitude(double Logitude) {
		this.longitude = Logitude;
	}
	
	
	public int compareTo(Errinnerung another) {
        return ((String)errinnerungstext.toLowerCase()).compareTo((String)another.errinnerungstext.toLowerCase());
    }

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	//Funktion muss implementiert werden, damit Parcelable Objekt geschrieben werden kˆnnen
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(errinnerungstext);
		dest.writeString(straﬂe);
		dest.writeInt(hausnummer);
		dest.writeInt(plz);
		dest.writeString(ort);
		dest.writeString(land);
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
	}
	
	//Funktion muss implementiert werden, damit Parcelable Objekt gelesen werden kan
	public void readFromParcel(Parcel in) {
		errinnerungstext = in.readString();
		straﬂe = in.readString();
		hausnummer = in.readInt();
		plz = in.readInt();
		ort = in.readString();
		land = in.readString();
		latitude = in.readDouble();
		longitude = in.readDouble();
	}
	
}

package com.avilyne.rest.resource;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class Serializer {
	
	Errinnerung errinnerung;
	
	public void serialize(Errinnerung errinnerung) {
		 String filename = "errinnerungen.xml";
		 XMLEncoder enc = null;
		    try
		    {
		      enc = new XMLEncoder( new FileOutputStream(filename) );
		      enc.writeObject( "version 1.0" );
		      enc.writeObject(errinnerung);
		    }
		    catch ( IOException e ) {
		      e.printStackTrace();
		    }
		    finally {
		      if ( enc != null )
		        enc.close();
		    }
	}
	
	public Errinnerung deserialize() {
		Errinnerung errinnerung = null; 
		String filename = "errinnerungen.xml";
		    // Serialize
		   
		 XMLDecoder dec = null;
		    try
		    {
		      dec = new XMLDecoder( new FileInputStream(filename) );
		      String version = (String) dec.readObject();
		      errinnerung  = (Errinnerung)   dec.readObject();
		      System.out.println(version);
		      System.out.println(errinnerung);
		    }
		    catch ( IOException e ) {
		      e.printStackTrace();
		    }
		    finally {
		      if ( dec != null )
		        dec.close();
		      
		    }
		    return errinnerung;
	}
	
}

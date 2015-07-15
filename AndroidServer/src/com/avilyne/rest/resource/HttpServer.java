package com.avilyne.rest.resource;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

//Path Variable setzen für den URL-Pfad
@Path("/errinnerung")
public class HttpServer {
	private final static String Errinnerungstext = "errinnerungstext";
	private final static String Strasse = "strasse";
	private final static String Hausnummer = "hausnummer";
	private final static String Plz = "plz";
	private final static String Ort = "ort";
	private final static String Land = "land";
	private final static String Latitude = "latitude";
	private final static String Longitude = "longitude";

	private Errinnerung e;
	private Errinnerung test = new Errinnerung("test1", "a", 2, 4453, "b", "c",
			0.1, 1.0);

	// Die @Context annotation erlaubt es verschiedene Objekte in diese Klasse zu induzieren
	@Context
	UriInfo uriInfo;

	// Request-Objekt um zum Beispiel die Sender-Adresse auszulesen.
	@Context
	Request request;

	// Get-Method Funktion um zu testen ob der Service läuft
	//@Produces definiert das auszugebende Datenformat
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String respondAsReady() {
		return "Demo service is ready!";

	}

	//Get-Methode um das ein Errinnerungsobjekt als JSON-File zu holen.
	@GET
	@Path("errinnerung")
	@Produces(MediaType.APPLICATION_JSON)
	public Errinnerung getSampleErrinnerung() {
		
		Serializer ser = new Serializer();
		Errinnerung e = ser.deserialize();
		
		return e;
	}

	// @Post-Method um Daten vom Client entgegen zu nehmen. @Consumes definiert, dass die Daten im JSON-Datenformat übergeben werden
	@POST
	@Path("post")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String postErrinnerung(Errinnerung errinnerungsParam) {

		System.out.println("wurde angesprochen");
		//Errinnerungsobjekt anlegen
		e = new Errinnerung(errinnerungsParam.errinnerungstext,
				errinnerungsParam.getStrasse(),
				Integer.valueOf(errinnerungsParam.hausnummer),
				Integer.valueOf(errinnerungsParam.getPlz()),
				errinnerungsParam.ort, errinnerungsParam.land,
				Double.valueOf(errinnerungsParam.latitude),
				Double.valueOf(errinnerungsParam.longitude));

		System.out.println("Errinnerung info: " + e.getErrinnerungstext());

		//Serializiren
		Serializer serializer = new Serializer();
		serializer.serialize(e);

		String result = "erfolgreich";
		return result;
	}
}
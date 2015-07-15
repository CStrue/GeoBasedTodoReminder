package first.android.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;


public class JsonClient {
	//Die Funktion erstellt einen DefaultHttpClient Objekt, anschließend ein HTTGet Objekt. 
	//Danach wird ein Get-Request ausgeführt und eine eine Webseite zurückgeliefert. Diese wird in einen String verwandelt.
	public static String connect(String url) throws Exception {

		HttpClient httpclient = new DefaultHttpClient();

		// Prepare a request object
		HttpGet httpget = new HttpGet(url);

		HttpGet getRequest = new HttpGet();

        getRequest.setURI(new URI(url));
        HttpResponse response = httpclient.execute(getRequest);
        
        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer sb = new StringBuffer("");
        String line = "";
        String NL = System.getProperty("line.separator");
        while ((line = in.readLine()) != null) {
            sb.append(line + NL);
        }
        in.close();
        String page = sb.toString();
    return page; 
}
	/** Die folgende Funktion führt einen HTTP-Post Request durch. Dabei wird ein JSON-Objekt an den Web-Service übergeben
	*/
	public static void httpPost(String url, JSONObject data, String objectname) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try {
			HttpParams params = new BasicHttpParams();
			StringEntity se = new StringEntity(data.toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			httpPost.setEntity(se);
			
			
			params.setParameter(objectname, data.toString());
			httpPost.setParams(params);
			
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			
			HttpResponse response = httpclient.execute(httpPost);
			
			Log.i("Post: ", "Post request, data: " + params.getParameter("errinnerung").toString());
			Log.i("HTTPClient: ", "Post request, data: " + response.getStatusLine().getStatusCode());
			
			if(response!=null){
                InputStream in = response.getEntity().getContent(); //Get the data in the entity

			}
	
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				httpPost.abort();
			}
	}
	
	/** ToDo httpDelete, HttpPut */
}



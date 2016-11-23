package jaehee_epcis_client.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;

import jaehee_epcis_client.configuration.EPCISConfiguration;

/**
* The EPCIS Client API is the program for Java programmed devices using EPCIS.
* First of all, please configure EPCISConfiguration.java file.
*
* @author  Jaehee Ha
* @version 2.0
* @since   2016.11.14 
*/

public class EPCISCaptureClient extends EPCISClient{
	
	
	private String m_uri = "";
	private String m_EPCIS_AC_Server_URL = "";
	private String m_EPCISname = "";
	private String m_Username = "";
	private String m_EPCIS_Clienttoken = "";
	private JSONObject m_httpBody;
	private String m_Event;
	
	public EPCISCaptureClient () {
		m_EPCIS_AC_Server_URL = EPCISConfiguration.EPCIS_AC_Server_URL;
		m_EPCISname = EPCISConfiguration.EPCISname;
		m_Username = EPCISConfiguration.Username;
		m_EPCIS_Clienttoken = EPCISConfiguration.EPCIS_Clienttoken;
		m_httpBody = new JSONObject();
		m_httpBody.put("token", m_EPCIS_Clienttoken);
		m_httpBody.put("epcisevent", "");
		setURI();
	}
	
	public EPCISCaptureClient (String a_EPCIS_AC_Server_URL, String a_EPCISname, String a_Username, String a_EPCIS_Clienttoken){
		m_EPCIS_AC_Server_URL = a_EPCIS_AC_Server_URL;
		m_EPCISname = a_EPCISname;
		m_Username = a_Username;
		m_EPCIS_Clienttoken = a_EPCIS_Clienttoken;
		m_httpBody = new JSONObject();
		m_httpBody.put("token", m_EPCIS_Clienttoken);
		m_httpBody.put("epcisevent", "");
		setURI();
	}
	
	public EPCISCaptureClient (String a_EPCIS_AC_Server_URL, String a_EPCISname, String a_Username, String a_EPCIS_Clienttoken, String a_Event){
		m_EPCIS_AC_Server_URL = a_EPCIS_AC_Server_URL;
		m_EPCISname = a_EPCISname;
		m_Username = a_Username;
		m_EPCIS_Clienttoken = a_EPCIS_Clienttoken;
		m_Event = a_Event;
		m_httpBody = new JSONObject();
		m_httpBody.put("token", m_EPCIS_Clienttoken);
		m_httpBody.put("epcisevent", m_Event);
		setURI();
	}
	
	public void setEvent (String a_Event)
	{
		if (a_Event == null)
			a_Event = "";
		m_Event = a_Event;
		m_httpBody.remove("epcisevent");
		m_httpBody.put("epcisevent", m_Event);
	}
	
	public String getEvent ()
	{
		return m_Event;
	}
	
	public void addEvent (String a_Event)
	{
		if (a_Event == null)
			a_Event = "";
		m_Event = m_Event + a_Event;
		m_httpBody.remove("epcisevent");
		m_httpBody.put("epcisevent", m_Event);
	}
	
	public String doCapture()
	{
		return httpPost();
	}
	
	private void setURI()
	{
		this.m_uri = "http://"+m_EPCIS_AC_Server_URL+"/captureepcis/"+m_EPCISname+"/user/"+m_Username+"/apicapture";
	}
	
	
	/**
	 * Do a HTTT POST request and return the status code.
	 * 
	 * @return String, when request was successful
	 * @throws RuntimeException
	 *             When status code is not HTTP_CREATED
	 */
	private String httpPost() throws RuntimeException {	
		HttpClient httpClient = HttpClientBuilder.create().build();
		StringBuilder result = new StringBuilder();
		
		try {
			HttpPost postRequest = new HttpPost(m_uri);
			postRequest.addHeader(HttpHeaders.AUTHORIZATION, "true");
	
			StringEntity ent = new StringEntity(m_httpBody.toString());
			ent.setContentType(ContentType.APPLICATION_JSON.toString());
			
			postRequest.setEntity(ent);
			
			HttpResponse response = httpClient.execute(postRequest);
			
			int statusCode = response.getStatusLine().getStatusCode();			
			if (statusCode != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("ERROR: HTTP code: " + statusCode);
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			
			String line = "";
			while((line = br.readLine()) != null){
				result.append(line);
			}
			br.close();
	
		} catch (IOException e) {
			e.printStackTrace();			
		} finally {
			
		}
		
		return result.toString();
	}
}

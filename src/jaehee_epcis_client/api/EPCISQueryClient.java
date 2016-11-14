package jaehee_epcis_client.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import jaehee_epcis_client.configuration.EPCISConfiguration;

/**
* The EPCIS Client API is the program for Java programmed devices using EPCIS.
* First of all, please configure EPCISConfiguration.java file.
*
* @author  Jaehee Ha
* @version 2.0
* @since   2016.11.14 
*/

public class EPCISQueryClient extends EPCISClient{
	
	private String m_uri = "";
	private String m_EPCIS_AC_Server_URL = "";
	private String m_EPCISname = "";
	private String m_Username = "";
	private String m_EPCIS_Clienttoken = "";
	private String m_Query = "";
	
	public EPCISQueryClient (){
		m_EPCIS_AC_Server_URL = EPCISConfiguration.EPCIS_AC_Server_URL;
		m_EPCISname = EPCISConfiguration.EPCISname;
		m_Username = EPCISConfiguration.Username;
		m_EPCIS_Clienttoken = EPCISConfiguration.EPCIS_Clienttoken;
		m_Query = "";
		setURI();
	}
	
	public EPCISQueryClient (String a_EPCIS_AC_Server_URL, String a_EPCISname, String a_Username, String a_EPCIS_Clienttoken){
		m_EPCIS_AC_Server_URL = a_EPCIS_AC_Server_URL;
		m_EPCISname = a_EPCISname;
		m_Username = a_Username;
		m_EPCIS_Clienttoken = a_EPCIS_Clienttoken;
		m_Query = "";
		setURI();
	}
	
	public EPCISQueryClient (String a_EPCIS_AC_Server_URL, String a_EPCISname, String a_Username, String a_EPCIS_Clienttoken, String a_Query){
		m_EPCIS_AC_Server_URL = a_EPCIS_AC_Server_URL;
		m_EPCISname = a_EPCISname;
		m_Username = a_Username;
		m_EPCIS_Clienttoken = a_EPCIS_Clienttoken;
		m_Query = a_Query;
		setURI();
	}
	
	public void setQuery (String a_Query)
	{
		m_Query = a_Query;
		setURI();
	}
	
	public String getQuery ()
	{
		return m_Query;
	}
	
	public void addQuery (String a_Query)
	{
		m_Query = m_Query + "&" + a_Query;
		setURI();
	}
	
	public String doQuery()
	{
		return httpGet();
	}
	
	private void setURI()
	{
		this.m_uri = "http://"+m_EPCIS_AC_Server_URL+"/qryepcis/"+m_EPCISname+"/user/"+m_Username+"/token/"+m_EPCIS_Clienttoken+"/apiquery?"+m_Query;
	}
	
	/**
	 * Do a HTTP GET request and return the result.
	 * 
	 * @return Response (content of the received entity)
	 * @throws RuntimeException
	 *             When status code is not HTTP_OK
	 */
	private String httpGet() throws RuntimeException {

		StringBuilder result = new StringBuilder();
		HttpClient httpClient = HttpClientBuilder.create().build();
	
		try {
			HttpGet getRequest = new HttpGet(m_uri);	
			getRequest.addHeader(HttpHeaders.ACCEPT, "applicatoin/json");				

			getRequest.addHeader(HttpHeaders.AUTHORIZATION, "true");
			HttpResponse response = httpClient.execute(getRequest);
			
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
	
		} catch (ConnectException e) {
			System.err.println("ConnectException: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
		
		return result.toString();
	}
}

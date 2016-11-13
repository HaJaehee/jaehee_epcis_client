package jaehee_epcis_client;

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

public class EPCISQueryClient extends EPCISClient{
	/**
	 * Do a HTTP GET request and return the result.
	 * 
	 * @param uri
	 *            URI for HTTP GET request
	 * @param headerAccept
	 *            Empty or header accept
	 * @param headerAuthorization
	 *            Empty or authorization header
	 * @return Response (content of the received entity)
	 * @throws RuntimeException
	 *             When status code is not HTTP_OK
	 */
	public static String httpGet(String uri, String headerAccept) throws RuntimeException {

		StringBuilder result = new StringBuilder();
		HttpClient httpClient = HttpClientBuilder.create().build();
	
		try {
			HttpGet getRequest = new HttpGet(uri);
			if (!headerAccept.isEmpty()) {
				getRequest.addHeader(HttpHeaders.ACCEPT, headerAccept);				
			}
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

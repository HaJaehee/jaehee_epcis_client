package jaehee_epcis_client;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class EPCISCaptureClient extends EPCISClient{
	/**
	 * Do a HTTT POST request and return the status code.
	 * 
	 * @param uri
	 *            URI for HTTP POST request
	 * @param headerAuthorization
	 *            Empty or authorization header
	 * @param stringEntity
	 *            String entity which will be posted
	 * @param entityConentType
	 *            Empty or entity content type
	 * @return True, when request was successful
	 * @throws RuntimeException
	 *             When status code is not HTTP_CREATED
	 */
	public static boolean httpPost(String uri, String stringEntity, ContentType entityConentType) throws RuntimeException {	
		HttpClient httpClient = HttpClientBuilder.create().build();
		
		try {
			HttpPost postRequest = new HttpPost(uri);
			postRequest.addHeader(HttpHeaders.AUTHORIZATION, "true");
	
			StringEntity ent = new StringEntity(stringEntity);
			if (entityConentType != null) {
				ent.setContentType(entityConentType.toString());
			}
			postRequest.setEntity(ent);
			
			HttpResponse response = httpClient.execute(postRequest);
			
			int statusCode = response.getStatusLine().getStatusCode();			
			if (statusCode != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("ERROR: HTTP code: " + statusCode);
			}
			
			return true;
	
		} catch (IOException e) {
			e.printStackTrace();			
		} finally {
			
		}
		
		return false;
	}
}

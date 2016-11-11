package jaehee_epcis_client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class EPCISClient{
    public static void main(String[] args) {
    	JSONObject auth = new JSONObject();
    	auth.put("email", "root");
    	auth.put("token","0db038aaef9261c0846d9d2aae68c07ce9e0800a");
    	String url = "http://winsgkwogml.iptime.org:3000/qryepcis/myepcis";
    	http(url, auth);
    }

    public static HttpResponse http(String url, JSONObject auth) {

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
        	
        	
            HttpPost request = new HttpPost(url);
            
            StringEntity params = new StringEntity(auth.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            System.out.println(request.getEntity().toString());
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            try {
                JSONParser parser = new JSONParser();
                Object resultObject = parser.parse(json);

                if (resultObject instanceof JSONArray) {
                    JSONArray array=(JSONArray)resultObject;
                    for (Object object : array) {
                        JSONObject obj =(JSONObject)object;
                        System.out.println(obj.toString());
                    }

                }else if (resultObject instanceof JSONObject) {
                    JSONObject obj =(JSONObject)resultObject;
                    System.out.println(obj.toString());
                }

            } catch (Exception e) {
                // TODO: handle exception
            }

        } catch (IOException ex) {
        }
        return null;
    }
}

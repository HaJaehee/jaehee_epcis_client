package jaehee_epcis_client;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.json.simple.JSONObject;

public class Main {
	public static void main(String[] args) {
		EPCISCaptureClient capture = new EPCISCaptureClient();
		EPCISQueryClient query = new EPCISQueryClient();
		
		String ACCESS_TOKEN = "1222403E47D8647DD0918528910B4389B92D99D9";
		String geturl = "http://winsgkwogml.iptime.org:3000/qryepcis/myepcis/user/root/token/"+ACCESS_TOKEN+"/apiquery?";
		String epcisquery = "";
		geturl = geturl + epcisquery;
	
		String headerAccept = "applicatoin/json";
//		String result = query.httpGet(geturl, headerAccept);
//		System.out.println(result);

		String epcisevent = "";
		JSONObject httpBody = new JSONObject();
		httpBody.put("token", ACCESS_TOKEN);
		httpBody.put("epcisevent", epcisevent);
		
		String posturl = "http://winsgkwogml.iptime.org:3000/captureepcis/myepcis/user/root";
		capture.httpPost(posturl, httpBody.toString(), ContentType.APPLICATION_JSON);	
		


	}
}

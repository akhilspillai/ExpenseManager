package com.trip.expensemanager;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class GCMUtil {
	

	private static final String API_KEY = "AIzaSyAIG5GBeL2dYuoN5525AqOmHydvAoW7_LE";

	private static final Logger log = Logger.getLogger(GCMUtil.class.getName());


	public void addToToSync(String message, Long lngId, Long userId, Long changerId) throws IOException {
		ToSync toSync=new ToSync();
		toSync.setSyncItem(lngId);
		toSync.setSyncType(message);
		toSync.setUserId(userId);
		toSync.setChangerId(changerId);
		ToSyncEndpoint toSyncEndpoint=new ToSyncEndpoint();
		toSyncEndpoint.insertToSync(toSync);
	}

	public void doSendViaGcm(JSONArray jsonArr) throws IOException, JSONException {
		String json ="{}";
		//		jsonArr.put("APA91bFgxjBiEAGTAUfEDUKNTWQbgImWqGoafiN1sjmSvaLF7v0x8IAFUNcCvOXpI3_VuJfLEOFpoxapCa6h37A1NJckgtVA3_kl3BXvLiR3Mf9aEJptrR6QDOWOR44fXHrLk1FalqMe-q2xdpic-0iCBdUWO7bdtg");
		if(jsonArr.length()!=0){
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("registration_ids", jsonArr);

			json=jsonObj.toString();
			log.info("request "+json);
			URL url = new URL("https://android.googleapis.com/gcm/send");
			HTTPRequest request = new HTTPRequest(url, HTTPMethod.POST);
			request.addHeader(new HTTPHeader("Content-Type","application/json")); 
			request.addHeader(new HTTPHeader("Authorization", "key="+API_KEY));
			request.setPayload(json.getBytes("UTF-8"));
			HTTPResponse response = URLFetchServiceFactory.getURLFetchService().fetch(request);
			log.info("Content "+new String(response.getContent()));
		} else{
			log.info("Array is empty");
		}
	}
}

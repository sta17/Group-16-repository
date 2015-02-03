/*@(#) MIME.java 1.1 2015-1-27
 *
 *This class details the communication
 *between the database and the Application.
 *A HTTPClient is created and sends A HTTP Post
 *in the form of a JSON Object.
 *
 *This class does not perform exactly as intended
 *and is missing functionality.
 *
 *
 * Copyright (c) 2015 Aberystwyth University.
 * All rights reserved.
 *
 *@Author Kieran Stone
 *@Author Stoyan Vatov
 *@Since 1.1 Initial Experimentation
 *@Version 1.2
 *@see DataBaseHelper
 *
 *
 */
package uk.ac.aber.dcs.cs221.group16.main.databasemime;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import uk.ac.aber.dcs.cs221.group16.main.recordpackage.Record;
import android.provider.MediaStore.Files;
import android.util.Base64;

import java.nio.*;

/*
 * The code here is heavily influenced by but substantially different from the code located at this address http://stackoverflow.com/questions/3027066/how-to-send-a-json-object-over-request-with-android
 *
 * The implemented code here does not account for the sending of images
 * In addition the code reads from the Record class and not the Local Android SQL lite database that we have created.
 *
 */
public class MIME {
	// Create the HTTPClient to make the post request
	HttpClient client = new DefaultHttpClient();
	HttpResponse response;
	JSONObject json = new JSONObject();
	Record record = new Record();
	{
		try {
			// URL with the post data inside
			HttpPost post = new HttpPost("192.60.15.32");
			// Stuff in the post that will be sent to the server
			json.put("Record id", record.getRecordname());
			json.put("Plant Name", record.getPlantCommon());
			json.put("Plant latin name", record.getPlantLatin());
			json.put("Comment", record.getComment());
			json.put("DAFOR", record.getDAFOR());
			json.put("Latitude", record.getLatitude());
			json.put("Longitude", record.getLongitude());
			// byte[] specimenimgpath = Files.readAllBytes(path1);
			// byte[] specimenscenepath=Files.readAllBytes(path2);
			// String stringImage = new String(Base64.encode(specimenimgpath));
			// String stringImage2= new String(Base64.encode(SceneIMGPATH));
			json.put("Specimen image path", record.getSpecimenIMGPath());
			json.put("Scene image path", record.getSceneIMGPath());
			// Passes results into A Stringbuilder/entity
			StringEntity se = new StringEntity(json.toString());
			// Sets a request header so the thing receiving the request will be
			// able to process it
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			// Sets the post request as the resulting String
			post.setEntity(se);
			response = client.execute(post);
			// Check to see if there is a response from the server
			if (response != null) {
				InputStream input = response.getEntity().getContent();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Sorry could not find a connection");
		}// End try/catch
	}
}// end Class
package uk.ac.aber.dcs.cs22120.group16.utilities;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
 



import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
 



import com.example.botanistcompanionapp.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
 
/**
 * from: https://vikaskanani.wordpress.com/2011/01/11/android-upload-image-or-file-using-http-post-multi-part/
 * 
 * @author Steven
 *
 */
public class FileUploadV2 extends Activity {
    Bitmap bm;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_database);
        try {
            // bm = BitmapFactory.decodeResource(getResources(),
            // R.drawable.forest);
            bm = BitmapFactory.decodeFile("/sdcard/DCIM/forest.png");
            executeMultipartPost();
        } catch (Exception e) {
            Log.e(e.getClass().getName(), e.getMessage());
        }
    }
 
    public void executeMultipartPost() throws Exception {
        try {
        	
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bm.compress(CompressFormat.JPEG, 75, bos);
            byte[] data = bos.toByteArray();
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(
                    "INTERNET URL GOES HERE, DESTINATION!");
            
            ByteArrayBody bab = new ByteArrayBody(data, "forest.jpg");
            // File file= new File("/mnt/sdcard/forest.png");
            // FileBody bin = new FileBody(file);
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            
            reqEntity.addPart("uploaded", bab);
            reqEntity.addPart("photoCaption", new StringBody("sfsdfsdf"));
            postRequest.setEntity(reqEntity);
            
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();        

            /* example for setting a HttpMultipartMode */
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            /* example for adding an image part */
            //FileBody fileBody = new FileBody(image); //image should be a String
            //builder.addPart("my_file", fileBody); 
            //and so on

            //After you're done with the building, you can get the built HttpEntity by invoking the MultipartEntityBuilder#build() method:

            //HttpEntity entity = builder.build();
            
            HttpResponse response = httpClient.execute(postRequest);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent(), "UTF-8"));
            String sResponse;
            StringBuilder s = new StringBuilder();
 
            while ((sResponse = reader.readLine()) != null) {
                s = s.append(sResponse);
            }
            System.out.println("Response: " + s);
        } catch (Exception e) {
            // handle exception here
            Log.e(e.getClass().getName(), e.getMessage());
        }
    }
}